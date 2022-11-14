package Team7159.ComplexRobots;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.hardware.motors.*;


import Team7159.BasicRobots.BasicMecanum;
import Team7159.Enums.Direction;

public class Christopher extends BasicMecanum {

    public DcMotor armMotor;
    public Servo servoClaw;
    public Servo servoArm2;

    public int armPosHigh = 10;
    public int armPosMid = 5;
    public int armPosLow = 1;
    public int armPosGround = 0;

    public int servoPosHigh = 10;
    public int servoPosMid = 5;
    public int servoPosLow = 1;
    public int servoPosGround = 0;

    public void init(HardwareMap Map) {

        super.init(Map);

        armMotor = Map.dcMotor.get("armMotor");
//        servoClaw = Map.servo.get("servoClaw");
//        servoArm2 = Map.servo.get("servoArm2");

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        servoClaw.scaleRange(0, 0.7);
//        servoClaw.setPosition(0);
//
//        servoArm2.scaleRange(0, 0.7);
//        servoArm2.setPosition(0);

    }

    // For Autos
//    public void strafe(Direction direction, double power, double time) throws InterruptedException{
//        if(direction == Direction.LEFT){
//            LFMotor.setPower(-power);
//            RFMotor.setPower(power);
//            LBMotor.setPower(power);
//            RBMotor.setPower(-power);
//            wait((int)time * 1000);
//            stop();
//        }else if(direction == Direction.RIGHT){
//            LFMotor.setPower(power);
//            RFMotor.setPower(-power);
//            LBMotor.setPower(-power);
//            RBMotor.setPower(power);
//            wait((int)time * 1000);
//            stop();
//        }
//        else if(direction == Direction.FORWARDS) {
//            LFMotor.setPower(power);
//            RFMotor.setPower(power);
//            LBMotor.setPower(power);
//            RBMotor.setPower(power);
//            wait((int) time * 1000);
//            stop();
//        }
//        else if(direction == Direction.BACKWARDS) {
//            LFMotor.setPower(-power);
//            RFMotor.setPower(-power);
//            LBMotor.setPower(-power);
//            RBMotor.setPower(-power);
//            wait((int) time * 1000);
//            stop();
//        }
//        else{
//            //Throw an exception
//        }
//    }

    // Tile Version
    public void strafe(Direction direction, double power, double tiles) throws InterruptedException{
        int tileTime = 1000;
        if(direction == Direction.LEFT){
            LFMotor.setPower(-power);
            RFMotor.setPower(power);
            LBMotor.setPower(power);
            RBMotor.setPower(-power);
            Thread.sleep((long)tiles * tileTime);
            stop();
        }else if(direction == Direction.RIGHT){
            LFMotor.setPower(power);
            RFMotor.setPower(-power);
            LBMotor.setPower(-power);
            RBMotor.setPower(power);
            Thread.sleep((long)tiles * tileTime);
            stop();
        }
        else if(direction == Direction.FORWARDS) {
            LFMotor.setPower(power);
            RFMotor.setPower(power);
            LBMotor.setPower(power);
            RBMotor.setPower(power);
            Thread.sleep((long) tiles * tileTime);
            stop();
        }
        else if(direction == Direction.BACKWARDS) {
            LFMotor.setPower(-power);
            RFMotor.setPower(-power);
            LBMotor.setPower(-power);
            RBMotor.setPower(-power);
            Thread.sleep((long) tiles * tileTime);
            stop();
        }
        else{
            //Throw an exception
        }
    }

    // Rotate angle method
    public void rotate(Direction direction, double power, double inputAngle) throws InterruptedException {
        double time90 = 1000;
        double angleTime = (inputAngle/90) * time90;

        if(direction == Direction.LEFT) {
            RFMotor.setPower(-power);
            LFMotor.setPower(power);
            RBMotor.setPower(-power);
            LBMotor.setPower(power);
            Thread.sleep((long) angleTime);
        } else if(direction == Direction.RIGHT) {
            RFMotor.setPower(power);
            LFMotor.setPower(-power);
            RBMotor.setPower(power);
            LBMotor.setPower(-power);
            Thread.sleep((long) angleTime);
        }
    }

    // Method for arm pos
    public void armPos(int armPos, int servoArmPos) {
        armMotor.setPower(0.5);
        armMotor.setTargetPosition(armPos);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        servoArm2.setPosition(servoArmPos);
    }

}