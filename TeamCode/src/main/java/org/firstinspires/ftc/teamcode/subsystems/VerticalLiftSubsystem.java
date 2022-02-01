package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VerticalLiftSubsystem extends SubsystemBase {
    private final MotorGroup liftMotors;

    private final Telemetry telemetry;

    private VerticalLevel liftLevel = VerticalLevel.Home;

    /**
     * Enum of pre-saved position
     */
    public enum VerticalLevel {
        Top(1500), // 1500
        Middle(800), // 800
        Bottom(200), // 200
        Home(0), // 0
        Ground(-400); // -400

        public int encoderLevel;
        VerticalLevel(int eLevel) {
            encoderLevel = eLevel;
        }
    }

    public VerticalLiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        Motor leftLift = hardwareMap.get(Motor.class, "liftLeft");
        Motor rightLift = hardwareMap.get(Motor.class, "liftRight");
        rightLift.setInverted(true);

        liftMotors = new MotorGroup(leftLift, rightLift);

        this.telemetry = telemetry;
    }

    /**
     * Updates telemetry every loop cycle
     */
    @Override
    public void periodic() {
        telemetry.addData("Vertical Lift Level:", liftLevel);
        telemetry.update();
    }

    /**
     * Sets the power of both motors
     * @param power : speed of the motor
     */
    public void setPower(double power) {
        liftMotors.set(power);
    }

    /**
     * Travels to one of the pre-saved positions
     * @param level
     */
    public void liftToPosition(VerticalLevel level) {
        liftMotors.setTargetPosition(level.encoderLevel);
        liftLevel = level;
    }

    /**
     * Stops the two motors
     */
    public void stop() {
        liftMotors.stopMotor();
    }

    /**
     * @return : the motors' current encoder value
     */
    public int getPosition() {
        return liftMotors.getCurrentPosition();
    }

    /**
     * @return : whether the motors have reached their position
     */
    public boolean atTargetPosition() {
        return liftMotors.atTargetPosition();
    }

}
