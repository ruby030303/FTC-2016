package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by cicada03 on 2/23/17.
 */
@Autonomous(name = "NewAutoRedCode", group = "Auto")
public class NewAutoRedCode extends LinearOpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor uptake;
    DcMotor intake;
    DcMotor shooter;

    //servo
    Servo rightButtonServo;
    Servo leftButtonServo;
    Servo loadFront;
    double out;

    //gyro
    ModernRoboticsI2cGyro gyro;

    //color sensor
    ColorSensor beaconColor;
    
    //distance sensor
    OpticalDistanceSensor dSensor;
    double dSensorValue;
    double maxValue;

    //touch sensors
    TouchSensor leftSwitch;
    TouchSensor rightSwitch;

    //encoders
    static final double     COUNTS_PER_MOTOR_REV    = 1120;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.94;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    ElapsedTime runtime                     = new ElapsedTime();

    //shooter
    double shooterSpeed;
    double shooterPosition;
    int    currentShootPosition;
    int    previousShootPosition;

    //reload
    double  loadFrontPosUp;
    double  loadFrontPosDown;
    double  loadFrontTime;
    boolean loadIsReady;

    //beacon
    double  lBeaconPositionIn;
    double  lBeaconPositionOut;
    double  rBeaconPositionIn;
    double  rBeaconPositionOut;

    //touch sensor
    boolean leftButton;
    boolean rightButton;

    //color sensor
    double colorOffset;
    double red;

    //gyro rotate
    boolean curResetState;
    boolean lastResetState;
    int     headingEncoder;
    int     headingOffset;
    double  turnPower;

    double currentTime;
    double strafeTime;
    double strafeSpeed;
    boolean quickFix;

    int    value = 1;
    
    public void roboInit(){

        frontRight  = hardwareMap.dcMotor.get("FRONT_RIGHT");
        frontLeft   = hardwareMap.dcMotor.get("FRONT_LEFT");
        backRight   = hardwareMap.dcMotor.get("BACK_RIGHT");
        backLeft    = hardwareMap.dcMotor.get("BACK_LEFT");
        intake      = hardwareMap.dcMotor.get("INTAKE");
        uptake      = hardwareMap.dcMotor.get("UPTAKE");
        shooter     = hardwareMap.dcMotor.get("SHOOTER");

        //SERVO INIT
        rightButtonServo    = hardwareMap.servo.get("RIGHT_BUTTON");
        leftButtonServo     = hardwareMap.servo.get("LEFT_BUTTON");
        loadFront           = hardwareMap.servo.get("LOAD_FRONT");

        //MOTOR REVERSE
        shooter.setDirection(DcMotor.Direction.REVERSE);

        //GYRO INIT
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("GYRO");

        //COLOR SENSOR
        beaconColor = hardwareMap.colorSensor.get("BEACON_COLOR");
        beaconColor.enableLed(false);
        colorOffset = 0;
        red       = 2.0;

        //TOUCH SENSOR
        leftSwitch  = hardwareMap.touchSensor.get("LEFT_TOUCH");
        rightSwitch = hardwareMap.touchSensor.get("RIGHT_TOUCH");

        //BEACON
        lBeaconPositionIn    = 0.3;
        lBeaconPositionOut   = 0.75;
        rBeaconPositionIn    = 0.75;
        rBeaconPositionOut   = 0.3;
        rightButtonServo.setPosition(rBeaconPositionIn);
        leftButtonServo.setPosition(lBeaconPositionIn);

        //SHOOTER
        shooterSpeed            = 1.0;
        previousShootPosition   = 0;
        shooterPosition         = 3400;

        //RELOAD VARIABLES
        loadFrontPosUp      = 0.0;
        loadFrontPosDown    = 1.0;
        loadFrontTime       = 0.5;
        loadIsReady         = false;
        loadFront.setPosition(loadFrontPosDown);

        //TOUCH SENSOR
        leftButton  = false;
        rightButton = false;

        maxValue = 0.5;

        //GYRO ROTATE
        curResetState   = false;
        lastResetState  = false;
        headingEncoder  = 0;
        headingOffset   = 7;
        turnPower       = 0.02;

        //STRAFE
        strafeTime  = 0.3;
        strafeSpeed = 0.5;
        quickFix    = false;

        value       = 1;
        
    }
    public void runOpMode(){

        roboInit();

        gyroInit();
        
        beaconColor.enableLed(false);

        waitForStart();

        //our main teleop loop
        while(opModeIsActive()) {

            beaconSwitchCase();
            debug();

            idle();

        }

    }

    public void gyroInit(){

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

    }

    public void beaconSwitchCase(){

        switch(value){

            case 1: encoderDrive(DRIVE_SPEED,  22.5,  -22.5, 1.5, headingEncoder);
                break;
            case 2: shoot();
                break;
            case 3: reload();delay(0.5);
                break;
            case 4: shoot();
                break;
            case 5: encoderDrive(DRIVE_SPEED,  20.00,  -20.00, 1.5, headingEncoder);
                break;
            case 6: gyroRotate(-0.5, 280);
                break;
            case 7: moveToWall(0.5);
                break;
            case 8: distanceSensor(0.5, currentTime);
                break;
            case 9: currentTime = getRuntime();while(getRuntime() - currentTime < 2.0 && opModeIsActive())strafe(0.5, "right");value++;
                break;
            case 20: moveToWall(0.5);
                break;
            case 10: distanceSensor(0.5, currentTime);
                break;
            case 11: currentTime = getRuntime();while(getRuntime() - currentTime < 5.0 && opModeIsActive())strafe(0.5, "left");value++;
                break;
            case 12: moveToWall(0.5);
                break;
            case 13: currentTime = getRuntime();while(getRuntime()-currentTime < 3.0 && opModeIsActive()){robotDrive(-0.3, 0.3);}robotStop();value++;
                break;
            case 14: gyroRotate(-0.5, 240);
                break;
            case 15: currentTime = getRuntime();while(getRuntime()-currentTime < 3.5 && opModeIsActive()){robotDrive(1.0, -1.0);}robotStop();value++;
                break;
            default:frontLeft.setPower(0.0);frontRight.setPower(0.0);backLeft.setPower(0.0);backRight.setPower(0.0);
                break;

        }

    }

    public void robotStop(){

        frontRight.setPower(0.0);
        backRight.setPower(0.0);
        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);


    }

    public void robotDrive(double left, double right){

        frontRight.setPower(right);
        backRight.setPower(right);
        frontLeft.setPower(left);
        backLeft.setPower(left);

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

    public void checkColor(double speed, double time){


        while(beaconColor.red() < red + colorOffset && opModeIsActive()){//getRuntime() - currentTime < strafeTime && !quickFix){

            strafe(speed, "right");
            debug();

        }

        // wallFix(0.5);
        moveToWall(0.5);

        //stop and press button
        frontRight.setPower(0.0);
        backRight.setPower(0.0);
        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);

        pressBeaconButton(0.5);

    }

    public void shoot(){

        while(shooter.getCurrentPosition() - previousShootPosition < shooterPosition && opModeIsActive()){

            shooter.setPower(shooterSpeed);
            telemetry.addData("encoder_data", shooter.getCurrentPosition());
            telemetry.update();

        }

        previousShootPosition = shooter.getCurrentPosition();

        shooter.setPower(0.0);
        value++;
    }

    public void reload(){

        currentTime = getRuntime();

        while(getRuntime() - currentTime < loadFrontTime){

            loadFront.setPosition(loadFrontPosUp);
            intake.setPower(1.0);

        }

        loadFront.setPosition(loadFrontPosDown);
        intake.setPower(0.0);
        value++;

    }

    public void pressBeaconButton(double time) {

        currentTime = getRuntime();

        while(getRuntime() - currentTime < time && opModeIsActive()){

            rightButtonServo.setPosition(rBeaconPositionOut);
            keepItOnWall(0.3);

        }

        rightButtonServo.setPosition(rBeaconPositionIn);
        value++;

    }

    public void distanceSensor(double speed, double time){
        dSensorValue = dSensor.getRawLightDetected();

        while(dSensorValue < maxValue && opModeIsActive()){

            strafe(speed, "right");

        }

        robotStop();
        
        checkColor(0.5, currentTime);

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS, int heading) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = frontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            frontLeft.setTargetPosition(newLeftTarget);
            frontRight.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontLeft.setPower(Math.abs(speed));
            frontRight.setPower(-speed);
            backRight.setPower(-speed);
            backLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            //add one to the value
            value++;

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void gyroRotate(double speed, int heading) {

        while(gyro.getHeading() < heading - headingOffset || gyro.getHeading() > heading + headingOffset && opModeIsActive()){

            frontRight.setPower(speed);
            frontLeft.setPower(speed);
            backRight.setPower(speed);
            backLeft.setPower(speed);


        }

        frontRight.setPower(0.0);
        frontLeft.setPower(0.0);
        backRight.setPower(0.0);
        backLeft.setPower(0.0);
        headingEncoder = heading;
        value++;

    }

    public void moveToWall(double speed){

        leftButton  = leftSwitch.isPressed();
        rightButton = rightSwitch.isPressed();

        if(!leftButton){
            frontLeft.setPower(speed);
            backLeft.setPower(speed);
        }
        else if(leftButton){
            frontLeft.setPower(0.0);
            backLeft.setPower(0.0);
        }
        if(!rightButton){
            frontRight.setPower(-speed);
            backRight.setPower(-speed);
        }
        else if(rightButton){
            frontRight.setPower(0.0);
            backRight.setPower(0.0);
        }
        if(rightButton && leftButton){value++;}


    }

    public void keepItOnWall(double speed){

        leftButton  = leftSwitch.isPressed();
        rightButton = rightSwitch.isPressed();

        if(!leftButton){
            frontLeft.setPower(speed);
            backLeft.setPower(speed);
        }
        else if(leftButton){
            frontLeft.setPower(0.0);
            backLeft.setPower(0.0);
        }
        if(!rightButton){
            frontRight.setPower(-speed);
            backRight.setPower(-speed);
        }
        else if(rightButton){
            frontRight.setPower(0.0);
            backRight.setPower(0.0);
        }


    }

    public void delay(double time){

        currentTime = getRuntime();

        while(getRuntime() - currentTime < time && opModeIsActive()){

            frontLeft.setPower(0.0);frontRight.setPower(0.0);backLeft.setPower(0.0);backRight.setPower(0.0);

        }

    }

    public void debug(){

        telemetry.addData("CASE_NUMBER",value);

        telemetry.addData("HEADING", gyro.getHeading());
        telemetry.addData("INT_Z", gyro.getIntegratedZValue());
        
        telemetry.addData("CHECK_RED_COLOR", beaconColor.red());
        telemetry.addData("CHECK_BLUE_COLOR", beaconColor.blue());

        telemetry.update();

    }
    }

