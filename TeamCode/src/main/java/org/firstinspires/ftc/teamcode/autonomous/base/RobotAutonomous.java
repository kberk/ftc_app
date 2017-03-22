package org.firstinspires.ftc.teamcode.autonomous.base;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousOpMode;

public abstract class RobotAutonomous extends AutonomousOpMode {

    public RobotAutonomousActions actions;
    public RobotHardware robotHardware;
    public DcMotor leftMotor, rightMotor;
    public ColorSensor colorSensor;
    public Servo beaconServo;

    @Override
    public void initOpMode() {
        actions = new RobotAutonomousActions(this);

        robotHardware = new RobotHardware(hardwareMap);
        robotHardware.init();

        leftMotor = robotHardware.getLeftMotor();
        rightMotor = robotHardware.getRightMotor();
        colorSensor = robotHardware.getColorSensor();
        beaconServo = robotHardware.getBeaconServo();

        leftMotor.setDirection(leftMotor.getDirection() == DcMotorSimple.Direction.FORWARD ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(rightMotor.getDirection() == DcMotorSimple.Direction.FORWARD ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turn(int factor) {
        // In autonomous, left and right motors are switch
        // negative -> turn left
        // positive -> turn right
        leftMotor.setTargetPosition(Math.round(leftMotor.getCurrentPosition() - factor));
        rightMotor.setTargetPosition(Math.round(rightMotor.getCurrentPosition() + factor));
    }

    public void move(int factor) {
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + factor);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + factor);
    }

    public void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void setMode(DcMotor.RunMode mode) {
        leftMotor.setMode(mode);
        rightMotor.setMode(mode);
    }

    public boolean areMotorsAtRest() {
        return !leftMotor.isBusy() && !rightMotor.isBusy();
    }

    public boolean isOnLine() {
        return colorSensor.alpha() > 10;
    }
}
