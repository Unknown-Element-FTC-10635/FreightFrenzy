package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

class DeliverToLevel extends SequentialCommandGroup {
    public DeliverToLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal, VerticalLevel vertLevel, HorizontalLevel horizontalLevel) {
        addCommands(
                new ExtendLift(horizontal, HorizontalLevel.PushOut),
                new ParallelCommandGroup(
                        new RaiseLift(vertical, vertLevel, 0.4),
                        new SequentialCommandGroup(
                                new WaitCommand(300),
                                new ExtendLift(horizontal, horizontalLevel)
                        )
                )
                //new ReturnLift(vertical, horizontal)

        );
        addRequirements(vertical, horizontal);
    }
}
