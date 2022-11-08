package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="String Map")

public class StringMap extends LinearOpMode {

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
        
        // KEYBIND MAPS
        //// 0: Turn Left, 1: Turn Right, 2: Turn Either, 3: Strafe Left, 4: Strafe Right, 5: Strafe L/R, 6: Drive Forward, 7: Drive Backward, 8: Drive Either, 9: Slow Forward, 10: Slow Backward
        //// 11: Power Left, 12: Power Right, 13: Increase Speed, 14: Decrease Speed, 15: Brake, 16: Strafe Forward, 17: Strafe Back, 18: Strafe F/B
        String[] driveAndrew = new String[]{null,null,null,"x","b",null,null,null,null,null,null,"joystick-l-y","joystick-r-y",null,null,null,null,null,null};
        String[] driveGautam = new String[]{null,null,"joystick-l-x","joystick-r-x","joystick-r-x","joystick-r-x",null,null,"joystick-l-y",null,null,null,null,null,null,null,null,null,null};
        String[] driveKrish = new String[]{null,null,"joystick-r-x","joystick-l-x","joystick-l-x","joystick-l-x",null,null,null,null,null,null,null,"trigger-l","trigger-r","b",null,null,null};
        String[] driveNoam = new String[]{null,null,"bumper","dpad-l","dpad-r",null,"trigger-r","trigger-l",null,"y","a","x","b",null,null,null,null,null,null,null,null};

        //// 0: Arm Up, 1: Arm Down, 2: Open Claw, 3: Half Claw, 4: Close Claw, 5: Toggle Claw, 6: Claw Fix, 7: Preset High
        //// 8: Preset Mid, 9: Preset Low, 10: Preset Ground
        String[] armAndrew = new String[]{"y","a","bumper-r",null,"bumper-l",null,null,null,null,null,null};
        String[] armGautam = new String[]{"joystick-l","joystick-r","bumper-r",null,"bumper-l",null,null,null,null,null,null};
        String[] armKrish = new String[]{"trigger-r","trigger-l",null,null,null,"bumper-r","bumper-l","y","x","b","a"};
        String[] armNoam = new String[]{"trigger-l","trigger-r","a","y","x",null,null,null,null,null,null};

