package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    private final CRServo intake;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intake = hardwareMap.get(CRServo.class, "intake");
    }

    /**
     * Spins the intake inwards
     */
    public void in() {
        intake.setPower(1);
    }

    /**
     * Spins the intake outwards
     */
    public void out() {
        intake.setPower(-1);
    }

    /**
     * Stops the intake
     */
    public void stop() {
        intake.setPower(0);
    }

    public boolean isSpinning() {return (intake.getPower() != 0);}
}
