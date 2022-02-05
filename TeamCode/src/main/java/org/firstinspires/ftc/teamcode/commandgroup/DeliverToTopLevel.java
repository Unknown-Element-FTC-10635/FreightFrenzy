package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

public class DeliverToTopLevel extends SequentialCommandGroup {
    public DeliverToTopLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal, IntakeSubsystem intake) {
        addCommands(
                new ParallelCommandGroup(
                        new RaiseLift(vertical, VerticalLevel.Top, 0.5),
                        new ExtendLift(horizontal, HorizontalLevel.Top)),
                new OuttakeCube(intake),
                new ReturnLift(vertical, horizontal)
        );
        addRequirements(vertical, horizontal, intake);
    }
}