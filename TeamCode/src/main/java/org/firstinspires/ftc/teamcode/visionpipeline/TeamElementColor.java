package org.firstinspires.ftc.teamcode.visionpipeline;

import android.annotation.SuppressLint;
import android.os.Build;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Arrays;
import java.util.Comparator;

public class TeamElementColor extends OpenCvPipeline {
    final private Scalar LOWERGREEN = new Scalar(30, 50, 50);
    final private Scalar UPPERGREEN = new Scalar(55, 255, 255);

    final private Rect LEFTBARCODE = new Rect(0, 24, 266, 400);
    final private Rect CENTERBARCODE = new Rect(266, 24, 266, 400);
    final private Rect RIGHTBARCODE = new Rect(532, 24, 266, 400);

    private int elementPosition;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);

        Core.inRange(input, LOWERGREEN, UPPERGREEN, input);

        Imgproc.rectangle(input, LEFTBARCODE, new Scalar(50, 255, 255), 4);
        Imgproc.rectangle(input, CENTERBARCODE, new Scalar(50, 255, 255), 4);
        Imgproc.rectangle(input, RIGHTBARCODE, new Scalar(50, 255, 255), 4);

        processBarcodes(input);

        switch (elementPosition) {
            case 0:
                Imgproc.rectangle(input, LEFTBARCODE, new Scalar(255, 255, 255), 4);
                break;

            case 1:
                Imgproc.rectangle(input, CENTERBARCODE, new Scalar(255, 255, 255), 4);
                break;

            case 2:
                Imgproc.rectangle(input, RIGHTBARCODE, new Scalar(255, 255, 255), 4);
                break;
        }

        return input;
    }

    @SuppressLint("NewApi")
    private void processBarcodes(Mat frame) {
        double[][] barcodes = {
            {Core.mean(frame.submat(LEFTBARCODE)).val[0], 0},
            {Core.mean(frame.submat(CENTERBARCODE)).val[0], 1},
            {Core.mean(frame.submat(RIGHTBARCODE)).val[0], 2}
        };

        Arrays.sort(barcodes, Comparator.comparingDouble(o -> o[0]));

        elementPosition = (int)barcodes[barcodes.length - 1][1];
    }

    public int getElementPosition() {
        return elementPosition;
    }
}
