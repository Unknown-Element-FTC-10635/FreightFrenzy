package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class OuttakeCube extends CommandBase {
    private final IntakeSubsystem intake;

    private ElapsedTime time;

    public OuttakeCube(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.out();

        time = new ElapsedTime();
        time.startTime();
    }

    @Override
    public void execute() {
        if (time.milliseconds() > 2000) {
            end(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return intake.isSpinning();
    }
}
