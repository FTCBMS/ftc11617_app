package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by grigg on 12/7/2016.
 */

public abstract class AutonomousOpMode extends LinearOpMode {
    boolean pressed;
    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.4;
    static final double TURN_SPEED = 0.3;
    Push robot = new Push();
    VuforiaTrackables beacons;
    ColorSensor rgbs = null;
    ElapsedTime runtime = new ElapsedTime();
    VuforiaLocalizer.Parameters params;
    VuforiaLocalizer vuforia;

    public void enableEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void disableEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initVuforia() {
        params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfQTqo//////AAAAGavZWqzkWk2doCWRXf8Y5Vc3FRE8eVl+2UyeBBNtFWIOdD1y0yVVXsz9vSOQKnpzt/QTHaHe+wQ/ulCYHGMxLWC7rtTBI7+bmWCTlOm8Sz9iZLiQAZZxedoDVzoPjTepbhHJBipbxmUrqPhpp/cyIAqqP0w9pfGzX+0r7aJP8RU2Fvayqe5pr6B3WK91sHOkuhL0SV6bQGqjcnetWvgBs+pDJm/PQon8QKQXw3w0cbJhyd+P2w1Gr92w+ZX6ctJh0AkCKz4KIvkh6fd1ND/qNGmp0mDHwOhIwuZIIeNjmBDYYIOfWH3l4F4HQHWmVGhIwV19woHA0PyCWJODyXCdSt3olvtBSz3cJj42AFIRXITI";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Legos");
        beacons.get(3).setName("Gears");
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
//                                            ^^ LEAVE THESE HERE
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
    public void enableLaunchMode() {
        robot.launcher.setPower(1);
        sleep(3500);
        robot.sweepAndElevator.setPower(1);
        sleep(3500);
        pressed = true;
    }
    public void disableLaunchMode() {
        robot.launcher.setPower(0);
        robot.sweepAndElevator.setPower(0);
        pressed = false;
    }
}
