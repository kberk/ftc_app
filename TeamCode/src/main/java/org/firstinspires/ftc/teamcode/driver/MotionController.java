package org.firstinspires.ftc.teamcode.driver;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class MotionController {

    final double
        PICKUP_MAX_POWER = 1,
        PICKUP_POW = 2,
        LAUNCHING_MAX_POWER = 1,
        LAUNCHING_POW = 2,
        SERVO_MIN = 0,
        SERVO_MAX = 1;


    final int
        LAUNCH_T0 = 0,
        LAUNCH_T1 = (int) Math.round(0.3 * RobotHardware.ONE_ROTATION_60),
        LAUNCH_T2 = (int) Math.round(0.5 * RobotHardware.ONE_ROTATION_60),
        LAUNCH_ONE_ROTATION = RobotHardware.ONE_ROTATION_60;
    final double
        LAUNCH_SLOW_POWER = 0.15,
        LAUNCH_FAST_POWER = 0.7;

    int launchMotorStartPosition;
    int launchPhase;
    int launchAmount;

    DcMotor pickupMotor;
    DcMotor launchMotor;
    Servo beaconServo;
    public MotionController (DcMotor pickupMotor, DcMotor launchMotor, Servo beaconServo) {
        this.pickupMotor = pickupMotor;
        this.launchMotor = launchMotor;
        this.beaconServo = beaconServo;
    }

    public void init() {

    }

    public void start() {
        launchPhase = 0;
        launchAmount = 0;
        launchMotorStartPosition = launchMotor.getCurrentPosition();
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void control(Gamepad gamepad1, Gamepad gamepad2) {

        if(gamepad2.a && launchPhase == 0) {
            launchPhase = 1;
            launchAmount++;

            launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            launchMotor.setPower(LAUNCH_SLOW_POWER);
        }
        if(launchPhase == 1) {
            int launchRelMotorPos = launchMotor.getCurrentPosition() - LAUNCH_ONE_ROTATION * (launchAmount - 1) - launchMotorStartPosition;
            if(launchRelMotorPos >= LAUNCH_T1 && launchRelMotorPos <= LAUNCH_T2) {
                    launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    launchMotor.setPower(LAUNCH_FAST_POWER);
                    launchMotor.setTargetPosition(launchMotorStartPosition + LAUNCH_ONE_ROTATION * launchAmount + LAUNCH_T0);
                    launchPhase = 2;
            }
        }
        if(launchPhase == 2 && !launchMotor.isBusy()) {
            launchPhase = 0;
            if(launchMotor.getTargetPosition() == launchMotor.getCurrentPosition()) {
                launchMotor.setPower(0);
            }
        }


        if(gamepad1.right_bumper) {
            pickupMotor.setPower(0.3);
        } else if (gamepad1.left_bumper){
            pickupMotor.setPower(-0.3);
        } else {
            pickupMotor.setPower(0);
        }

//        if(gamepad2.right_trigger > 0) {
//            pickupMotor.setPower(Math.pow(gamepad2.right_trigger, PICKUP_POW) * PICKUP_MAX_POWER);
//        } else {
//            pickupMotor.setPower(-Math.pow(gamepad2.left_trigger, PICKUP_POW) * PICKUP_MAX_POWER);
//        }

        if(gamepad2.dpad_left) {
            beaconServo.setPosition(SERVO_MIN);
        } else if(gamepad2.dpad_right) {
            beaconServo.setPosition(SERVO_MAX);
        }

    }

    public void stop() {
        launchMotor.setPower(0);
        pickupMotor.setPower(0);
    }


}
