package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.GyroSensor;
/**
 * Created by TigeRobots on 11/10/2016.
 */
@TeleOp(name = "HeyCode" , group = "TeleOp")
public class HeyCode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

    }


    /*
    //drive
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    //ball uptake
    DcMotor upTake;

    //ball intake
    DcMotor inTake;

    //shooting mechanism
    DcMotor flicker;

    //beacons
    Servo buttonPress1;
    Servo buttonPress2;

    double  expoCurve      = 1.0;





    public void runOpMode(){

        roboInit();

        while (opModeIsActive()){

            drive();

        }






    }

    public void roboInit(){
        frontRight   = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft    = hardwareMap.dcMotor.get("FRONT_LEFT");
        backRight    = hardwareMap.dcMotor.get("BACK_RIGHT");
        backLeft     = hardwareMap.dcMotor.get("BACK_LEFT");
        upTake       = hardwareMap.dcMotor.get("UPTAKE");
        inTake       = hardwareMap.dcMotor.get("INTAKE");
        flicker      = hardwareMap.dcMotor.get("FLICKER");
        buttonPress1 = hardwareMap.servo.get("BUTTON_PRESS_ONE");
        buttonPress2 = hardwareMap.servo.get("BUTTON_PRESS_TWO");

    }

    double constrain(double x){

        double speed;
        speed = x;

        speed = Range.clip(speed, -1, 1);

        return speed;

    }

    public void drive(){

      frontLeft.setPower(constrain(gamepad1));



    }








*/

}
