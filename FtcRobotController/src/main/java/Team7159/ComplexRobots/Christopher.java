//package Team7159.ComplexRobots;
//
//import com.arcrobotics.ftclib.hardware.ServoEx;
//import com.arcrobotics.ftclib.hardware.SimpleServo;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.arcrobotics.ftclib.hardware.motors.MotorEx;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import Team7159.BasicRobots.BasicMecanum;
//
//public class Christopher extends BasicMecanum {
//
//}


package Team7159.ComplexRobots;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.hardware.motors.*;



import Team7159.BasicRobots.BasicMecanum;

public class Christopher extends BasicMecanum {

    public DcMotor armRotation;
    public DcMotor carouselMotor;
    public DcMotor intakeMotorPower;
    public DcMotor intakeMotorRotation;

    public Servo clawServo;

    public void init(HardwareMap Map) {

        super.init(Map);

        armRotation = Map.dcMotor.get("armRotation");
        carouselMotor = Map.dcMotor.get("carouselMotor");

        intakeMotorPower = Map.dcMotor.get("intakeMotorPower");
        intakeMotorRotation = Map.dcMotor.get("intakeMotorRotation");

        clawServo = Map.servo.get("clawServo");

        armRotation.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //linearSlidesDrive.setVeloCoefficients(0.8, 0, 0);
        carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //carouselMotor.setVeloCoefficients(0.8, 0, 0);
        intakeMotorPower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeMotorRotation.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        intakeMotorRotation.setVeloCoefficients(0.80, 0, 0);
        armRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carouselMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotorPower.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotorRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armRotation.setPower(0);
        armRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        carouselMotor.setPower(0);
        carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intakeMotorPower.setPower(0);

        intakeMotorRotation.setPower(0);
        intakeMotorRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        clawServo.scaleRange(0.0, 1.0);
        clawServo.setPosition(0);
    }
}
