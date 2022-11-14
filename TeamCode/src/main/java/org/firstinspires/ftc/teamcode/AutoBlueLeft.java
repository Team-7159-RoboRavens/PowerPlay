package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import Team7159.ComplexRobots.Christopher;
import Team7159.Enums.Direction;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "org.firstinspires.ftc.teamcode.AutoBlueLeft")
public class AutoBlueLeft extends LinearOpMode {

    private Christopher robot = new Christopher();

    // strafe(Direction direction, double power, double tiles)

    SleeveDetection sleeveDetection = new SleeveDetection();
    OpenCvCamera phoneCam;
    String webcamName = "Webcam 1";
    public int location = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.servoClaw.setPosition(0);

        // Signal Sleeve
        // Rotate Left 45 degrees
        // Drop cone on ground junction
        // Rotate Right 45 degrees
        // Strafe right, rotate right 135 degrees
        // Grab cone (ground junction preset height)
        // Rotate 135 degrees left
        // Location 1: Strafe Left, forward to location one
        // Rotate Right 90 Degrees, place cone on mid junction
        // Location 2: Strafe Left, forward to location 2
        // Rotate 45 degrees right, place cone on high junction
        // Location 3: reverse location 2

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        phoneCam.setPipeline(sleeveDetection);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240);
            }

            @Override
            public void onError(int errorCode) {}
        });

        while (!isStarted()) {
            if(sleeveDetection.getPosition() == SleeveDetection.ParkingPosition.LEFT) {
                location = 1;
            }
            else if (sleeveDetection.getPosition() == SleeveDetection.ParkingPosition.CENTER) {
                location = 2;
            }
            else {
                location = 3;
            }
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        waitForStart();


        robot.rotate(Direction.LEFT, 1, 45);
        robot.armPos(robot.armPosGround, robot.servoPosGround);
        robot.servoClaw.setPosition(0.7);
        robot.rotate(Direction.RIGHT, 1, 45);
        robot.strafe(Direction.RIGHT, 1, 1.0);
        robot.rotate(Direction.RIGHT, 1, 135);
        robot.servoClaw.setPosition(0);
        robot.rotate(Direction.LEFT, 1, 135);

        if(location == 1) {
            robot.strafe(Direction.LEFT, 1, 2);
            robot.strafe(Direction.FORWARDS, 1, 1.5);
            robot.rotate(Direction.RIGHT, 1, 90);
            robot.armPos(robot.armPosMid, robot.servoPosMid);
            robot.servoClaw.setPosition(0.7);
        }
        else if(location == 2) {
            robot.strafe(Direction.LEFT, 1, 1);
            robot.strafe(Direction.FORWARDS, 1, 2);
            robot.rotate(Direction.RIGHT, 1, 45);
            robot.armPos(robot.armPosHigh, robot.servoPosHigh);
            robot.servoClaw.setPosition(0.7);
        }
        else if(location == 3) {
            robot.strafe(Direction.LEFT, 1, 2);
            robot.strafe(Direction.FORWARDS, 1, 1.5);
            robot.rotate(Direction.RIGHT, 1, 90);
            robot.armPos(robot.armPosMid, robot.servoPosMid);
            robot.servoClaw.setPosition(0.7);
        }
    }
}