        ////// Set Driver and Arm operator prior to match
        String[] currentDrive = driveNoam;
        String[] currentArm = armAndrew;
        while (opModeIsActive()) {

            //Testing Bucket
            int clawMotorRotationCurrentPos = robot.intakeMotorRotation.getCurrentPosition();
            int armMotorRotationCurrentPos = robot.armRotation.getCurrentPosition();
            //int LinSlidesDriveCurrentPos = robot.linearSlidesDrive.getCurrentPosition();
            //JN: Can probably be deleted

//            telemetry.addData("intakePos: ", robot.intakeMotorRotation.getCurrentPosition());
//            telemetry.addData("targetPos: ", intakeTarget);
////            telemetry.addData("intakeVelocity: ", robot.intakeMotorRotation.getVelocity());
//            telemetry.addData("arm rotation: ", robot.armRotation.getCurrentPosition());
//            telemetry.addData("Bucket Servo:  ", robot.bucketTiltServo.getPosition());

            // Intake Pseudocode
            /*
             * int get position
             *if clawToggle is false and gamepad1.y is pressed:
             * clawToggle = true
             *if clawToggle is true and gamepad1.y is pressed:
             * clawToggle = false
             *
             *if clawToggle is true and intakeMotorRotation's position is not -300
             * intakeMotorRotation.set(0.3)
             *if clawToggle is true and intakeMotorRotation's position is -300
             * intakeMotorRotation.set(0)
             *if clawToggle is false and intakeMotorRotation's position is not 0
             * intakeMotorRotation.set(-0.5)
             *if clawToggle is false and intakeMotorRotation's position is 0
             * intakeMotorRotation.set(0)
             */

            // Claw

            //Claw Toggle
            if (getControl(currentArm[5])>0 && !previousClaw) {
                clawToggle = !clawToggle;
            }
            previousClaw = !clawToggle;

            if (!clawToggle && getControl(currentArm[5])>0 && clawMotorRotationCurrentPos >= 0) {
                clawToggle = true;
            }
            if (clawToggle && getControl(currentArm[5])>0 && clawMotorRotationCurrentPos <= clawOpen) {
                clawToggle = false;
            }

            //Claw Open
            if (getControl(currentArm[2])>0 && clawMotorRotationCurrentPos >= 0){
                //open claw
            }
            //Claw Halfway
            if (getControl(currentArm[3])>0 && clawMotorRotationCurrentPos <= clawOpen/2) {
                //half claw
            }
            //Close Claw
            if (getControl(currentArm[4])>0 && clawMotorRotationCurrentPos <= clawOpen) {
                //close claw
            }

            // Arm

            //Arm Up
            if(getControl(currentArm[0])>0 && armMotorRotationCurrentPos<=armRaised){
                //arm up
            }
            //Arm Down
            if(getControl(currentArm[1])>0 && armMotorRotationCurrentPos>0) {
                //arm down
            }

            // Arm Presets
            if(getControl(currentArm[7])>0 && armMotorRotationCurrentPos != high){
                armMotorRotationCurrentPos = high;
            }
            if(getControl(currentArm[8])>0 && armMotorRotationCurrentPos != mid){
                armMotorRotationCurrentPos = mid;
            }
            if(getControl(currentArm[9])>0 && armMotorRotationCurrentPos != low){
                armMotorRotationCurrentPos = low;
            }
            if(getControl(currentArm[10])>0 && armMotorRotationCurrentPos != ground){
                armMotorRotationCurrentPos = ground;
            }

            //Driving Code

            //Left Stick--Acceleration
            accel = getControl(currentDrive[8]);

            //Left Stick--Rotation
            rotate = getControl(currentDrive[2]);

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


            robot.pivotTurn(1, getControl(currentDrive[0])>0, getControl(currentDrive[1])>0);
            // Set Strafe with Joystick
            if(getControl(currentDrive[3])==getControl(currentDrive[4])){
                strafeL=getControl(currentDrive[5])>0;
                strafeR=getControl(currentDrive[5])<0;
            }
            else{
                strafeL=getControl(currentDrive[3])>0;
                strafeR=getControl(currentDrive[4])>0;
            }
            //Strafing controls (thanks Nick)
            robot.octoStrafe(getControl(currentDrive[16])>0,getControl(currentDrive[17])>0,strafeL,strafeR);
            telemetry.update();

        }
    }
    public float getControl(String str){
        switch(str) {
            case "a": return gamepad1.a ? 1.0f : 0.0f;
            case "b": return gamepad1.b ? 1.0f : 0.0f;
            case "x": return gamepad1.x ? 1.0f : 0.0f;
            case "y": return gamepad1.y ? 1.0f : 0.0f;
            case "dpad-u": return gamepad1.dpad_up ? 1.0f : 0.0f;
            case "dpad-d": return gamepad1.dpad_down ? 1.0f : 0.0f;
            case "dpad-l": return gamepad1.dpad_left ? 1.0f : 0.0f;
            case "dpad-r": return gamepad1.dpad_right ? 1.0f : 0.0f;
            case "bumper-l": return gamepad1.left_bumper ? 1.0f : 0.0f;
            case "bumper-r": return gamepad1.right_bumper ? 1.0f : 0.0f;
            case "bumper": return (gamepad1.left_bumper ? 1.0f : 0.0f)-(gamepad1.right_bumper ? 1.0f : 0.0f);
            case "trigger-l": return gamepad1.left_trigger;
            case "trigger-r": return gamepad1.right_trigger;
            case "joystick-l": return (gamepad1.left_stick_y > 0 || gamepad1.left_stick_y < 0) || (gamepad1.left_stick_x > 0 || gamepad1.left_stick_x < 0) ? 1.0f : 0.0f;
            case "joystick-l-x": return gamepad1.left_stick_x;
            case "joystick-l-y": return gamepad1.left_stick_y;
            case "joystick-r": return (gamepad1.right_stick_y > 0 || gamepad1.right_stick_y < 0) || (gamepad1.right_stick_x > 0 || gamepad1.right_stick_x < 0) ? 1.0f : 0.0f;
            case "joystick-r-x": return gamepad1.right_stick_x;
            case "joystick-r-y": return gamepad1.right_stick_y;
        }
        return 0.0f;
    }
}