package org.firstinspires.ftc.teamcode.autonomous.module;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import for_camera_opmodes.CameraCommunicator;

//import for_camera_opmodes.LinearOpModeCamera;

public class BeaconColorDetector {

        // padding factor of width/height
    double
            paddingHorizontal = 0.1,
            paddingVertical = 0.1;

    int
            ds = 4,
            ds2 = 2;

    private OpMode opMode;
    private CameraCommunicator camera;

    public int leftColor;

    public BeaconColorDetector(OpMode opMode) {
        this.opMode = opMode;
        camera = new CameraCommunicator(opMode.hardwareMap.appContext);
    }

    public void startCamera() {
        if (camera.isCameraAvailable()) {
            camera.setCameraDownsampling(ds);
            camera.startCamera();
        }
    }

    public boolean analyzeImage() {
        if (camera.imageReady()) {
            Bitmap rgbImage = CameraCommunicator.convertYuvImageToRgb(camera.yuvImage, camera.width, camera.height, ds2);

            int halfWidth = (int) (rgbImage.getWidth() * 0.5);
            int maxWidth = (int) (rgbImage.getWidth() - rgbImage.getWidth() * paddingHorizontal);
            int maxHeight = (int) (rgbImage.getHeight() - rgbImage.getHeight() * paddingVertical);


            int leftRed = 0, leftGreen = 0, leftBlue = 0;
            for(int x = (int) (rgbImage.getWidth() * paddingHorizontal); x < halfWidth; x++) {
                for(int y = (int) (rgbImage.getHeight() * paddingVertical); y < maxHeight; y++) {
                    int pixel = rgbImage.getPixel(x, y);
                    leftRed += CameraCommunicator.red(pixel);
                    leftGreen += CameraCommunicator.green(pixel);
                    leftBlue += CameraCommunicator.blue(pixel);
                }
            }

            int rightRed = 0, rightGreen = 0, rightBlue = 0;
            for(int x = halfWidth; x < maxWidth ; x++) {
                for (int y = (int) (rgbImage.getHeight() * paddingVertical); y < maxHeight; y++) {
                    int pixel = rgbImage.getPixel(x, y);
                    rightRed += CameraCommunicator.red(pixel);
                    rightGreen += CameraCommunicator.green(pixel);
                    rightBlue += CameraCommunicator.blue(pixel);
                }
            }


            if(leftRed > rightRed) {
                if(leftBlue > rightBlue) {
                    opMode.telemetry.addData("Color Warning", "Red side has higher value of blue than supposed blue side");
                } else {
                    opMode.telemetry.addData("Color Warning", "All good");
                }
                leftColor = Color.RED;
            } else {
                leftColor = Color.BLUE;
            }

            return true;
        } else {
            return false;
        }
    }


}
