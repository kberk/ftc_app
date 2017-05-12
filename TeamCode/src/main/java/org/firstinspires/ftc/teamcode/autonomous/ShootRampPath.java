package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;

@Autonomous (name = "Left | Shoot+Ramp | 0t | 0s", group = "left-competition-ready")
public class ShootRampPath extends RobotAutonomous {

    @Override
    public int getStartDelay() {
        return 8 * 1000;
    }


    @Override
    public void initOpMode() {
        super.initOpMode();

        double angle = 20;

        if(isOnLeft()) {
            add(actions.move(RobotHardware.cmToPosition(31.5 - RobotHardware.AXIS_BACK_DISTANCE), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));
            add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.NORMAL_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
            add(actions.move(RobotHardware.cmToPosition(-108 + getTileOffset() * RobotHardware.TILE_WIDTH_CM), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));

            add(actions.turn(getRotationDirection() * RobotHardware.angleToPosition(angle), RobotHardware.SLOW_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        }

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

    public boolean isStartDelayed() {
        return false;
    }

    @Autonomous (name = "Right | Shoot Ramp | 0t | 0s", group = "right-competition-ready")
    public static class ShootRampPathRight extends ShootRampPath {
        @Override
        public boolean isOnLeft() {
            return false;
        }
    }

    @Autonomous (name = "Left | Shoot Ramp | 0t | 8s", group = "left-competition-ready")
    public static class ShootRampPathDelay extends ShootRampPath {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

    @Autonomous (name = "Right | Shoot Ramp | 0t | 8s", group = "right-competition-ready")
    public static class ShootRampPathRightDelay extends ShootRampPathRight {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }






}
