package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by cicada05 on 12/9/16.
 */
@Autonomous(name = "RedSide",group = "Auto")
public class RedSide extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //drive
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    //particle intake/uptake/shooter
    DcMotor inTake;
    DcMotor upTake;
    DcMotor flicker;
    //servos
    Servo reload;
    Servo beaconOne;
    Servo beaconTwo;

    int value = 1;
    int encoderValue = 0;
    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // Gyro integrated heading
    int angleZ = 0;


    ModernRoboticsI2cGyro gyro;

    double motorOne;
    double motorTwo;
    double motorThree;
    double motorFour;
    double distanceTraveled;
    double currentTime;



    public void runOpMode() {

        roboInit();

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        gyro.calibrate();

        while (!isStopRequested() && gyro.isCalibrating()) {
            sleep(50);
            idle();
        }

        telemetry.addData(">", "Gyro Calibrated.  Press Start.");
        telemetry.update();

        runtime.reset();

        waitForStart();

        while(opModeIsActive()) {

            telemetry.addData("Status", "Run Time: " + runtime.toString());
           switchCase();

            debug();

            idle();

        }


    }


    public void roboInit(){

        frontLeft  = hardwareMap.dcMotor.get("FRONT_LEFT");
        frontRight = hardwareMap.dcMotor.get("FRONT_RIGHT");
        backLeft   = hardwareMap.dcMotor.get("BACK_LEFT");
        backRight  = hardwareMap.dcMotor.get("BACK_RIGHT");
        flicker    = hardwareMap.dcMotor.get("FLICKER");
        inTake     = hardwareMap.dcMotor.get("INTAKE");
        upTake     = hardwareMap.dcMotor.get("UPTAKE");
        beaconOne  = hardwareMap.servo.get("BEACON_ONE");
        beaconTwo  = hardwareMap.servo.get("BEACON_TWO");
        reload     = hardwareMap.servo.get("RELOAD");
    }

    public void switchCase(){

        switch(value){
           //this is just for an example
            case 1: drive(-0.5, 0.5, -0.5, 0.5, 2650 );
                break;
            case 2: delay(0.5);value++;encoderValue = frontRight.getCurrentPosition();
                break;
            case 3: drive(-0.5, -0.5 , 0.5, 0.5, 3000);
                break;
            case 4: delay(0.5);value++;encoderValue = frontRight.getCurrentPosition();
                break;
            case 5:drive(-0.5, 0.5, -0.5, 0.5, 5250);
                break;
            case 6:delay(0.5);value++;encoderValue = frontRight.getCurrentPosition();
                break;
            case 7:drive(0.5, 0.5, -0.5, -0.5, 6500);
                break;
            case 8:delay(0.5);value++;encoderValue = frontRight.getCurrentPosition();
                break;
            case 9:drive(0.5, -0.5, 0.5, -0.5, 8000);
                break;
            default:frontLeft.setPower(0.0);frontRight.setPower(0.0);backLeft.setPower(0.0);backRight.setPower(0.0);
                break;

        }

    }

    public void drive(double motor1, double motor2, double motor3, double motor4, int distance){

        double gyroOffset = (gyro.getIntegratedZValue()*0.03)-0.00;

        motorOne           = motor1;
        motorTwo           = motor2;
        motorThree         = motor3;
        motorFour          = motor4;
        distanceTraveled   = distance;

        if(Math.abs(frontRight.getCurrentPosition() - encoderValue) <= distance) {
            frontRight.setPower(motorOne);
            frontLeft.setPower(motorTwo);
            backRight.setPower(motorThree);
            backLeft.setPower(motorFour);
        }
        else {
            frontRight.setPower(0.0);
            frontLeft.setPower(0.0);
            backRight.setPower(0.0);
            backLeft.setPower(0.0);
            encoderValue = frontRight.getCurrentPosition();
            value++;
        }

       /* if (Math.abs(frontRight.getCurrentPosition() - encoderValue) <= distance) {
            frontRight.setPower(motorOne + gyroOffset);
            frontLeft.setPower(motorTwo + gyroOffset);
            backRight.setPower(motorThree + gyroOffset);
            backLeft.setPower(motorFour + gyroOffset);
        } else {
            frontRight.setPower(0.0);
            frontLeft.setPower(0.0);
            backRight.setPower(0.0);
            backLeft.setPower(0.0);
            encoderValue = frontRight.getCurrentPosition();
            value++;
        }
        */
    }

    public void delay(double time){

        currentTime = getRuntime();

        while(getRuntime() - currentTime < time){

            frontLeft.setPower(0.0);
            frontRight.setPower(0.0);
            backLeft.setPower(0.0);
            backRight.setPower(0.0);

        }

    }

    public void gyrostuff(){





    }

    public void debug(){

        telemetry.addData("FRONT_RIGHT", frontRight.getCurrentPosition());
        telemetry.addData("DISTANCE", distanceTraveled);
        telemetry.addData("WHAT_IS_COMPARED", Math.abs(frontRight.getCurrentPosition()-encoderValue));
        telemetry.addData("CASE", value);

    }
}

