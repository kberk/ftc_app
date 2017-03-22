package org.firstinspires.ftc.teamcode.autonomous.base;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.Range;

public class RobotAutonomousActions {

    private RobotAutonomous r;
    public RobotAutonomousActions (RobotAutonomous robotAutonomous) {
        this.r = robotAutonomous;
    }

    public Action move(final int factor, final double power, final double accelerationTime) {
        return new AccelerateAction(power, accelerationTime) {
            @Override
            public void start() {
                super.start();
                r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                r.setPower(targetPower);
                r.move(factor);
            }

            @Override
            public void run() {
                super.run();
                r.setPower(targetPower);
            }

            @Override
            public boolean hasEnded() {
                return r.areMotorsAtRest();
            }

            @Override
            public void stop() {
                r.setPower(0);
            }
        };
    }

    public Action move(final int factor, final double power) {
        return move(factor, power,  0);
    }

    public Action turn(final int factor, final double power, final double accelerationTime) {
        return new AccelerateAction(power, accelerationTime) {
            @Override
            public void start() {
                super.start();
                r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                r.setPower(targetPower);
                r.turn(factor);
            }

            @Override
            public void run() {
                super.run();
                r.setPower(targetPower);
            }

            @Override
            public boolean hasEnded() {
                return r.areMotorsAtRest();
            }

            @Override
            public void stop() {
                r.setPower(0);
            }
        };
    }

    public Action turn(final int factor, final double power) {
        return turn(factor, power, 0);
    }

    private class AccelerateAction extends Action {
        double power, targetPower, accelerateTime, acceleration;
        long startTime;
        AccelerateAction(double power, double accelerationTime) {
            this.power = power;
            this.accelerateTime = accelerationTime;
            this.acceleration = power/accelerationTime;
        }

        @Override
        public void start() {
            r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            targetPower = accelerate() ? 0 : power;
            startTime = System.nanoTime();
        }

        @Override
        public void run() {
            if(accelerate() && Math.abs(targetPower) < Math.abs(power)) {
                double elapsedTime = (System.nanoTime() - startTime) / (double)(1000000000);
                targetPower = Range.clip(elapsedTime * power, -power, power);
            }
        }
        public boolean accelerate() {
            return accelerateTime > 0;
        }
    }

    public Action sleep(final long time) {
        return new Action() {
            @Override
            public void run() {
                r.sleep(time);
            }
        };
    }

    public Action idle() {
        return new Action() {
            @Override
            public void run() {
                idle();
            }
        };
    }

}
