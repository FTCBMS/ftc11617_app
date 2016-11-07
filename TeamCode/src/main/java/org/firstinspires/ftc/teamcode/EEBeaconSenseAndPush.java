package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="Beac", group="Beac")
public class EEBeaconSenseAndPush extends LinearOpMode{
    RMHardwarePushbot robot = new RMHardwarePushbot();
    EEAUTO vars = new EEAUTO(false, false, false, false, false, false, false, false, false, false, false, false);
    private ElapsedTime runtime = new ElapsedTime();
    ColorSensor rgbs = null;

    Servo servo;


    public EEBeaconSenseAndPush(){}

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        servo.setPosition(1);
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
        rgbs = hardwareMap.colorSensor.get("colorsensor");
        rgbs.enableLed(true);
        //&& vars.redTeam == true
        if (color == "red" ) {
            servo.setPosition(1);
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        else if (color == "blue" ){
            servo.setPosition(0);
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
       // && vars.redTeam == true
        //if (color == "Red" && vars.blueTeam == true) {
          //  servo.setPosition(0);
          //  robot.leftMotor.setPower(-0.2);
          //  robot.rightMotor.setPower(-0.2);
         //   sleep(1000);
          //  robot.leftMotor.setPower(0);
         //   robot.rightMotor.setPower(0);
     //   }
     //   else if (color == "Blue" && vars.blueTeam == true){
    //        servo.setPosition(1);
    //        robot.leftMotor.setPower(-0.2);
     //       robot.rightMotor.setPower(-0.2);
     //       sleep(1000);
     //       robot.leftMotor.setPower(0);
     //      robot.rightMotor.setPower(0);
       // }
        else {
            robot.leftMotor.setPower(0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
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
