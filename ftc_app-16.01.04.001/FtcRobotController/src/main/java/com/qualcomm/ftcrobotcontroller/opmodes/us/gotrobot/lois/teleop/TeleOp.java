package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.teleop;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import java.sql.Time;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TeleOp extends OpMode
{
	DcMotor motorRight1;
	DcMotor motorRight2;
	DcMotor motorLeft1;
	DcMotor motorLeft2;
	DcMotor Liftmotor;
	DcMotor Winchmotor;
	Servo Climberservo;
	Servo EOPDServo;
	Servo GateServo;
	Servo MountainservoRight;
	Servo MountainservoLeft;

	//==============================================================
	//intialization of all the varibiles
	//==============================================================

	float left = gamepad1.left_stick_y;
	float right = gamepad1.right_stick_y;
	boolean gp1m_winch = false;
	boolean gp2_winch = false;
	final double SERVO_STOP = 0.495;
	boolean lift_active = false;
	boolean HK_lift_active = false;	//HK = Hot Key

	final int LIFT_HIGH_POS = 8240;

	//==============================================================
	//intialization of all the hardware and direction and posititon of all the servos
	//==============================================================

	@Override
	public void init() {
		motorRight1 = hardwareMap.dcMotor.get("motor_right_1");
		motorRight2 = hardwareMap.dcMotor.get("motor_right_2");
		motorLeft1 = hardwareMap.dcMotor.get("motor_left_1");
		motorLeft2 = hardwareMap.dcMotor.get("motor_left_2");
//		Liftmotor = hardwareMap.dcMotor.get("Liftmotor");
//		Winchmotor = hardwareMap.dcMotor.get("Winchmotor");
//		servoLeft1 = hardwareMap.servo.get("servo_L");
//		servoRight1 = hardwareMap.servo.get("servo_R");
//		Climberservo = hardwareMap.servo.get("climber");
//		Lightservo = hardwareMap.servo.get("Li_servo");
//		GateServo = hardwareMap.servo.get("gateservo");
//		MountainservoRight = hardwareMap.servo.get("msLeft");
//		MountainservoLeft = hardwareMap.servo.get("msRight");
//		EOPDServo = hardwareMap.servo.get("EOPDServo");

		//servoLeft1.setPosition(0.48);
		GateServo.setPosition(0.575);
		Climberservo.setPosition(0.0);
		EOPDServo.setPosition(0.6);
		MountainservoRight.setPosition(0.0);
		MountainservoLeft.setPosition(0.75);

		Liftmotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
		Liftmotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

		motorLeft2.setDirection(DcMotor.Direction.FORWARD);
		motorRight2.setDirection(DcMotor.Direction.FORWARD);
		motorLeft1.setDirection(DcMotor.Direction.FORWARD);
		motorRight1.setDirection(DcMotor.Direction.FORWARD);
		Liftmotor.setDirection(DcMotor.Direction.REVERSE);
		Winchmotor.setDirection(DcMotor.Direction.FORWARD);

		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);
	}


	public void E_stop()
	{
		//servoLeft1.setPosition(0.48);
		Climberservo.setPosition(0.0);
		//Lightservo.setPosition(0.4);
		//servoRight1.setPosition(0.48);
		MountainservoRight.setPosition(0.0);
		MountainservoLeft.setPosition(1.0);
		leftDriveSet((float) 0.0);
		rightDriveSet((float) 0.0);
	}

	public void leftDriveSet (float speed) {
		motorLeft2.setPower(speed);
		motorLeft1.setPower(-speed);
	}
	public void rightDriveSet (float speed)
	{
		motorRight2.setPower(-speed);
		motorRight1.setPower(speed);
	}

	@Override
	//=======================================================================
	// BODY
	//=======================================================================

	public void loop()
	{
		//==============================================================
		// servo control
		//==============================================================
		if (gamepad1.back && gamepad2.back)			//-------------------------------------
		{		                                    //
			telemetry.addData("TRUE","");           //
			E_stop();								//KILL SWITCH
			while (true);							//
		}											//
		else										//-------------------------------------
		{
			telemetry.addData("FALSE","");
			if (gamepad2.y) Climberservo.setPosition(1.0);
			else Climberservo.setPosition(0.0);

			if (gamepad1.b) GateServo.setPosition(0.575);
			if (gamepad1.x) GateServo.setPosition(1.0);

			if (gamepad2.dpad_up) GateServo.setPosition(0.575);
			if (gamepad2.b) GateServo.setPosition(1.0);

			if (gamepad1.dpad_left) MountainservoRight.setPosition(1.0);
			if (gamepad1.dpad_right) MountainservoLeft.setPosition(0.0);
			if (gamepad1.dpad_down) {
				MountainservoLeft.setPosition(0.75);
				MountainservoRight.setPosition(0.0);
			}

			if (gamepad2.dpad_left) MountainservoRight.setPosition(1.0);
			if (gamepad2.dpad_right) MountainservoLeft.setPosition(0.0);
			if (gamepad2.dpad_down) {
				MountainservoLeft.setPosition(0.75);
				MountainservoRight.setPosition(0.0);
			}

			//==============================================================
			// lift motor and drive control
			//==============================================================
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

			//else Liftmotor.setPower(0.0);
/*
			if (gamepad2.x)	//Hot key lift goes up
			{
				Liftmotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
				Liftmotor.setPower(-0.75);

				while (Liftmotor.getCurrentPosition() <=1440)
				{
				}
				Liftmotor.setPower(0.0);
			}
*/
			if (gamepad1.y) gp1m_winch = true;
			else if (gamepad1.a) gp1m_winch = true;
			else gp1m_winch = false;

			if ((Math.abs(gamepad1.left_stick_y) >= 0.20) || (Math.abs(gamepad1.right_stick_y) >= 0.20)) {
				leftDriveSet(gamepad1.left_stick_y);
				rightDriveSet(gamepad1.right_stick_y);
			} else if (gamepad2.right_trigger != 0) {
				gp2_winch = true;
				leftDriveSet((float) 0.3);
				rightDriveSet((float) 0.3);
			} else {
				gp2_winch = false;

				leftDriveSet((float) 0.0);
				rightDriveSet((float) 0.0);
			}

			if (gp1m_winch) {
				if (gamepad1.y) Winchmotor.setPower(1.0);
				else Winchmotor.setPower(-1.0);
			} else if (gp2_winch || gamepad2.a) {
				if (gamepad2.a) Winchmotor.setPower(1.0);
				else Winchmotor.setPower(1.0);
			} else Winchmotor.setPower(0.0);
		}
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
		telemetry.addData("LIft Encoder", -Liftmotor.getCurrentPosition());
		telemetry.addData("Right Position", motorRight2.getCurrentPosition());
		//telemetry.addData("Winch", "value:" + String.format("%.5f", gamepad1.right_trigger));
		//telemetry.addData("Distance", "Dist Val:" + String.format("%5d", distanceSensorValue));
		//telemetry.addData("servo", "amount: " + String.format("%.3f", Buttonservo.getPosition()));
		//telemetry.addData("servo", "degree: " + String.format("%.3f", Testservo.getPosition()));
	}


	@Override
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
