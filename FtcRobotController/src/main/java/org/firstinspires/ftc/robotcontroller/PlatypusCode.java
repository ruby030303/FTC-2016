package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by cicada03 on 2/22/17.
 */
@Autonomous(name = "PlatypusCode", group = "Auto")
public class PlatypusCode extends LinearOpMode{

        OpticalDistanceSensor dSensor;
        Servo rightBeaconButton;
        DcMotor frontRight;
        DcMotor frontLeft;
        DcMotor backRight;
        DcMotor backLeft;
        ColorSensor beaconColor;
        double dSensorValue;
        double maxValue;
        double speed;
        double red;
        double colorOffSet;
        double out;


        public void runOpMode(){

          roboInit();
          distanceSensor();


        }

        public void roboInit(){

            maxValue = 0.7;
            speed = 1.0;
            red = 2.0;
            colorOffSet = 0.0;
            out = 1.0;
        }

        public void distanceSensor(){
            dSensorValue = dSensor.getRawLightDetected();

            while(dSensorValue < maxValue && opModeIsActive()){

                strafe(speed, "right");

            }

            robotStop();

            beaconStrafe();

        }

    public void beaconStrafe(){

        while(beaconColor.red() < red + colorOffSet && opModeIsActive()){

            strafe(speed, "right");
            debug();
        }

        rightBeaconButton.setPosition(out);

    }

    public void strafe(double speed, String direction){

        if(direction == "right"){

            frontRight.setPower(speed);
            backRight.setPower(-speed);
            frontLeft.setPower(speed);
            backLeft.setPower(-speed);

        }

        else if(direction == "left"){

            frontRight.setPower(-speed);
            backRight.setPower(speed);
            frontLeft.setPower(-speed);
            backLeft.setPower(speed);

        }

        else{

            robotStop();

        }

    }

    public void robotStop(){

        frontLeft.setPower(0.0);
    }

    public void debug(){

        telemetry.addData("CHECK_RED_COLOR", beaconColor.red());
        telemetry.addData("CHECK_BLUE_COLOR", beaconColor.blue());
        telemetry.update();
    }
}


