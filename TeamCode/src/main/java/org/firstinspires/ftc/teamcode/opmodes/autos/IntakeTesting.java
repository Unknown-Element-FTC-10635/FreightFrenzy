package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commandgroup.DeliverToTopLevel;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.commands.RetractLift;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

@Disabled
@Autonomous
public class IntakeTesting extends CommandOpMode {
    VerticalLiftSubsystem verticalLiftSubsystem;
    HorizontalLiftSubsystem horizontalLiftSubsystem;
    IntakeSubsystem intake;
    LimitSwitchSubsystem topLimit;

    @Override
    public void initialize() {
        verticalLiftSubsystem = new VerticalLiftSubsystem(hardwareMap, telemetry);
        horizontalLiftSubsystem = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);
        topLimit = new LimitSwitchSubsystem(hardwareMap, "topLimit");

        schedule(
            new SequentialCommandGroup(
                new DeliverToTopLevel(verticalLiftSubsystem, horizontalLiftSubsystem),
                new ParallelRaceGroup(
                    new WaitCommand(2000),
                    new OuttakeCube(intake)
                ),
                new RetractLift(horizontalLiftSubsystem, topLimit)
            )
        );

        register(intake);
    }
}
