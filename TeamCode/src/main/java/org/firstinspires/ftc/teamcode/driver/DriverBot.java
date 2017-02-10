package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name = "DriverBot")
public class DriverBot extends OpMode {

    private ElapsedTime runtime;
    private DcMotor leftMotor, rightMotor, pickupMotor, launchMotor;
    private Servo beaconServo;

    private TriggerDriver driver;
    private MotionController motionController;

    @Override
    public void init() {
        runtime = new ElapsedTime();

        RobotHardware robotHardware = new RobotHardware(hardwareMap);
        robotHardware.init();

        leftMotor = robotHardware.getLeftMotor();
        rightMotor = robotHardware.getRightMotor();
        pickupMotor = robotHardware.getPickupMotor();
        launchMotor = robotHardware.getLaunchMotor();
        beaconServo = robotHardware.getBeaconServo();

        driver = new TriggerDriver(leftMotor, rightMotor, telemetry);
        driver.init();

        motionController = new MotionController(pickupMotor, launchMotor, beaconServo);
        motionController.init();
    }

    @Override
    public void start() {
        driver.start();
        motionController.start();
        runtime.reset();
    }
    @Override
    public void loop() {
        telemetry.addData("Status", "Running for " + runtime.toString());
        driver.drive(gamepad1, gamepad2);
        motionController.control(gamepad1, gamepad2);
    }

    @Override
    public void stop() {
        driver.stop();
        motionController.stop();
    }
}
