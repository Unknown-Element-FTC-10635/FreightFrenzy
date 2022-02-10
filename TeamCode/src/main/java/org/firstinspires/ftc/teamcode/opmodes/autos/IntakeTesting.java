package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ExtendLift;
import org.firstinspires.ftc.teamcode.commands.OuttakeCube;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Autonomous
public class IntakeTesting extends CommandOpMode {
    HorizontalLiftSubsystem horizontalLiftSubsystem;
    IntakeSubsystem intake;

    @Override
    public void initialize() {
        horizontalLiftSubsystem = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);


        schedule(
            new SequentialCommandGroup(
                new ExtendLift(horizontalLiftSubsystem, HorizontalLiftSubsystem.HorizontalLevel.Middle),
                new ParallelRaceGroup(
                    new WaitCommand(2000),
                    new OuttakeCube(intake)
                )
            )
        );

        register(intake);
    }
}
