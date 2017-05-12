package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;

import static org.firstinspires.ftc.teamcode.RobotHardware.HALF_TURN_POWER;

public class TriggerDriver {

    private DcMotor leftMotor, rightMotor;
    private Telemetry telemetry;

    private boolean halfTurning = false;

    public TriggerDriver(DcMotor leftMotor, DcMotor rightMotor, Telemetry telemetry) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.telemetry = telemetry;
    }

    public void init() {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void start() {

    }

    public void drive(Gamepad gamepad1, Gamepad gamepad2) {
        double leftPower = 0, rightPower = 0;

        if(RobotHardware.ENABLE_QUICKTURN) {
            if (halfTurning) {
                if (!leftMotor.isBusy() && !rightMotor.isBusy()) {
                    halfTurning = false;
                    leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                } else {
                    return;
                }
            } else if (gamepad1.x || gamepad1.b) {
                halfTurning = true;

                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                int pos = RobotHardware.angleToPosition(180);
                if (gamepad1.x) {
                    // To the left
                    leftMotor.setTargetPosition(leftMotor.getCurrentPosition() - pos);
                    rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + pos);
                } else {
                    // To the right
                    leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + pos);
                    rightMotor.setTargetPosition(rightMotor.getCurrentPosition() - pos);
                }

                leftMotor.setPower(RobotHardware.HALF_TURN_POWER);
                rightMotor.setPower(RobotHardware.HALF_TURN_POWER);

                return;
            }
        }


        if(gamepad1.a) {

            if(gamepad1.left_stick_x < 0) {
                leftPower = -Math.pow( - gamepad1.left_stick_x, RobotHardware.TURNING_POW);
                rightPower = Math.pow( - gamepad1.left_stick_x, RobotHardware.TURNING_POW);
            } else if(gamepad1.left_stick_x > 0) {
                leftPower = Math.pow(gamepad1.left_stick_x, RobotHardware.TURNING_POW);
                rightPower = -Math.pow(gamepad1.left_stick_x, RobotHardware.TURNING_POW);
            }

        } else {

            if (gamepad1.right_trigger > 0) {
                leftPower = rightPower = Math.pow(gamepad1.right_trigger, RobotHardware.TRIGGER_POW);
            } else if (gamepad1.left_trigger > 0) {
                leftPower = rightPower = -Math.pow(gamepad1.left_trigger, RobotHardware.TRIGGER_POW);
            }

            if (gamepad1.left_stick_x < 0) {
                leftPower *= 1 - Math.pow(-gamepad1.left_stick_x, RobotHardware.STEERING_POW);
                rightPower *= 1 + Math.pow(-gamepad1.left_stick_x, RobotHardware.STEERING_POW);
            } else if (gamepad1.left_stick_x > 0) {
                leftPower *= 1 + Math.pow(gamepad1.left_stick_x, RobotHardware.STEERING_POW);
                rightPower *= 1 - Math.pow(gamepad1.left_stick_x, RobotHardware.STEERING_POW);
            }


            /*

            (1 +/- x)^2
                        0   0.25    0.5     0.75    1
            left        1   1.56    2.25   3.1      4
            right      1   0.56    0.25   0.06    0

            1 +/- x^2
                         0   0.25    0.5     0.75    1
            left        1   1.06    1.25   1.56     2
            right      1   0.93    0.75   0.43    0

             */


        }

        leftMotor.setPower(Range.clip(leftPower, -1, 1));
        rightMotor.setPower(Range.clip(rightPower, -1, 1));
    }

    public void stop() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

}
