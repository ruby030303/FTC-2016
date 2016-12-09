package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by cicada05 on 12/8/16.
 */
@Autonomous (name = "TheMainAuto", group = "Auto")
public class TheMainAuto extends LinearOpMode{

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    double motorOne;
    double motorTwo;
    double motorThree;
    double motorFour;
    double distanceTraveled;
    double currentTime;

    ModernRoboticsI2cGyro gyro;

    int encoderValue;
    int value;

    boolean lastResetState = false;
    boolean curResetState  = false;


    public void runOpMode(){

      roboInit();


    }

    public void  roboInit(){

        frontLeft    = hardwareMap.dcMotor.get("FRONT_LEFT");
        frontRight   = hardwareMap.dcMotor.get("FRONT_RIGHT");
        backLeft     = hardwareMap.dcMotor.get("BACK_LEFT");
        backRight    = hardwareMap.dcMotor.get("BACK_RIGHT");


    }

    public void atonomous(){

        switchCase();


    }

    public void switchCase(){




    }

    public void drive(double motor1,double motor2,double motor3,double motor4,int distance){

       motorOne = motor1;
       motorTwo = motor2;
       motorThree = motor3;
       motorFour  = motor4;
       distanceTraveled = distance;

       if(Math.abs(frontRight.getCurrentPosition() - encoderValue) <= distance){

           frontRight.setPower(motor1);
           frontLeft.setPower(motor2);
           backRight.setPower(motor3);
           backLeft.setPower(motor4);

       }

       else {

           frontRight.setPower(0.0);
           frontLeft.setPower(0.0);
           backRight.setPower(0.0);
           backLeft.setPower(0.0);
           encoderValue = frontRight.getCurrentPosition();
           value++;
       }

    }

    /*public void delay(double time){

        currentTime = getRuntime();

        while(getRuntime() - currentTime < time){

            frontLeft.setPower(0.0);
            frontRight.setPower(0.0);
            backLeft.setPower(0.0);
            backRight.setPower(0.0);

        }
*/
    }

    public void debug(){

        telemetry.addData("FRONT_RIGHT", frontRight.getCurrentPosition());
        telemetry.addData("DISTANCE", distanceTraveled);
        telemetry.addData("WHAT_IS_COMPARED",Math.abs(frontRight.getCurrentPosition()-encoderValue));
        telemetry.addData("CASE",value);


    }
}
