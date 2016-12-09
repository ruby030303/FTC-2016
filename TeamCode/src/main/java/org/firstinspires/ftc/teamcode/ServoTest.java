package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by cicada05 on 12/2/16.
 */
@ TeleOp (name = "Servo_Test", group = "TeleOp")
public class ServoTest extends LinearOpMode  {

    Servo servo;

    private ElapsedTime runtime = new ElapsedTime();

    final static double minRange = 0.4;
    final static double maxRange = 0.94;

    double delta                 = 0.01;
    double in                    = 0.3;
    double out                   = 0.7;
    double position              = in;

    boolean lastState = false;
    boolean currentState = false;
    boolean servoPosition = false;


    public void runOpMode (){

        roboInit ();

        waitForStart();

        while (opModeIsActive()) {

            beaconBob();


            debug();


            idle();

        }

    }

    public void roboInit (){

        servo = hardwareMap.servo.get("Servo");

    }

    public void beaconBob(){

        currentState = gamepad1.a;

        if (currentState && !lastState && position ==in){

            position = out;
            servoPosition = true;
        }



        else if (currentState && !lastState && position == out){

            position = in;
            servoPosition = false;

        }

        lastState = currentState;

        position = Range.clip(position, minRange, maxRange);

        servo.setPosition(position);
    }







    public void debug(){

        telemetry.addData("ARM_POSITION", position);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("currentState", currentState);
        telemetry.addData("lastState", lastState);
        telemetry.addData("servoPosition", servoPosition);
        telemetry.update();
    }
}
