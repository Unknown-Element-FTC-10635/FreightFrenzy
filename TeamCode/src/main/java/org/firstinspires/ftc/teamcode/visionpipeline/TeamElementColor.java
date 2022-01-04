package org.firstinspires.ftc.teamcode.visionpipeline;

import android.annotation.SuppressLint;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TeamElementColor extends OpenCvPipeline {
    private final Scalar LOWER_GREEN = new Scalar(30, 50, 50);
    private final Scalar UPPER_GREEN = new Scalar(55, 255, 255);

    private final Rect LEFTBARCODE = new Rect(0, 24, 266, 400);
    private final Rect CENTERBARCODE = new Rect(266, 24, 266, 400);
    private final Rect RIGHTBARCODE = new Rect(532, 24, 266, 400);

    private int elementPosition = 0;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);

        Core.inRange(input, LOWER_GREEN, UPPER_GREEN, input);

        processBarcodes(input);

        return input;
    }

    @SuppressLint("NewApi")
    private void processBarcodes(Mat frame) {
        double[] barcodes = new double[3];
        barcodes[0] = Core.mean(frame.submat(LEFTBARCODE)).val[0];
        barcodes[1] = Core.mean(frame.submat(CENTERBARCODE)).val[0];
        barcodes[2] = Core.mean(frame.submat(RIGHTBARCODE)).val[0];

        for (int i = 0; i < barcodes.length; i++) {
            elementPosition = barcodes[i] > barcodes[elementPosition] ? i : elementPosition;
        }
    }

    public int getElementPosition() {
        return elementPosition;
    }
}
