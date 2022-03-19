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
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCube;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commandgroup.Reset;
import org.firstinspires.ftc.teamcode.commands.RetractLift;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.util.Webcam1;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

@Autonomous(group = "Blue", name = "Blue Warehouse")
public class BlueWarehouseTurbo extends CommandOpMode {
    private Webcam1 webcam;
    private int elementPosition = 2;

    private SampleMecanumDrive drive;
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

        horizontalLift = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        verticalLift = new VerticalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);
        topLimit = new LimitSwitchSubsystem(hardwareMap, "topLimit");

        drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addLine("Creating Paths");
        telemetry.update();

        Pose2d start = new Pose2d(13, 62, Math.toRadians(270));
        drive.setPoseEstimate(start);

        TrajectorySequence initialToHub = drive.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(2, 38), Math.toRadians(220))
                .build();

        TrajectorySequence finalToWarehouse = drive.trajectorySequenceBuilder(initialToHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(8, 68, Math.toRadians(15)))
                .lineTo(new Vector2d(45, 68))
                .strafeRight(25)
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
                new ParallelCommandGroup(
                        new PickLevel(elementPosition, verticalLift, horizontalLift, intake),
                        new SequentialCommandGroup(
                                new WaitCommand(500),
                                new FollowTrajectoryCommand(drive, initialToHub)
                        )
                ),
                new ParallelRaceGroup(
                        new WaitCommand(2000),
                        new OuttakeCube(intake)
                ),
                new ParallelCommandGroup(
                        new FollowTrajectoryCommand(drive, finalToWarehouse),
                        new RetractLift(horizontalLift, topLimit)
                )
            )
        );

        register(verticalLift, horizontalLift, intake);
    }
}
