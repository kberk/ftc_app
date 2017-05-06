package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "SimpleDriverLoop")
public class SimpleDriverLoop extends OpMode {

    ElapsedTime runtime;
    DcMotor leftMotor, rightMotor;

    @Override
    public void init() {
        runtime = new ElapsedTime();

        RobotHardware robotHardware = new RobotHardware(hardwareMap);
        robotHardware.init();
        leftMotor = robotHardware.getLeftMotor();
        rightMotor = robotHardware.getRightMotor();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running for " + runtime.toString());
        telemetry.addData("lefty", gamepad1.left_stick_y);

        leftMotor.setPower(-gamepad1.left_stick_y);
        rightMotor.setPower(-gamepad1.right_stick_y);
    }

    @Override
    public void stop() {
        telemetry.addData("Status", "Running for " + runtime.toString());

        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(gamepad1.right_stick_y);
    }
}
