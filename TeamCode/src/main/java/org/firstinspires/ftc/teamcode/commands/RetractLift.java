package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;

public class RetractLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;

    private HorizontalLevel level;

    public RetractLift(HorizontalLiftSubsystem subsystem, HorizontalLevel level) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);

        this.level = level;
    }

    @Override
    public void initialize() {
        liftSubsystem.extendToPosition(level);
        liftSubsystem.out();
    }

    @Override
    public boolean isFinished() {
        return liftSubsystem.atTargetPosition();
    }

    @Override
    public void end(boolean interrupted) {
        liftSubsystem.stop();
    }
}
