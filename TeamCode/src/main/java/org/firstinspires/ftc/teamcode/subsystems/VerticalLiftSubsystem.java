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

    /**
     * Enum of pre-saved position
     */
    public enum VerticalLevel {
        CycleTop(2600),
        Top(1800), // 1500
        Middle(950), // 800
        Bottom(200), // 200
        Home(0), // 0
        Ground(-100); // -100 + TOP force to be at the correct position

        public int encoderLevel;

        VerticalLevel(int eLevel) {
            encoderLevel = eLevel;
        }
    }

    public VerticalLiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        liftLeft = new MotorEx(hardwareMap, "liftLeft", Motor.GoBILDA.RPM_312);
        liftLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        liftLeft.setRunMode(Motor.RunMode.PositionControl);

        liftLeftEn = liftLeft.encoder;
        liftLeftEn.reset();

        liftRight = new MotorEx(hardwareMap, "liftRight", Motor.GoBILDA.RPM_312);
        liftRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
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
    }

    public void liftToPosition(int encoderValue) {
        liftLeft.setTargetPosition(encoderValue);
        liftRight.setTargetPosition(encoderValue);
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
        // return liftLeft.atTargetPosition() || liftRight.atTargetPosition();
        //return (liftLeftEn.getPosition() > targetFloor && liftLeftEn.getPosition() < targetCeiling)
        //        || (liftRightEn.getPosition() > targetFloor && liftRightEn.getPosition() < targetCeiling);

        //return (liftLeft.atTargetPosition() || liftRight.atTargetPosition());

        if (liftLevel != VerticalLevel.Home) {
            return (Math.abs(liftLeftEn.getPosition()) > Math.abs(liftLevel.encoderLevel)
                    || Math.abs(liftRightEn.getPosition()) > Math.abs(liftLevel.encoderLevel));
        } else {
            return (liftLeftEn.getPosition() > 200  || liftRightEn.getPosition() > 200);
        }

    }

    public void releaseBrakes() {
        liftLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        liftRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }

    public void enableBrakes() {
        liftLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }
}
