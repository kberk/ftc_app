package org.firstinspires.ftc.teamcode.autonomous.base;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

public abstract class AutonomousOpMode extends LinearOpMode {

    final ElapsedTime runtime = new ElapsedTime();
    final List<Action> actions = new ArrayList<Action>();
    private Action currentAction;
    private long previousTime;

    public abstract void initOpMode();

    @Override
    public void runOpMode() throws InterruptedException {
        initOpMode();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if(actions.isEmpty()) {
            return;
        }

        currentAction = actions.get(0);
        previousTime = System.nanoTime();
        currentAction.start();
        while(opModeIsActive()) {
            runActions();
            idle();
            telemetry.update();
        }
    }

    private void runActions() {
        while (!actions.isEmpty()) {
            currentAction.run();
            if (currentAction.hasEnded()) {
                currentAction.stop();
                actions.remove(0);
                if (!actions.isEmpty()) {
                    currentAction = actions.get(0);
                    currentAction.start();
                }
            } else {
                break;
            }
        }
    }

    public void add(Action runnable) {
        actions.add(runnable);
    }

    public void add(AutonomousModule module) {
        module.insert(this);
    }

}
