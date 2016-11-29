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


//INVERSE OF B_OutInBlue
//Out Start, In Beacon




@Autonomous(name="Out, In, Team Red", group="Vuforia: Team Red")
public class R_OutInRed extends LinearOpMode {
    RMHardwarePushbot robot = new RMHardwarePushbot();
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.4;
    ColorSensor rgbs = null;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfQTqo//////AAAAGavZWqzkWk2doCWRXf8Y5Vc3FRE8eVl+2UyeBBNtFWIOdD1y0yVVXsz9vSOQKnpzt/QTHaHe+wQ/ulCYHGMxLWC7rtTBI7+bmWCTlOm8Sz9iZLiQAZZxedoDVzoPjTepbhHJBipbxmUrqPhpp/cyIAqqP0w9pfGzX+0r7aJP8RU2Fvayqe5pr6B3WK91sHOkuhL0SV6bQGqjcnetWvgBs+pDJm/PQon8QKQXw3w0cbJhyd+P2w1Gr92w+ZX6ctJh0AkCKz4KIvkh6fd1ND/qNGmp0mDHwOhIwuZIIeNjmBDYYIOfWH3l4F4HQHWmVGhIwV19woHA0PyCWJODyXCdSt3olvtBSz3cJj42AFIRXITI";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Legos");
        beacons.get(3).setName("Gears");
        rgbs = hardwareMap.colorSensor.get("colorsensor");
        rgbs.enableLed(false);
        waitForStart();

        beacons.activate();
//       sleep(5000);
//
// robot.tankDrive(0.4);
//        sleep(300);
//        robot.tankDrive(0);
//        enableEncoders();
//        encoderDrive(TURN_SPEED, 6, -6, 4.0);
//        disableEncoders();
        robot.leftMotor.setPower(-0.7);
        robot.rightMotor.setPower(-1);
        sleep(6000);
//        robot.tankDrive(0);
//        enableEncoders();
//        encoderDrive(TURN_SPEED, 3, -3, 4.0);
//        disableEncoders();
//        robot.tankDrive(0.4);
//       sleep(3250);
//        robot.tankDrive(0);
//        enableEncoders();
//        encoderDrive(TURN_SPEED, 8, -8, 4.0);
//        disableEncoders();
        robot.tankDrive(0);
        VuforiaTracker tracker = new VuforiaTracker(telemetry, robot, beacons, this);
        telemetry.addData("Info", "Tracking started");
        telemetry.update();
        tracker.followBeacon();
        telemetry.update();
        String color = getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());

        telemetry.addData("Red", rgbs.red());
        telemetry.addData("Green", rgbs.green());
        telemetry.addData("Blue", rgbs.blue());
        telemetry.addData("Clear", rgbs.alpha());
        telemetry.addData("Color", color);
        //telemetry.update();
        //color = getColorNameFromValues(rgbs.red(), rgbs.green(), rgbs.blue());
        idle();
        if (color == "red" ) {
            robot.servo.setPosition(0);
            idle();
            telemetry.addData("", "Red Detected");
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        if (color == "blue" ){
            robot.servo.setPosition(1);
            idle();
            telemetry.addData("", "Blue Detected");
            robot.leftMotor.setPower(-0.2);
            robot.rightMotor.setPower(-0.2);
            sleep(1000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        else {
            telemetry.addData("", "No blue or red detected");
            //robot.leftMotor.setPower(-0.2);
            //robot.rightMotor.setPower(-0.2);
            sleep(2000);
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        telemetry.update();
        telemetry.update();
        sleep(10000);
        rgbs.enableLed(false);
        stop();
    }
    public void enableEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void disableEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    }


    public String getColorNameFromValues(int r, int g, int b) {
        //if (r >= 8 && g >= 8 && b >= 8) {
        //    return "white";
        //}
        if (r >= 2) {
            return "red";
        }else if (b >= 2) {
            return "blue";
        }//else if (r <= 3 && g <= 3 && b <= 3) {
        // return "black";
        //}
        else{
            return "other";
        }







    }
}