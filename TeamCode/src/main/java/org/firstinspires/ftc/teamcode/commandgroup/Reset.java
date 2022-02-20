package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class Reset extends ParallelCommandGroup {
    public Reset(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal) {
        vertical.reset();
        horizontal.reset();
    }
}
