package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Windows on 2016-09-19.
 */


//In Start, In Beacon


@Autonomous(name = "BlueAutonomous", group = "Vuforia V2")
public class BlueAutonomousSAFETYCOPY extends AutonomousOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        initVuforia();
        rgbs = hardwareMap.colorSensor.get("colorsensor");
        rgbs.enableLed(true);
        waitForStart();
//        robot.launcher.setPower(0.5);
        beacons.activate();

        enableEncoders();
        encoderDrive(DRIVE_SPEED, -16, -16, 80.0);
        encoderDrive(TURN_SPEED, 3.2, -3.2, 4.0);
        disableEncoders();
        TeamCode.pushBeacon(this, TeamCode.Color.BLUE);
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        sleep(170);
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
//        robot.sweepAndElevator.setPower(0.4);
//        sleep(2000);
//        robot.sweepAndElevator.setPower(0);
        robot.launcher.setPower(0);

        //robot.tankDrive(0.2);
        //sleep(750);
       // rgbs.enableLed(true);
        enableEncoders();
        encoderDrive(TURN_SPEED, 7, 7, 8.0);
        sleep(200);
        encoderDrive(TURN_SPEED, -5, 5, 8.0);
        sleep(200);
        encoderDrive(DRIVE_SPEED, -17.75, -17.75, 80.0);
        sleep(200);
        encoderDrive(TURN_SPEED, 5, -5, 8.0);
        disableEncoders();
        TeamCode.pushBeacon(this, TeamCode.Color.BLUE);
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        sleep(500);
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        stop();
    }
}