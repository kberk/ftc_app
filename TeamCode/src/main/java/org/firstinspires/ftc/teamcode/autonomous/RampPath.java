package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;
import android.test.RenamingDelegatingContext;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;
@Autonomous (name = "Left | Ramp", group = "left-competition-ready")
public class RampPath extends RobotAutonomous {

    private final double
            POWER = 0.5,
            SLOW_POWER = 0.2,
            ACCELERATION_TIME = 2;
    private final long
            START_DELAY = 12 * 1000;

    BeaconColorDetector bcd;

    @Override
    public void initOpMode() {
        super.initOpMode();

        bcd = new BeaconColorDetector(this);
        bcd.startCamera();

        if(isStartDelayed()) {
            add(actions.sleep(START_DELAY));
        }

        add(actions.move(RobotHardware.cmToPosition(70), POWER, ACCELERATION_TIME));
        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(90), POWER, 0.5 * ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(100), POWER, ACCELERATION_TIME));
        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(45), POWER, 0.5 * ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(50), POWER));
    }

    public boolean isOnLeft() {
        return true;
    }

    private int getRotationDirection() {
        return isOnLeft() ? -1 : 1;
    }

    public boolean isStartDelayed() {
        return false;
    }

    @Autonomous (name = "Right | Ramp", group = "right-competition-ready")
    public static class RampPathRight extends RampPath {
        @Override
        public boolean isOnLeft() {
            return false;
        }
    }

    @Autonomous (name = "Left | Ramp | 12sec", group = "left-competition-ready")
    public static class RampPathDelay extends RampPath {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

    @Autonomous (name = "Right | Ramp | 12sec", group = "right-competition-ready")
    public static class RampPathRightDelay extends RampPathRight {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }






}
