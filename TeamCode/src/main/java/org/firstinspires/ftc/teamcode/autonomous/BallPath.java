package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

@Autonomous (name = "Either | Ball", group = "either-competition-ready")
public class BallPath extends RobotAutonomous {

    private final int
            DRIVE_DISTANCE = 200;
    private final long
            START_DELAY = 20 * 1000;

    @Override
    public void initOpMode() {
        super.initOpMode();
        if(isStartDelayed()) {
            add(actions.sleep(START_DELAY));
        }
        add(actions.move(RobotHardware.cmToPosition(DRIVE_DISTANCE), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
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