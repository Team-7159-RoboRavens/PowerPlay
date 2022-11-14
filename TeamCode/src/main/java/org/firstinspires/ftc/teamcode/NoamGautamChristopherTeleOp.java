package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="org.firstinspires.ftc.teamcode.NoamGautamChristopherTeleOp")
public class NoamGautamChristopherTeleOp extends LinearOpMode {

    //y - Slow strafe left
    //x - Slow forward
    //a - Slow backward
    //b  - Slow right
    //D-Pad - Directional Strafing
    //RB - Rotate Right
    //LB - Rotate Left
    //RT - Move Forward
    //LT - Move back

    private Christopher robot = new Christopher();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        double slowPower = 0.25;
        double regPower = 1.0;

        double accel;
        double rotate;
        double powR;
        double powL;

        while (opModeIsActive()) {

            telemetry.addData("Claw pos: ", robot.servoClaw.getPosition());
            telemetry.addData("Arm 2 pos: ", robot.servoArm2.getPosition());
            telemetry.addData("Arm pos: ", robot.armMotor.getCurrentPosition());
            //Noam driving teleop \/
            if (gamepad1.x) {
                robot.moveLeft(slowPower);
            }
            else if (gamepad1.y) {
                robot.moveStraight(slowPower);
            }
            else if (gamepad1.a) {
                robot.moveBackwards(slowPower);
            }
            else if (gamepad1.b) {
                robot.moveRight(slowPower);
            }
            else {
                robot.stop();
            }

            if(gamepad1.right_trigger > 0.1) {
                robot.moveStraight(regPower);
            }
            else if(gamepad1.left_trigger > 0.1) {
                robot.moveBackwards(regPower);
            }
            else {
                robot.stop();
            }

            //Noam TeleOp /\
            //Gautam Teleop \/
            if(gamepad2.right_bumper) {
                robot.servoClaw.setPosition(0.7);
            }else if(gamepad2.left_bumper){
                robot.servoClaw.setPosition(0);
            }
            //arm up
            //TODO: figure out which direction is positive and change the multiplication
            if(gamepad2.left_stick_x > 0.1 || gamepad2.left_stick_x < 0.1 || gamepad2.left_stick_y > 0.1 || gamepad2.left_stick_y < 0.1){
                robot.armMotor.setPower(Math.max(Math.abs(gamepad2.left_stick_x), Math.abs(gamepad2.left_stick_y))*-0.5);
            }else{
                robot.armMotor.setPower(0);
            }
            //arm down
            if(gamepad2.right_stick_x > 0.1 || gamepad2.right_stick_x < 0.1 || gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < 0.1) {
                robot.armMotor.setPower(Math.max(Math.abs(gamepad2.right_stick_x), Math.abs(gamepad2.right_stick_y)) * 0.5);
            }else{
                robot.armMotor.setPower(0);
            }
            //Gautam Teleop /\
            robot.pivotTurn(1, gamepad1.right_bumper, gamepad1.left_bumper);
            robot.octoStrafe(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right);

            telemetry.update();

        }
    }
}