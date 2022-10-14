//package LegacyOpModes;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import Team7159.LegacyRobots.VacuumBotV2;
//
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpPerson2")
//public class TeleOp2Person extends LinearOpMode {
//
//    //Defines the direction of vertical movement, and to go while a button is pressed
//    //Stands for Vertical Direction
//    int vDir = 0;
//
//    //Defines the direction of horizontal movement, and to go while a button is pressed
//    //Stands for Horizontal Direction
//    int hDir = 0;
//
//    //Defines the direction of the chain rotational movement, and to go while a button is pressed
//    //Stands for chain Direction
//    int cDir = 0;
//
//    //The robot
//    private VacuumBotV2 robot = new VacuumBotV2();
//
//    public void runOpMode() {
//        //Linear Actuator direction
//        int vDir;
//        //Vacuum direction
//        int hDir;
//        //Chain direction
//
//        int cDir;
//        robot.init(hardwareMap);
//        robot.liftServo.setPosition(0.3);
//        waitForStart();
//        while (opModeIsActive()) {
//
//            //Sets the direction of the liftMotor for moving up or down
//
//            if (gamepad1.a||gamepad2.a) {
//                vDir = 2;
//            } else if (gamepad1.y||gamepad2.y) {
//                vDir = 1;
//            } else {
//                vDir = 0;
//            }
//
//            //Sets the direction of the CR servos for moving either in or out
//            //Up should be moving out, down should be moving in
//
//            if (gamepad1.dpad_up) {
//                hDir = 1;
//            } else if (gamepad1.dpad_down) {
//                hDir = 2;
//            } else {
//                hDir = 0;
//            }
//
//            //Sets the direction of the chain for moving the cleaners in and out
//            //X should be picking up, B should be shooting out
//
//            if (gamepad1.x) {
//                cDir = 1;
//            } else if (gamepad1.b) {
//                cDir = 2;
//            } else {
//                cDir = 0;
//            }
//
//            //Makes the lift motor go up and down
//
//            if (vDir == 0) {
//                robot.liftMotor.setPower(0);
//            } else if (vDir == 1) {
//                robot.liftMotor.setPower(1);
//            } else if (vDir == 2) {
//                robot.liftMotor.setPower(-1);
//            }
//
//            //Makes the rack and pinion motor go in and out
//            if (hDir == 0) {
//                robot.intakeMotor.setPower(0);
//            } else if (hDir == 1) {
//                robot.intakeMotor.setPower(0.9);
//            } else if (hDir == 2) {
//                robot.intakeMotor.setPower(-0.9);
//            }
//
//            //Makes the chain rotate, and by extension the rotater
//            if (cDir == 0) {
//                robot.chainMotor.setPower(0);
//            } else if (cDir == 1) {
//                robot.chainMotor.setPower(0.5);
//            } else if (cDir == 2) {
//                robot.chainMotor.setPower(-0.5);
//            }
//
//            if (gamepad1.right_bumper) {
//                robot.liftServo.setPosition(0.9);
//            }
//
//            if (gamepad1.right_trigger > 0.1) {
//                robot.liftServo.setPosition(0.24);
//            }
//
//            if (gamepad2.left_bumper||gamepad1.left_bumper) {
//                robot.vacuumMotor.setPower(1);
//            }
//
//            if (gamepad2.left_trigger > 0.1||gamepad1.left_trigger>0.1) {
//                robot.vacuumMotor.setPower(-1);
//            }
//
//            robot.vacuumMotor.setPower(0);
//
//            double rf = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x -gamepad2.left_stick_y - gamepad2.left_stick_x - gamepad2.right_stick_x;
//            double rb = -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x -gamepad2.left_stick_y - gamepad2.left_stick_x + gamepad2.right_stick_x;
//            double lf = -gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x -gamepad2.left_stick_y + gamepad2.left_stick_x + gamepad2.right_stick_x;
//            double lb = -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x -gamepad2.left_stick_y + gamepad2.left_stick_x - gamepad2.right_stick_x;
//
//            double maxr = Math.max(Math.abs(rf), Math.abs(rb));
//            double maxl = Math.max(Math.abs(lf), Math.abs(lb));
//
//            double max = Math.max(maxr, maxl);
//            if (max != 0 && max > 1) {
//                rf /= max;
//                rb /= max;
//                lf /= max;
//                lb /= max;
//            }
//
//            robot.RFMotor.set(rf);
//            robot.RBMotor.set(rb);
//            robot.LFMotor.set(lf);
//            robot.LBMotor.set(lb);
//        }
//    }
//}
