package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    private final CRServo intake;
    private final ColorSensor colorSensor;

    private boolean intakeStatus = false;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intake = hardwareMap.get(CRServo.class, "intake");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
    }

    /**
     * Spins the intake inwards
     */
    public void in() {
        intake.setPower(1);
    }

    public void in(double speed) {intake.setPower(speed);}

    /**
     * Spins the intake outwards
     */
    public void out() {
        intake.setPower(-0.5);
    }

    /**
     * Stops the intake
     */
    public void stop() {
        intake.setPower(0);
    }

    /**
     * @return whether the intake is spinning or not
     */
    public boolean isSpinning() {
        return (intake.getPower() != 0);
    }

    public boolean hasElement() {
        return colorSensor.red() > 100 && colorSensor.blue() > 100 && colorSensor.green() > 100;
    }

    public boolean justIntaken() {
        intakeStatus = hasElement();
        if (!intakeStatus) {
            intakeStatus = true;
        } else {
            intakeStatus = false;
        }
        return intakeStatus;
    }

    public int[] rawColor() {
        return new int[]{colorSensor.red(), colorSensor.green(), colorSensor.blue()};
    }
}
