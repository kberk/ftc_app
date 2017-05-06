package org.firstinspires.ftc.teamcode.autonomous.base;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.control.PIDController;

public class RobotAutonomousActions {

    private RobotAutonomous r;
    public RobotAutonomousActions (RobotAutonomous robotAutonomous) {
        this.r = robotAutonomous;
    }

    public Action move(final int factor, final double power, final double accelerationTime) {
        return new AccelerateAction(power, 0) {
            @Override
            public void start() {
                super.start();
                r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                r.setPower(targetPower);
                r.move(factor);
            }

            @Override
            public void run() {
                super.run();
            }

            @Override
            public boolean hasEnded() {
                return r.leftMotor.getCurrentPosition() >= r.leftMotor.getTargetPosition();
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
        return new AccelerateAction(power, 0) {
            @Override
            public void start() {
                super.start();
                r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if(factor > 0) {
                    r.leftMotor.setPower(-power);
                    r.rightMotor.setPower(power);
                } else {
                    r.leftMotor.setPower(power);
                    r.rightMotor.setPower(-power);
                }
                r.turn(factor);
            }

            @Override
            public void run() {
            }

            @Override
            public boolean hasEnded() {
                if(r.leftMotor.getPower() > 0) {
                    return r.leftMotor.getCurrentPosition() >= r.leftMotor.getTargetPosition();
                } else {
                    return r.leftMotor.getCurrentPosition() <= r.leftMotor.getTargetPosition();
                }
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
        PIDController pid;
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

    public Action shoot() {
        return new Action() {
            int launchMotorStartPosition;
            @Override
            public void start() {
                launchMotorStartPosition = r.launchMotor.getCurrentPosition();
                r.launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                r.launchMotor.setPower(RobotHardware.LAUNCH_SLOW_POWER);
            }

            @Override
            public boolean hasEnded() {
                int launchRelMotorPos = r.launchMotor.getCurrentPosition() - launchMotorStartPosition;
                return launchRelMotorPos >= RobotHardware.LAUNCH_T1 && launchRelMotorPos <= RobotHardware.LAUNCH_T2;
            }

            @Override
            public void stop() {
                r.launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                r.launchMotor.setPower(RobotHardware.LAUNCH_FAST_POWER);
                r.launchMotor.setTargetPosition(launchMotorStartPosition + RobotHardware.LAUNCH_ONE_ROTATION + RobotHardware.LAUNCH_T0);
                sleep(500);
            }
        };
    }

}
