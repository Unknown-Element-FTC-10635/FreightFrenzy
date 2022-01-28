package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.visionpipeline.BasicPipeline;
import org.firstinspires.ftc.teamcode.visionpipeline.DuckDetectionPipeline;
import org.firstinspires.ftc.teamcode.visionpipeline.TeamElementColorPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.logging.Logger;

public class Webcam1 {
    private final HardwareMap hardwareMap;

    private BasicPipeline basicPipeline;
    private TeamElementColorPipeline teamElementPipeline;
    private DuckDetectionPipeline duckDetectionPipeline;

    private final Logger log = Logger.getLogger(Webcam1.class.getName());

    private OpenCvWebcam webcam;

    public Webcam1(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void startBasic() {
        basicPipeline = new BasicPipeline();
        start(basicPipeline);
    }

    public void startTeamelementColor() {
        teamElementPipeline = new TeamElementColorPipeline();
        start(teamElementPipeline);
    }

    public int getElementPosition() {
        return teamElementPipeline.getElementPosition();
    }

    public void startDuckDectionPipeline() {
        duckDetectionPipeline = new DuckDetectionPipeline();
        start(duckDetectionPipeline);
    }

    private void start(OpenCvPipeline pipeline) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.

        log.info("Opening Camera Device");
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                log.info("Calling StartStreaming");
                webcam.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                log.warning("EasyOpenCV error: " + errorCode);
            }
        });


        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.startCameraStream(webcam, 30);
    }

    public void stop() {
        webcam.stopStreaming();
    }
}
