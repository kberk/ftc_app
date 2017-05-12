package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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

    // AUTONOMOUS base position is the exact middle of the field

	/* Driver */
    public static final double
            TRIGGER_POW = 2,
            STEERING_POW = 1,
            TURNING_POW = 2,
            HALF_TURN_POWER = 1;

    /* Motion */
    public static final double
            PICKUP_MAX_POWER = 0.3,
            PICKUP_POW = 2,
            SERVO_MIN_POS = 0,
            SERVO_MAX_POS = 1;

    /* Launch */
    public static final int
            LAUNCH_T0 = 0,
            LAUNCH_T1 = (int) Math.round(0.3 * RobotHardware.ONE_ROTATION_60),
            LAUNCH_T2 = (int) Math.round(0.5 * RobotHardware.ONE_ROTATION_60),
            LAUNCH_ONE_ROTATION = RobotHardware.ONE_ROTATION_60;
    public static final double
            LAUNCH_SLOW_POWER = 0.15,
            LAUNCH_FAST_POWER = 0.7;

    /* Encoder */
    public static final int
            ONE_ROTATION_40 = 1120,
            ONE_ROTATION_60 = 1680;

    /* Construction */
    public static final double
            WHEEL_CIRCUMFERENCE = 1120 * 298.78 / 14168,
            WHEEL_DISTANCE = 39.6,
            SENSOR_WHEEL_DISTANCE = 14,
            AXIS_BACK_DISTANCE  = 10;

    /* Autonomous */
    public static final double
            VERY_SLOW_POWER = 0.1,
            SLOW_POWER = 0.18,
            NORMAL_POWER = 0.25,
            FAST_POWER = 0.4,
            ACCELERATION_TIME = 0;

    /* Field */
    public static final double
            FIELD_WIDTH_CM = 358.14001,
            TILE_WIDTH_CM = 59.69;

    /* Features */
    public static final boolean
            ENABLE_QUICKTURN = false,
            ENABLE_PICKUP_GAMEPAD1 = true,
            ENABLE_SHOOTING_GAMEPAD_1 = true,
            ENABLE_SERVO_GAMEPAD1 = true;

    private HardwareMap hardwareMap;
    public RobotHardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init()  {
        ModernRoboticsUsbDcMotorController wheelMotorController = (ModernRoboticsUsbDcMotorController) getWheelMotorController();
        wheelMotorController.setGearRatio(1, 1d/40d);
        wheelMotorController.setGearRatio(2, 1d/40d);

        ModernRoboticsUsbDcMotorController secondaryMotorController = (ModernRoboticsUsbDcMotorController) getSecondaryMotorController();
        secondaryMotorController.setGearRatio(1, 1d/40d);
        secondaryMotorController.setGearRatio(2, 1d/60d);


        getLaunchMotor().setDirection(DcMotorSimple.Direction.FORWARD);

        setDirection(true);
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

    public DcMotorController getSecondaryMotorController() {
        return hardwareMap.dcMotorController.get("secondary_motor_controller");
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
        return (int) Math.round ((cm / RobotHardware.WHEEL_CIRCUMFERENCE) * RobotHardware.ONE_ROTATION_40);
    }

    public static int angleToPosition(double angle) {
        return cmToPosition((RobotHardware.WHEEL_DISTANCE * Math.PI) * (angle / 360));
    }
}
