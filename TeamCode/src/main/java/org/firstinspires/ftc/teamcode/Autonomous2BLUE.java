package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by grigg on 12/10/2016.
 */
@Autonomous(name="Autonomous2_BLUE", group="Plan B")
public class Autonomous2BLUE extends AutonomousOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        enableLaunchMode();

        disableLaunchMode();

        sleep(25000);//WAIT TO MOVE FORWARD

        enableEncoders();
        encoderDrive(DRIVE_SPEED, 20.0, 20.0, 8.0);//FORWARD TO CENTER
        sleep(200);

//        encoderDrive(TURN_SPEED, 5.0, -5.0, 20.0);//LEFT TURN
//        sleep(200);
//
//        encoderDrive(DRIVE_SPEED, 28.0, 28.0, 80.0);//FORWARD TO RAMP
//        sleep(200);
//        disableEncoders();
//        robot.tankDrive(0.1);
//        sleep(30000);
    }
}
