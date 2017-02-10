package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DoubleMotorPID {

    private DcMotor motor1, motor2;
    private PIDController controller1, controller2;

    public DoubleMotorPID(DcMotor motor1, DcMotor motor2) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        controller1 = new PIDController(motor1);
        controller2 = new PIDController(motor2);
    }

    public boolean isAtDestination() {
        return controller1.isAtDestination() && controller1.isAtDestination();
    }

    public void reset() {
        controller1.reset();
        controller2.reset();
    }

    public void control(double deltaTime) {
        controller1.control(deltaTime);
        controller2.control(deltaTime);
    }

    public void setMinIntegral(double minIntegral) {
        controller1.minIntegral = minIntegral;
        controller2.minIntegral = minIntegral;
    }

    public void setMaxIntegral(double maxIntegral) {
        controller1.maxIntegral = maxIntegral;
        controller2.maxIntegral = maxIntegral;
    }

    public void setMinPower(double minPower) {
        controller1.minPower = minPower;
        controller2.minPower = minPower;
    }

    public void setMaxPower(double maxPower) {
        controller1.maxPower = maxPower;
        controller2.maxPower = maxPower;
    }

    public void setPIDValues(double kp, double ki, double kd) {
        setKp(kp);
        setKi(ki);
        setKd(kd);
    }

    public void setKp(double kp) {
        controller1.kp = kp;
        controller2.kp = kp;
    }

    public void setKi(double ki) {
        controller1.ki = ki;
        controller2.ki = ki;
    }

    public void setKd(double kd) {
        controller1.kd = kd;
        controller2.kd = kd;
    }

}
