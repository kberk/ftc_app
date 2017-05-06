package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.base.Action;
import org.firstinspires.ftc.teamcode.autonomous.base.RobotAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.module.BeaconColorDetector;
import org.firstinspires.ftc.teamcode.autonomous.module.LineCatcher;
import org.firstinspires.ftc.teamcode.autonomous.module.LineFollower;

@Autonomous (name = "TestPathMOVE", group = "left-competition-ready")
public class TestPath extends RobotAutonomous {

    private final double
            POWER = 0.2;

    @Override
    public void initOpMode() {
        super.initOpMode();

        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        add(new Action() {
            @Override
            public void run() {
                setPower(POWER);
            }
        });

        telemetry.addData("Status", "Ready for takeoff");
    }

    public boolean isOnLeft() {
        return true;
    }

    public int getRotationDirection() {
        return isOnLeft() ? -1 : 1;
    }

    public boolean isStartDelayed() {
        return false;
    }

}
