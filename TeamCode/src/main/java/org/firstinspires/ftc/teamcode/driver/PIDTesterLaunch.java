package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.DifferentialControlLoopCoefficients;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.control.DoubleMotorPID;
import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.input.GamepadListener;

@TeleOp(name = "PIDTesterLaunch", group = "test")
@Disabled
public class PIDTesterLaunch extends LinearOpMode {

    int mode;

    int valueToChange;

    RobotHardware hardware;

    DcMotor launchMotor;
    PIDController pidController;
    ModernRoboticsUsbDcMotorController dcMotorController;

    double
            kp = 0,
            ki = 0,
            kd = 0;

    double dp = 0.0001;
    double di = 0.0001;
    double dd= 0.0001;

    double ddp = 0.0001;
    double ddi = 0.0001;
    double ddd = 0.0001;

    double maxPower = 1;

    GamepadListener gamepadListener;

    public void runOpMode() throws InterruptedException {
        hardware = new RobotHardware(hardwareMap);
        launchMotor = hardware.getLaunchMotor();
        dcMotorController = (ModernRoboticsUsbDcMotorController) hardware.getSecondaryMotorController();

        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        pidController = new PIDController(launchMotor);
        pidController.kp = kp;
        pidController.ki = ki;
        pidController.kd = kd;

        pidController.maxPower = maxPower;

        gamepadListener = new GamepadListener(gamepad1);
        gamepadListener.registerListener(new GamepadListener.InputListener() {
            @Override
            public Object getValue(Gamepad gamepad) {
                return gamepad1.dpad_up;
            }

            @Override
            public void onChange() {
                changeValues(1);
            }
        });
        gamepadListener.registerListener(new GamepadListener.InputListener() {
            @Override
            public Object getValue(Gamepad gamepad) {
                return gamepad2.dpad_down;
            }

            @Override
            public void onChange() {
                changeValues(-1);
            }
        });

        waitForStart();

        long previousTime = System.nanoTime();

        while(opModeIsActive()) {

            double deltaTime = (System.nanoTime() - previousTime) / 1000000000d;

            gamepadListener.update();

            telemetry.addData((valueToChange == 0 ? ">" : "") + "P", kp);
            telemetry.addData((valueToChange == 1 ? ">" : "") + "I", kp);
            telemetry.addData((valueToChange == 2 ? ">" : "") + "D", kp);
            telemetry.addData((valueToChange == 3 ? ">" : "") + "d P", dp);
            telemetry.addData((valueToChange == 4 ? ">" : "") + "d I", di);
            telemetry.addData((valueToChange == 5 ? ">" : "") + "d D", dd);

            if(gamepad1.x) {
                valueToChange = 0;
            } else if(gamepad1.a) {
                valueToChange = 1;
            } else if(gamepad1.b) {
                valueToChange = 2;
            } else if(gamepad1.left_bumper) {
                valueToChange = 3;
            } else if(gamepad1.y) {
                valueToChange = 4;
            } else if(gamepad1.right_bumper) {
                valueToChange = 5;
            }

            if(gamepad1.left_stick_button) {
                launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                mode = 0;
            } else if(gamepad1.right_stick_button) {
                launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                mode = 1;
            }

            pidController.kp = kp;
            pidController.ki = ki;
            pidController.kd = kd;
            dcMotorController.setDifferentialControlLoopCoefficients(1, new DifferentialControlLoopCoefficients(kp, ki, kd));
            dcMotorController.setDifferentialControlLoopCoefficients(2, new DifferentialControlLoopCoefficients(kp, ki, kd));

            if(gamepad1.right_trigger > 0) {
                pidController.reset();
                launchMotor.setTargetPosition(launchMotor.getCurrentPosition() + RobotHardware.ONE_ROTATION_60);
            }
            if(gamepad1.left_trigger > 0) {
                DcMotor.RunMode runModeLeft = launchMotor.getMode();
                launchMotor.setPower(0);
                launchMotor.setTargetPosition(0);
                launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                idle();
                launchMotor.setMode(runModeLeft);
            }

            if(mode == 0) {
                launchMotor.setPower(maxPower);
            }
            if(mode == 1) {
                pidController.control(deltaTime);
            }


            telemetry.addData("finishCustom", pidController.isAtDestination());
            telemetry.addData("finishDefault", !launchMotor.isBusy());

            idle();
            telemetry.update();
        }

    }

    private void changeValues (int mul) {
        switch(valueToChange) {
            case 0:
                kp += dp * mul;
            case 1:
                ki += di * mul;
            case 2:
                kd += dd * mul;
            case 3:
                dp += ddp * mul;
            case 4:
                di += ddi * mul;
            case 5:
                dd += ddd * mul;
        }
    }
}
