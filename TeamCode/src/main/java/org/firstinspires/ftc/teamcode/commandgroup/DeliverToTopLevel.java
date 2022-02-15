package org.firstinspires.ftc.teamcode.commandgroup;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class DeliverToTopLevel extends DeliverToLevel {
    public DeliverToTopLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal) {
        super(vertical, horizontal, VerticalLevel.Top, HorizontalLevel.Top);
    }
}
