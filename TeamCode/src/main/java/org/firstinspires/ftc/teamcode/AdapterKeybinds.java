package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="AdapterKeybinds")

public class AdapterKeybinds extends LinearOpMode {

    public Christopher robot = new Christopher();

    //drive controls
    double accel;
    double rotate;
    double powR;
    double powL;
    double rightRatio;
    double leftRatio;
    double maxRatio;
    double braking;
    double clawMotorCurrentPow;
    int armMotorRotationCurrentPos;
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

    boolean clawToggle = false;
    boolean inversed = false;

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
            armMotorRotationCurrentPos = robot.armRotation.getCurrentPosition();
            clawMotorCurrentPow = robot.clawServo.getPosition();

//            telemetry.addData("intakePos: ", robot.intakeMotorRotation.getCurrentPosition());
//            telemetry.addData("targetPos: ", intakeTarget);
//            telemetry.addData("intakeVelocity: ", robot.intakeMotorRotation.getVelocity());
//            telemetry.addData("arm rotation: ", robot.armRotation.getCurrentPosition());
//            telemetry.addData("Bucket Servo:  ", robot.bucketTiltServo.getPosition());

            control[claw].claw();

            control[driver].drive();

            telemetry.update();

        }
    }

    interface ControlScheme {
        void drive();
        void claw();
    }

    private ControlScheme[] control = new ControlScheme[] {
        new ControlScheme(){
            public void drive(){
                powL = gamepad1.left_stick_y;
                powR = gamepad1.right_stick_y;
                robot.RFMotor.setPower(-powR);
                robot.RBMotor.setPower(-powR);
                robot.LFMotor.setPower(-powL);
                robot.LBMotor.setPower(-powL);
                robot.octoStrafe(gamepad1.y,gamepad1.a,gamepad1.x,gamepad1.b);
            }

            public void claw(){
                robot.armRotation.setTargetPosition(robot.armRotation.getCurrentPosition()+(gamepad2.y?1:0)-(gamepad2.a?1:0));

                robot.clawServo.setPosition(clawMotorCurrentPow+(gamepad2.right_bumper?0.1f:0.0f)-(gamepad2.left_bumper?0.1f:0.0f));
            }
        },

        new ControlScheme() {
            public void drive(){
                accel = gamepad1.left_stick_y;
                rotate = gamepad1.left_stick_x;
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
                robot.octoStrafe(gamepad1.right_stick_y>0,gamepad1.right_stick_y<0,gamepad1.right_stick_x>0,gamepad1.right_stick_x<0);
            }
            public void claw(){
                robot.armRotation.setTargetPosition(robot.armRotation.getCurrentPosition()+((gamepad2.left_stick_x!=0||gamepad2.left_stick_y!=0)?1:0)-((gamepad2.right_stick_x!=0||gamepad2.right_stick_y!=0)?1:0));
                robot.clawServo.setPosition(clawMotorCurrentPow+(gamepad2.right_bumper?0.1f:0.0f)-(gamepad2.left_bumper?0.1f:0.0f));
            }
        },

        new ControlScheme() {
            public void drive() {
                robot.RFMotor.setPower(robot.RFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.RBMotor.setPower(robot.RBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.LFMotor.setPower(robot.LFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.LBMotor.setPower(robot.LBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.pivotTurn(1, gamepad1.right_stick_x>0, gamepad1.right_stick_x<0);
                robot.octoStrafe(gamepad1.left_stick_y>0, gamepad1.left_stick_y<0,
                        gamepad1.left_stick_x>0, gamepad1.left_stick_x<0);
                braking = 1 - (gamepad1.b ? 1 : 0);
                robot.RFMotor.setPower(robot.RFMotor.getPower()*braking);
                robot.RBMotor.setPower(robot.RBMotor.getPower()*braking);
                robot.LFMotor.setPower(robot.LFMotor.getPower()*braking);
                robot.LBMotor.setPower(robot.LBMotor.getPower()*braking);
            }
            public void claw() {
                // arm rotation
                robot.armRotation.setTargetPosition(armMotorRotationCurrentPos+((gamepad2.left_stick_x!=0||gamepad2.left_stick_y!=0)?1:0)-((gamepad2.right_stick_x!=0||gamepad2.right_stick_y!=0)?1:0));

                // claw toggle
                if (gamepad2.right_bumper && !previousClaw) {
                    clawToggle = !clawToggle;
                }
                previousClaw = !clawToggle;

                if (!clawToggle && gamepad2.right_bumper && clawMotorCurrentPow >= 0) {
                    clawToggle = true;
                }
                if (clawToggle && gamepad2.right_bumper && clawMotorCurrentPow <= clawOpen) {
                    clawToggle = false;
                }

                // preset heights
                robot.armRotation.setTargetPosition(gamepad2.a ? ground : armMotorRotationCurrentPos);
                robot.armRotation.setTargetPosition(gamepad2.b ? low : armMotorRotationCurrentPos);
                robot.armRotation.setTargetPosition(gamepad2.x ? mid : armMotorRotationCurrentPos);
                robot.armRotation.setTargetPosition(gamepad2.y ? high : armMotorRotationCurrentPos);
            }
        },

        new ControlScheme() {
            public void drive() {
                accel = gamepad1.right_trigger-gamepad1.left_trigger;
                rotate = (gamepad1.left_bumper ? 1 : 0)-(gamepad1.right_bumper ? 1 : 0);
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
                robot.octoStrafe(gamepad2.dpad_up,gamepad2.dpad_down,gamepad2.dpad_left,gamepad2.dpad_right);
            }

            public void claw(){
                // arm position
                robot.armRotation.setTargetPosition(armMotorRotationCurrentPos+(int)(gamepad2.left_trigger*10)-(int)(gamepad2.right_trigger*10));

                // claw presets
                robot.clawServo.setPosition(gamepad2.x?0.0:clawMotorCurrentPow);
                robot.clawServo.setPosition(gamepad2.y?0.5:clawMotorCurrentPow);
                robot.clawServo.setPosition(gamepad2.a?1.0:clawMotorCurrentPow);
            }
        }
    };
}