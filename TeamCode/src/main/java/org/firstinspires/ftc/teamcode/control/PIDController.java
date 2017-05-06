package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.DifferentialControlLoopCoefficients;
import com.qualcomm.robotcore.util.Range;

public class PIDController {

    //

    public double
            kp = 0,
            ki = 0,
            kd = 0,
            maxPower = 1,
            minPower = 0.01,
            minIntegral = 0,
            maxIntegral = Double.MAX_VALUE,
            errorMargin = 0;

    private DcMotor motor;

    private int
            totalError,
            previousError;

    private double
            proportion,
            integral,
            derivative,
            power;

    public PIDController(DcMotor motor) {
        this.motor = motor;
    }

    public void reset() {
        totalError = 0;
        previousError = 0;
    }

    public void control(double deltaTime) {
        int error = motor.getTargetPosition() - motor.getCurrentPosition();

        if(isAtDestination()) {
            error = 0;
        }

        if(Math.abs(error) > minIntegral && !isAtDestination()) {
            totalError += error * deltaTime;
        } else {
            totalError = 0;
        }

        if(Math.abs(integral) > maxIntegral) {
            integral = Range.clip(integral, -maxIntegral, maxIntegral);
        }

        proportion = error * kp;
        integral = totalError * ki;
        derivative = (error - previousError)/deltaTime * kd;

        previousError = error;

        power = proportion + integral + derivative;
        if(power < 0) {
            power = Range.clip(power, -maxPower, -minPower);
        } else {
            power = Range.clip(power, minPower, maxPower);
        }
        motor.setPower(power);
    }

    public boolean isAtDestination() {
        return Math.abs(previousError) <= errorMargin;
    }

}
