package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Andrew(D)-Krish(A) PowerPlay TeleOp")
public class AndrewKrishChristopherTeleOp extends LinearOpMode {
    private Christopher robot = new Christopher();

    @Override
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

        }
    }
}
