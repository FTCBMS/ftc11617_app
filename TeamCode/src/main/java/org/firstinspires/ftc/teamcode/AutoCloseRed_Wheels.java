
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Red Team: Wheels_Beacon", group="RedTeam")
@Disabled
public class AutoCloseRed_Wheels extends LinearOpMode {

    /* Declare OpMode members. */
    Push robot = new Push();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.8;
    static final double TURN_SPEED = 0.6;
    static final double WHITE = 0.3;
//not sure on the light level of GREY for the ods, so this is just a guess
    static final double REDLINE = 0.5;

    OpticalDistanceSensor odsSensor;
    TouchSensor touchSensor = null;


    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Start", "Motors starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //begining turn
        encoderDrive(TURN_SPEED, 5, -5, 4.0);
        idle();
        telemetry.addData("Turn", " executed!");

        OpticalDistanceSensor odsSensor;  // Hardware Device Object

        robot.init(hardwareMap);
        odsSensor = hardwareMap.opticalDistanceSensor.get("ods");
        odsSensor.enableLed(true);
        // wait for the start button to be pressed.
        waitForStart();
        // while the op mode is active, loop and read the light levels.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.

        runtime.reset();

        while (runtime.seconds() < 1.5) {
            robot.leftMotor.setPower(-0.6);
            robot.rightMotor.setPower(-0.6);
        }
        runtime.reset();

        robot.leftMotor.setPower(-0.6);
        robot.rightMotor.setPower(-0.6);

        while (odsSensor.getRawLightDetected() < WHITE) {
            //robot.rightMotor.setPower(-0.5); // This command doesn't need to happen in a while loop
            //robot.leftMotor.setPower(-0.5);
            // send the info back to driver station using telemetry function.
            telemetry.addData("Raw", odsSensor.getRawLightDetected());
            telemetry.addData("Normal", odsSensor.getLightDetected());

            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        telemetry.addData("Found", " the white line!");
        idle();
        idle();
           telemetry.update();
            encoderDrive(TURN_SPEED, 5, -5, 4.0);
        telemetry.addData("Turn", " executed!");
        idle();
        idle();
    telemetry.update();
        touchSensor=hardwareMap.touchSensor.get("touchsensor");
    robot.leftMotor.setPower(-0.2);
    robot.rightMotor.setPower(-0.2);
    while(!touchSensor.isPressed()) {
        idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
    }
        telemetry.addData("Beacon", " detected!");
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        /*PRESS BEACON HERE






















        */
        //Back up to cap ball
        //'Backing up', but I believe that the 11617 "Beacon Pusher" will be on the back, so @start, the sweeper is by the wall
        while (odsSensor.getLightDetected() < REDLINE){
            robot.leftMotor.setPower(.8);
            robot.rightMotor.setPower(.8);
        }
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        idle();
        telemetry.addData("Red", " cap ball hit!");
        //turn to corner
        encoderDrive(TURN_SPEED, 5, -5, 4.0);
        while (odsSensor.getLightDetected() < REDLINE) {
            robot.leftMotor.setPower(-.8);
            robot.rightMotor.setPower(-.8);
        }
        idle();
        runtime.reset();
        while (runtime.seconds() < 1) {
            robot.leftMotor.setPower(-.3);
            robot.rightMotor.setPower(-.3);
        }
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        idle();
        runtime.reset();
        while (runtime.seconds() < 5) {
            robot.sweep.setPower(-1);
            robot.elevator.setPower(-1);
        }
        stop();
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
       /* encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,  -10, 10, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout-7
        stop();
         while (opModeIsActive() && (odsSensor.getLightDetected() < WHITE_THRESHOLD)) {
            robot.leftMotor.setPower(0.8);
            robot.rightMotor.setPower(0.8);
        }
        runtime.reset();
        while ((opModeIsActive()) && (runtime.seconds() < 3) && (odsSensor.getLightDetected() == WHITE_THRESHOLD)) {
            robot.leftMotor.setPower(0.2);
            robot.rightMotor.setPower(0.2);
        }
            runtime.reset();
        if (odsSensor.getLightDetected() < WHITE_THRESHOLD) {
            while (odsSensor.getLightDetected() < WHITE_THRESHOLD) {
                robot.leftMotor.setPower(0.2);
                robot.rightMotor.setPower(-0.2);
            }
            while (runtime.seconds() < 3) {
                robot.leftMotor.setPower(0.2);
                robot.rightMotor.setPower(0.2);
            }
        }
       */
                  //encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        //robot.leftClaw.setPosition(1.0);            // S4: Stop and close the claw.
        //robot.rightClaw.setPosition(0.0);
        //sleep(1000);     // pause for servos to move


    }


     /**  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
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
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}

/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/