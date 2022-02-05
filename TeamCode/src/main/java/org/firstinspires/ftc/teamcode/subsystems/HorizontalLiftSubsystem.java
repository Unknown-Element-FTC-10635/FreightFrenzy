package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HorizontalLiftSubsystem extends SubsystemBase {
    private final Motor extension;

    private final HorizontalLevel horizontalLevel = HorizontalLevel.Home;

    private Telemetry telemetry;

    private boolean moving = false;

    /**
     * Enum of pre-saved positions
     */
    public enum HorizontalLevel {
        Top(-1100), // 1100
        Middle(-800), // 800
        Bottom(-550), // 550
        Home(0); // 0

        public int encoderLevel;
        HorizontalLevel(int eLevel) {
            encoderLevel = eLevel;
        }
    }

    public HorizontalLiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        extension = new Motor(hardwareMap, "extension", Motor.GoBILDA.RPM_312);
        extension.setRunMode(Motor.RunMode.PositionControl);

        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        if (moving) {
            telemetry.addData("Horizontal Lift Position", extension.getCurrentPosition());
        }

        telemetry.addData("Horizontal Lift Target", horizontalLevel.encoderLevel);
    }

    /**
     * Travels to one of the pre-saved positions
     * @param position the position the horizontal lift should go to
     */
    public void extendToPosition(HorizontalLevel position) {
        extension.setTargetPosition(position.encoderLevel);
        moving = true;
    }

    /**
     * Extends the lift inwards
     */
    public void in() {
        extension.set(0.5);
        moving = true;
    }

    /**
     * Extends the lift out
     */
    public void out() {
        extension.set(-0.5);
        moving = true;
    }

    /**
     * Stops the lift
     */
    public void stop() {
        extension.stopMotor();
        moving = false;
    }

    /**
     * @return the position of the extension
     */
    public int getPosition() {
        return extension.getCurrentPosition();
    }

    /**
     * @return whether the motor has reached it's position
     */
    public boolean atTargetPosition() {
        return extension.atTargetPosition();
    }
}
