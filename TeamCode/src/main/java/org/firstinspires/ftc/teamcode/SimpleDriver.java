package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "SimpleDriver")
public class SimpleDriver extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();

        DcMotor leftMotor = hardwareMap.dcMotor.get("left_motor");
        DcMotor rightMotor = hardwareMap.dcMotor.get("right_motor");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {

            telemetry.addData("Status", "Running for " + runtime.toString());

            leftMotor.setPower(gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);


            idle();
            telemetry.update();
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

}
