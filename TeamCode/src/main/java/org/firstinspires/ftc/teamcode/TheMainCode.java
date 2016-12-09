package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by cicada05 on 12/2/16.
 */
@TeleOp(name = "TheMainCode", group = "TeleOp")
public class TheMainCode extends LinearOpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor intake;
    DcMotor uptake;
    DcMotor flicker;
    DcMotor capBall;

    Servo beaconLeft;
    Servo beaconRight;

    public void runOpMode(){


        while (opModeIsActive()){

            drive();
        }

    }
    public void roboInit(){

        frontRight = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft  = hardwareMap.dcMotor.get("FRONT_LEFT");
        backRight  = hardwareMap.dcMotor.get("BACK_RIGHT");
        backLeft   = hardwareMap.dcMotor.get("BACK_LEFT");
    }
    double constrain(double x, double low, double high){

        double speed;
        speed = x;

        speed = Range.clip(speed, low, high);

        return speed;

    }


    double constrain(double x){

        double speed;
        speed = x;

        speed = Range.clip(speed, -1, 1);

        return speed;

    }




    public void drive(){

        frontLeft.setPower(constrain(gamepad1.right_stick_x - gamepad1.left_stick_x  + gamepad1.left_stick_y));
        frontRight.setPower(constrain(gamepad1.right_stick_x + gamepad1.left_stick_x  - gamepad1.left_stick_y));
        backLeft.setPower(constrain(gamepad1.right_stick_x + gamepad1.left_stick_x + gamepad1.left_stick_y));
        backRight.setPower(constrain(gamepad1.right_stick_x - gamepad1.left_stick_x - gamepad1.left_stick_y));
    }






    }

