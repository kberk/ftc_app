package org.firstinspires.ftc.teamcode.driver;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class MotionController {

    int launchMotorStartPosition;
    int launchPhase;
    int launchAmount;

    DcMotor pickupMotor;
    DcMotor launchMotor;
    Servo beaconServo;

    RobotHardware hardware;

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
            launchMotor.setPower(RobotHardware.LAUNCH_SLOW_POWER);
        }
        if(launchPhase == 1) {
            int launchRelMotorPos = launchMotor.getCurrentPosition() - RobotHardware.LAUNCH_ONE_ROTATION * (launchAmount - 1) - launchMotorStartPosition;
            if(launchRelMotorPos >= RobotHardware.LAUNCH_T1 && launchRelMotorPos <= RobotHardware.LAUNCH_T2) {
                    launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    launchMotor.setPower(RobotHardware.LAUNCH_FAST_POWER);
                    launchMotor.setTargetPosition(launchMotorStartPosition + RobotHardware.LAUNCH_ONE_ROTATION * launchAmount + RobotHardware.LAUNCH_T0);
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
            pickupMotor.setPower(RobotHardware.PICKUP_MAX_POWER);
        } else if (gamepad1.left_bumper){
            pickupMotor.setPower(-
                    RobotHardware.PICKUP_MAX_POWER);
        } else if(gamepad2.right_trigger > 0) {
            pickupMotor.setPower(Math.pow(gamepad2.right_trigger, RobotHardware.PICKUP_POW) * RobotHardware.PICKUP_MAX_POWER);
        } else {
            pickupMotor.setPower(-Math.pow(gamepad2.left_trigger, RobotHardware.PICKUP_POW) * RobotHardware.PICKUP_MAX_POWER);
        }

        if(gamepad2.dpad_left) {
            beaconServo.setPosition(RobotHardware.SERVO_MIN_POS);
        } else if(gamepad2.dpad_right) {
            beaconServo.setPosition(RobotHardware.SERVO_MAX_POS);
        }

    }

    public void stop() {
        launchMotor.setPower(0);
        pickupMotor.setPower(0);
    }


}
