package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.commands.RetractLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class ToGround extends SequentialCommandGroup {
    ToGround(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal) {
        addCommands(
                new InstantCommand(vertical::reset),
                new ParallelCommandGroup(
                    new ExtendLift(horizontal, HorizontalLiftSubsystem.HorizontalLevel.Ground),
                    new RaiseLift(vertical, VerticalLiftSubsystem.VerticalLevel.Ground, 0.5)
                )
        );
        addRequirements(vertical, horizontal);
    }
}
