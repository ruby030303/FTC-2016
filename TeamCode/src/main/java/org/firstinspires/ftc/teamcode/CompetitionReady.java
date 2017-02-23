package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.Disabled;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.util.Range;

    /**
     * Created by cicada02 on 12/16/16.
     */
    @TeleOp(name = "CompetitionReady", group = "Cayles")
    public class CompetitionReady extends LinearOpMode{

        DcMotor frontRight;
        DcMotor frontLeft;
        DcMotor backRight;
        DcMotor backLeft;
        DcMotor inTake;
        DcMotor upTake;
        DcMotor flicker;

        Servo rightButton;
        Servo leftButton;
        Servo gate;

        double currentTime;
        double roundTime;
        double currentRoundTime;

        //Mecanum Drive
        double driveX;
        double driveY;
        double driveRotate;
        double expoCurve;

        //Flicker
        boolean flickerButton;
        boolean previousflickerButton;
        double  flickerSpeed;
        int     flickerPosition;
        int     currentflickerPosition;
        int     previousFlickerPosition;

        //Gate
        double gatePosUp;
        double gatePosDown;
        double gateTime;
        boolean loadIsReady;
        boolean gateButton;
        boolean gateCurrentPress;
        boolean gatePreviousPress;

        //InTakeAndUpTake
        boolean inUpTakeButton;
        boolean inUpReverseButton;
        boolean inUpTakeCurrent;
        boolean inUpTakePrevious;
        boolean isRunning;

        //Beacons
        double rightBeaconPosIn;
        double leftBeaconPosIn;
        double rightBeaconPosOut;
        double lBeaconPositionOut;
        double rightButtonPos;
        double leftButtonPos;
        boolean rightBeaconButton;
        boolean leftBeaconButton;
        boolean rightBeaconCurrent;
        boolean rightBeaconPrevious;
        boolean leftBeaconCurrent;
        boolean leftBeaconPrevious;


        public void runOpMode(){

            roboInit();

            waitForStart();

            while (opModeIsActive()&& getRuntime() - currentRoundTime < roundTime){

                drive();
                flicker();
                reload();
                inTakeAndUpTake();
                beacons();
                debug();

            }

        }

        public void roboInit(){

            //Drive Motors
            frontRight  = hardwareMap.dcMotor.get("FRONT_RIGHT");
            frontLeft   = hardwareMap.dcMotor.get("FRONT_LEFT");
            backRight   = hardwareMap.dcMotor.get("BACK_RIGHT");
            backLeft    = hardwareMap.dcMotor.get("BACK_LEFT");

            //Intake, Uptake, & Flicker
            inTake      = hardwareMap.dcMotor.get("INTAKE");
            upTake      = hardwareMap.dcMotor.get("UPTAKE");
            flicker     = hardwareMap.dcMotor.get("SHOOTER");

            //Servos
            rightButton    = hardwareMap.servo.get("RIGHT_BUTTON");
            leftButton     = hardwareMap.servo.get("LEFT_BUTTON");
            gate           = hardwareMap.servo.get("LOAD_FRONT");

            //Flicker
            flickerButton = false;
            previousflickerButton = false;
            flickerSpeed = 1.0;
            flickerPosition = 3400;
            currentflickerPosition = flicker.getCurrentPosition();
            previousFlickerPosition = flicker.getCurrentPosition();

            flicker.setDirection(DcMotor.Direction.REVERSE);

            //Gate
            gatePosUp = 0.0;
            gatePosDown = 1.0;
            gateTime = 0.3;
            loadIsReady = false;
            gateButton = false;
            gateCurrentPress = false;
            gatePreviousPress = false;

            //Intake and Uptake
            inUpTakeButton = false;
            inUpReverseButton = false;
            isRunning = false;

            //BEACON BUTTON VARIABLES
            leftBeaconPosIn = 0.3;
            lBeaconPositionOut = 0.75;
            rightBeaconPosIn = 0.75;
            rightBeaconPosOut = 0.3;
            rightButtonPos = rightBeaconPosIn;
            leftButtonPos = leftBeaconPosIn;
            leftBeaconButton = false;
            rightBeaconButton = false;
            leftBeaconCurrent = false;
            rightBeaconCurrent = false;

            //Time
            roundTime = 120.0;
            currentRoundTime = 0.0;

        }

        public void drive(){

            driveRotate = gamepad1.right_stick_x;
            driveY      = gamepad1.left_stick_y;
            driveX      = gamepad1.left_stick_x;

            //This is where you drive the robot
            frontRight.setPower(expo(constrain(driveRotate + driveY + driveX),expoCurve));
            frontLeft.setPower(expo(constrain(driveRotate - driveY + driveX),expoCurve));
            backRight.setPower(expo(constrain(driveRotate + driveY - driveX),expoCurve));
            backLeft.setPower(expo(constrain(driveRotate - driveY  - driveX),expoCurve));

        }
        public void flicker(){

            double speed;
            flickerButton = gamepad1.right_bumper;
            currentflickerPosition = flicker.getCurrentPosition();

            if (currentflickerPosition - previousFlickerPosition <= flickerPosition){

                speed = flickerSpeed;

            }

            else if(currentflickerPosition - previousFlickerPosition >= flickerPosition && flickerButton && !previousflickerButton){

                speed = flickerSpeed;
                previousFlickerPosition = currentflickerPosition;

            }

            else{

                speed = 0.0;

            }

            previousflickerButton = flickerButton;

            flicker.setPower(speed);

        }

        public void reload(){

            gateButton = gamepad1.left_bumper;
            gateCurrentPress = gateButton;

            if (gateCurrentPress && !gatePreviousPress) {

                loadIsReady = true;
                currentTime = getRuntime();

            }

            if (loadIsReady) {

                delayServoPosition(gateTime, gate, gatePosUp, gatePosDown, loadIsReady);

            } else if (!loadIsReady) {
                gate.setPosition(gatePosDown);
            }

            gatePreviousPress = gateCurrentPress;

        }

        public void inTakeAndUpTake(){

            inUpTakeButton = gamepad1.a;
            inUpReverseButton = gamepad1.y;
            inUpTakeCurrent = inUpTakeButton;

            if (inUpTakeCurrent && !inUpTakePrevious && !isRunning && !gamepad1.y && !gamepad1.start) {

                isRunning = true;
            }
            else if (inUpTakeCurrent && !inUpTakePrevious && isRunning && !gamepad1.y) {

                isRunning = false;

            }

            inUpTakePrevious = inUpTakeCurrent;

            if (isRunning && !inUpReverseButton) {

                inTake.setPower(1.0);
                upTake.setPower(1.0);

            } else if (!isRunning && !inUpReverseButton) {

                inTake.setPower(0.0);
                upTake.setPower(0.0);

            }

            if (inUpReverseButton) {

                inTake.setPower(-1.0);
                upTake.setPower(-1.0);

            }

        }

        public void beacons(){

            leftBeaconButton = gamepad1.x;
            rightBeaconButton = gamepad1.b;

            leftBeaconCurrent = leftBeaconButton;
            rightBeaconCurrent = rightBeaconButton;

            if (leftBeaconCurrent && !leftBeaconPrevious && leftButton.getPosition() == leftBeaconPosIn) {

                leftButtonPos = lBeaconPositionOut;

            }
            else if (leftBeaconCurrent && !leftBeaconPrevious && leftButton.getPosition() == lBeaconPositionOut) {

                leftButtonPos = leftBeaconPosIn;

            }

            if (rightBeaconCurrent && !rightBeaconPrevious && rightButton.getPosition() == rightBeaconPosIn) {

                rightButtonPos = rightBeaconPosOut;

            }
            else if (rightBeaconCurrent && !rightBeaconPrevious && rightButton.getPosition() == rightBeaconPosOut) {

                rightButtonPos = rightBeaconPosIn;

            }

            leftBeaconPrevious = leftBeaconCurrent;
            rightBeaconPrevious = rightBeaconCurrent;

            rightButton.setPosition(rightButtonPos);
            leftButton.setPosition(leftButtonPos);

        }


        double expo(double x, double a){

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

        boolean delayServoPosition(double time, Servo servo1, double position1, double position2, boolean isReady) {

            if (getRuntime() - currentTime < time) {
                servo1.setPosition(position1);
            } else {
                servo1.setPosition(position2);
                isReady = false;
            }

            return isReady;
        }

        public void debug() {

            telemetry.addData("MOTOR_FRONT_RIGHT", frontRight.getPower());
            telemetry.addData("MOTOR_FRONT_LEFT", frontLeft.getPower());
            telemetry.addData("MOTOR_BACK_RIGHT", backRight.getPower());
            telemetry.addData("MOTOR_BACK_LEFT", backLeft.getPower());
            telemetry.addData("MOTOR_FLICKER", flicker.getPower());
            telemetry.addData("ENCODER_FLICKER", flicker.getCurrentPosition());
            telemetry.addData("GATE", gate.getPosition());
            telemetry.addData("LOAD_IS_READY", loadIsReady);
            telemetry.addData("CURRENT_TIME", (getRuntime() - currentTime));
            telemetry.update();
        }

    }
