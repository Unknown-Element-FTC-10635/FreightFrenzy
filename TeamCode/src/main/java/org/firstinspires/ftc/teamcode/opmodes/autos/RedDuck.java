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
import org.firstinspires.ftc.teamcode.commandgroup.Reset;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCube;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commands.RetractLift;
import org.firstinspires.ftc.teamcode.commands.SpinCarouselAuto;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.util.Webcam1;


@Autonomous(group = "Red")
public class RedDuck extends CommandOpMode {
    private Webcam1 webcam;
    private int elementPosition = 2;

    private SampleMecanumDrive drive;
    private DuckWheelSubsystem duck;
    private HorizontalLiftSubsystem horizontalLift;
    private VerticalLiftSubsystem verticalLift;
    private IntakeSubsystem intake;
    private LimitSwitchSubsystem topLimit;

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
        topLimit = new LimitSwitchSubsystem(hardwareMap, "topLimit");

        drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addLine("Creating Paths");
        telemetry.update();

        Pose2d start = new Pose2d(-36.0, -62, Math.toRadians(90));
        drive.setPoseEstimate(start);

        TrajectorySequence toDuck = drive.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineTo(new Vector2d(-38, -50))
                .setReversed(false)
                .turn(-Math.toRadians(90))
                .back(5)
                .lineToLinearHeading(new Pose2d(-58, -55, 0))
                .waitSeconds(1)
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-60, -35))
                .lineTo(new Vector2d(-50, -25))
                .build();

        TrajectorySequence approachHub = drive.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-31, -24))
                .build();

        TrajectorySequence toSquare = drive.trajectorySequenceBuilder(approachHub.end())
                .lineTo(new Vector2d(-45, -22))
                .lineTo(new Vector2d(-61, -34))
                .strafeRight(3)
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
                        new Reset(verticalLift, horizontalLift),
                        new InstantCommand(() -> intake.in(0.2)),
                        new FollowTrajectoryCommand(drive, toDuck),
                        new ParallelDeadlineGroup(
                                new WaitCommand(4000),
                                new SpinCarouselAuto(duck, false)
                        ),
                        new FollowTrajectoryCommand(drive, toHub),
                        new ParallelCommandGroup(
                                new PickLevel(elementPosition, verticalLift, horizontalLift, intake),
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new FollowTrajectoryCommand(drive, approachHub)
                                )
                        ),
                        new ParallelRaceGroup(
                                new WaitCommand(2000),
                                new OuttakeCube(intake)
                        ),
                        new ParallelCommandGroup(
                                new FollowTrajectoryCommand(drive, toSquare),
                                new RetractLift(horizontalLift, topLimit)
                        )
                )
        );

        register(verticalLift, horizontalLift, intake, duck);
    }

}
