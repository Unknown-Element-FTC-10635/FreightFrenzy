package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;

public class SpinCarousel extends CommandBase {
    private final DuckWheelSubsystem ducky;

    private ElapsedTime time;

    // True -> Blue; False -> Red;
    private final boolean blueDirection;

    public SpinCarousel(DuckWheelSubsystem ducky, boolean blue) {
        this.ducky = ducky;
        blueDirection = blue;
        addRequirements(ducky);

        time = new ElapsedTime();
    }

    @Override
    public void initialize() {
        time.startTime();
    }

    @Override
    public void execute() {
        if (blueDirection) {
            ducky.speed(0.3 + (time.milliseconds() / 10000 * 5));
        } else {
            ducky.speed(-(0.3 + (time.milliseconds() / 10000 * 5)));
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
