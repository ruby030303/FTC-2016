package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by cicada03 on 1/14/17.
 */
@Autonomous(name = "OctopusAutoCode", group = "Auto")
public class OctopusAutoCode extends LinearOpMode{

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    Servo bBLeft;
    Servo bBRight;

    int num = 1;


    public void roboInit(){

        frontRight = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft  = hardwareMap.dcMotor.get("FRONT_LEFT");
        backRight  = hardwareMap.dcMotor.get("BACK_RIGHT");
        backLeft   = hardwareMap.dcMotor.get("BACK_LEFT");



    }

    public void runOpMode(){

        roboInit();

        while(opModeIsActive()){

            switchCase();
        }

    }

    public void switchCase(){

        switch (num){

            case 1:



        }





    }
}
