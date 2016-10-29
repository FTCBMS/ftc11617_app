package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
@Autonomous(name="RMVO", group="Vuforia")
public class RMVO extends LinearOpMode {
    RMHardwarePushbot robot = new RMHardwarePushbot();
    @Override
    public void runOpMode() throws InterruptedException {
        //robot.init(hardwareMap);
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfQTqo//////AAAAGavZWqzkWk2doCWRXf8Y5Vc3FRE8eVl+2UyeBBNtFWIOdD1y0yVVXsz9vSOQKnpzt/QTHaHe+wQ/ulCYHGMxLWC7rtTBI7+bmWCTlOm8Sz9iZLiQAZZxedoDVzoPjTepbhHJBipbxmUrqPhpp/cyIAqqP0w9pfGzX+0r7aJP8RU2Fvayqe5pr6B3WK91sHOkuhL0SV6bQGqjcnetWvgBs+pDJm/PQon8QKQXw3w0cbJhyd+P2w1Gr92w+ZX6ctJh0AkCKz4KIvkh6fd1ND/qNGmp0mDHwOhIwuZIIeNjmBDYYIOfWH3l4F4HQHWmVGhIwV19woHA0PyCWJODyXCdSt3olvtBSz3cJj42AFIRXITI";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");


        waitForStart();
        // The following awesome code is created by Robbie Moore.
        // Copyright (c) 2016 Robbie Moore.
        beacons.activate();
        while (opModeIsActive()) {
            //robot.rightMotor.setPower(0);
            //robot.leftMotor.setPower(0);
            int i = 0;
            for (VuforiaTrackable beac : beacons) {
                if (i == 0) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();
                    if (pose != null) {
                        VectorF translation = pose.getTranslation();
                        telemetry.addData(beac.getName() + "-Translation", translation);
                        telemetry.addData("X offset", translation.get(0));
                        telemetry.addData("Y offset", translation.get(1));
                        telemetry.addData("Z offset (?)", translation.get(2));
                        double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));
                        telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);
                        double positionOnScreen = translation.get(1); // = translation.get(0) for upright phones
                        // ^^ IMPORTANT ^^: phone must be right-side-down or it will move away from the picture!
                        double adjust = positionOnScreen / 250;
                        adjust = clamp(adjust, 0, 0.3);
                        if (translation.get(2) > -450) { // If z axis (distance) > ~8in (approx.)
                            robot.rightMotor.setPower(0.4-adjust);
                            robot.leftMotor.setPower(0.4+adjust);
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
                        int idles = 0;
                        for (int j=0;j<idles;j++) {
                            idle();
                        }
                    }else{
                        telemetry.addData("No image found", "");
                    }
                }
                i++;
            }
            telemetry.update();
            idle();
        }
        stop();
    }
    public double clamp(double x, double min, double max) {
        return Math.min(max, Math.max(min, x));
    }
}