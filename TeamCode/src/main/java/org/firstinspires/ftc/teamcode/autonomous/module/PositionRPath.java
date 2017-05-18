package org.firstinspires.ftc.teamcode.autonomous.module;

import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousModule;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

/**
 * Created by user on 13/05/17.
 */

public abstract class PositionRPath implements AutonomousModule {

//    private boolean rotateLeft;
//    public PositionRPath(boolean rotateLeft) {
//        this.rotateLeft = rotateLeft;
//    }


    @Override
    public void insert(AutonomousOpMode opMode) {
        RobotAutonomous r = (RobotAutonomous) opMode;

        double angle = 20;
        double startCm = 31.5;

        r.add(r.actions.move(RobotHardware.cmToPosition(startCm - RobotHardware.AXIS_BACK_DISTANCE), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));
        r.add(r.actions.turn(r.getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.NORMAL_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));

        if(r.isOnLeft()) {
            r.add(r.actions.move(RobotHardware.cmToPosition(-108 + r.getTileOffset() * RobotHardware.TILE_WIDTH_CM), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));

            r.add(r.actions.turn(r.getRotationDirection() * RobotHardware.angleToPosition(angle), RobotHardware.SLOW_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));

            r.add(r.actions.shoot());

            r.add(r.actions.turn(-r.getRotationDirection() * RobotHardware.angleToPosition(angle + 15), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        } else {
            r.add(r.actions.move(RobotHardware.cmToPosition(108 - r.getTileOffset() * RobotHardware.TILE_WIDTH_CM), RobotHardware.NORMAL_POWER, RobotHardware.ACCELERATION_TIME));

            r.add(r.actions.turn(r.getRotationDirection() * RobotHardware.angleToPosition(90 - angle), RobotHardware.SLOW_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));

            r.add(r.actions.shoot());

            r.add(r.actions.turn(-r.getRotationDirection() * RobotHardware.angleToPosition(90 + angle), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));
        }

        r.add(r.actions.move(RobotHardware.cmToPosition(108), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
        r.add(r.actions.turn(-r.getRotationDirection() * RobotHardware.angleToPosition(90), RobotHardware.FAST_POWER, 0.5 * RobotHardware.ACCELERATION_TIME));


        r.add(r.actions.move(RobotHardware.cmToPosition(74 - startCm), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
    }

}
