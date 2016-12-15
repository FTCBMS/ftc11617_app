package org.firstinspires.ftc.teamcode;

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
 * Created by grigg on 12/3/2016.
 */

public abstract class TeamCode {
    Push robot = new Push();
    ColorSensor rgbs = null;
    public static boolean pressedY;
    public static final int AUTONOMOUS_P_SENSITIVITY = 250;
    public static final int AUTONOMOUS_STOP_POINT = -200;
    public static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    public static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    public static final double DRIVE_SPEED = 0.4;
    public static final double TURN_SPEED = 0.3;
    ElapsedTime runtime = new ElapsedTime();
    public static enum Color {
        RED,
        BLUE
    };
    public static void pushBeacon(AutonomousOpMode op, TeamCode.Color teamColor, boolean shoot) {
        op.robot.tankDrive(0);
        whole_thing:
        while (op.opModeIsActive()) {
            double status = 0;
            int i = 0;
            for (VuforiaTrackable beac : op.beacons) {
                if (i < 4) {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();
                    if (pose != null) {
                        if (status == 0 && shoot) {
                            op.robot.launcher.setPower(1);
                            status = 1;
                        }
                        VectorF translation = pose.getTranslation();
                        // telemetry.addData("X offset", translation.get(0));
                        // telemetry.addData("Y offset", translation.get(1));
                        op.telemetry.addData("Z offset", translation.get(2));
                        op.telemetry.update();
                        double positionOnScreen = translation.get(1); // = translation.get(0) for upright phones
                        // ^^ IMPORTANT ^^: phone must be right-side-down or it will move away from the picture!
                        if (translation.get(2) < TeamCode.AUTONOMOUS_STOP_POINT) { // If z axis (distance) > ~8in (approx.)
                            double adj = positionOnScreen / TeamCode.AUTONOMOUS_P_SENSITIVITY;
                            adj = Math.min(0.2, Math.max(-0.2, adj));
                            op.robot.rightMotor.setPower(-0.2 - adj);
                            op.robot.leftMotor.setPower(-0.2 + adj);
                        } else {
                            op.robot.rightMotor.setPower(0);
                            op.robot.leftMotor.setPower(0);
                            break whole_thing;
                        }
                        op.telemetry.update();
                        op.sleep(100);
                    } else {
                        op.telemetry.addData("No image found", "");
                        op.robot.tankDrive(0);
                    }
                }
            }
            op.telemetry.update();
            op.idle();
        }
        op.telemetry.update();
        String color = getColorNameFromValues(op.rgbs.red(), op.rgbs.green(), op.rgbs.blue());

        op.telemetry.addData("Red", op.rgbs.red());
        op.telemetry.addData("Green", op.rgbs.green());
        op.telemetry.addData("Blue", op.rgbs.blue());
        op.telemetry.addData("Clear", op.rgbs.alpha());
        op.telemetry.addData("Color", color);
        //telemetry.update();
        op.idle();
        if (color == "red") {
            int pos = (teamColor == Color.BLUE ? 1 : -1);
            op.robot.servo.setPosition(pos);
            op.idle();
            op.sleep(500);
            op.telemetry.addData("", "Red Detected");
            op.telemetry.update();
            op.robot.leftMotor.setPower(-0.2);
            op.robot.rightMotor.setPower(-0.2);
            op.sleep(1000);
            op.robot.leftMotor.setPower(0);
            op.robot.rightMotor.setPower(0);
            if (shoot) {
                op.robot.sweepAndElevator.setPower(1);
            }
            op.sleep(3500);
        }
        if (color == "blue") {
            int pos = (teamColor == Color.BLUE ? -1 : 1);
            op.robot.servo.setPosition(pos);
            op.idle();
            op.sleep(500);
            op.telemetry.addData("", "Blue Detected");
            op.telemetry.update();
            op.robot.leftMotor.setPower(-0.2);
            op.robot.rightMotor.setPower(-0.2);
            op.sleep(1000);
            op.robot.leftMotor.setPower(0);
            op.robot.rightMotor.setPower(0);
            if (shoot) {
                op.robot.sweepAndElevator.setPower(1);
            }
            op.sleep(3500);
        }
        if (shoot) {
            op.robot.sweepAndElevator.setPower(0);
            op.robot.launcher.setPower(0);
        }
    }
    public double clamp(double x, double min, double max) {
        return Math.min(max, Math.max(min, x));
    }
    public static String getColorNameFromValues(int r, int g, int b) {
        //if (r >= 8 && g >= 8 && b >= 8) {
        //    return "white";
        //}
        if (r >= 2) {
            return "red";
        } else if (b >= 2) {
            return "blue";
        }//else if (r <= 3 && g <= 3 && b <= 3) {
        // return "black";
        //}
        else {
            return "other";
        }
    }
}
