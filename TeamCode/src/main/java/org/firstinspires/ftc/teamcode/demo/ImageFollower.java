package org.firstinspires.ftc.teamcode.demo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RobotHardware;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "ImageFollower", group = "demo")
public class ImageFollower extends LinearOpMode {

    private final String LICENSE_KEY = "ARKRQH//////AAAAGVD/jh7fuU7EpcQhO0UpqVeIxNTDdbADgkqFgSqAypo4TBIdD5aRrH1NfXcplCBvypMXgNbQqyL0SHwrwM0zrstG0GRgAmmNiR3sNkjXhqTefIr5WJZQC42+GPgM5PYuJQ+BQ5LKmMKgsf1sCwbgEf1fCd324HDm3P2XTBDTLYdc/ZpWz1mTMKivp1NLm9p0wZRm+PP2tKLMtH2cJWRKmyXtNAO3oLfPbi815RG2vN1nYIOyPjbSFP8D9H2f4XK2xVPzKlUFIug+Oph4hUiamk5jh4EWn8FwjeGHi0UAG37EFAy+dcXWyLlsk+aTVL6TosB090hcpl+aT5u+Q1rUO3qjDJzWTiJbs/BEw40gFY/y";

    private final double
        MAX_X_OFFSET = 200,
        MIN_X_OFFSET = 10,
        MAX_ROTATION_POWER = 0.15,
        MAX_Z_OFFSET = 700,
        TARGET_DISTANCE = 800,
        MIN_Z_OFFSET = 100,
        MAX_DRIVING_POWER = 0.15;



    VuforiaLocalizer vuforia;

    boolean driveToDistance = true;

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robotHardware = new RobotHardware(hardwareMap);
        robotHardware.init();
        DcMotor leftMotor = robotHardware.getLeftMotor();
        DcMotor rightMotor = robotHardware.getRightMotor();

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = LICENSE_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("StonesAndChips");

        VuforiaTrackable stones = stonesAndChips.get(0);
        stones.setName("Stones");  // Stones

        VuforiaTrackable chips = stonesAndChips.get(1);
        chips.setName("BlueTarget");  // Chips

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);

        // Just for tracking the position of the images
        stones.setLocation(new OpenGLMatrix());
        chips.setLocation(new OpenGLMatrix());

        OpenGLMatrix phoneLocationOnRobot = new OpenGLMatrix();

        ((VuforiaTrackableDefaultListener) stones.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener) chips.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        stonesAndChips.activate();

        while (opModeIsActive()) {

            OpenGLMatrix imageLocation = null;

            for (VuforiaTrackable trackable : allTrackables) {
                VuforiaTrackableDefaultListener listener = ((VuforiaTrackableDefaultListener) trackable.getListener());
                telemetry.addData(trackable.getName(), listener.isVisible() ? "Visible" : "Not Visible");    //

                imageLocation = ((VuforiaTrackableDefaultListener) trackable.getListener()).getRawPose();
                if (imageLocation != null && listener.isVisible()) {
                    break;
                } else {
                    imageLocation = null;
                }
            }

            if(gamepad1.a) {
                driveToDistance = true;
            } else if(gamepad1.b) {
                driveToDistance = false;
            }

            telemetry.addData("Distance", driveToDistance);

            if (imageLocation != null) {
                for (int row = 0; row < 4; row++) {
                    String s = "";
                    for (int col = 0; col < 4; col++) {
                        s += imageLocation.get(row, col) + "\t";
                    }
                    telemetry.addData(Integer.toString(row), s);
                }

                double xOffset = imageLocation.get(1, 3);
                double distance = imageLocation.get(2, 3);

                double leftPower = 0, rightPower = 0;

                if(Math.abs(distance - TARGET_DISTANCE) > MIN_Z_OFFSET && driveToDistance) {
                    double power = (-distance + TARGET_DISTANCE) / MAX_Z_OFFSET * MAX_DRIVING_POWER;
                    leftPower += power;
                    rightPower += power;
                }

                if(Math.abs(xOffset) > MIN_X_OFFSET) {
                    double power = xOffset / MAX_X_OFFSET * MAX_ROTATION_POWER;
                    telemetry.addData("Power", power);
                    leftPower += power;
                    rightPower -= power;
                }

                leftMotor.setPower(Range.clip(leftPower, -1, 1));
                rightMotor.setPower(Range.clip(rightPower, -1, 1));

            }

            telemetry.update();

            idle();
        }


    }
}
