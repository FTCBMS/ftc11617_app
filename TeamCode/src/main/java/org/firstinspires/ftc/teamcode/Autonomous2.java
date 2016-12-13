package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by grigg on 12/10/2016.
 */
@Autonomous(name="Autonomous2_RED", group="Plan B")
public class Autonomous2 extends AutonomousOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        enableLaunchMode();

        disableLaunchMode();

        sleep(17000);//WAIT TO MOVE FORWARD

        enableEncoders();
        encoderDrive(DRIVE_SPEED, 16.0, 16.0, 80.0);//FORWARD TO CENTER
        sleep(200);

        encoderDrive(TURN_SPEED, -5.0, 5.0, 20.0);//LEFT TURN
        sleep(200);

        encoderDrive(DRIVE_SPEED, 16.0, 16.0, 80.0);//FORWARD TO RAMP
        sleep(200);
        disableEncoders();
        stop();
    }
}
