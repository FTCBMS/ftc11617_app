package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by meberle on 11/9/2016.
 */
@Autonomous(name="Autonomous iterative", group="Auto")
public class Autonomous_Iterative extends OpMode {
    @Override
    public void init() {
        telemetry.addData("", "init pushed");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        telemetry.addData("", "init loop");
        telemetry.update();
        Thread.yield();
    }

    @Override
    public void loop() {
        telemetry.addData("", "loop");
        telemetry.update();
        Thread.yield();
    }

    @Override
    public void start() {
        telemetry.addData("", "started");
    }

    @Override
    public void stop() {
        telemetry.addData("", "stopped");
    }
}
