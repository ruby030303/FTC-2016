package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by cicada03 on 12/21/16.
 */
@TeleOp(name = "ScratchPad", group = "TeleOp")
public class ScratchPad extends LinearOpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;






    public void runOpMode(){

        roboInit();



    }

    public void roboInit(){

        frontRight = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft  = hardwareMap.dcMotor.get("FRONT_LEFT");
        backLeft   = hardwareMap.dcMotor.get("BACK_LEFT");
        backRight  = hardwareMap.dcMotor.get("BACK_RIGHT");



    }
}
