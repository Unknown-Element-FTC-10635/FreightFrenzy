package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeCube extends CommandBase {
    private final IntakeSubsystem intake;
    private final SampleMecanumDrive drive;
    private final Pose2d pose;

    private int iteration = 0;

    public IntakeCube(IntakeSubsystem intake, SampleMecanumDrive drive, Pose2d pose) {
        this.intake = intake;
        this.drive = drive;
        this.pose = pose;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.in();
    }

    /*
    @Override
    public void execute() {
        TrajectorySequence forward = drive.trajectorySequenceBuilder(new Pose2d(pose.getX() + iteration, pose.getY()))
                .forward(10)
                .build();
        iteration += 10;
        CommandScheduler.getInstance().schedule(new FollowTrajectoryCommand(drive, forward));
    }*/

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return intake.hasElement();
    }
}
