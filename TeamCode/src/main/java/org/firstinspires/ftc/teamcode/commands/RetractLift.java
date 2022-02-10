package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;

public class RetractLift extends ExtendLift {

    public RetractLift(HorizontalLiftSubsystem subsystem) {
        super(subsystem, HorizontalLevel.Home);
    }

    @Override
    public void initialize() {
        getLiftSubsystem().in();
    }
}
