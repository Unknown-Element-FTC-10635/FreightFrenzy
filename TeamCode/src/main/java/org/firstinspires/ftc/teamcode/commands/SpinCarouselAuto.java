package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;

public class SpinCarouselAuto extends CommandBase {
    private final DuckWheelSubsystem ducky;
    private final boolean blueDirection;

    public SpinCarouselAuto(DuckWheelSubsystem ducky, boolean blue) {
        this.ducky = ducky;
        blueDirection = blue;
        addRequirements(ducky);
    }

    @Override
    public void initialize() {
        if (blueDirection) {
            ducky.right();
        } else {
            ducky.left();
        }
    }

    @Override
    public void end(boolean interrupted) {
        ducky.stop();
    }

    @Override
    public boolean isFinished() {
        return ducky.isSpinning();
    }
}
