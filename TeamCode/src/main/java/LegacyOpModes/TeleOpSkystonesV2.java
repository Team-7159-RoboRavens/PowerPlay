//package LegacyOpModes;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import Team7159.LegacyRobots.DR4BBotV1;
//
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV2")
//public class TeleOpSkystonesV2 extends LinearOpMode {
//
//    //We make the robot
//    private DR4BBotV1 robot = new DR4BBotV1();
//
//    @Override
//    public void runOpMode() {
//        //Initializes the hardware map (i.e. configuration)
//        robot.init(hardwareMap);
//        waitForStart();
//
//        while (opModeIsActive()) {
//
//            //Controls the lift motors
//            if(gamepad1.a) {
//                if(gamepad1.right_trigger>0.5){
//                    robot.leftLiftMotor.setPower(0.1);
//                    robot.rightLiftMotor.setPower(0.1);
//                } else {
//                    robot.leftLiftMotor.setPower(0.05);
//                    robot.rightLiftMotor.setPower(0.05);
//                }
//            }
//            if(gamepad1.y) {
//                if(gamepad1.right_trigger>0.5){
//                    robot.leftLiftMotor.setPower(0.7);
//                    robot.rightLiftMotor.setPower(0.7);
//                } else {
//                    robot.leftLiftMotor.setPower(1.0);
//                    robot.rightLiftMotor.setPower(1.0);
//                }
//            }
//            //Neutralizing case to clean in;puts
//            if(gamepad1.a && gamepad1.y) {
//                robot.leftLiftMotor.setPower(0);
//                robot.rightLiftMotor.setPower(0);
//            }
//            if(!gamepad1.a && !gamepad1.y) {
//                robot.leftLiftMotor.setPower(0);
//                robot.rightLiftMotor.setPower(0);
//            }
//
//            //Controls the lift servos (Claw thing)
//            if(gamepad1.x) {
//                robot.leftLiftServo.setPosition(0);
//                robot.rightLiftServo.setPosition(1);
//            }
//            if(gamepad1.b) {
//                robot.leftLiftServo.setPosition(1);
//                robot.rightLiftServo.setPosition(0);
//            }
//            //Input cleaning
//            if(gamepad1.a && gamepad1.b) {
//                robot.leftLiftServo.setPosition(0);
//                robot.rightLiftServo.setPosition(0);
//            }
//
//            //Left Stick--Acceleration
//            double accel = -gamepad1.left_stick_y;
//
//            //Right Stick--Rotation
//            double rotate = gamepad1.right_stick_x;
//
//            //Controls Award at Worlds 2020
//            //Determines ratio of motor powers (by sides) using the right stick
//            double rightRatio = 0.5 - (0.5 * rotate);
//            double leftRatio = 0.5 + (0.5 * rotate);
//            //Declares the maximum power any side can have
//            double maxRatio = 1;
//
//            //If we're turning left, the right motor should be at maximum power, so it decides the maxRatio. If we're turning right, vice versa.
//            if (rotate < 0) {
//                maxRatio = 1 / rightRatio;
//            } else {
//                maxRatio = 1 / leftRatio;
//            }
//
//            //Uses maxRatio to max out the motor ratio so that one side is always at full power.
//            double powR = rightRatio * maxRatio;
//            double powL = leftRatio * maxRatio;
//            //Uses left trigger to determine slowdown.
//            if(gamepad1.left_trigger>0.5) {
//                robot.RFMotor.set((powR * accel)/2);
//                robot.RBMotor.set((powR * accel)/2);
//                robot.LFMotor.set((powL * accel)/2);
//                robot.LBMotor.set((powL * accel)/2);
//            } else {
//                robot.RFMotor.set(powR * accel);
//                robot.RBMotor.set(powR * accel);
//                robot.LFMotor.set(powL * accel);
//                robot.LBMotor.set(powL * accel);
//            }
//
//            //Spinny funny
//            if(gamepad1.right_bumper) {
//                if(gamepad1.left_trigger>0.5) {
//                    robot.RFMotor.set(-0.5);
//                    robot.LFMotor.set(0.5);
//                    robot.RBMotor.set(-0.5);
//                    robot.LBMotor.set(0.5);
//                } else {
//                    robot.RFMotor.set(-1);
//                    robot.LFMotor.set(1);
//                    robot.RBMotor.set(-1);
//                    robot.LBMotor.set(1);
//                }
//            }
//            if(gamepad1.left_bumper) {
//                if(gamepad1.left_trigger>0.5) {
//                    robot.RFMotor.set(0.5);
//                    robot.LFMotor.set(-0.5);
//                    robot.RBMotor.set(0.5);
//                    robot.LBMotor.set(-0.5);
//                } else {
//                    robot.RFMotor.set(1);
//                    robot.LFMotor.set(-1);
//                    robot.RBMotor.set(1);
//                    robot.LBMotor.set(-1);
//                }
//            }
//            //Input cleaning
//            if(gamepad1.right_bumper && gamepad1.left_bumper) {
//                robot.RFMotor.set(0);
//                robot.LFMotor.set(0);
//                robot.RBMotor.set(0);
//                robot.LBMotor.set(0);
//            }
//            //Strafing controls (thanks Nick)
//            if (gamepad1.dpad_up) {
//                if (gamepad1.dpad_left) {
//                    robot.RFMotor.set(1);
//                    robot.LFMotor.set(0);
//                    robot.RBMotor.set(0);
//                    robot.LBMotor.set(1);
//                } else if (gamepad1.dpad_right) {
//                    robot.RFMotor.set(0);
//                    robot.LFMotor.set(1);
//                    robot.RBMotor.set(1);
//                    robot.LBMotor.set(0);
//                } else {
//                    robot.RFMotor.set(1);
//                    robot.LFMotor.set(1);
//                    robot.RBMotor.set(1);
//                    robot.LBMotor.set(1);
//                }
//            } else if (gamepad1.dpad_down) {
//                if (gamepad1.dpad_left) {
//                    robot.RFMotor.set(0);
//                    robot.LFMotor.set(-1);
//                    robot.RBMotor.set(-1);
//                    robot.LBMotor.set(0);
//                } else if (gamepad1.dpad_right) {
//                    robot.RFMotor.set(-1);
//                    robot.LFMotor.set(0);
//                    robot.RBMotor.set(0);
//                    robot.LBMotor.set(-1);
//                } else {
//                    robot.RFMotor.set(-1);
//                    robot.LFMotor.set(-1);
//                    robot.RBMotor.set(-1);
//                    robot.LBMotor.set(-1);
//                }
//            }
//            else {
//                if (gamepad1.dpad_left) {
//                    robot.RFMotor.set(1);
//                    robot.LFMotor.set(-1);
//                    robot.RBMotor.set(-1);
//                    robot.LBMotor.set(1);
//                } else if (gamepad1.dpad_right) {
//                    robot.RFMotor.set(-1);
//                    robot.LFMotor.set(1);
//                    robot.RBMotor.set(1);
//                    robot.LBMotor.set(-1);
//                }
//            }
//
//        }
//
//    }
//}
//
//
