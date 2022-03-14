package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.LowerLiftToGround;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.commands.RetractLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

public class ToGround extends SequentialCommandGroup {

    ToGround(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal, LimitSwitchSubsystem liftSwitch) {
        addCommands(
                new ParallelCommandGroup(
                    new ExtendLift(horizontal, HorizontalLiftSubsystem.HorizontalLevel.Ground),
                    new LowerLiftToGround(vertical, liftSwitch)
                )
        );
        addRequirements(vertical, horizontal);
    }
}
