package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.LineCatcher;

/**
 * Created by user on 13/05/17.
 */

@Autonomous (name = "LineCatcherTest")
public class LineCatcherTest extends RobotAutonomous {

    @Override
    public void initOpMode() {
        super.initOpMode();

        add(new Action() {
            @Override
            public void run() {
                leftMotor.setPower(RobotHardware.NORMAL_POWER);
                rightMotor.setPower(RobotHardware.NORMAL_POWER);
            }
        });
        add(new LineCatcher() {
            @Override
            public boolean rotateLeft() {
                return false;
            }
        });
    }

}
