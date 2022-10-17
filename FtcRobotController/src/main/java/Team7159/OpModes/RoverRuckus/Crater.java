//package Team7159.OpModes.RoverRuckus;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//
//import java.util.List;
//
//import Team7159.LegacyRobots.VacuumBot;
//import Team7159.Enums.Direction;
//import Team7159.Enums.Side;
//
//@Autonomous(name="Crater")
//@Disabled
//public class Crater extends LinearOpMode{
//
//    VacuumBot robot = new VacuumBot();
//
//    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
//    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
//    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
//
//    Side side;
//
//    /*
//     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
//     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
//     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
//     * web site at https://developer.vuforia.com/license-manager.
//     *
//     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
//     * random data. As an example, here is a example of a fragment of a valid key:
//     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
//     * Once you've obtained a license key, copy the string from the Vuforia web site
//     * and paste it in to your code on the next line, between the double quotes.
//     */
//    private static final String VUFORIA_KEY = "AQ8rpDD/////AAABmRNIMKzPaEhBgamlRTL2RRMKI6zA+IW8Qqd6l0v65fwa8N2l" +
//            "n17xMthqidBc7uWyTNA1pYUodjK8ngEvudjz4FeoJbQpXxwYm2/H5XXwlWywZfUHa74lGuma90fRmTuEeFwAsDTZ4JfXojf719" +
//            "wTliDCdlKCOkQuuvU0Cx0JyzdYT/NnOYZWroHx2maby73wQW1T76aSlKsHE/cZ1FmVoOokP+HqIfaOPpUR/gVkDgqyB7XAOaBd" +
//            "kHzY3FOv7oCC7Ybn7jbAdVuJX8uow08atIH0dwmS/LC6BqpakDGFNj4JAyyd9cnz43UWBMEz6cTBk9Um2+a/5XLfV+W7RaHDEF" +
//            "fs726qLAIagk9Nd2CzIg/x";
//
//    /**
//     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
//     * localization engine.
//     */
//    private VuforiaLocalizer vuforia;
//
//    /**
//     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
//     * Detection engine.
//     */
//    private TFObjectDetector tfod;
//
//    @Override
//    public void runOpMode(){
//        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
//        // first.
//        initVuforia();
//
//        initTfod();
//        /** Wait for the game to begin */
//        telemetry.addData(">", "Press Play to start tracking");
//        telemetry.update();
//        robot.init(hardwareMap);
//        waitForStart();
//
//        robot.AMotor.setPower(0.2);
//        try {
//            wait(1000);
//            robot.AMotor.setPower(0);
//            robot.strafe(Direction.RIGHT,0.4,0.2);
//            robot.AMotor.setPower(-0.2);
//            wait(1000);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        /** Activate Tensor Flow Object Detection. */
//        if (tfod != null) {
//            tfod.activate();
//        }
//
//        while (side != null) {
//            if (tfod != null) {
//                // getUpdatedRecognitions() will return null if no new information is available since
//                // the last time that call was made.
//                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//                if (updatedRecognitions != null) {
//                    telemetry.addData("# Object Detected", updatedRecognitions.size());
//                    if (updatedRecognitions.size() == 3) {
//                        int goldMineralX = -1;
//                        int silverMineral1X = -1;
//                        int silverMineral2X = -1;
//                        for (Recognition recognition : updatedRecognitions) {
//                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
//                                goldMineralX = (int) recognition.getLeft();
//                            } else if (silverMineral1X == -1) {
//                                silverMineral1X = (int) recognition.getLeft();
//                            } else {
//                                silverMineral2X = (int) recognition.getLeft();
//                            }
//                        }
//                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
//                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
//                                telemetry.addData("Gold Mineral Position", "Left");
//                                side = Side.LEFT;
//                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
//                                telemetry.addData("Gold Mineral Position", "Right");
//                                side = Side.RIGHT;
//                            } else {
//                                telemetry.addData("Gold Mineral Position", "Center");
//                                side = Side.CENTER;
//                            }
//                        }
//                    }
//                    telemetry.update();
//                }
//            }
//        }
//
//        try {
//            if (side == Side.LEFT) {
//                telemetry.addData("Strafing", "On the Left side");
//                robot.strafe(Direction.LEFT, 0.5, 2);
//            } else if (side == Side.RIGHT) {
//                telemetry.addData("Strafing", "On the Right side");
//                robot.strafe(Direction.RIGHT,0.5, 2);
//            } else if (side == Side.CENTER) {
//                telemetry.addData("Center", "No need to strafe");
//            }
//            telemetry.update();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////        robot.driveDir(Direction.FORWARDS,0.5);
////        robot.turn(Direction.LEFT,90);
////        robot.driveDir(Direction.FORWARDS,1);
////        robot.turn(Direction.LEFT,45);
////        robot.driveDir(Direction.FORWARDS,2);
////        //Write some code to let down the marker
////        robot.driveDir(Direction.BACKWARDS,3.5);
//
//    }
//
//    /**
//     * Initialize the Vuforia localization engine.
//     */
//    private void initVuforia() {
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         */
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
//    }
//
//    /**
//     * Initialize the Tensor Flow Object Detection engine.
//     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
//    }
//
//    public void strafe(Direction direction, double power, double time){
//        if(direction == Direction.LEFT){
//            robot.LFMotor.set(-power);
//            robot.RFMotor.set(power);
//            robot.LBMotor.set(power);
//            robot.RBMotor.set(-power);
//            sleep((int)time * 1000);
//        }else if(direction == Direction.RIGHT){
//            robot.LFMotor.set(power);
//            robot.RFMotor.set(-power);
//            robot.LBMotor.set(-power);
//            robot.RBMotor.set(power);
//            sleep((int)time * 1000);
//        }else{
//            //Throw an exception about the wrong side
//        }
//    }
//
//    public void stopMotors(){
//        robot.RFMotor.set(0);
//        robot.RBMotor.set(0);
//        robot.LFMotor.set(0);
//        robot.LBMotor.set(0);
//    }
//
//}