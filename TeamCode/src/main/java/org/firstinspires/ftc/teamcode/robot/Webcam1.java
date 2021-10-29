package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.visionpipeline.BasicPipeline;
import org.firstinspires.ftc.teamcode.visionpipeline.TeamElementColor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class Webcam1 {
    private final HardwareMap hardwareMap;

    public Webcam1(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void startBasic() {
        BasicPipeline pipeline = new BasicPipeline();
        start(pipeline);
    }

    public void startTeamelementColor() {
        TeamElementColor pipeline = new TeamElementColor();
        start(pipeline);
    }

    public void startAprilTracking() {
        //AprilTagTracking pipeline = new AprilTagTracking(tagsize, fx, fy, cx, cy);
        //start(pipeline);
    }

    private void start(OpenCvPipeline pipeline) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                webcam.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.startCameraStream(webcam, 30);
    }
}
