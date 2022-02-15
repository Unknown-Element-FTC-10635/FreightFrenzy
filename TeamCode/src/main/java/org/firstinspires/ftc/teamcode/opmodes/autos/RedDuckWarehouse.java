package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
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
import org.firstinspires.ftc.teamcode.commands.SpinCarousel;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

@Autonomous(group = "Red")
public class RedDuckWarehouse extends CommandOpMode {
    private Webcam1 webcam;
    private int elementPosition = -1;

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

        // Energize the tape servos so they don't move
        Servo tapeYaw = hardwareMap.get(Servo.class, "tapeYaw");
        Servo tapePitch = hardwareMap.get(Servo.class, "tapePitch");

        duck = new DuckWheelSubsystem(hardwareMap);
        horizontalLift = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        verticalLift = new VerticalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addLine("Creating Paths");
        telemetry.update();

        Pose2d start = new Pose2d(-36.0, -62, Math.toRadians(90));
        drive.setPoseEstimate(start);

        TrajectorySequence toDuck = drive.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineTo(new Vector2d(-38, -50))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-60, -55, 0))
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-60, -35))
                .lineTo(new Vector2d(-50, -25))
                .build();

        TrajectorySequence approachHub = drive.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-31, -24))
                .build();

        TrajectorySequence toWarehouse = drive.trajectorySequenceBuilder(approachHub.end())
                .lineTo(new Vector2d(-50, -25))
                .lineTo(new Vector2d(-60, -35))
                .lineToLinearHeading(new Pose2d(8, -70, -Math.toRadians(15)))
                .lineTo(new Vector2d(50, -70))
                .build();

        telemetry.addLine("Starting Webcam");
        telemetry.update();

        webcam.startTeamelementColor();

        telemetry.addLine("Scheduling Tasks");
        telemetry.update();

        telemetry.addLine("Ready to Start");
        telemetry.update();

        waitForStart();

        tapeYaw.setPosition(0.25);
        tapePitch.setPosition(0.5);

        elementPosition = webcam.getElementPosition();
        telemetry.addData("Going to position", elementPosition);
        telemetry.update();

        schedule(
            new SequentialCommandGroup(
                new InstantCommand(() -> webcam.stop()),
                new ParallelRaceGroup(
                        new WaitCommand(250),
                        new IntakeCube(intake)
                ),
                new FollowTrajectoryCommand(drive, toDuck),
                new ParallelDeadlineGroup(
                        new WaitCommand(3000),
                        new SpinCarousel(duck, false)
                ),
                new FollowTrajectoryCommand(drive, toHub),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, approachHub),
                        new PickLevel(elementPosition, verticalLift, horizontalLift, intake)
                ),
                new ParallelRaceGroup(
                        new WaitCommand(2000),
                        new OuttakeCube(intake)
                ),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, toWarehouse),
                        new ReturnLift(verticalLift, horizontalLift)
                )
            )
        );

        register(verticalLift, horizontalLift, intake, duck);
    }
}
