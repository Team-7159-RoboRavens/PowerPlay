package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Manual Keybinds")

public class Noam_ChristopherTeleOp extends LinearOpMode {

    public Christopher robot = new Christopher();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        //Stuff to do
        //TODO: Add More Strafe Speeds

        //Stuff Done but not tested
        //TODO: Ducking Motor
        //TODO: Bumper Rotate
        //TODO: Octostrafe
        //TODO: Intake Toggles
        //TODO: Rotate Output

        //Stuff Done

        //drive controls
        double accel;
        double rotate;
        double powR;
        double powL;
        boolean strafeL;
        boolean strafeR;

        double maxPower = 0.05;

        //intake
        boolean clawActive = false;

        boolean previousClaw = false;
        int intakeTarget = 0;
        double intakeDrivePower = -1;
        //robot.linearSlidesDrive.resetEncoder();

        boolean clawToggle = false;
        double servoPos = robot.bucketTiltServo.getPosition();
        boolean inversed = false;

        // TODO: CHANGE MAGIC NUMBERS BASED ON READOUTS
        // none of these are currently correct
        final int clawOpen = -390;
        final int armRaised = 750;
        final int high = 0;
        final int mid = 0;
        final int low = 0;
        final int ground = 0;

        while (opModeIsActive()) {

            // KEYBIND MAPS
            //// 0: Turn Left, 1: Turn Right, 2: Turn Either, 3: Strafe Left, 4: Strafe Right, 5: Strafe L/R, 6: Drive Forward, 7: Drive Backward, 8: Drive Either, 9: Slow Forward, 10: Slow Backward
            //// 11: Power Left, 12: Power Right, 13: Increase Speed, 14: Decrease Speed, 15: Brake, 16: Strafe Forward, 17: Strafe Back, 18: Strafe F/B
            Float[] currentDrive = new Float[]{gamepad1.left_bumper ? 1.0f : 0.0f, gamepad1.right_bumper ? 1.0f : 0.0f, null, gamepad1.dpad_left ? 1.0f : 0.0f, gamepad1.dpad_right ? 1.0f : 0.0f, null,
                    gamepad1.right_trigger, gamepad1.left_trigger, null, gamepad1.y ? 1.0f : 0.0f, gamepad1.a ? 1.0f : 0.0f, gamepad1.x ? 1.0f : 0.0f, gamepad1.b ? 1.0f : 0.0f, null, null, null, null, null, null, null, null};
            //Testing Bucket
            int clawMotorRotationCurrentPos = robot.intakeMotorRotation.getCurrentPosition();
            int armMotorRotationCurrentPos = robot.armRotation.getCurrentPosition();

            // Claw

            //Claw Open
            if (gamepad2.a && clawMotorRotationCurrentPos >= 0) {
                //open claw
            }
            //Claw Halfway
            if (gamepad2.y && clawMotorRotationCurrentPos <= clawOpen / 2) {
                //half claw
            }
            //Close Claw
            if (gamepad2.x && clawMotorRotationCurrentPos <= clawOpen) {
                //close claw
            }

            // Arm

            //Arm Up
            if (gamepad2.left_trigger > 0.1 && armMotorRotationCurrentPos <= armRaised) {
                //arm up
            }
            //Arm Down
            if (gamepad2.right_trigger > 0.1 && armMotorRotationCurrentPos > 0) {
                //arm down
            }

            //Driving Code

            //Left Stick--Acceleration
            accel = gamepad1.right_trigger - gamepad1.left_trigger;
            if(gamepad1.y&&gamepad1.a)
                accel=(float)((gamepad1.y ? 1 : 0) - (gamepad1.a ? 1 : 0))/2;

            //Left Stick--Rotation
            rotate = (gamepad1.left_bumper ? 1 : 0) - (gamepad1.right_bumper ? 1 : 0);
            if(gamepad1.x&&gamepad1.b)
                rotate=(float)((gamepad1.x ? 1 : 0) - (gamepad1.b ? 1 : 0))/2;


            //JN: Doesn't really handle simultaneous input well, if you want SOCD cleaning check for both buttons being pushed at the same time
//            if (gamepad1.right_bumper){
//                right = true;
//            } else if (gamepad1.left_bumper){
//                left = true;
//            }
//            else{
//                left = false;
//                right = false;
//            }

            //Determines ratio of motor powers (by sides) using the right stick
            double rightRatio = 0.5 - (0.5 * rotate);
            double leftRatio = 0.5 + (0.5 * rotate);
            //Declares the maximum power any side can have
            double maxRatio;

            //If we're turning left, the right motor should be at maximum power, so it decides the maxRatio. If we're turning right, vice versa.
            if (rotate < 0) {
                maxRatio = 1 / rightRatio;
            } else {
                maxRatio = 1 / leftRatio;
            }


            //Uses maxRatio to max out the motor ratio so that one side is always at full power.

            powR = rightRatio * maxRatio;
            powL = leftRatio * maxRatio;

            //Uses left trigger to determine slowdown.
            robot.RFMotor.setPower(-powR * accel);
            robot.RBMotor.setPower(-powR * accel);
            robot.LFMotor.setPower(-powL * accel);
            robot.LBMotor.setPower(-powL * accel);


//            robot.pivotTurn(1, gamepad1.left_bumper, gamepad1.right_bumper);
            //Strafing controls (thanks Nick)
            robot.octoStrafe(gamepad1.dpad_up,gamepad1.dpad_down,gamepad1.dpad_left,gamepad1.dpad_right);
            telemetry.update();

        }
    }
}