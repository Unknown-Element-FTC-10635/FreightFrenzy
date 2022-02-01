package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class RaiseLift extends CommandBase {
    private final VerticalLiftSubsystem liftSubsystem;
    private final int position;


    public RaiseLift(VerticalLiftSubsystem subsystem, int position) {
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
