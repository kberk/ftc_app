package org.firstinspires.ftc.teamcode.autonomous.module;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousModule;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

public abstract class LineCatcher implements AutonomousModule {

    double FAST_POWER = 0.15;
    double SLOW_POWER = 0.1;


    public void insert(final AutonomousOpMode opMode) {
        final RobotAutonomous o = (RobotAutonomous) opMode;


        // Find line
        o.add(new Action() {
            @Override
            public boolean hasEnded() {
                return o.isOnLine();
            }

//            @Override
//            public void stop() {
//                o.setPower(0);
//            }
        });

        // Move robot's axis of rotation on line
        o.add(o.actions.move(RobotHardware.cmToPosition(RobotHardware.SENSOR_WHEEL_DISTANCE), SLOW_POWER));

        o.add(o.actions.sleep(10000));

        // Rotate robot parallel to line
        o.add(new Action() {
            @Override
            public void start() {
                o.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                o.leftMotor.setPower(-getRotationDirection() * FAST_POWER);
                o.rightMotor.setPower(getRotationDirection() * FAST_POWER);
            }

            @Override
            public boolean hasEnded() {
                return o.isOnLine();
            }
        });

    }

    public abstract boolean rotateLeft();

    private int getRotationDirection() {
        return rotateLeft() ? -1 : 1;
    }
}

