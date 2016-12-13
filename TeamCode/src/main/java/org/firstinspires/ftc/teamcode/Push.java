package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 * <p>
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 * <p>
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 * <p>
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Push {
    public static final double MID_SERVO = 0.5;
    public static final double ARM_UP_POWER = 0.45;
    public static final double ARM_DOWN_POWER = -0.45;
    /* Public OpMode members. */
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor sweepAndElevator = null;
    public DcMotor launcher = null;
    //public DcMotor launcherPart2 = null;
    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public Servo servo = null;

    /* Constructor */
    // Robbie: is that function really needed?
    public Push() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor = hwMap.dcMotor.get("left_drive");
        rightMotor = hwMap.dcMotor.get("right_drive");
        sweepAndElevator = hwMap.dcMotor.get("sweeper");
        launcher = hwMap.dcMotor.get("launcher");
       // launcherPart2 = hwMap.dcMotor.get("launcher2");

        servo = hwMap.servo.get("servo");

        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        launcher.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
      //  launcherPart2.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        sweepAndElevator.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors

        leftMotor.setPower(0);
        rightMotor.setPower(0);
        launcher.setPower(0);
       // launcherPart2.setPower(0);
        sweepAndElevator.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //launcherPart2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sweepAndElevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        servo.setPosition(0.5);

        // Define and initialize ALL installed servos.
        //leftClaw = hwMap.servo.get("left_hand");
        //rightClaw = hwMap.servo.get("right_hand");
        //leftClaw.setPosition(MID_SERVO);
        //rightClaw.setPosition(MID_SERVO);
    }

    /***
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void tankDrive(double leftMotorPower, double rightMotorPower) {
        leftMotor.setPower(leftMotorPower);
        rightMotor.setPower(rightMotorPower);
    }
    public void tankDrive(double motorPower) { // Function overloading
        leftMotor.setPower(motorPower);
        rightMotor.setPower(motorPower);
    }
    public void stop() {
        tankDrive(0);
    }
    public void waitForTick(long periodMs) throws InterruptedException {

        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

