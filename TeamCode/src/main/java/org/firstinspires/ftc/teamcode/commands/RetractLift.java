package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;

public class RetractLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;

    public RetractLift(HorizontalLiftSubsystem subsystem) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);
    }

    @Override
    public void initialize() {
        liftSubsystem.extendToPosition(HorizontalLevel.Home);
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
