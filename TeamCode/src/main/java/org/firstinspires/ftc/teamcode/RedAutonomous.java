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
        robot.tankDrive(0);
//        robot.tankDrive(-0.1);
        whole_thing:
        while (opModeIsActive()) {
            int i = 0;
            for (VuforiaTrackable beac : beacons) {
                if (i < 4) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();
                    if (pose != null) {
                        VectorF translation = pose.getTranslation();
                       // telemetry.addData("X offset", translation.get(0));
                       // telemetry.addData("Y offset", translation.get(1));
                        telemetry.addData("Z offset", translation.get(2));
                        telemetry.addData("positionOnScreen", translation.get(1));
                        double positionOnScreen = translation.get(1); // = translation.get(0) for upright phones
                        // ^^ IMPORTANT ^^: phone must be right-side-down or it will move away from the picture!
                        /*if (translation.get(2) < -200) { // If z axis (distance) > ~8in (approx.)
                            if (positionOnScreen > 10) { // Right side of screen
                                robot.rightMotor.setPower(-0.3);
                                robot.leftMotor.setPower(-0.025);
                                telemetry.addData("Turning:", "Right");
                            } else if (positionOnScreen < -10) { // Left side of screen
                                robot.rightMotor.setPower(-0.025);
                                robot.leftMotor.setPower(-0.3);
                                telemetry.addData("Turning:", "Left");
                            } else { // Near the middle
                                robot.leftMotor.setPower(-0.15);
                                robot.rightMotor.setPower(-0.15);
                                telemetry.addData("Turning:", "Straight");
                            }//TeamCode.AUTONOMOUS_STOP_POINT
                        } else {

                            robot.rightMotor.setPower(0);
                            robot.leftMotor.setPower(0);
                            break whole_thing;
                        }*/

                        if (translation.get(2) < -200) { // If z axis (distance) > ~8in (approx.)
//                            robot.leftMotor.setPower((-0.2667 + (adjust - 0.1) * 0.3));
//                            robot.rightMotor.setPower((-0.2667 - (adjust) - 0.1) * 0.3);
                            double adj = positionOnScreen / 250;
                            adj = Math.min(0.2, Math.max(-0.2, adj));
                            robot.rightMotor.setPower(-0.2 - adj);
                            robot.leftMotor.setPower(-0.2 + adj);
                        } else {
                            robot.rightMotor.setPower(0);
                            robot.leftMotor.setPower(0);
                            break whole_thing;
                        }

                        telemetry.update();
                        sleep(100);
                    } else {
                        telemetry.addData("No image found", "");
                        robot.tankDrive(0);
                    }
                }
            }
            telemetry.update();
            idle();
        }
        telemetry.update();
        String color = TeamCode.getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());

        //telemetry.addData("Red", rgbs.red());
        //telemetry.addData("Green", rgbs.green());
        //telemetry.addData("Blue", rgbs.blue());
        //telemetry.addData("Clear", rgbs.alpha());
        //telemetry.addData("Color", color);
        //telemetry.update();
        idle();
        if (color == "red") {
            robot.servo.setPosition(-1);
            idle();
            sleep(500);
            telemetry.addData("", "Red Detected");
            telemetry.update();
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        if (color == "blue") {
            robot.servo.setPosition(1);
            idle();
            sleep(500);
            telemetry.addData("", "Blue Detected");
            telemetry.update();
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
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
        encoderDrive(TURN_SPEED, 9, 9, 8.0);
        sleep(200);
        encoderDrive(TURN_SPEED, 5, -5, 8.0);
        sleep(200);
        encoderDrive(DRIVE_SPEED, -17.75, -17.75, 80.0);
        sleep(200);
        encoderDrive(TURN_SPEED, -5, 5, 8.0);
        disableEncoders();

        whole_thing_2:
        while (opModeIsActive()) {
            int i = 0;
            for (VuforiaTrackable beac : beacons) {
                if (i < 4) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();
                    if (pose != null) {
                        VectorF translation = pose.getTranslation();
                        // telemetry.addData("X offset", translation.get(0));
                        // telemetry.addData("Y offset", translation.get(1));
                        telemetry.addData("Z offset", translation.get(2));
                        telemetry.addData("positionOnScreen", translation.get(1));
                        double positionOnScreen = translation.get(1); // = translation.get(0) for upright phones
                        // ^^ IMPORTANT ^^: phone must be right-side-down or it will move away from the picture!
                        /*if (translation.get(2) < -200) { // If z axis (distance) > ~8in (approx.)
                            if (positionOnScreen > 10) { // Right side of screen
                                robot.rightMotor.setPower(-0.3);
                                robot.leftMotor.setPower(-0.025);
                                telemetry.addData("Turning:", "Right");
                            } else if (positionOnScreen < -10) { // Left side of screen
                                robot.rightMotor.setPower(-0.025);
                                robot.leftMotor.setPower(-0.3);
                                telemetry.addData("Turning:", "Left");
                            } else { // Near the middle
                                robot.leftMotor.setPower(-0.15);
                                robot.rightMotor.setPower(-0.15);
                                telemetry.addData("Turning:", "Straight");
                            }//TeamCode.AUTONOMOUS_STOP_POINT
                        } else {

                            robot.rightMotor.setPower(0);
                            robot.leftMotor.setPower(0);
                            break whole_thing_2;
                        }*/
                        if (translation.get(2) < -200) { // If z axis (distance) > ~8in (approx.)
//                            robot.leftMotor.setPower((-0.2667 + (adjust - 0.1) * 0.3));
//                            robot.rightMotor.setPower((-0.2667 - (adjust) - 0.1) * 0.3);
                            double adj = positionOnScreen / 250;
                            adj = Math.min(0.2, Math.max(-0.2, adj));
                            robot.rightMotor.setPower(-0.2 - adj);
                            robot.leftMotor.setPower(-0.2 + adj);
                        } else {
                            robot.rightMotor.setPower(0);
                            robot.leftMotor.setPower(0);
                            break whole_thing_2;
                        }
                        telemetry.update();
                        sleep(100);
                    } else {
                        telemetry.addData("No image found", "");
                        robot.tankDrive(0);
                    }
                }
            }
            telemetry.update();
            idle();
        }
        telemetry.update();
        color = TeamCode.getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());
        //telemetry.addData("Green", rgbs.green());
        //telemetry.addData("Blue", rgbs.blue());
        //telemetry.addData("Clear", rgbs.alpha());
        //telemetry.addData("Color", color);
        //telemetry.update();

        idle();
        if (color == "red") {
            robot.servo.setPosition(-1);
            sleep(500);
            idle();
            telemetry.addData("", "Red Detected");
            telemetry.update();
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        if (color == "blue") {
            robot.servo.setPosition(1);
            idle();
            sleep(500);
            telemetry.addData("", "Blue Detected");
            telemetry.update();
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        sleep(500);
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        stop();
    }
}




