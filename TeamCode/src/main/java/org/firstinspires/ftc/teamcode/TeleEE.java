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
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TeleOp w/ Sweeper, Elevator, and Launcher", group="11617 TeleOp")
//@Disabled
public class TeleEE extends LinearOpMode {

    /* Declare OpMode members. */
    Push robot = new Push();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    // could also use HardwarePushbotMatrix class.
    //   double          clawOffset      = 0;                       // Servo mid position
    //  final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo
    double left = 0;
    double right = 0;
    double launch;
    double activateSweeperAndElevator;
    double reverseSweeperAndElevator;
    boolean pressed = false;

    boolean exponentialRate = false; // exponential rate disabled by default
    boolean xIsPressedLastFrame =  false;
    boolean aIsPressedLastFrame = false;
    boolean drivingBackwards = false;
    static final double MAX_POWER = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        //telemetry.addData("Remember", "Press X");
        telemetry.update();
        //to enable exponential rate, a helpful feature.
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.x && !xIsPressedLastFrame) { // When button gets pushed down;
                exponentialRate = !exponentialRate;
                telemetry.addData("Dual rate/exp. enabled", exponentialRate);
                telemetry.update();
            }
            xIsPressedLastFrame = gamepad1.x;
            if (exponentialRate) {
                left = -(gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y));
                right = -(gamepad1.right_stick_y * Math.abs(gamepad1.left_stick_y));
            }else {
                left = -gamepad1.left_stick_y;
                right = -gamepad1.right_stick_y;
            }
            launch = (gamepad2.right_trigger * MAX_POWER);
            telemetry.addData("Launcher at %launch","power!");
            telemetry.update();
            /// /activateSweeperAndElevator = gamepad2.left_trigger;
            ///reverseSweeperAndElevator = gamepad2.right_trigger;

//            if (exponentialRate)                  {
//                activateSweeperAndElevator = gamepad2.left_trigger * gamepad2.left_trigger;
//            }
            if (drivingBackwards) {
                robot.leftMotor.setPower(-right);
                robot.rightMotor.setPower(-left);
            }else{
                robot.leftMotor.setPower(left);
                robot.rightMotor.setPower(right);
            }
            robot.launcher.setPower(launch * MAX_POWER);
           // robot.launcherPart2.setPower(launch);

            if (gamepad2.left_trigger > 0) {
                if (gamepad2.left_bumper) {
                    robot.sweepAndElevator.setPower(-1);
                    robot.launcher.setPower(-0.3);
                } else {
                    robot.sweepAndElevator.setPower(1);
                }
            }
            else {
                robot.sweepAndElevator.setPower(0);
            }


//
//if (gamepad2.b && pressed == false) {
//                robot.sweepAndElevator.setPower(-activateSweeperAndElevator);
//                pressed = true;
//            }
//            if (gamepad2.b && pressed == true) {
//                robot.sweepAndElevator.setPower(activateSweeperAndElevator);
//                pressed = false;
//            }

            if(gamepad2.a) {
                robot.servo.setPosition(-1);
                telemetry.addData("Set servo to 0", "");
            }
            if (gamepad2.b) {
                robot.servo.setPosition(1);
                telemetry.addData("Set servo to 1","");
            }
            telemetry.update();

            if (gamepad1.a && !aIsPressedLastFrame) {
                drivingBackwards = !drivingBackwards;
            }
            aIsPressedLastFrame = gamepad1.a;

            // Use gamepad left & right Bumpers to open and close the claw
            //   if (gamepad1.right_bumper)
            //      clawOffset += CLAW_SPEED;
            //  else if (gamepad1.left_bumper)
            //   clawOffset -= CLAW_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
            //    clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            //robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
            //robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

            // Use gamepad buttons to move arm up (Y) and down (A)
            //if (gamepad1.y)
            //robot.armMotorPushbotAutoDriveByTime_Linear.setPower(robot.ARM_UP_POWER);
            // else if (gamepad1.a)
            //robot.armMotor.setPower(robot.ARM_DOWN_POWER);
            // else
            //robot.armMotor.setPower(0.0);

            // Send telemetry message to signify robot running;
            //    telemetry.addData("claw",  "Offset = %.2f", clawOffset);

            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);

            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);



        }

    }
}
