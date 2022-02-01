package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HorizontalLiftSubsystem extends SubsystemBase {
    private final MotorGroup liftMotors;

    public HorizontalLiftSubsystem(HardwareMap hardwareMap) {
        Motor leftLift = hardwareMap.get(Motor.class, "liftLeft");
        Motor rightLift = hardwareMap.get(Motor.class, "liftRight");
        rightLift.setInverted(true);

        liftMotors = new MotorGroup(leftLift, rightLift);
    }

    public void lift(int position) {
        liftMotors.setTargetPosition(position);
    }

    public void lift(float power) {
        liftMotors.set(power);
    }

    public void stop() {
        liftMotors.stopMotor();
    }

    public int getEncoder() {
        return liftMotors.getCurrentPosition();
    }

    public boolean atTargetPosition() {
        return liftMotors.atTargetPosition();
    }

}
