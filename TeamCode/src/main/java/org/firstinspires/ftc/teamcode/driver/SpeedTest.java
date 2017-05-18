package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;

/**
 * Created by user on 13/05/17.
 */

@TeleOp(name = "SpeedTest")
public class SpeedTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware r = new RobotHardware(hardwareMap);
        r.init();
        r.setDirection(false);

        DcMotor leftMotor = r.getLeftMotor();
        DcMotor rightMotor = r.getRightMotor();

        waitForStart();

        double metersDriven = 2.0;
        int speed = 2;
        int deltaSpeed = 1;

        boolean driving = false;
        int testSpeed = 0;
        long startTime = 0, endTime = 0;
        double elapsedTimeSeconds = 0;
        int startPos = 0;
        double realSpeed = 0;

        while(opModeIsActive()) {


            if(gamepad1.dpad_up) {
                speed+=deltaSpeed;
                sleep(500);
            } else if(gamepad1.dpad_down) {
                speed-=deltaSpeed;
                sleep(500);
            }

            if(gamepad1.a && !driving) {
                sleep(500);
                driving = true;
                startPos = leftMotor.getCurrentPosition();
                testSpeed = speed;
                double actualSpeed = testSpeed / 20d;
                leftMotor.setPower(actualSpeed);
                rightMotor.setPower(actualSpeed);
                startTime = System.nanoTime();
            }

            double realMeterDriven = RobotHardware.positionToCm(leftMotor.getCurrentPosition() - startPos) / 100;
            if(driving && realMeterDriven >= metersDriven) {
                endTime = System.nanoTime();
                elapsedTimeSeconds = (endTime - startTime) / ((double) (1000000000));
                realSpeed = realMeterDriven / elapsedTimeSeconds;
                leftMotor.setPower(0);
                rightMotor.setPower(0);
                driving = false;
            }

            telemetry.addData("Speed", speed);

            if(elapsedTimeSeconds > 0) {
                telemetry.addData("Test Speed", testSpeed + "motor power");
                telemetry.addData("Elapsed Time", elapsedTimeSeconds + " s");
                telemetry.addData("Speed", speed + " motor power");
                telemetry.addData("Real Speed", realSpeed + " m/s");
            }

            telemetry.update();
            idle();


        }
    }
}
