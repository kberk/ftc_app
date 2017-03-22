package org.firstinspires.ftc.teamcode.autonomous.module;

import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousModule;
import org.firstinspires.ftc.teamcode.autonomous.base.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;

public abstract class LineFollower implements AutonomousModule {

    double FAST_POWER = 0.25;
    double MOTOR_ADJUST_MUL = 1.2;

    @Override
    public void insert(AutonomousOpMode opMode) {
        final RobotAutonomous o = (RobotAutonomous) opMode;

        o.add(new Action() {
            @Override
            public void run() {
                double leftPower = FAST_POWER;
                double rightPower = FAST_POWER;

                boolean onLine = o.isOnLine() ^ !isLineLeft();

                if (onLine) {
                    leftPower /= MOTOR_ADJUST_MUL;
                    rightPower *= MOTOR_ADJUST_MUL;
                } else {
                    rightPower /= MOTOR_ADJUST_MUL;
                    leftPower *= MOTOR_ADJUST_MUL;
                }

                o.leftMotor.setPower(leftPower);
                o.rightMotor.setPower(rightPower);
            }

            @Override
            public boolean hasEnded() {
                return hasModuleEnded();
            }
        });
    }

    public abstract boolean hasModuleEnded();
    public abstract boolean isLineLeft();
}
