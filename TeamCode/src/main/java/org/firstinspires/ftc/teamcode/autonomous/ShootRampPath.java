package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;

@Autonomous (name = "Left | Shoot Ramp", group = "left-competition-ready")
public class ShootRampPath extends RobotAutonomous {

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

        add(actions.move(RobotHardware.cmToPosition(20), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));
        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(120), RobotHardware.NORMAL_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));

        add(actions.shoot());

        add(actions.turn(-getRotationDirection() * RobotHardware.angleToPosition(15), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(90), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
        add(actions.turn(-getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(50), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));

        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(100), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
        add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(45), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        add(actions.move(RobotHardware.cmToPosition(50), RobotHardware.FAST_POWER));
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

    @Autonomous (name = "Right | Shoot Ramp", group = "right-competition-ready")
    public static class RampPathRight extends ShootRampPath {
        @Override
        public boolean isOnLeft() {
            return false;
        }
    }

    @Autonomous (name = "Left | Shoot Ramp | 12sec", group = "left-competition-ready")
    public static class RampPathDelay extends ShootRampPath {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

    @Autonomous (name = "Right | Shoot Ramp | 12sec", group = "right-competition-ready")
    public static class RampPathRightDelay extends RampPathRight {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }






}
