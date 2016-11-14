package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
@Autonomous(name="EEAUTO", group="Vuforia")
@Disabled
public class EEAUTO extends LinearOpMode {
    RMHardwarePushbot robot = new RMHardwarePushbot();
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    boolean blueTeam = false;
    boolean redTeam = false;
    boolean position1 = false;
    boolean position2 = false;
    boolean position3 = false;
    boolean position4 = false;
    boolean wheelsImage = false;
    boolean toolsImage = false;
    boolean legosImage = false;
    boolean gearsImage = false;
    boolean centerVortex = false;
    boolean cornerVortex = false;

    public EEAUTO(boolean blue, boolean red, boolean pos1, boolean pos2, boolean pos3, boolean pos4, boolean wheels, boolean tools, boolean legos, boolean gears, boolean center, boolean corner){
        blueTeam = blue;
        redTeam = red;
        position1 = pos1;
        position2 = pos2;
        position3 = pos3;
        position4 = pos4;
        wheelsImage = wheels;
        toolsImage = tools;
        legosImage = legos;
        gearsImage = gears;
        centerVortex = center;
        cornerVortex = corner;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfQTqo//////AAAAGavZWqzkWk2doCWRXf8Y5Vc3FRE8eVl+2UyeBBNtFWIOdD1y0yVVXsz9vSOQKnpzt/QTHaHe+wQ/ulCYHGMxLWC7rtTBI7+bmWCTlOm8Sz9iZLiQAZZxedoDVzoPjTepbhHJBipbxmUrqPhpp/cyIAqqP0w9pfGzX+0r7aJP8RU2Fvayqe5pr6B3WK91sHOkuhL0SV6bQGqjcnetWvgBs+pDJm/PQon8QKQXw3w0cbJhyd+P2w1Gr92w+ZX6ctJh0AkCKz4KIvkh6fd1ND/qNGmp0mDHwOhIwuZIIeNjmBDYYIOfWH3l4F4HQHWmVGhIwV19woHA0PyCWJODyXCdSt3olvtBSz3cJj42AFIRXITI";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        if (gamepad1.left_bumper) {
            blueTeam = true;
            telemetry.addData("", "BLUE TEAM");
        }
        if (gamepad1.right_bumper) {
            redTeam = true;
            telemetry.addData("", "RED TEAM");
        }
        if (gamepad1.dpad_up) {
            position1 = true;
            telemetry.addData("STARt", "POSITION 1");
        }
        if (gamepad1.dpad_left) {
            position2 = true;
            telemetry.addData("START", "POSITION 2");
        }
        if (gamepad1.dpad_right) {
            position3 = true;
            telemetry.addData("START", "POSITION 3");
        }
        if (gamepad1.dpad_down) {
            position4 = true;
            telemetry.addData("START", "POSITION 4");
        }
        if (gamepad1.a) {
            toolsImage = true;
            telemetry.addData("", "TOOLS");
        }
        if (gamepad1.b) {
            legosImage = true;
            telemetry.addData("", "LEGOS");
        }
        if (gamepad1.x) {
            gearsImage = true;
            telemetry.addData("", "GEARS");
        }
        if (gamepad1.y) {
            wheelsImage = true;
            telemetry.addData("", "WHEELS");
        }
        if (gamepad1.left_trigger == 1) {
            centerVortex = true;
            telemetry.addData("", "CENTER VORTEX");
        }
        if (gamepad1.right_trigger == 1) {
            cornerVortex = true;
            telemetry.addData("", "CORNER VORTEX");
        }




        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");
/*
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        // The following awesome code is created by Robbie Moore.
        // Copyright (c) 2016 Robbie Moore.
        beacons.activate();
        encoderDrive(TURN_SPEED,   5, -5, 4.0);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        whole_thing:while (opModeIsActive()) {
            robot.rightMotor.setPower(0.2);
            robot.leftMotor.setPower(0.2);
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
                        double adjust = positionOnScreen / 70;
                        adjust = clamp(adjust, -0.2, 0.3);
                        if (translation.get(2) < -300) { // If z axis (distance) > ~8in (approx.)
                            robot.rightMotor.setPower(0.4 - adjust);
                            robot.leftMotor.setPower(0.4 + adjust);
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
                        }
                        /*int idles = 0;
                        for (int j=0;j<idles;j++) {
                            idle();
                        }
                        sleep(200);
                    } else {
                        telemetry.addData("No image found", "");
                        robot.leftMotor.setPower(0.2);
                        robot.rightMotor.setPower(0.2);
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

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

        }
    */
    }
}