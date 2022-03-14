package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;

public class RetractLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;
    private final LimitSwitchSubsystem topLimit;

    public RetractLift(HorizontalLiftSubsystem liftSubsystem, LimitSwitchSubsystem topLimit) {
        this.liftSubsystem = liftSubsystem;
        this.topLimit = topLimit;
        addRequirements(this.liftSubsystem, this.topLimit);
    }

    @Override
    public void initialize() {
        liftSubsystem.extendToPosition(HorizontalLevel.Home);
        liftSubsystem.out();
    }

    @Override
    public boolean isFinished() {
        return topLimit.isPressed();
    }

    @Override
    public void end(boolean interrupted) {
        liftSubsystem.stop();
    }
}
