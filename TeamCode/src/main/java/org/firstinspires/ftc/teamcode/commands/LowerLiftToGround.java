package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class LowerLiftToGround extends CommandBase {
    private final VerticalLiftSubsystem liftSubsystem;
    private final LimitSwitchSubsystem switchSubsystem;

    public LowerLiftToGround(VerticalLiftSubsystem verticalSubsystem, LimitSwitchSubsystem switchSubsystem) {
        liftSubsystem = verticalSubsystem;
        liftSubsystem.enableBrakes();
        this.switchSubsystem = switchSubsystem;
        addRequirements(liftSubsystem);
    }

    @Override
    public void initialize() {
        liftSubsystem.liftToPosition(VerticalLevel.Ground);
        liftSubsystem.setPower(0.2);
    }

    @Override
    public boolean isFinished() {
        return switchSubsystem.isPressed();
    }

    @Override
    public void end(boolean interrupted) {
        liftSubsystem.stop();
        liftSubsystem.reset();
    }
}
