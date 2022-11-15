package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Everything Bagel")

public class DriveClawSeperateInterfaces extends LinearOpMode {

    public Christopher robot = new Christopher();
    //TODO Figure out if undefined variables need to be in runOpMode()
    //drive controls
    double accel;
    double rotate;
    double powR;
    double powL;
    double powRX1;
    double powLX1;
    double powRY1;
    double powLY1;
    double powRX2;
    double powLX2;
    double powRY2;
    double powLY2;
    double rightRatio;
    double leftRatio;
    double maxRatio;
    double motorPower;
    double braking;
    double clawMotorCurrentPow;
    int armMotorRotationCurrentPos;
    int armPower;

    boolean strafeL;
    boolean strafeR;//Stuff to do
    //TODO: Add More Strafe Speeds

    //Stuff Done but not tested
    //TODO: Ducking Motor
    //TODO: Bumper Rotate
    //TODO: Octostrafe
    //TODO: Intake Toggles
    //TODO: Rotate Output

    //Stuff Done

    //intake
    boolean clawActive = false;

    boolean previousClaw = false;
    int intakeTarget = 0;
    double intakeDrivePower = -1;
    //robot.linearSlidesDrive.resetEncoder();

    double slowPower = 0.25;
    double regPower = 1.0;

    boolean armToggle = false;
    boolean previousRB = false;

    boolean clawToggle = false;
    boolean inversed = false;

    int[] motorArmIntervals = new int[]{};
    int[] servoArmIntervals = new int[]{};


    // TODO: CHANGE MAGIC NUMBERS BASED ON READOUTS
    // none of these are currently correct
//    final int clawOpen = -390;
    final float clawOpen = 1.0f;
    final int armRaised = 750;
    final int high = 0;
    final int mid = 0;
    final int low = 0;
    final int ground = 0;

    @Override
    public void runOpMode() {

        //// TODO BEFORE COMPETITION, ALWAYS CHOOSE THE DRIVER AND OPERATOR

        //// 0: Andrew, 1: Gautam, 2: Krish, 3: Noam

        //// WHO IS THE DRIVER
        int driver = 1;

        //// WHO IS THE CLAW
        int claw = 2;

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            armMotorRotationCurrentPos = robot.armMotor.getCurrentPosition();
            clawMotorCurrentPow = robot.servoClaw.getPosition();

            telemetry.addData("Claw pos: ", robot.servoClaw.getPosition());
            telemetry.addData("Arm pos: ", robot.armMotor.getCurrentPosition());

            testDrive.noam();
            testClaw.krish();

            telemetry.update();

        }
    }

    // driver interface
    interface DriveScheme {
        void noam();

        void andrew();
    }

    // claw interface
    interface ClawScheme {
        void krish();

        void gautam();
    }

    private final DriveScheme testDrive = new DriveScheme() {
        @Override
        public void andrew() {
            powRX1 = gamepad1.right_stick_x;
            powRY1 = gamepad1.right_stick_y;
            powLX1 = gamepad1.left_stick_x;
            powLY1 = gamepad1.left_stick_y;

            //Use triggers to determine
            if (powRX1 >= 0.1 || powRX1 <= -0.1) {
                motorPower = (-powRX1) * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if (powRY1 >= 0.1 || powRY1 <= -0.1) {
                motorPower = powRY1 * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if (powLX1 >= 0.1 || powLX1 <= -0.1) {
                motorPower = (powLX1) * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            } else if (powLY1 >= 0.1 || powLY1 <= -0.1) {
                motorPower = -powLY1 * 0.5;
                robot.RFMotor.setPower(motorPower);
                robot.RBMotor.setPower(motorPower);
            }

            robot.octoStrafe(false, false, gamepad1.x, gamepad1.b);
        }
        @Override
        public void noam() {
            accel = gamepad1.right_trigger - gamepad1.left_trigger;
            rotate = (gamepad1.left_bumper ? 1 : 0) - (gamepad1.right_bumper ? 1 : 0);
            rightRatio = 0.5 - (0.5 * rotate);
            leftRatio = 0.5 + (0.5 * rotate);
            //Declares the maximum power any side can have

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
            robot.octoStrafe(gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right);
        }
    };
    private final ClawScheme testClaw = new ClawScheme() {
        @Override
        public void krish() {
            if (gamepad2.x) {
                robot.armMotor.setPower(0.5);
                robot.armMotor.setTargetPosition(robot.armPosMid);
                robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.servoArm2.setPosition(robot.servoPosMid);
            }
            else if (gamepad2.y) {
                robot.armMotor.setPower(0.5);
                robot.armMotor.setTargetPosition(robot.armPosHigh);
                robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.servoArm2.setPosition(robot.servoPosMid);
            }
            else if (gamepad2.a) {
                robot.armMotor.setPower(0.5);
                robot.armMotor.setTargetPosition(robot.armPosGround);
                robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.servoArm2.setPosition(robot.servoPosMid);
            }
            else if (gamepad2.b) {
                robot.armMotor.setPower(0.5);
                robot.armMotor.setTargetPosition(robot.armPosLow);
                robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.servoArm2.setPosition(robot.servoPosMid);
            }
            else {
                robot.armMotor.setPower(0);
            }

            if(gamepad2.right_bumper) {
                robot.servoClaw.setPosition(0.7);
            }else if(gamepad2.left_bumper){
                robot.servoClaw.setPosition(0);
            }
        }

        @Override
        public void gautam() {
            powRX2 = gamepad2.right_stick_x;
            powRY2 = gamepad2.right_stick_y;
            powLX2 = gamepad2.left_stick_x;
            powLY2 = gamepad2.left_stick_y;

            //Use triggers tp determine
            if (powRX2 >= 0.1 || powRX2 <= -0.1 || powRY2 >= 0.1 || powRY2 <= -0.1) {
                armPower = -1;
                robot.armMotor.setPower(armPower);
            } else if (powLX2 >= 0.1 || powLX2 <= -0.1 || powLY2 >= 0.1 || powLY2 <= -0.1) {
                armPower = 1;
                robot.armMotor.setPower(armPower);
            }

            if (gamepad2.right_bumper) {
                robot.servoClaw.setPosition(0.7);
            }
            if (gamepad2.left_bumper) {
                robot.servoClaw.setPosition(0);
            }
        }
    };
}