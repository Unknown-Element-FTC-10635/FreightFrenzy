package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HorizontalLiftSubsystem extends SubsystemBase {
    private final Motor extension;

    private HorizontalLevel horizontalLevel = HorizontalLevel.Home;

    private Telemetry telemetry;

    /**
     * Enum of pre-saved positions
     */
    public enum HorizontalLevel {
        Top(1200), // 1100
        Middle(900), // 800
        Bottom(650), // 550,
        PushOut(200), // 200 - Pushes out past the servos
        Ground(1300),
        Home(0); // 0

        public int encoderLevel;

        HorizontalLevel(int eLevel) {
            encoderLevel = eLevel;
        }
    }

    public HorizontalLiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        extension = new Motor(hardwareMap, "extension", Motor.GoBILDA.RPM_312);
        extension.setRunMode(Motor.RunMode.PositionControl);
        extension.setInverted(true);
        extension.resetEncoder();

        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        telemetry.addData("Horizontal Lift Position", extension.getCurrentPosition());
        telemetry.addData("Horizontal Lift Target", horizontalLevel);
        telemetry.addData("Horizontal Lift At Position", atTargetPosition());
    }

    /**
     * Travels to one of the pre-saved positions
     *
     * @param position the position the horizontal lift should go to
     */
    public void extendToPosition(HorizontalLevel position) {
        extension.setTargetPosition(position.encoderLevel);
        horizontalLevel = position;
    }

    /**
     * Extends the lift inwards
     */
    public void in() {
        extension.set(-0.4);
    }

    /**
     * Extends the lift out
     */
    public void out() {
        extension.set(0.4);
    }

    /**
     * Stops the lift
     */
    public void stop() {
        extension.stopMotor();
    }

    /**
     * Resets the lift encoder
     */
    public void reset() {
        extension.resetEncoder();
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
        //return (extension.getCurrentPosition() > targetFloor && extension.getCurrentPosition() < targetCeiling);
        //          -1100                         -1045                                             -1155

        //return extension.atTargetPosition();

        if (horizontalLevel != HorizontalLevel.Home) {
            return (Math.abs(extension.getCurrentPosition()) > Math.abs(horizontalLevel.encoderLevel));
        } else {
            return (Math.abs(extension.getCurrentPosition()) < horizontalLevel.encoderLevel);
        }
    }
}
