package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class RaiseLift extends CommandBase {
    private final VerticalLiftSubsystem liftSubsystem;
    private final VerticalLevel position;
    private final double speed;

    public RaiseLift(VerticalLiftSubsystem subsystem, VerticalLevel position, double speed) {
        liftSubsystem = subsystem;
        addRequirements(liftSubsystem);

        this.position = position;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        liftSubsystem.liftToPosition(position);
        liftSubsystem.setPower(speed);
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
