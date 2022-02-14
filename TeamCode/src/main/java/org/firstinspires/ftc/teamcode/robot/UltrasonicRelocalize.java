package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class UltrasonicRelocalize {
    private final int MINIMUM_DISTANCE = 60;
    private final int MAXIMUM_DISTANCE = 200;
    private final int IN_SET = 6;

    private final SampleMecanumDrive drive;
    private final ModernRoboticsI2cRangeSensor rangeSensor;

    private final double x;
    private final double heading;

    public UltrasonicRelocalize(SampleMecanumDrive drive, ModernRoboticsI2cRangeSensor rangeSensor, Pose2d currentPose) {
        this.drive = drive;
        this.rangeSensor = rangeSensor;
        x = currentPose.getX();
        heading = currentPose.getHeading();
    }

    public void update() {
        double distance = rangeSensor.getDistance(DistanceUnit.MM);

        if(!inRange(distance)) { return; }

        double y = (distance - IN_SET) - 65;
        drive.setPoseEstimate(new Pose2d(x, y, heading));
    }

    private boolean inRange(double distance) {
        return (distance > MINIMUM_DISTANCE && distance < MAXIMUM_DISTANCE);
    }
}
