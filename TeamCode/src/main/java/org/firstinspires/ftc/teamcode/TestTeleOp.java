package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Test TeleOp")

public class TestTeleOp extends LinearOpMode {

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

        //y - Lower Intake(toggle)
        //a - bucket drop
        //b -  motor
        //rb/lb  - change robot heading
        //right stick L/R/F/B - Strafe
        //trigger left - rotate output out
        //trigger right - rotate output in
        //Drive and Steer - F/B/L/R left stick

        //drive controls
        double accel;
        double rotate;
        double powR;
        double powL;

        double maxPower = 0.05;

        //intake
        boolean intakeActive = false;

        boolean previousY = false;
        int intakeTarget = 0;
        double intakeDrivePower = -1;
        //robot.linearSlidesDrive.resetEncoder();

        boolean intakeToggle = false;
        double servoPos = robot.bucketTiltServo.getPosition();
        boolean inversed = false;

        //MAGIC NUMBERS
        final int intakeLowered = -390;
        final int armRaised = 750;

        boolean[] driveControls;
        boolean[] armControls=new boolean[10];

        while (opModeIsActive()) {
            //// Scheme for Drive Control Map { 0: intake motor on/off, 1: carousel counterclockwise, 2: carousel clockwise, 3: intake
            //// 4: strafe up, 5: strafe down, 6: strafe left, 7: strafe right, 8: pivot turn left, 9: pivot turn right }

            // Default:
            driveControls=new boolean[]{gamepad1.a,gamepad1.b,gamepad1.x,gamepad1.y,
                    gamepad1.dpad_up,gamepad1.dpad_down,gamepad1.dpad_left,gamepad1.dpad_right,
                    gamepad1.left_bumper,gamepad1.right_bumper};

            //// Scheme for Arm Control Map {}
            ////
            // Default:
            armControls[0]=gamepad1.a;
            armControls[1]=gamepad1.b;
            armControls[2]=gamepad1.x;
            armControls[3]=gamepad1.y;
            armControls[4]=gamepad1.dpad_up;
            armControls[5]=gamepad1.dpad_down;
            armControls[6]=gamepad1.dpad_left;
            armControls[7]=gamepad1.dpad_right;
            armControls[8]=gamepad1.left_bumper;
            armControls[9]=gamepad1.right_bumper;

            //Testing Bucket
            int intakeMotorRotationCurrentPos = robot.intakeMotorRotation.getCurrentPosition();
            //int LinSlidesDriveCurrentPos = robot.linearSlidesDrive.getCurrentPosition();
            //JN: Can probably be deleted

            telemetry.addData("intakePos: ", robot.intakeMotorRotation.getCurrentPosition());
            telemetry.addData("targetPos: ", intakeTarget);
//            telemetry.addData("intakeVelocity: ", robot.intakeMotorRotation.getVelocity());
            telemetry.addData("arm rotation: ", robot.armRotation.getCurrentPosition());
            telemetry.addData("Bucket Servo:  ", robot.bucketTiltServo.getPosition());

            // Intake Pseudocode
            /*
             * int get position
             *if intakeToggle is false and gamepad1.y is pressed:
             * intakeToggle = true
             *if intakeToggle is true and gamepad1.y is pressed:
             * intakeToggle = false
             *
             *if intakeToggle is true and intakeMotorRotation's position is not -300
             * intakeMotorRotation.set(0.3)
             *if intakeToggle is true and intakeMotorRotation's position is -300
             * intakeMotorRotation.set(0)
             *if intakeToggle is false and intakeMotorRotation's position is not 0
             * intakeMotorRotation.set(-0.5)
             *if intakeToggle is false and intakeMotorRotation's position is 0
             * intakeMotorRotation.set(0)
             */
            // Intake
            if (driveControls[3] && !previousY) {
                intakeToggle = !intakeToggle;
            }
            previousY = !intakeToggle;

            if (!intakeToggle && driveControls[3] && intakeMotorRotationCurrentPos >= 0) {
                intakeToggle = true;
            }
            if (intakeToggle && driveControls[3] && intakeMotorRotationCurrentPos <= intakeLowered) {
                intakeToggle = false;
            }

            if (intakeToggle && intakeMotorRotationCurrentPos > intakeLowered) {
                robot.intakeMotorRotation.setPower(-0.3);
            } else if (intakeMotorRotationCurrentPos <= intakeLowered && intakeToggle) {
                robot.intakeMotorRotation.setPower(0);
                if (driveControls[0]) {
                    robot.intakeMotorPower.setPower(1);
                } else{
                    robot.intakeMotorPower.setPower(-1);
                }
            }
            if (!intakeToggle && intakeMotorRotationCurrentPos < 110) {
                robot.intakeMotorRotation.setPower(0.3);
            } else if (!intakeToggle) {
                robot.intakeMotorRotation.setPower(0);
                robot.intakeMotorPower.setPower(0);
            }

            if (intakeToggle && !driveControls[0]) {
                robot.intakeMotorPower.setPower(-1);
            } else if (intakeToggle && gamepad1.a) {
                robot.intakeMotorPower.setPower(1);
            } else {
                robot.intakeMotorPower.setPower(0);
            }

            // Carousel(Duck) Motor
            if (driveControls[1]) {
                robot.carouselMotor.setPower(-1);
            } else if(driveControls[2]){
                robot.carouselMotor.setPower(1);
            }
            else {
                robot.carouselMotor.setPower(0);
            }

            //TODO: Output
            if (gamepad1.left_trigger > 0.1) {
                //Proportionality constant is a magic number
                robot.armRotation.setPower(gamepad1.left_trigger * -.75);
                if (robot.armRotation.getCurrentPosition() > armRaised){
                    robot.armRotation.setPower(0);
                }
            } else if (gamepad1.right_trigger > 0.1) {
                robot.armRotation.setPower(gamepad1.right_trigger * .5);
            } else {
                robot.armRotation.setPower(0);
            }

            // Bucket
//            if(gamepad1.left_stick_button){
//                servoPos = servoPos+.01;
//            } else if (gamepad1.right_stick_button){
//                servoPos = servoPos - .01;
//            }
//            robot.bucketTiltServo.turnToAngle(servoPos);

            //Driving Code

            //Left Stick--Acceleration
            accel = gamepad1.left_stick_y;

            //Left Stick--Rotation
            rotate = gamepad1.right_stick_x;

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


            robot.pivotTurn(1, driveControls[8], driveControls[9]);
            //Strafing controls (thanks Nick)
            robot.octoStrafe(driveControls[4], driveControls[5], driveControls[6], driveControls[7]);
            telemetry.update();

        }
    }
}