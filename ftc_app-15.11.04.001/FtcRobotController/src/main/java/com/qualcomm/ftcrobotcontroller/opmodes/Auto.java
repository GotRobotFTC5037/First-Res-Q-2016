package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Auto extends OpMode
{
    DcMotor right_motor;
    DcMotor left_motor;
    GyroSensor gyro;

    int v_state = 0;
    public void init()
    {

        left_motor = hardwareMap.dcMotor.get("motor_1");
        left_motor.setDirection(DcMotor.Direction.FORWARD);
        right_motor = hardwareMap.dcMotor.get("motor_2");
        right_motor.setDirection(DcMotor.Direction.FORWARD);

        right_motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        left_motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        gyro.resetZAxisIntegrator();

    } //Init

    @Override public void start()
    {
        left_motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right_motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        left_motor.setPower(0.5);
        right_motor.setPower(0.5);

    } // start

    //final static double GEAR_RATIO = 2;
    final static int WHEEL_DIAMETER = 4;

    double calcEncoderValue(double inches)
    {
        return (1440 * (inches/(Math.PI * WHEEL_DIAMETER)));
    }

    @Override public void loop()
    {
        switch (v_state)
        {
            case 0:

                if(left_motor.getCurrentPosition()>=calcEncoderValue(12)&&right_motor.getCurrentPosition() <= -calcEncoderValue(12))
                {
                    left_motor.setPower(0.0);
                    right_motor.setPower(0.0);

                    v_state++;
                }
                break;

            case 1:

                left_motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                right_motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

                gyro.resetZAxisIntegrator();
                right_motor.setPower(-0.5);
                left_motor.setPower(0.5);

                v_state++;
                break;

            case 2:

                if (gyro.getHeading()>=45&&gyro.getHeading()<180)
                {
                    left_motor.setPower(0.0);
                    right_motor.setPower(0.0);
                    v_state++;
                }
                break;

            case 3:

                right_motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                left_motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

                left_motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                right_motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

                left_motor.setPower(0.5);
                right_motor.setPower(0.5);

                v_state++;
                break;

            case 4:

                if(left_motor.getCurrentPosition()>=calcEncoderValue(36)&&right_motor.getCurrentPosition() <= -calcEncoderValue(36))
                {
                    left_motor.setPower(0.0);
                    right_motor.setPower(0.0);

                    v_state++;
                }

                break;

            default:


                break;
        }

        telemetry.addData("Left Position", left_motor.getCurrentPosition());
        telemetry.addData("Right Position", right_motor.getCurrentPosition());
        telemetry.addData("Gyro Heading", gyro.getHeading());
        telemetry.addData("State", v_state);
    } // loop

    @Override public void stop()
    {

    } // stop


} // PushBotAuto
