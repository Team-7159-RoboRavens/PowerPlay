package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="org.firstinspires.ftc.teamcode.GyroTestTeleOp")
public class GyroTestTeleOp extends LinearOpMode {

    //y - Slow strafe left
    //x - Slow forward
    //a - Slow backward
    //b  - Slow right
    //D-Pad - Directional Strafing
    //RB - Rotate Right
    //LB - Rotate Left
    //RT - Move Forward
    //LT - Move back

    //private Christopher robot = new Christopher();

    @Override
    public void runOpMode() {

        //robot.init(hardwareMap);
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit= BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(params);
        while(opModeIsActive() && !imu.isSystemCalibrated()){
            telemetry.addData("Status", "IMU calib");
            telemetry.update();
        }
        telemetry.addData("Status", "Ready");
        telemetry.update();
        waitForStart();


        while (opModeIsActive()) {
            Orientation o =  imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            telemetry.addData("x", o.firstAngle);
            telemetry.addData("y", o.secondAngle);
            telemetry.addData("z", o.thirdAngle);
            telemetry.update();
            sleep(500);

        }
    }
}