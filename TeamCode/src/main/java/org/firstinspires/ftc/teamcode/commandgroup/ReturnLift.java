package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class ReturnLift extends ParallelCommandGroup {
    public ReturnLift(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal) {
        addCommands(
                new ExtendLift(horizontal, HorizontalLiftSubsystem.HorizontalLevel.Home),
                new RaiseLift(vertical, VerticalLiftSubsystem.VerticalLevel.Home, 0.5)
        );
        addRequirements(vertical, horizontal);
    }
}
