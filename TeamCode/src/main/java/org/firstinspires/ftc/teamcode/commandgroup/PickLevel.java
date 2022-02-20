package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class PickLevel extends SequentialCommandGroup {
    public PickLevel(int level, VerticalLiftSubsystem verticalLift, HorizontalLiftSubsystem horizontalLift, IntakeSubsystem intake) {
        switch (level) {
            case 0:
                addCommands(new DeliverToBottomLevel(verticalLift, horizontalLift));
                break;
            case 1:
                addCommands(new DeliverToMiddleLevel(verticalLift, horizontalLift));
                break;
            case 2:
                addCommands(new DeliverToTopLevel(verticalLift, horizontalLift));
                break;
        }
    }
}
