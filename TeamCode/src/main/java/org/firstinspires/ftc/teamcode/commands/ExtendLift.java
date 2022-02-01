package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;


public class ExtendLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;
    private final HorizontalLevel position;

    public ExtendLift(HorizontalLiftSubsystem subsystem, HorizontalLevel position) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);

        this.position = position;
    }

    @Override
    public void initialize() {
        liftSubsystem.extendToPosition(position);
        if (position.encoderLevel < liftSubsystem.getPosition()) {
            liftSubsystem.out();
        } else {
            liftSubsystem.in();
        }
    }

    @Override
    public void execute() {
        if (liftSubsystem.atTargetPosition()) {
            liftSubsystem.stop();
            isFinished();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}