package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

public class RobotHardware {

	/*
    * circumference = pi * d
    * d = circumference / pi
    *
    * position = (distance / circumference) * 1120
    *
    * position / 1120 = distance / circumference
    * (position * circumference) / 1120 = distance
    * position * circumference = 1120 * distance
    * circumference = 1120 * distance / position
    *
    *
    * circumference = 1120 * distance / position
    * circumference = 1120 * 298.78 / 14168
	*/


    public static final int ONE_ROTATION = 1120;

    public static final double
            WHEEL_CIRCUMFERENCE = 1120 * 298.78 / 14168,
            WHEEL_DISTANCE = 39.6,
            SENSOR_WHEEL_DISTANCE = 21;

    private HardwareMap hardwareMap;
    public RobotHardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {
        ModernRoboticsUsbDcMotorController wheelMotorController = (ModernRoboticsUsbDcMotorController) getWheelMotorController();
        wheelMotorController.setGearRatio(1, 1d/40d);
        wheelMotorController.setGearRatio(2, 1d/40d);

        setDirection(true);

        getLaunchMotor().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDirection(boolean pickupFront) {
        if(pickupFront) {
            getLeftMotor().setDirection(DcMotorSimple.Direction.FORWARD);
            getRightMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            getLeftMotor().setDirection(DcMotorSimple.Direction.REVERSE);
            getRightMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public DcMotorController getWheelMotorController() {
        return hardwareMap.dcMotorController.get("wheel_motor_controller");
    }

    public DcMotor getLeftMotor() {
        return hardwareMap.dcMotor.get("left_motor");
    }

    public DcMotor getRightMotor() {
        return hardwareMap.dcMotor.get("right_motor");
    }

    public DcMotor getPickupMotor() {return hardwareMap.dcMotor.get("pickup_motor");}

    public DcMotor getLaunchMotor() {return hardwareMap.dcMotor.get("launch_motor");}

    public ColorSensor getColorSensor() {
        return hardwareMap.colorSensor.get("color_sensor");
    }

    public Servo getBeaconServo() {
        return hardwareMap.servo.get("beacon_servo");
    }


    public static int cmToPosition(double cm) {
        return (int) Math.round ((cm / RobotHardware.WHEEL_CIRCUMFERENCE) * RobotHardware.ONE_ROTATION);
    }

    public static int angleToPosition(double angle) {
        return cmToPosition((RobotHardware.WHEEL_DISTANCE * Math.PI) * (angle / 360));
    }
}
