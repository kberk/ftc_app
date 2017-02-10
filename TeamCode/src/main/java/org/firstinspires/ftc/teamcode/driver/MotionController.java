package org.firstinspires.ftc.teamcode.driver;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class MotionController {

    final double
        PICKUP_MAX_POWER = 1,
        PICKUP_POW = 2,
        LAUNCHING_MAX_POWER = 1,
        LAUNCHING_POW = 2,
        SERVO_MIN = 0,
        SERVO_MAX = 1;

    DcMotor pickupMotor;
    DcMotor launchingMotor;
    Servo beaconServo;
    public MotionController (DcMotor pickupMotor, DcMotor launchingMotor, Servo beaconServo) {
        this.pickupMotor = pickupMotor;
        this.launchingMotor = launchingMotor;
        this.beaconServo = beaconServo;
    }

    public void init() {

    }

    public void start() {

    }

    public void control(Gamepad gamepad1, Gamepad gamepad2) {

        if(gamepad2.right_trigger > 0) {
            pickupMotor.setPower(Math.pow(gamepad2.right_trigger, PICKUP_POW) * PICKUP_MAX_POWER);
        } else {
            pickupMotor.setPower(-Math.pow(gamepad2.left_trigger, PICKUP_POW) * PICKUP_MAX_POWER);
        }

        if(gamepad2.left_stick_y > 0) {
            launchingMotor.setPower(Math.pow(gamepad2.left_stick_y, LAUNCHING_POW) * LAUNCHING_MAX_POWER);
        } else {
            launchingMotor.setPower(0);
        }

        if(gamepad2.dpad_left) {
            beaconServo.setPosition(SERVO_MIN);
        } else if(gamepad2.dpad_right) {
            beaconServo.setPosition(SERVO_MAX);
        }

    }

    public void stop() {
        launchingMotor.setPower(0);
        pickupMotor.setPower(0);
    }


}
