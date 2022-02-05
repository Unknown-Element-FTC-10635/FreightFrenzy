package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commandgroup.DeliverToBottomLevel;
import org.firstinspires.ftc.teamcode.commandgroup.DeliverToMiddleLevel;
import org.firstinspires.ftc.teamcode.commandgroup.DeliverToTopLevel;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.SpinCarousel;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Lift;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;


@Autonomous
public class BlueDuck extends CommandOpMode {
    private Webcam1 webcam;
    private int elementPosition = 2;

    private SampleMecanumDrive drive;
    private DuckWheelSubsystem duck;
    private HorizontalLiftSubsystem horizontalLift;
    private VerticalLiftSubsystem verticalLift;
    private IntakeSubsystem intake;

    @Override
    public void initialize() {
        telemetry.addLine("Creating Subsystems");
        telemetry.update();

        webcam = new Webcam1(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);
        duck = new DuckWheelSubsystem(hardwareMap);
        horizontalLift = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        verticalLift = new VerticalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);

        telemetry.addLine("Creating Paths");
        telemetry.update();

        Pose2d start = new Pose2d(-36.0, 62, Math.toRadians(270));
        drive.setPoseEstimate(start);

        TrajectorySequence toDuck = drive.trajectorySequenceBuilder(start)
                .lineTo(new Vector2d(-50, 50))
                .lineTo(new Vector2d(-63, 55))
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-63, 40))
                .lineTo(new Vector2d(-50, 25))
                .turn(Math.toRadians(90))

                // RAISE LIFT SOMEHOW

                .lineTo(new Vector2d(-33, 25))

                .build();

        TrajectorySequence toSquare = drive.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-59, 40))
                .build();

        telemetry.addLine("Starting Webcam");
        telemetry.update();

        telemetry.addLine("Ready to Start");
        telemetry.update();

        webcam.startTeamelementColor();

        waitForStart();

        webcam.stop();

        telemetry.addLine("Scheduling Tasks");
        telemetry.update();

        schedule(
            new SequentialCommandGroup(
                new FollowTrajectoryCommand(drive, toDuck),
                new ParallelDeadlineGroup(
                    new WaitCommand(2000),
                    new SpinCarousel(duck, true)
                ),
                new FollowTrajectoryCommand(drive, toHub),
                new FollowTrajectoryCommand(drive, toSquare)
            )
        );
    }
}
