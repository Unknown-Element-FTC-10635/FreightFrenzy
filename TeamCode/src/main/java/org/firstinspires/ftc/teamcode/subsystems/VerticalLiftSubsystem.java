package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VerticalLiftSubsystem extends SubsystemBase {
    private final MotorEx liftLeft, liftRight;
    private final Motor.Encoder liftLeftEn, liftRightEn;


    private final Telemetry telemetry;

    private VerticalLevel liftLevel = VerticalLevel.Home;

    private double targetFloor, targetCeiling;

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
        liftLeft = new MotorEx(hardwareMap, "liftLeft", Motor.GoBILDA.RPM_312);
        liftLeft.setRunMode(Motor.RunMode.PositionControl);

        liftLeftEn = liftLeft.encoder;
        liftLeftEn.reset();

        liftRight = new MotorEx(hardwareMap, "liftRight", Motor.GoBILDA.RPM_312);
        liftRight.setRunMode(Motor.RunMode.PositionControl);
        liftRight.setInverted(true);

        liftRightEn = liftRight.encoder;
        liftRightEn.reset();
        liftRight.resetEncoder();

        this.telemetry = telemetry;
    }

    /**
     * Updates telemetry every loop cycle
     */
    @Override
    public void periodic() {
        telemetry.addData("Vertical Lift Level:", liftLevel);
        telemetry.addData("Left Lift", liftLeftEn.getPosition());
        telemetry.addData("Right Lift", liftRightEn.getPosition());
        telemetry.update();
    }

    /**
     * Sets the power of both motors
     *
     * @param power speed of the motor
     */
    public void setPower(double power) {
        liftLeft.set(power);
        liftRight.set(power);
    }

    /**
     * Travels to one of the pre-saved positions
     *
     * @param level the level the lift should go to
     */
    public void liftToPosition(VerticalLevel level) {
        liftLeft.setTargetPosition(level.encoderLevel);
        liftRight.setTargetPosition(level.encoderLevel);
        liftLevel = level;

        targetFloor = level.encoderLevel - level.encoderLevel * 0.05;
        targetCeiling = level.encoderLevel + level.encoderLevel * 0.05;
    }

    /**
     * Stops the two motors
     */
    public void stop() {
        liftLeft.stopMotor();
        liftRight.stopMotor();
    }

    /**
     * Resets the encoder values
     */
    public void reset() {
        liftLevel = VerticalLevel.Home;
        liftLeftEn.reset();
        liftRightEn.reset();
    }

    /**
     * @return the motors' current encoder value
     */
    public int getPosition() {
        return (liftLeftEn.getPosition() + liftRightEn.getPosition()) / 2;
    }

    /**
     * @return whether the motors have reached their position
     */
    public boolean atTargetPosition() {
        return liftLeft.atTargetPosition() || liftRight.atTargetPosition();
        //return (liftLeftEn.getPosition() > targetFloor && liftLeftEn.getPosition() < targetCeiling)
        //        || (liftRightEn.getPosition() > targetFloor && liftRightEn.getPosition() < targetCeiling);

        //return (liftLeft.atTargetPosition() || liftRight.atTargetPosition());
        //return (Math.abs(liftLeftEn.getPosition()) > Math.abs(liftLevel.encoderLevel)
        // || Math.abs(liftRightEn.getPosition()) > Math.abs(liftLevel.encoderLevel));
    }

}
