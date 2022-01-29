package org.firstinspires.ftc.teamcode.visionpipeline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DuckDetectionPipeline extends OpenCvPipeline {
    private final Scalar LOWER_DUCK = new Scalar(22, 100, 100);
    private final Scalar UPPER_DUCK = new Scalar(30, 255, 255);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Mat processFrame(Mat input) {
        Mat grey = new Mat();
        Imgproc.cvtColor(input, grey, Imgproc.COLOR_RGB2HSV);

        Core.inRange(grey, LOWER_DUCK, UPPER_DUCK, grey);


        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(grey, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(255, 0, 0);
            Imgproc.drawContours(input, contours, i, color, 2, Imgproc.LINE_8, hierarchy);
        }

        return input;
    }
}
