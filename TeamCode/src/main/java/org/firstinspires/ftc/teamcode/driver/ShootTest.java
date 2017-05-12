package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name = "ShootTest")
@Disabled
public class ShootTest extends OpMode {

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

    boolean done;

    @Override
    public void loop() {
        if(!done) {
            gamepad2.a = true;
            motionController.control(gamepad1, gamepad2);
            gamepad2.a = false;
            done=true;
            return;
        }

        telemetry.addData("Status", "Running for " + runtime.toString());
        driver.drive(gamepad1, gamepad2);

        telemetry.addData("LaunchStartPos", motionController.launchMotorStartPosition);
        telemetry.addData("LeftPos", leftMotor.getCurrentPosition());
        telemetry.addData("RightPos", rightMotor.getCurrentPosition());

        telemetry.addData("Launch motor", launchMotor.getCurrentPosition());
        telemetry.addData("Launch pos", launchMotor.isBusy());
    }

    @Override
    public void stop() {
        driver.stop();
        motionController.stop();
    }
}
