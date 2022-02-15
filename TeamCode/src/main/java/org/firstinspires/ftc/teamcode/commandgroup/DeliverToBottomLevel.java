package org.firstinspires.ftc.teamcode.commandgroup;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class DeliverToBottomLevel extends DeliverToLevel {
    public DeliverToBottomLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal) {
        super(vertical, horizontal, VerticalLevel.Bottom, HorizontalLevel.Bottom);
    }
}
