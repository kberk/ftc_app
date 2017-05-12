package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.input.GamepadListener;

/**
 * Created by user on 18/03/17.
 */

@TeleOp(name = "Increment")
@Disabled
public class IncrementTester extends LinearOpMode {


    GamepadListener gamepadListener;

    double d = 0;

    public void runOpMode() throws InterruptedException {

        gamepadListener = new GamepadListener(gamepad1);
        gamepadListener.registerListener(new GamepadListener.InputListener() {
            @Override
            public Object getValue(Gamepad gamepad) {
                return gamepad1.dpad_up;
            }

            @Override
            public void onChange() {
                d += 5;
            }
        });
        gamepadListener.registerListener(new GamepadListener.InputListener() {
            @Override
            public Object getValue(Gamepad gamepad) {
                return gamepad2.dpad_down;
            }

            @Override
            public void onChange() {
                d += 5;
            }
        });

        waitForStart();

        while(opModeIsActive()) {

            gamepadListener.update();

            telemetry.addData("d", d);

            idle();
            telemetry.update();
        }

    }

}
