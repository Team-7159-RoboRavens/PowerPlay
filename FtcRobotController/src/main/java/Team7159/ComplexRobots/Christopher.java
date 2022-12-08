
package Team7159.ComplexRobots;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.*;


import Team7159.BasicRobots.BasicMecanum;
import Team7159.Enums.Direction;

public class Christopher extends BasicMecanum {

    public DcMotor armMotor;
    public Servo servoClaw;
    public Servo servoArm2;


    public double armPosHigh = 10;
    public double armPosMid = 5;
    public double armPosLow = 1;
    public double armPosGround = 0;
    public double armPosBack = 0;
    public double servoPosHigh = 1.0;
    public double servoPosMid = 0.9;
    public double servoPosLow = 0.7;
    public double servoPosGround = 0.5;
    public double servoPosBack = 1.0;

    //TODO: Find new magic numbers
    public int armPosGrab = 0;
    public int armPosGnd = 0;
    public int armPosL = 0;

    public double servoClawOpen = 0.95;
    public double servoClawGrab = 0.1;

    public void init(HardwareMap Map) {

        super.init(Map);

        armMotor = Map.dcMotor.get("armMotor");
        servoClaw = Map.servo.get("servoClaw");
//        servoArm2 = Map.servo.get("servoArm2");

        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setPower(0);
//        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        servoClaw.setPosition(servoClawGrab);


//        servoArm2.setPosition(servoPosBack);
    }

    // For Autos
    public void strafeBruh(Direction direction, double power) throws InterruptedException{
        if(direction == Direction.LEFT){
            LFMotor.setPower(-power * 0.5);
            RFMotor.setPower(power);
            LBMotor.setPower(power * 0.5);
            RBMotor.setPower(-power - motorOffset);
        }else if(direction == Direction.RIGHT){
            LFMotor.setPower(power * mult);
            RFMotor.setPower(-power);
            LBMotor.setPower(-power * mult);
            RBMotor.setPower(power + motorOffset);
        }
        else if(direction == Direction.FORWARDS) {
            LFMotor.setPower(power * mult);
            RFMotor.setPower(power);
            LBMotor.setPower(power * mult);
            RBMotor.setPower(power + motorOffset);
        }
        else if(direction == Direction.BACKWARDS) {
            LFMotor.setPower(-power * mult);
            RFMotor.setPower(-power);
            LBMotor.setPower(-power * mult);
            RBMotor.setPower(-power - motorOffset);
        }
        else{
            //Throw an exception
        }
    }

    // Tile Version
    public void strafe(Direction direction, double power, double tiles) throws InterruptedException{
        int tileTime = 1000;
        if(direction == Direction.LEFT){
            LFMotor.setPower(-power * mult);
            RFMotor.setPower(power);

            LBMotor.setPower(power * mult);
            RBMotor.setPower(-power - motorOffset);
//            Thread.sleep((long)tiles * tileTime);
//            Thread.sleep((long)tiles * tileTime * (1 / (long) power));
//            stop();

        }else if(direction == Direction.RIGHT){
            LFMotor.setPower(power * mult);
            RFMotor.setPower(-power);

            LBMotor.setPower(-power * mult);
            RBMotor.setPower(power + motorOffset);
//            Thread.sleep((long)tiles * tileTime);
//            Thread.sleep((long)tiles * tileTime * (1 / (long) power));
//            stop();

        }
        else if(direction == Direction.FORWARDS) {
            LFMotor.setPower(power * mult);
            RFMotor.setPower(power);

            LBMotor.setPower(power * mult);
            RBMotor.setPower(power + motorOffset);
//            Thread.sleep((long) tiles * tileTime);
//            Thread.sleep((long)tiles * tileTime * (1 / (long) power));
//            stop();

        }
        else if(direction == Direction.BACKWARDS) {
            LFMotor.setPower(-power * mult);
            RFMotor.setPower(-power);

            LBMotor.setPower(-power * mult);
            RBMotor.setPower(-power - motorOffset);
//            Thread.sleep((long) tiles * tileTime);
//            Thread.sleep((long)tiles * tileTime * (1 / (long) power));
//            stop();

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
            LFMotor.setPower(power * mult);
            RBMotor.setPower(-power - motorOffset);
            LBMotor.setPower(power * mult);
        } else if(direction == Direction.RIGHT) {
            RFMotor.setPower(power);
            LFMotor.setPower(-power * mult);
            RBMotor.setPower(power + motorOffset);
            LBMotor.setPower(-power * mult);
        }
    }

    public void rotateBruh(Direction direction, double power) throws InterruptedException {

        if(direction == Direction.LEFT) {
            RFMotor.setPower(-power);
            LFMotor.setPower(power * mult);
            RBMotor.setPower(-power - motorOffset);
            LBMotor.setPower(power * mult);
        } else if(direction == Direction.RIGHT) {
            RFMotor.setPower(power);
            LFMotor.setPower(-power * mult);
            RBMotor.setPower(power + motorOffset);
            LBMotor.setPower(-power * mult);
        }
    }

    // Method for arm pos
    public void armPos(double power, double armPos, double servoArmPos) {
        if(armMotor.getCurrentPosition() < armPosMid) {
            while (armMotor.getCurrentPosition() <= armPosMid) {
                armMotor.setPower(power);
            }
            armMotor.setPower(0);
        }
        else {
            while (armMotor.getCurrentPosition() >= armPosMid) {
                armMotor.setPower(-power);
            }
            armMotor.setPower(0);
        }

        servoArm2.setPosition(servoPosMid);
    }

}
