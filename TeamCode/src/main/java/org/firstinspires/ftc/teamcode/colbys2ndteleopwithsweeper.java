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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="colbys2nd teleop sweeper", group="Pushbot")
public class colbys2ndteleopwithsweeper extends OpMode{

    /* Declare OpMode members. */
    colbyPushBot robot       = new colbyPushBot(); // use the class created to define a Pushbot's hardware
    static final double INCREMENT   = 0.1;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    static final double FOUR_FIVE     = -0.125;
    static final double START     =  -0.25;
    static final double ARM_UP     =  0.0;
    // Define class members
    com.qualcomm.robotcore.hardware.Servo servo;




    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
        servo.setPosition(START);
        //if (gamepad1.right_trigger != 0) {
            //robot.sweeperMoter.setPower(1);
        //}
        //else if (gamepad1.left_trigger != 0) {
            //robot.sweeperMoter.setPower(0);
       //}

        //if (gamepad2.left_stick_y < 0) { // pressed forward
        //    robot.sweeperMoter.setPower(0.8);
        //}
        //else if (gamepad2.left_stick_y > 0) { // pressed backward
          //  robot.sweeperMoter.setPower(0);
        if (gamepad1.left_trigger != 0) {
            servo.setPosition(FOUR_FIVE);
        }

        {
            if (gamepad1.y)
        servo.setPosition(FOUR_FIVE);
        }
//om my gosh why wont this work
    /*
     * Code to run ONCE after the driver hits STOP
     */
    //@Override
   // public void stop() {
    }

}
