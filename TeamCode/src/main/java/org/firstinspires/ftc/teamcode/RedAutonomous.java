package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Windows on 2016-09-19.
 */


//In Start, In Beacon


@Autonomous(name = "RedAutonomous", group = "Vuforia V2")
public class RedAutonomous extends AutonomousOpMode {
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
        encoderDrive(TURN_SPEED, -3.2, 3.2, 4.0);
        disableEncoders();
//        robot.tankDrive(0);
//        robot.tankDrive(-0.1);
        TeamCode.pushBeacon(this, TeamCode.Color.RED, true);
//        robot.leftMotor.setPower(0.1);
//        robot.rightMotor.setPower(0.1);
//        sleep(170);
//        robot.leftMotor.setPower(0);
//        robot.rightMotor.setPower(0);
//        robot.sweepAndElevator.setPower(0.4);
//        sleep(2000);
//        robot.sweepAndElevator.setPower(0);
//        robot.launcher.setPower(0);

        //robot.tankDrive(0.2);
        //sleep(750);
       // rgbs.enableLed(true);
        enableEncoders();
        encoderDrive(TURN_SPEED, 5, 5, 8.0);
        sleep(200);
        encoderDrive(TURN_SPEED, 5, -5, 8.0);
        sleep(200);
        encoderDrive(DRIVE_SPEED, -18.75, -18.75, 80.0);
        sleep(200);
        encoderDrive(TURN_SPEED, -5, 5, 8.0);
        disableEncoders();

        TeamCode.pushBeacon(this, TeamCode.Color.RED, false);
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        sleep(500);
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        stop();
    }
}




