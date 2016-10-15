package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name="Pushbot: RGBSensorTest", group="Pushbot")
public class RGBSensorTest extends LinearOpMode {
    //RMHardwarePushbot robot = new RMHardwarePushbot();
    ColorSensor rgbs;
    //DcMotor leftMotor = null;
    //DcMotor rightMotor = null;
    @Override
    public void runOpMode() throws InterruptedException {
        //robot.init(hardwareMap);
        waitForStart();
        rgbs = hardwareMap.colorSensor.get("colorsensor");
        rgbs.enableLed(false);
        while (opModeIsActive()) {
            telemetry.addData("Red", rgbs.red());
            telemetry.addData("Green", rgbs.green());
            telemetry.addData("Blue", rgbs.blue());
            telemetry.update();
            idle();
        }
        rgbs.enableLed(false);
        stop();
    }
}
