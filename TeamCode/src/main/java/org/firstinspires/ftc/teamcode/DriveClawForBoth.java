package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Team7159.ComplexRobots.Christopher;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Everything Bagel")

public class DriveClawForBoth extends LinearOpMode {

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

        //// TODO WHO IS THE DRIVER
        int driver = 1;

        //// TODO WHO IS THE CLAW
        int claw = 2;

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            armMotorRotationCurrentPos = robot.armMotor.getCurrentPosition();
            clawMotorCurrentPow = robot.servoClaw.getPosition();

            telemetry.addData("Claw pos: ", robot.servoClaw.getPosition());
            telemetry.addData("Arm pos: ", robot.armMotor.getCurrentPosition());

//            rControl[driver].drive();
//
//            rControl[claw].claw();

            // Andrew: Driver, Gautam: Claw, Krish: Claw, Noam: Driver
            // 0: Andrew, 1: Gautam, 2: Krish, 3: Noam
            testControl[3].drive();
            testControl[2].claw();

            telemetry.update();

        }
    }

    // Interface of drive and claw();

    // This is the definitive best if
    // every person will drive *and* claw
    interface ControlScheme {
        void drive();

        void claw();
    }

    //// 0: Andrew, 1: Gautam, 2: Krish, 3: Noam
    private final ControlScheme[] testControl = new ControlScheme[]{
//      Andrew
        new ControlScheme() {
            // Andrew is a driver
            @Override
            public void drive() {
                powRX1 = gamepad1.right_stick_x;
                powRY1 = gamepad1.right_stick_y;
                powLX1 = gamepad1.left_stick_x;
                powLY1 = gamepad1.left_stick_y;

                //Use triggers tp determine
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
            public void claw() {
                robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition() + (gamepad2.y ? 1 : 0) - (gamepad2.a ? 1 : 0));

                robot.servoClaw.setPosition(clawMotorCurrentPow + (gamepad2.right_bumper ? 0.1f : 0.0f) - (gamepad2.left_bumper ? 0.1f : 0.0f));
            }
        },
//      Gautam
        new ControlScheme() {
            @Override
            public void drive() {
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
                robot.octoStrafe(gamepad1.right_stick_y > 0, gamepad1.right_stick_y < 0, gamepad1.right_stick_x > 0, gamepad1.right_stick_x < 0);
            }

            //            Gautam is a claw operator
            @Override
            public void claw() {
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
        },
//      Krish
        new ControlScheme() {
            @Override
            public void drive() {
                robot.RFMotor.setPower(robot.RFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.RBMotor.setPower(robot.RBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.LFMotor.setPower(robot.LFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.LBMotor.setPower(robot.LBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
                robot.pivotTurn(1, gamepad1.right_stick_x > 0, gamepad1.right_stick_x < 0);
                robot.octoStrafe(gamepad1.left_stick_y > 0, gamepad1.left_stick_y < 0,
                        gamepad1.left_stick_x > 0, gamepad1.left_stick_x < 0);
                braking = 1 - (gamepad1.b ? 1 : 0);
                robot.RFMotor.setPower(robot.RFMotor.getPower() * braking);
                robot.RBMotor.setPower(robot.RBMotor.getPower() * braking);
                robot.LFMotor.setPower(robot.LFMotor.getPower() * braking);
                robot.LBMotor.setPower(robot.LBMotor.getPower() * braking);
            }

            //            Krish is a claw operator
            @Override
            public void claw() {
                if (gamepad2.x) {
                    robot.armMotor.setPower(0.5);
                    robot.armMotor.setTargetPosition(robot.armPosMid);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.servoArm2.setPosition(robot.servoPosMid);
                } else if (gamepad2.y) {
                    robot.armMotor.setPower(0.5);
                    robot.armMotor.setTargetPosition(robot.armPosHigh);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.servoArm2.setPosition(robot.servoPosMid);
                } else if (gamepad2.a) {
                    robot.armMotor.setPower(0.5);
                    robot.armMotor.setTargetPosition(robot.armPosGround);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.servoArm2.setPosition(robot.servoPosMid);
                } else if (gamepad2.b) {
                    robot.armMotor.setPower(0.5);
                    robot.armMotor.setTargetPosition(robot.armPosLow);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.servoArm2.setPosition(robot.servoPosMid);
                } else {
                    robot.armMotor.setPower(0);
                }

                if (gamepad2.right_bumper) {
                    robot.servoClaw.setPosition(0.7);
                } else if (gamepad2.left_bumper) {
                    robot.servoClaw.setPosition(0);
                }

//            TODO: Add current position thresholds
//            if (!armToggle && gamepad2.right_bumper) {
//                armToggle = true;
//            }
//            if (armToggle && gamepad2.right_bumper) {
//                armToggle = false;
//            }
//            if (armToggle) {
//                robot.servoClaw.setPosition(0.7);
//            } else {
//                robot.servoClaw.setPosition(0);
//            }

                if (gamepad2.right_trigger > 0.1) {
                    robot.armMotor.setPower(0.5);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                } else if (gamepad2.left_trigger > 0.1) {
                    robot.armMotor.setPower(-0.5);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                } else {
                    robot.armMotor.setPower(0);
                }

                robot.pivotTurn(1, gamepad1.right_bumper, gamepad1.left_bumper);

                robot.octoStrafe(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right);
                telemetry.update();
            }
        },
//      Noam
        new ControlScheme() {
            // Noam is a driver
            @Override
            public void drive() {
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
            @Override
            public void claw() {
                // arm position
                robot.armMotor.setTargetPosition(armMotorRotationCurrentPos + (int) (gamepad2.left_trigger * 10) - (int) (gamepad2.right_trigger * 10));

                // claw presets
                robot.servoClaw.setPosition(gamepad2.x ? 0.0 : clawMotorCurrentPow);
                robot.servoClaw.setPosition(gamepad2.y ? 0.5 : clawMotorCurrentPow);
                robot.servoClaw.setPosition(gamepad2.a ? 1.0 : clawMotorCurrentPow);
            }
        }
    };
    private final ControlScheme[] rControl = new ControlScheme[] {
//            new ControlScheme(){
//                public void drive(){
//                    powL = gamepad1.left_stick_y;
//                    powR = gamepad1.right_stick_y;
//                    robot.RFMotor.setPower(-powR);
//                    robot.RBMotor.setPower(-powR);
//                    robot.LFMotor.setPower(-powL);
//                    robot.LBMotor.setPower(-powL);
//                    robot.octoStrafe(false,false,gamepad1.x,gamepad1.b);
//                }
//
//                public void claw(){
//                    robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+(gamepad2.y?1:0)-(gamepad2.a?1:0));
//
//                    robot.servoClaw.setPosition(clawMotorCurrentPow+(gamepad2.right_bumper?0.1f:0.0f)-(gamepad2.left_bumper?0.1f:0.0f));
//                }
//            },
//
//            new ControlScheme() {
//                public void drive(){
//                    accel = gamepad1.left_stick_y;
//                    rotate = gamepad1.left_stick_x;
//                    rightRatio = 0.5 - (0.5 * rotate);
//                    leftRatio = 0.5 + (0.5 * rotate);
//                    //Declares the maximum power any side can have
//
//                    //If we're turning left, the right motor should be at maximum power, so it decides the maxRatio. If we're turning right, vice versa.
//                    if (rotate < 0) {
//                        maxRatio = 1 / rightRatio;
//                    } else {
//                        maxRatio = 1 / leftRatio;
//                    }
//
//
//                    //Uses maxRatio to max out the motor ratio so that one side is always at full power.
//                    powR = rightRatio * maxRatio;
//                    powL = leftRatio * maxRatio;
//                    //Uses left trigger to determine slowdown.
//                    robot.RFMotor.setPower(-powR * accel);
//                    robot.RBMotor.setPower(-powR * accel);
//                    robot.LFMotor.setPower(-powL * accel);
//                    robot.LBMotor.setPower(-powL * accel);
//                    robot.octoStrafe(gamepad1.right_stick_y>0,gamepad1.right_stick_y<0,gamepad1.right_stick_x>0,gamepad1.right_stick_x<0);
//                }
//                public void claw(){
//                    robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+((gamepad2.left_stick_x!=0||gamepad2.left_stick_y!=0)?1:0)-((gamepad2.right_stick_x!=0||gamepad2.right_stick_y!=0)?1:0));
//                    robot.servoClaw.setPosition(clawMotorCurrentPow+(gamepad2.right_bumper?0.1f:0.0f)-(gamepad2.left_bumper?0.1f:0.0f));
//                }
//            },
//
//            new ControlScheme() {
//                public void drive() {
//                    robot.RFMotor.setPower(robot.RFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
//                    robot.RBMotor.setPower(robot.RBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
//                    robot.LFMotor.setPower(robot.LFMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
//                    robot.LBMotor.setPower(robot.LBMotor.getPower() + gamepad1.right_trigger - gamepad1.left_trigger);
//                    robot.pivotTurn(1, gamepad1.right_stick_x>0, gamepad1.right_stick_x<0);
//                    robot.octoStrafe(gamepad1.left_stick_y>0, gamepad1.left_stick_y<0,
//                            gamepad1.left_stick_x>0, gamepad1.left_stick_x<0);
//                    braking = 1 - (gamepad1.b ? 1 : 0);
//                    robot.RFMotor.setPower(robot.RFMotor.getPower()*braking);
//                    robot.RBMotor.setPower(robot.RBMotor.getPower()*braking);
//                    robot.LFMotor.setPower(robot.LFMotor.getPower()*braking);
//                    robot.LBMotor.setPower(robot.LBMotor.getPower()*braking);
//                }
//                public void claw() {
//                    // arm rotation
//                    robot.armMotor.setTargetPosition(armMotorRotationCurrentPos+((gamepad2.left_stick_x!=0||gamepad2.left_stick_y!=0)?1:0)-((gamepad2.right_stick_x!=0||gamepad2.right_stick_y!=0)?1:0));
//
//                    // claw toggle
//                    if (gamepad2.right_bumper && !previousClaw) {
//                        clawToggle = !clawToggle;
//                    }
//                    previousClaw = !clawToggle;
//
//                    if (!clawToggle && gamepad2.right_bumper && clawMotorCurrentPow >= 0) {
//                        clawToggle = true;
//                    }
//                    if (clawToggle && gamepad2.right_bumper && clawMotorCurrentPow <= clawOpen) {
//                        clawToggle = false;
//                    }
//
//                    // preset heights
//                    robot.armMotor.setTargetPosition(gamepad2.a ? ground : armMotorRotationCurrentPos);
//                    robot.armMotor.setTargetPosition(gamepad2.b ? low : armMotorRotationCurrentPos);
//                    robot.armMotor.setTargetPosition(gamepad2.x ? mid : armMotorRotationCurrentPos);
//                    robot.armMotor.setTargetPosition(gamepad2.y ? high : armMotorRotationCurrentPos);
//                }
//            },
//
//            new ControlScheme() {
//                public void drive() {
//                    accel = gamepad1.right_trigger-gamepad1.left_trigger;
//                    rotate = (gamepad1.left_bumper ? 1 : 0)-(gamepad1.right_bumper ? 1 : 0);
//                    rightRatio = 0.5 - (0.5 * rotate);
//                    leftRatio = 0.5 + (0.5 * rotate);
//                    //Declares the maximum power any side can have
//
//                    //If we're turning left, the right motor should be at maximum power, so it decides the maxRatio. If we're turning right, vice versa.
//                    if (rotate < 0) {
//                        maxRatio = 1 / rightRatio;
//                    } else {
//                        maxRatio = 1 / leftRatio;
//                    }
//
//                    //Uses maxRatio to max out the motor ratio so that one side is always at full power.
//                    powR = rightRatio * maxRatio;
//                    powL = leftRatio * maxRatio;
//                    //Uses left trigger to determine slowdown.
//                    robot.RFMotor.setPower(-powR * accel);
//                    robot.RBMotor.setPower(-powR * accel);
//                    robot.LFMotor.setPower(-powL * accel);
//                    robot.LBMotor.setPower(-powL * accel);
//                    robot.octoStrafe(gamepad2.dpad_up,gamepad2.dpad_down,gamepad2.dpad_left,gamepad2.dpad_right);
//                }
//
//                public void claw(){
//                    // arm position
//                    robot.armMotor.setTargetPosition(armMotorRotationCurrentPos+(int)(gamepad2.left_trigger*10)-(int)(gamepad2.right_trigger*10));
//
//                    // claw presets
//                    robot.servoClaw.setPosition(gamepad2.x?0.0:clawMotorCurrentPow);
//                    robot.servoClaw.setPosition(gamepad2.y?0.5:clawMotorCurrentPow);
//                    robot.servoClaw.setPosition(gamepad2.a?1.0:clawMotorCurrentPow);
//                }
//            }
    };
}