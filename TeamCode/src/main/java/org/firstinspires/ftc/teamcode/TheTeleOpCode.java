package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by cicada03 on 12/20/16.
 */
@TeleOp (name = "TheTeleOpCode", group = "TeleOp")
public class TheTeleOpCode extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

    }

/*
    //Drive
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;


    //Particles
    DcMotor upTake;
    DcMotor inTake;
    DcMotor outTake;
    Servo   reload;
    int     currentShootPosition;
    boolean shooterButton;


    //Beacons
    Servo leftButton;
    Servo rightButton;
    boolean leftBeaconButton;
    boolean rightBeaconButton;
    boolean leftBeaconCurrent;
    boolean rightBeaconCurrent;

    //driving
    double driveY;
    double driveX;
    double driveRotate;


    //other stuff
    double currentTime;
    double expoCurve = 1.0;
    double deadZone  = 0.2;

    boolean current;
    boolean previous;



    double  loadFrontPosUp;
    double  loadFrontPosDown;
    double  loadFrontTime;
    boolean loadIsReady;
    boolean loadButton;
    boolean loadCurrentPress;
    boolean loadPreviousPress;





    public void runOpMode(){

        roboInit();

        while(opModeIsActive()){

            drive(driveRotate ,driveY ,driveX);
            driveUpDate();
            shooter();
            debug();
            reload();

        }

    }

    public void roboInit(){

        //MOTORS
        frontRight  = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft   = hardwareMap.dcMotor.get("FRONT_LEFT");
        backRight   = hardwareMap.dcMotor.get("BACK_RIGHT");
        backLeft    = hardwareMap.dcMotor.get("BACK_LEFT");
        inTake      = hardwareMap.dcMotor.get("INTAKE");
        upTake      = hardwareMap.dcMotor.get("UPTAKE");
        outTake     = hardwareMap.dcMotor.get("SHOOTER");

        //SERVOS
        rightButton = hardwareMap.servo.get("RIGHT_BUTTON");
        leftButton  = hardwareMap.servo.get("LEFT_BUTTON");
        reload      = hardwareMap.servo.get("LOAD_FRONT");

        //DRIVE TRAIN
        driveX       = gamepad1.left_stick_x;
        driveY       = gamepad1.left_stick_y;
        driveRotate  = gamepad1.right_stick_x;

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        upTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        inTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooterButton = false;

        loadFrontPosUp      = 0.0;
        loadFrontPosDown    = 1.0;
        loadFrontTime       = 0.3;
        loadIsReady         = false;
        loadButton          = false;
        loadCurrentPress    = false;
        loadPreviousPress   = false;
        leftBeaconButton    = false;
        rightBeaconButton   = false;
        leftBeaconCurrent   = false;
        rightBeaconCurrent  = false;


    }

    public void driveUpDate(){

        driveX       = gamepad1.left_stick_x;
        driveY       = gamepad1.left_stick_y;
        driveRotate  = gamepad1.right_stick_x;

    }

    public void drive(double x, double y, double z){

        backRight.setPower(expo(constrain(x + y - z),expoCurve));
        backLeft.setPower(expo(constrain(x - y  - z),expoCurve));
        frontRight.setPower(expo(constrain(x + y + z),expoCurve));
        frontLeft.setPower(expo(constrain(x - y + z),expoCurve));

    }

    private double expo(double x, double a){

        double y = x;
        y = a * Math.pow(y, 3) + (1-a)*y;
        return y;

    }

    double constrain(double x){

        double speed;
        speed = x;

        speed = Range.clip(speed, -1, 1);

        return speed;

    }

    private void reload(){

        loadButton       = gamepad1.left_bumper;
        loadCurrentPress = loadButton;

        if(loadCurrentPress && !loadPreviousPress){

            loadIsReady = true;
            currentTime = getRuntime();

        }

        if(loadIsReady){

          delayServoPosition(loadFrontTime, loadFront, loadFrontPosUp, loadFrontPosDown, loadIsReady);

        }

        else if(!loadIsReady){

            reload.setPosition(loadFrontPosDown);
        }

        loadPreviousPress = loadCurrentPress;

    }

    private void shooter(){

        shooterButton = gamepad1.right_bumper;
        currentShootPosition = inTake.getCurrentPosition();

    }

    double constrain(double x, double y, double z){

        double speed;
        double low;
        double high;
        speed   = x;
        low     = y;
        high    = z;

        speed = Range.clip(speed, low, high);

        return speed;

    }

    public void  beacons(){

        leftBeaconButton  = gamepad1.x;
        rightBeaconButton = gamepad1.y;

        leftBeaconCurrent  = leftBeaconButton;
        rightBeaconCurrent = rightBeaconButton;



    }

    private void debug() {

        telemetry.addData("MOTOR_STUFF_FR", frontRight.getPower());
        telemetry.addData("MOTOR_STUFF_FL", frontLeft.getPower());
        telemetry.addData("MOTOR_STUFF_BR", backRight.getPower());
        telemetry.addData("MOTOR_STUFF_BL", backLeft.getPower());
        telemetry.addData("FRONT_LEFT", reload.getPosition());
        telemetry.addData("IS_READY", loadIsReady);
        telemetry.addData("CURRENT_TIME",(getRuntime()- currentTime));
        telemetry.update();

    }*/
}
