package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

@Autonomous (name = "Either | Ball | N.A. | 0s", group = "either-competition-ready")
public class BallPath extends RobotAutonomous {

    private final int
            DRIVE_DISTANCE = 200;

    @Override
    public int getStartDelay() {
        return 20 * 1000;
    }

    @Override
    public void initOpMode() {
        super.initOpMode();
        add(actions.move(RobotHardware.cmToPosition(DRIVE_DISTANCE), RobotHardware.FAST_POWER, RobotHardware.ACCELERATION_TIME));
    }

    @Autonomous (name = "Either | Ball | N.A. | 20s", group = "either-competition-ready")
    public static class BallPathDelay extends BallPath {
        @Override
        public boolean isStartDelayed() {
            return true;
        }
    }

}