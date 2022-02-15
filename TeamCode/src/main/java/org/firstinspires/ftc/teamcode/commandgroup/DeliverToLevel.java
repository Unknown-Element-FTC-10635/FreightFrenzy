package org.firstinspires.ftc.teamcode.commandgroup;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commands.RaiseLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem.HorizontalLevel;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem.VerticalLevel;

class DeliverToLevel extends SequentialCommandGroup {
    public DeliverToLevel(VerticalLiftSubsystem vertical, HorizontalLiftSubsystem horizontal, VerticalLevel vertLevel, HorizontalLevel horizontalLevel) {
        addCommands(
                new InstantCommand(() -> {
                    vertical.reset();
                    horizontal.reset();
                }),
                new ExtendLift(horizontal, HorizontalLevel.PushOut),
                new ParallelCommandGroup(
                        new RaiseLift(vertical, vertLevel, 0.5),
                        new ExtendLift(horizontal, horizontalLevel)
                )
                //new ReturnLift(vertical, horizontal)

        );
        addRequirements(vertical, horizontal);
    }
}
