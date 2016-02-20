package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.teleop;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;

public class Beta extends OpMode
{
    DcMotor MotorRightSide;
    DcMotor MotorLeftSide;
    DcMotor Winchmotor;
    DcMotor Liftmotor;
    Servo ClimberServo;
    Servo EOPDServo;
    Servo GateServo;
    Servo MountainservoRight;
    Servo MountainservoLeft;
    Servo CamServo;


    boolean gp1_winch = false;
    boolean gp2_winch = false;
    boolean HK_lift_active = false;	//HK = Hot
    final int LIFT_HIGH_POS = 8240;
//    float left = gamepad1.left_stick_y;
//    float right = gamepad1.right_stick_y;
    final double SERVO_STOP = 0.495;
//  boolean lift_active = false;
    float CamSpeed = (float)0.01;
    float CamPos = (float)0.48;

    public void init()
    {

        MotorRightSide = hardwareMap.dcMotor.get("motor_right_side");
        MotorLeftSide = hardwareMap.dcMotor.get("motor_left_side");
        Winchmotor = hardwareMap.dcMotor.get("Winchmotor");
        Liftmotor = hardwareMap.dcMotor.get("Liftmotor");
        ClimberServo = hardwareMap.servo.get("climber");
//		Lightservo = hardwareMap.servo.get("Li_servo");
//      GateServo = hardwareMap.servo.get("gateservo");
        MountainservoRight = hardwareMap.servo.get("msLeft");
        MountainservoLeft = hardwareMap.servo.get("msRight");
        EOPDServo = hardwareMap.servo.get("EOPDServo");
        CamServo = hardwareMap.servo.get("CamServo");

        ClimberServo.setPosition(1.0);
        CamServo.setPosition(1.0);
//        GateServo.setPosition(0.575);
//        EOPDServo.setPosition(0.6);
//        MountainservoRight.setPosition(0.0);
//        MountainservoLeft.setPosition(0.75);

        MotorLeftSide.setDirection(DcMotor.Direction.REVERSE);
        MotorRightSide.setDirection(DcMotor.Direction.FORWARD);
        Liftmotor.setDirection(DcMotor.Direction.FORWARD);
        Winchmotor.setDirection(DcMotor.Direction.REVERSE);
        //ClimberServo.setDirection(Servo.Direction.REVERSE);

        Liftmotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        Liftmotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

//        CamServo = Range.scale(0,1,-1,1);
//        left = Range.clip(left, -1, 1);
    }




public void loop()
{


//    ========================================
//    Driver Operations
//    ========================================

    if (gamepad1.y) gp1_winch = true;
    else if (gamepad1.a) gp1_winch = true;
    else gp1_winch = false;

//    ========================================
//    Gunner Operations
//    ========================================


    if (gamepad2.y) ClimberServo.setPosition(0.0);
    else ClimberServo.setPosition(1.0);

    if (gamepad2.left_bumper)
			{
				Liftmotor.setPower(0.50);
				HK_lift_active = false;
			}
			else if (gamepad2.start)
			{
				Liftmotor.setPower(-0.75);
				HK_lift_active = false;
			}
			else if (gamepad2.x && !HK_lift_active)
			{
				Liftmotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
				Liftmotor.setPower(0.50);
				HK_lift_active = true;
			}
			else if (HK_lift_active && Liftmotor.getCurrentPosition() >= LIFT_HIGH_POS) HK_lift_active = false;
			else if (!HK_lift_active) Liftmotor.setPower(0.0);

    if (gamepad2.a) gp2_winch = true;
    else gp2_winch = false;

    if (gamepad2.left_stick_y >=.4)
			{
                CamPos = CamPos + CamSpeed;
                if(CamPos>1) CamPos = 1;
			}
    else if (gamepad2.left_stick_y <=-.4)
            {
                CamPos = CamPos - CamSpeed;
                if(CamPos<.15) CamPos = (float)0.15;
			}
    CamServo.setPosition(CamPos);

//    ========================================
//    Motor and Winch handler
//    ========================================

    if ((Math.abs(gamepad1.left_stick_y) >= 0.20) || (Math.abs(gamepad1.right_stick_y) >= 0.20)) {
        MotorLeftSide.setPower(gamepad1.left_stick_y);
        MotorRightSide.setPower(gamepad1.right_stick_y);
    } else if (gamepad2.right_trigger != 0) {
        MotorLeftSide.setPower((float) 0.3);
        MotorRightSide.setPower((float) 0.3);
    } else {
        MotorLeftSide.setPower((float) 0.0);
        MotorRightSide.setPower((float) 0.0);
    }



    if (gp1_winch) {
        if (gamepad1.y) Winchmotor.setPower(1.0);
        else Winchmotor.setPower(-1.0);
    } else if (gp2_winch) Winchmotor.setPower(1.0);
    else Winchmotor.setPower(0.0);

    telemetry.addData("Text", "*** Robot Data***");
    telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", -MotorLeftSide.getPower()));
    telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", -MotorRightSide.getPower()));
    telemetry.addData("LIft Encoder", -Liftmotor.getCurrentPosition());
    telemetry.addData("Cam Servo Pos", String.format("%.2f",CamPos));//CamServo.getPosition()));
}
    public void stop()
    {

    }

    //==============================================================
    // Perobolic Drive
    //==============================================================

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
