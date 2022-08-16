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

    public IntakeCube(IntakeSubsystem intake, SampleMecanumDrive drive) {
        this.intake = intake;
        this.drive = drive;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.in();
    }


    @Override
    public void execute() {
        drive.setMotorPowers(0.2, 0.2, 0.2, 0.2);
        drive.update();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return intake.hasElement();
    }
}
