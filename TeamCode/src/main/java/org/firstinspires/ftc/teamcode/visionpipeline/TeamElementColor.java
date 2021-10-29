package org.firstinspires.ftc.teamcode.visionpipeline;

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
    static public int barcodePosition;

    final private Scalar LOWERGREEN = new Scalar(25, 50, 0);
    final private Scalar UPPERGREEN = new Scalar(55, 255, 255);

    final private Rect LEFTBARCODE = new Rect(50, 24, 170, 400);
    final private Rect CENTERBARCODE = new Rect(315, 24, 170, 400);
    final private Rect RIGHTBARCODE = new Rect(580, 24, 170, 400);

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);

        Core.inRange(input, LOWERGREEN, UPPERGREEN, input);

        Imgproc.rectangle(input, LEFTBARCODE, new Scalar(50, 255, 255), 4);
        Imgproc.rectangle(input, CENTERBARCODE, new Scalar(50, 255, 255), 4);
        Imgproc.rectangle(input, RIGHTBARCODE, new Scalar(50, 255, 255), 4);

        int code = processBarcodes(input);
        switch (code) {
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

    private int processBarcodes(Mat frame) {
        double[][] barcodes = {
            {Core.mean(frame.submat(LEFTBARCODE)).val[0], 0},
            {Core.mean(frame.submat(CENTERBARCODE)).val[0], 1},
            {Core.mean(frame.submat(RIGHTBARCODE)).val[0], 2}
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(barcodes, Comparator.comparingDouble(o -> o[0]));
        }

        barcodePosition = (int)barcodes[barcodes.length - 1][1];
        return barcodePosition;
    }
}
