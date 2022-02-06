package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;

public class SpinCarousel extends CommandBase {
    private final DuckWheelSubsystem ducky;

    // True -> Blue; False -> Red;
    private final boolean blueDirection;

    public SpinCarousel(DuckWheelSubsystem ducky, boolean blue) {
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
