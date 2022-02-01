package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class RaiseToLevel extends SequentialCommandGroup {
    public RaiseToLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal, IntakeSubsystem intake) {
        addCommands(
                new RaiseLift(vertical, VerticalLevel.Top, 0.5),
                new ExtendLift(horizontal, HorizontalLevel.Top)
        );
        addRequirements(vertical, horizontal, intake);
    }
}
