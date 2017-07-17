package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by grigg on 12/10/2016.
 */
@Autonomous(name="Autonomous2_REDNOCAP", group="Plan B")
public class Autonomous2NOCAP extends AutonomousOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        enableLaunchMode();

        disableLaunchMode();

        enableEncoders();
        encoderDrive(DRIVE_SPEED, 2, 2, 80.0);//FORWARD TO CENTER
        sleep(200);
        encoderDrive(DRIVE_SPEED, -1.5, 1.5, 80.0);//FORWARD TO CENTER
        sleep(200);

        sleep(11000);//WAIT TO MOVE FORWARD

        enableEncoders();
        encoderDrive(DRIVE_SPEED, 20.0, 20.0, 80.0);//FORWARD TO CENTER
        sleep(200);

        encoderDrive(TURN_SPEED, -5.0, 5.0, 20.0);//LEFT TURN
        sleep(200);

        encoderDrive(DRIVE_SPEED, 20.0, 20.0, 80.0);//FORWARD TO RAMP
        sleep(200);
        disableEncoders();
        robot.tankDrive(0.1);
        sleep(30000);
    }
}
