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

@Autonomous(group = "Blue")
public class BlueDuckWarehouse extends CommandOpMode {
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

        Pose2d start = new Pose2d(-36.0, 62, Math.toRadians(270));
        drive.setPoseEstimate(start);

        TrajectorySequence toDuck = drive.trajectorySequenceBuilder(start)
                .lineTo(new Vector2d(-50, 50))
                .lineTo(new Vector2d(-59, 54))
                .waitSeconds(1)
                .build();

        TrajectorySequence toHub = drive.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-63, 40))
                .lineTo(new Vector2d(-55, 24))
                .turn(Math.toRadians(90))
                .build();

        TrajectorySequence approachHub = drive.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-32, 24))
                .build();

        TrajectorySequence toWarehouse = drive.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-55, 25))
                .lineTo(new Vector2d(-60, 40))
                .strafeLeft(10)
                .lineToLinearHeading(new Pose2d(8, 70, Math.toRadians(10)))
                .lineTo(new Vector2d(50, 70))
                .build();

        telemetry.addLine("Starting Webcam");
        telemetry.update();

        webcam.startTeamelementColor();

        telemetry.addLine("Ready to Start");
        telemetry.update();

        waitForStart();

        tapeYaw.setPosition(0.25);
        tapePitch.setPosition(0.7);

        elementPosition = webcam.getElementPosition();
        telemetry.addData("Going to position", elementPosition);

        telemetry.addLine("Scheduling Tasks");
        telemetry.update();

        schedule(
                new SequentialCommandGroup(
                        new InstantCommand(() -> webcam.stop()),
                        new Reset(verticalLift, horizontalLift),
                        new InstantCommand(() -> intake.in(0.2)),
                        new FollowTrajectoryCommand(drive, toDuck),
                        new ParallelDeadlineGroup(
                                new WaitCommand(4000),
                                new SpinCarouselAuto(duck, true)
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
                        new InstantCommand(() -> verticalLift.releaseBrakes()),
                        new ParallelCommandGroup(
                                new FollowTrajectoryCommand(drive, toWarehouse),
                                new RetractLift(horizontalLift, topLimit)
                        )
                )
        );

        register(verticalLift, horizontalLift, intake, duck);
    }

}
