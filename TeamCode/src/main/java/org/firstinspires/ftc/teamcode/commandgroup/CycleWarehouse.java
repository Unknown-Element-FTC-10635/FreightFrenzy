package org.firstinspires.ftc.teamcode.commandgroup;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCube;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class CycleWarehouse extends SequentialCommandGroup {

    private final int direction;

    public CycleWarehouse(SampleMecanumDrive drive, VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal,
                          IntakeSubsystem intake, LimitSwitchSubsystem liftSwitch, Pose2d start, boolean red) {
        if (red) {
            direction = -1;
        } else {
            direction = 1;
        }

        TrajectorySequence toWarehouse = drive.trajectorySequenceBuilder(start)
                .back(20)
                .turn(Math.toRadians(90))
                .lineTo(new Vector2d(0, 60))
                .strafeLeft(7)
                .build();

        TrajectorySequence throughGap = drive.trajectorySequenceBuilder(toWarehouse.end())
                .forward(40)
                //.lineToLinearHeading(new Pose2d(45, 68 * direction))
                //.turn(Math.toRadians(cycles * -direction))
                //.waitSeconds(1.5)
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(throughGap.end())
                .setReversed(true)
                .strafeLeft(5)
                .lineToLinearHeading(new Pose2d(0, 68 * direction, 0))
                //.lineTo(new Vector2d(-12, 68 * direction))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-18, 55, Math.toRadians(270)))
                .forward(7)
                //.turn(-Math.toRadians(90) * direction)
                //.forward(17)
                .build();

        addCommands(
                new InstantCommand(() -> vertical.enableBrakes()),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, toWarehouse),
                        new SequentialCommandGroup(
                                new WaitCommand(250),
                                new ToGround(vertical, horizontal, liftSwitch)
                        )
                ),
                new InstantCommand(() -> drive.setPoseEstimate(new Pose2d(0, 68 * direction))),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, throughGap),
                        new ParallelRaceGroup(
                                new WaitCommand(2000),
                                new IntakeCube(intake, drive)
                        )
                ),
                new InstantCommand(() -> intake.in(0.2)),
                //new FollowTrajectoryCommand(drive, toExit),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, toHub),
                        new RaiseLift(vertical, VerticalLiftSubsystem.VerticalLevel.CycleTop, 0.5)
                ),
                new ParallelRaceGroup(
                        new WaitCommand(750),
                        new OuttakeCube(intake)
                )
        );
        addRequirements(vertical, horizontal, intake);
    }
}
