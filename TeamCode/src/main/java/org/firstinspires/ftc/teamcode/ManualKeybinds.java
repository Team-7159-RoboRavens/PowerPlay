package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Manual Keybinds")

public class ManualKeybinds extends LinearOpMode {

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
            // Andrew
            //Boolean[] currentDrive = new Boolean[]{null,null,null,"x","b",null,null,null,null,null,null,"joystick-l-y","joystick-r-y",null,null,null,null,null,null};
            // Gautam
            //Boolean[] currentDrive = new Boolean[]{null,null,"joystick-l-x","joystick-r-x","joystick-r-x","joystick-r-x",null,null,"joystick-l-y",null,null,null,null,null,null,null,null,null,null};
            // Krish
            //Boolean[] currentDrive = new Boolean[]{null,null,"joystick-r-x","joystick-l-x","joystick-l-x","joystick-l-x",null,null,null,null,null,null,null,"trigger-l","trigger-r","b",null,null,null};
            // Noam
            Float[] currentDrive = new Float[]{gamepad1.left_bumper ? 1.0f : 0.0f, gamepad1.right_bumper ? 1.0f : 0.0f, null, gamepad1.dpad_left ? 1.0f : 0.0f, gamepad1.dpad_right ? 1.0f : 0.0f, null,
                    gamepad1.right_trigger, gamepad1.left_trigger, null, gamepad1.y ? 1.0f : 0.0f, gamepad1.a ? 1.0f : 0.0f, gamepad1.x ? 1.0f : 0.0f, gamepad1.b ? 1.0f : 0.0f, null, null, null, null, null, null, null, null};

            //// 0: Arm Up, 1: Arm Down, 2: Open Claw, 3: Half Claw, 4: Close Claw, 5: Toggle Claw, 6: Claw Fix, 7: Preset High
            //// 8: Preset Mid, 9: Preset Low, 10: Preset Ground
            //Boolean[] currentArm = new Boolean[]{gamepad1.y ? 1.0f : 0.0f,gamepad1.a ? 1.0f : 0.0f,gamepad1.right_bumper ? 1.0f : 0.0f,null, gamepad1.dpad_left ? 1.0f : 0.0f,null,null,null,null,null,null};
            //Boolean[] currentArm = new Boolean[]{"joystick-l","joystick-r",gamepad1.right_bumper ? 1.0f : 0.0f,null, gamepad1.dpad_left ? 1.0f : 0.0f,null,null,null,null,null,null};
            //Boolean[] currentArm = new Boolean[]{"trigger-r","trigger-l",null,null,null,"bumper-r","bumper-l","y","x","b","a"};
            Float[] currentArm = new Float[]{gamepad2.left_trigger, gamepad2.right_trigger, gamepad2.a ? 1.0f : 0.0f, gamepad2.y ? 1.0f : 0.0f, gamepad2.x ? 1.0f : 0.0f, null, null, null, null, null, null};

            //Testing Bucket
            int clawMotorRotationCurrentPos = robot.intakeMotorRotation.getCurrentPosition();
            int armMotorRotationCurrentPos = robot.armRotation.getCurrentPosition();

            // Claw

            //Claw Toggle
            if (currentArm[5] > 0 && !previousClaw) {
                clawToggle = !clawToggle;
            }
            previousClaw = !clawToggle;

            if (!clawToggle && currentArm[5] > 0 && clawMotorRotationCurrentPos >= 0) {
                clawToggle = true;
            }
            if (clawToggle && currentArm[5] > 0 && clawMotorRotationCurrentPos <= clawOpen) {
                clawToggle = false;
            }

            //Claw Open
            if (currentArm[2] > 0 && clawMotorRotationCurrentPos >= 0) {
                //open claw
            }
            //Claw Halfway
            if (currentArm[3] > 0 && clawMotorRotationCurrentPos <= clawOpen / 2) {
                //half claw
            }
            //Close Claw
            if (currentArm[4] > 0 && clawMotorRotationCurrentPos <= clawOpen) {
                //close claw
            }

            // Arm

            //Arm Up
            if (currentArm[0] > 0 && armMotorRotationCurrentPos <= armRaised) {
                //arm up
            }
            //Arm Down
            if (currentArm[1] > 0 && armMotorRotationCurrentPos > 0) {
                //arm down
            }

            // Arm Presets
            if (currentArm[7] > 0 && armMotorRotationCurrentPos != high) {
                armMotorRotationCurrentPos = high;
            }
            if (currentArm[8] > 0 && armMotorRotationCurrentPos != mid) {
                armMotorRotationCurrentPos = mid;
            }
            if (currentArm[9] > 0 && armMotorRotationCurrentPos != low) {
                armMotorRotationCurrentPos = low;
            }
            if (currentArm[10] > 0 && armMotorRotationCurrentPos != ground) {
                armMotorRotationCurrentPos = ground;
            }

            //Driving Code

            //Left Stick--Acceleration
            if (currentDrive[8] != null)
                accel = currentDrive[8];
            else
                accel = currentDrive[6] - currentDrive[7];
            if (currentDrive[9] > 0)
                accel = currentDrive[9] / 2;
            if (currentDrive[10]>0)
                accel = -currentDrive[9] / 2;

            //Left Stick--Rotation
            if (currentDrive != null)
                rotate = currentDrive[2];
            else
                rotate = currentDrive[0] - currentDrive[1];


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
            if (currentDrive[11] != null && currentDrive[12] != null){
                powL = currentDrive[11];
                powR = currentDrive[12];
            }
            else {
                powR = rightRatio * maxRatio;
                powL = leftRatio * maxRatio;
            }
            //Uses left trigger to determine slowdown.
            robot.RFMotor.setPower(-powR * accel);
            robot.RBMotor.setPower(-powR * accel);
            robot.LFMotor.setPower(-powL * accel);
            robot.LBMotor.setPower(-powL * accel);


            robot.pivotTurn(1, currentDrive[0]>0, currentDrive[1]>0);
            // Set Strafe with Joystick
            if(currentDrive[3]==currentDrive[4]){
                strafeL=currentDrive[5]>0;
                strafeR=currentDrive[5]<0;
            }
            else{
                strafeL=currentDrive[3]>0;
                strafeR=currentDrive[4]>0;
            }
            //Strafing controls (thanks Nick)
            robot.octoStrafe(currentDrive[16]>0,currentDrive[17]>0,strafeL,strafeR);
            telemetry.update();

        }
    }
}