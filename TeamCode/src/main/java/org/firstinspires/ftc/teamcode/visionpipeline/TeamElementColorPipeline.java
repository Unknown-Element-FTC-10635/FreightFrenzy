package org.firstinspires.ftc.teamcode.visionpipeline;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TeamElementColorPipeline extends OpenCvPipeline {
    private final Scalar LOWER_GREEN = new Scalar(25, 50, 50);
    private final Scalar UPPER_GREEN = new Scalar(100, 255, 255);

    private final Rect LEFTBARCODE = new Rect(0, 24, 266, 400);
    private final Rect CENTERBARCODE = new Rect(266, 24, 266, 400);
    private final Rect RIGHTBARCODE = new Rect(532,  24, 266, 400);

    private int elementPosition = 0;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);

        Core.inRange(input, LOWER_GREEN, UPPER_GREEN, input);

        Imgproc.rectangle(input, LEFTBARCODE, new Scalar(255, 0, 0));
        Imgproc.rectangle(input, CENTERBARCODE, new Scalar(255, 0, 0));
        Imgproc.rectangle(input, RIGHTBARCODE, new Scalar(255, 0, 0));

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
