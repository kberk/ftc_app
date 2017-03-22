package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

@Autonomous (name = "Either | Ball", group = "either-competition-ready")
public class BallPath extends RobotAutonomous {

    private final double
            POWER = 0.5,
            SLOW_POWER = 0.2,
            ACCELERATION_TIME = 2;
    private final int
            DRIVE_DISTANCE = 110;
    private final long
            START_DELAY = 20 * 1000,
            INTER_PATH_DELAY = 3 * 1000;

    @Override
    public void initOpMode() {
        super.initOpMode();
        if(isStartDelayed()) {
            add(actions.sleep(START_DELAY));
        }
        add(actions.move(RobotHardware.cmToPosition(DRIVE_DISTANCE), POWER, ACCELERATION_TIME));
        add(actions.sleep(INTER_PATH_DELAY));
        if(!isStartDelayed()) {
            add(actions.move(RobotHardware.cmToPosition(-DRIVE_DISTANCE), POWER, ACCELERATION_TIME));
        }
    }

    protected boolean isStartDelayed() {
        return false;
    }

    @Autonomous (name = "Either | Ball | 20sec", group = "either-competition-ready")
    public static class BallPathDelay extends BallPath {
        @Override
        protected boolean isStartDelayed() {
            return true;
        }
    }

}