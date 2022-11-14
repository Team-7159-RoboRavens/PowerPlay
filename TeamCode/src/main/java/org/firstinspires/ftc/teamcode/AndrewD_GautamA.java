package org.firstinspires.ftc.teamcode;
/*
    Gamepad 1
        X - Strafe left
        B - Strafe Right
        Left Joystick - Power right side
        Right Joystick - Power left side
    Gamepad 2
        Right Joystick - Arm Down(no matter what direction)
        Left Joystick - Arm Up(no matter what direction)
        Right Bumper - Open claw
        Left Bumper - close claw
     */

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Andrew(D)-Gautam(A) PowerPlay TeleOp")
public class AndrewD_GautamA extends LinearOpMode {

    private final Christopher robot = new Christopher();

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        double motorPower;
        double armPower;

        double powRX1;
        double powRY1;
        double powLX1;
        double powLY1;

        double powRX2;
        double powRY2;
        double powLX2;
        double powLY2;

        while (opModeIsActive()) {
            //General Telemetry
            telemetry.addData("Claw pos: ", robot.servoClaw.getPosition());
            telemetry.addData("Arm pos: ", robot.armMotor.getCurrentPosition());

            //Andrew Code
            powRX1 = gamepad1.right_stick_x;
            powRY1 = gamepad1.right_stick_y;
            powLX1= gamepad1.left_stick_x;
            powLY1 = gamepad1.left_stick_y;

            //Use triggers tp determine
            if(powRX1 >= 0.1 || powRX1 <= -0.1) {
                motorPower = (-powRX1) * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if(powRY1 >= 0.1 || powRY1 <= -0.1) {
                motorPower = powRY1 * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if (powLX1 >= 0.1 || powLX1 <= -0.1) {
                motorPower = (powLX1) * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if(powLY1 >= 0.1 || powLY1 <= -0.1) {
                motorPower = -powLY1 * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            }

            robot.octoStrafe(false, false, gamepad1.x, gamepad1.b);


            // Gautam code

            powRX2 = gamepad2.right_stick_x;
            powRY2 = gamepad2.right_stick_y;
            powLX2 = gamepad2.left_stick_x;
            powLY2 = gamepad2.left_stick_y;

            //Use triggers tp determine
            if(powRX2 >= 0.1 || powRX2 <= -0.1 || powRY2 >= 0.1 || powRY2 <= -0.1) {
                armPower = -1;
                robot.armMotor.setPower(armPower);
            } else if(powLX2 >= 0.1 || powLX2 <= -0.1 || powLY2 >= 0.1 || powLY2 <= -0.1) {
                armPower = 1;
                robot.armMotor.setPower(armPower);
            }

            if(gamepad2.right_bumper){
                robot.servoClaw.setPosition(0.7);
            }
            if(gamepad2.left_bumper){
                robot.servoClaw.setPosition(0);
            }

            telemetry.update();
        }


    }
}