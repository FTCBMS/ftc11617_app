package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by grigg on 11/12/2016.
 */
@Disabled
public class VuforiaTracker {
    Telemetry telemetry;
    RMHardwarePushbot robot;
    VuforiaTrackables beacons;
    public VuforiaTracker(Telemetry tel, RMHardwarePushbot bot, VuforiaTrackables beacs) {
        telemetry = tel;
        robot = bot;
        beacons = beacs;
    }
    public void followBeacon() throws InterruptedException {
        whole_thing: while (true) {
            int i = 0;
            for (VuforiaTrackable beac : beacons) {
                if (i < 4) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();
                    if (pose != null) {
                        VectorF translation = pose.getTranslation();
                        telemetry.addData(beac.getName() + "-Translation", translation);
                        telemetry.addData("X offset", translation.get(0));
                        telemetry.addData("Y offset", translation.get(1));
                        telemetry.addData("Z offset (?)", translation.get(2));
                        double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));
                        telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);
                        telemetry.update();
                        double positionOnScreen = translation.get(1); // = translation.get(0) for upright phones
                        // ^^ IMPORTANT ^^: phone must be right-side-down or it will move away from the picture!
                        double adjust = positionOnScreen / 170;
                        adjust = Math.max(0, Math.min(adjust, 0.175));
                        if (translation.get(2) < -400) { // If z axis (distance) > ~8in (approx.)
                            robot.rightMotor.setPower(0.2667 - adjust);
                            robot.leftMotor.setPower(0.2667 + adjust);
                        } else if (translation.get(2) < -75) {
                            robot.leftMotor.setPower((0.2667 - (adjust - 0.1 + 0.1) * 0.3));
                            robot.rightMotor.setPower((0.2667 + (adjust) - 0.1 + 0.1) * 0.3);
                        } else {
                            robot.rightMotor.setPower(0);
                            robot.leftMotor.setPower(0);
                            break whole_thing;
                        }
                        /* (positionOnScreen > 10) { // Right side of screen
                            robot.rightMotor.setPower(0.3);
                            robot.leftMotor.setPower(0.9);
                        }else if (positionOnScreen < -10 ){ // Left side of screen
                            robot.rightMotor.setPower(0.9);
                            robot.leftMotor.setPower(0.3);
                        }else{ // Near the middle
                            robot.leftMotor.setPower(0.5);
                            robot.rightMotor.setPower(0.5);
                        }*/
                        /*int idles = 0;
                        for (int j=0;j<idles;j++) {
                            idle();
                        }*/
                        Thread.sleep(150);
                    } else {
                        telemetry.addData("No image found", "");
                        robot.tankDrive(0);
                    }
                }
                i++;
            }
            telemetry.update();
            Thread.yield();
        }
        robot.tankDrive(0.4);
        Thread.sleep(500);
        robot.tankDrive(0);
    }
}
