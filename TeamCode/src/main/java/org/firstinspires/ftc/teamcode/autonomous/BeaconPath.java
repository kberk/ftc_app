package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;
import android.test.RenamingDelegatingContext;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;
import org.firstinspires.ftc.teamcode.autonomous.module.LineCatcher;
import org.firstinspires.ftc.teamcode.autonomous.module.LineFollower;

@Autonomous (name = "Left | Beacon", group = "left-competition-ready")
public class BeaconPath extends RobotAutonomous {

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

        if(isStartDelayed()) {
            add(actions.sleep(START_DELAY));
        }

        add(actions.move(RobotHardware.cmToPosition(70), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));
        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.NORMAL_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));

        add(actions.move(RobotHardware.cmToPosition(100), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));
        add(actions.turn(-getRotationDirection() * RobotHardware.angleToPosition(64), RobotHardware.NORMAL_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        add(new Action() {
            @Override
            public void run() {
                setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                setPower(RobotHardware.NORMAL_POWER);
            }
        });
        add(new LineCatcher() {
            @Override
            public boolean rotateLeft() {
                return isOnLeft();
            }
        });
        add(new LineFollower() {
            @Override
            public boolean hasModuleEnded() {
                return false;
            }

            @Override
            public boolean isLineLeft() {
                return isOnLeft();
            }
        });
        add(new Action() {
            @Override
            public void start() {
                setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                setPower(RobotHardware.SLOW_POWER);
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
                if(blue > red) {
                    beaconServo.setPosition(0);
                } else {
                    beaconServo.setPosition(1);
                }
            }
        });

        telemetry.addData("Status", "Ready for takeoff");
    }

    public boolean isOnLeft() {
        return true;
    }

    public int getRotationDirection() {
        return isOnLeft() ? -1 : 1;
    }

    public boolean isStartDelayed() {
        return false;
    }

    @Autonomous (name = "Right | Beacon", group = "right-competition-ready")
    public static class BeaconPathRight extends BeaconPath {
        @Override
        public boolean isOnLeft() {
            return false;
        }
    }

    @Autonomous (name = "Left | Beacon | 10sec", group = "left-competition-ready")
    public static class BeaconPathDelay extends BeaconPath {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

    @Autonomous (name = "Right | Beacon | 10sec", group = "right-competition-ready")
    public static class BeaconPathRightDelay extends BeaconPathRight {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

}
