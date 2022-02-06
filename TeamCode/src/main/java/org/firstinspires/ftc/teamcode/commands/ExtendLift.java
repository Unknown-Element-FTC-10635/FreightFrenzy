package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;


public class ExtendLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;
    private final HorizontalLevel position;

    private final boolean out;

    // True -> out; False -> in;
    public ExtendLift(HorizontalLiftSubsystem subsystem, HorizontalLevel position, boolean out) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);

        this.position = position;
        this.out = out;
    }

    @Override
    public void initialize() {
        liftSubsystem.extendToPosition(position);
        if (out) {
            liftSubsystem.out();
        } else {
            liftSubsystem.in();
        }
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
