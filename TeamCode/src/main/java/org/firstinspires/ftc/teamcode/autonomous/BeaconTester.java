package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;
import org.firstinspires.ftc.teamcode.autonomous.module.LineCatcher;
import org.firstinspires.ftc.teamcode.autonomous.module.LineFollower;

/**
 * Created by user on 27/01/17.
 */

@Autonomous (name = "BeaconTester Red", group = "test")
public class BeaconTester extends RobotAutonomous {

    private final double
            POWER = 0.5,
            SLOW_POWER = 0.2,
            ACCELERATION_TIME = 2;
    private final int
            COLOR_DETECTION_ATTEMPTS = 10;
    private final long
            START_DELAY = 10 * 1000;

    BeaconColorDetector bcd;

    @Override
    public void initOpMode() {
        super.initOpMode();

        bcd = new BeaconColorDetector(this);
        bcd.startCamera();

        add(new Action() {
            @Override
            public void start() {
                //setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //setPower(SLOW_POWER);
            }

            int red = 0, blue = 0;
            @Override
            public void run() {
                bcd.analyzeImage();

                if(bcd.leftColor == Color.RED) {
                    red++;
                } else if(bcd.leftColor == Color.BLUE) {
                    blue++;
                }
            }

            @Override
            public boolean hasEnded() {
                return red + blue >= COLOR_DETECTION_ATTEMPTS;
            }

            @Override
            public void stop() {
                if(isTargetingRed()) {
                    if(blue > red) {
                        beaconServo.setPosition(0);
                    } else {
                        beaconServo.setPosition(1);
                    }
                } else {
                    if(blue > red) {
                        beaconServo.setPosition(1);
                    } else {
                        beaconServo.setPosition(0);
                    }
                }
            }
        });

        telemetry.addData("Status", "Ready for takeoff");
    }

    public boolean isTargetingRed() {
        return true;
    }

    @Autonomous (name = "BeaconTester Blue", group = "test")
    public static class BeaconTesterBlue extends BeaconTester {
        @Override
        public boolean isTargetingRed() {
            return false;
        }
    }


}
