package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Pushbot: RGBSensorTest", group="Pushbot")
public class RGBSensorTest extends LinearOpMode {
    RMHardwarePushbot robot = new RMHardwarePushbot();
    ColorSensor rgbs;
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    @Override
    public void runOpMode( ) throws InterruptedException {
        robot.init(hardwareMap);
        leftMotor  = robot.leftMotor;
        rightMotor = robot.rightMotor;
        waitForStart();
        rgbs = hardwareMap.colorSensor.get("colorsensor");
        rgbs.enableLed(false);
        //leftMotor.setPower(-0.2);
        //rightMotor.setPower(-0.2);
        String color = getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());
        while (opModeIsActive()) {
            telemetry.addData("Red", rgbs.red());
            telemetry.addData("Green", rgbs.green());
            telemetry.addData("Blue", rgbs.blue());
            telemetry.addData("Clear", rgbs.alpha());
            telemetry.addData("Color", color);
            telemetry.update();
            color = getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());
            idle();
        }
        rgbs.enableLed(false);
        stop();
    }
    public String getColorNameFromValues(int r, int g, int b) {
        if (r >= 8 && g >= 8 && b >= 8) {
            return "white";
        }else if (r >= 6 && g <= 3 && b <= 3) {
            return "red";
        }else if (r <= 3 && g <= 3 && b >= 6) {
            return "blue";
        }else if (r <= 3 && g <= 3 && b <= 3) {
            return "black";
        }else{
            return "other";
        }
    }
}
