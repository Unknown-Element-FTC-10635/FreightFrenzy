package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;

public class RaiseLift extends CommandBase {
    private final HorizontalLiftSubsystem liftSubsystem;
    private final int position;


    public RaiseLift(HorizontalLiftSubsystem subsystem, int position) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);

        this.position = position;
    }

    @Override
    public void initialize() {
        liftSubsystem.lift(position);
    }

    @Override
    public void execute() {
        if (liftSubsystem.atTargetPosition()) {
            isFinished();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
