package org.firstinspires.ftc.teamcode.commandgroup;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCube;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class CycleWarehouse extends SequentialCommandGroup {
    public CycleWarehouse(SampleMecanumDrive drive, VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal,
                                IntakeSubsystem intake, Pose2d start, boolean red) {
        int direction = 1;
        if (red) {
            direction = -1;
        }

        TrajectorySequence toWarehouse = drive.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(10, 68 * direction, 0))
                .build();

        TrajectorySequence throughGap = drive.trajectorySequenceBuilder(toWarehouse.end())
                .lineTo(new Vector2d(45, 68 * direction))
                .waitSeconds(1.5)
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(throughGap.end())
                .setReversed(true)
                .lineTo(new Vector2d(0, 68 * direction))
                .lineTo(new Vector2d(-12, 65 * direction))
                .setReversed(false)
                .turn(-Math.toRadians(90) * direction)
                .forward(17)
                .build();

        addCommands(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new FollowTrajectoryCommand(drive, toWarehouse),
                                new FollowTrajectoryCommand(drive, throughGap)
                        )

                        // TODO: TO GROUND
                ),
                new ParallelRaceGroup(
                        new WaitCommand(1500),
                        new IntakeCube(intake)
                ),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new FollowTrajectoryCommand(drive, throughGap),
                                new FollowTrajectoryCommand(drive, toHub)
                        )
                ),
                new DeliverToTopLevel(vertical, horizontal),
                new ParallelRaceGroup(
                        new WaitCommand(1500),
                        new OuttakeCube(intake)
                )
        );
        addRequirements(vertical, horizontal, intake);
    }
}
