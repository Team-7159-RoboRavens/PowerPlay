package Team7159.BasicRobots;

import com.arcrobotics.ftclib.hardware.motors.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.motors.*;
import com.qualcomm.robotcore.hardware.*;

import Team7159.Enums.Direction;
import Team7159.Enums.Version;
import Team7159.Utils.MotorGroup;
import Team7159.Utils.RobotComp;
import Team7159.Utils.RobotMath;

public class BasicTank {

    public RobotComp Comp = new RobotComp();

    public MotorGroup Right;
    public MotorGroup Left;


    /*!!NOTE!!
    // Either only the four motors with forward and back or the two motors with just sides will be initialized for each robot
    // This is because we can either do a four wheel drive or a two wheel drive
    //
    */
    public DcMotor RFMotor;
    public DcMotor RBMotor;
    public DcMotor LFMotor;
    public DcMotor LBMotor;

    public DcMotor LMotor;
    public DcMotor RMotor;

    int drive;

    public void init4Drive(HardwareMap Map){

        drive = 4;

        LFMotor = Map.dcMotor.get("FLDrive");
        LBMotor = Map.dcMotor.get("BLDrive");
        RFMotor = Map.dcMotor.get("FRDrive");
        RBMotor = Map.dcMotor.get("BRDrive");

        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LFMotor.setPower(0.0);
        LBMotor.setPower(0.0);
        RFMotor.setPower(0.0);
        RBMotor.setPower(0.0);

        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        LBMotor.setDirection(DcMotor.Direction.REVERSE);

    }

    public void init2Drive(HardwareMap Map){

        drive = 2;

        LMotor = Map.dcMotor.get("LDrive");
        RMotor = Map.dcMotor.get("RDrive");

        LMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LMotor.setPower(0);
        RMotor.setPower(0);

        RMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

//    This stuff is all deprecated by the FTCLib update, not sure if we're ever gonna refactor this
    public void moveStraight(double power){
        Left.setPowers(power);
        Right.setPowers(power);
    }

    public void stop(){
        Left.stop();
        Right.stop();
    }

    public void turn(Direction direction, int degrees){
        Right.resetEncoders();
        Left.resetEncoders();
        int LeftDistance = Comp.computeTurningPos(direction, degrees, Direction.LEFT, 26.5, Version.TWO);
        int RightDistance = Comp.computeTurningPos(direction, degrees, Direction.RIGHT, 26.5, Version.TWO);
        Left.setTargetPosition(LeftDistance);
        Right.setTargetPosition(RightDistance);
        Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        moveStraight(0.5);
        while(Left.isBusy()&&Right.isBusy()){}
        stop();
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void driveDir(Direction direction, double distance){
        Right.resetEncoders();
        Left.resetEncoders();
        Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        switch(direction){
            case FORWARDS:
                int pos = -Comp.computePositionD(RobotMath.toMeters(distance), Version.TWO);
                Right.setTargetPosition(pos);
                Left.setTargetPosition(pos);
                break;
            case BACKWARDS:
                pos = Comp.computePositionD(RobotMath.toMeters(distance), Version.TWO);
                Right.setTargetPosition(pos);
                Left.setTargetPosition(pos);
                break;
        }
        moveStraight(0.5);
        while(Right.isBusy()&&Left.isBusy()){}
        stop();
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
