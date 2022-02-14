package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.commandgroup.PickLevel;
import org.firstinspires.ftc.teamcode.commandgroup.ReturnLift;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCube;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

@Autonomous
public class RedWarehouse extends CommandOpMode {
    private Webcam1 webcam;
    private int elementPosition = -1;

    private SampleMecanumDrive drive;
    private HorizontalLiftSubsystem horizontalLift;
    private VerticalLiftSubsystem verticalLift;
    private IntakeSubsystem intake;

    Servo tapeYaw, tapePitch;

    @Override
    public void initialize() {
        telemetry.addLine("Creating Subsystems");
        telemetry.update();

        webcam = new Webcam1(hardwareMap);

        // Energize the tape servos so they don't move
        tapeYaw = hardwareMap.servo.get("tapeYaw");
        tapePitch = hardwareMap.servo.get("tapePitch");

        horizontalLift = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        verticalLift = new VerticalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addLine("Creating Paths");
        telemetry.update();

        Pose2d start = new Pose2d(13, -62, Math.toRadians(90));
        drive.setPoseEstimate(start);

        TrajectorySequence initialToHub = drive.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(2, -38), Math.toRadians(140))
                .build();

        /*
        TrajectorySequence toWarehouse = drive.trajectorySequenceBuilder(initialToHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(10, -67, 0))
                .build();

        TrajectorySequence throughGap = drive.trajectorySequenceBuilder(toWarehouse.end())
                .lineTo(new Vector2d(45, -67))
                .waitSeconds(1.5)
                .build();

        TrajectorySequence returnToHub = drive.trajectorySequenceBuilder(throughGap.end())
                .setReversed(true)
                .lineTo(new Vector2d(0, -67))
                .lineTo(new Vector2d(-12, -65))
                .setReversed(false)
                .turn(Math.toRadians(90))
                .forward(17)
                .build(); */

        TrajectorySequence finalToWarehouse = drive.trajectorySequenceBuilder(initialToHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(8, -68, -Math.toRadians(15)))
                .lineTo(new Vector2d(45, -68))
                .strafeLeft(25)
                .build();

        telemetry.addLine("Starting Webcam");
        telemetry.update();

        webcam.startTeamelementColor();

        telemetry.addLine("Ready to Start");
        telemetry.update();

        waitForStart();

        tapeYaw.setPosition(0.25);
        tapePitch.setPosition(0.5);

        elementPosition = webcam.getElementPosition();
        telemetry.addData("Going to position", elementPosition);

        telemetry.addLine("Scheduling Tasks");
        telemetry.update();

        schedule(
                new SequentialCommandGroup(
                        new InstantCommand(() -> webcam.stop()),
                        new ParallelRaceGroup(
                                new WaitCommand(250),
                                new IntakeCube(intake)
                        ),
                        new ParallelCommandGroup(
                                new FollowTrajectoryCommand(drive, initialToHub),
                                new PickLevel(elementPosition, verticalLift, horizontalLift, intake)
                        ),
                        new ParallelRaceGroup(
                                new WaitCommand(2000),
                                new OuttakeCube(intake)
                        ),
                        new ParallelCommandGroup(
                                new FollowTrajectoryCommand(drive, finalToWarehouse),
                                new ReturnLift(verticalLift, horizontalLift)
                        )
                )
        );

        register(verticalLift, horizontalLift, intake);
    }
}
