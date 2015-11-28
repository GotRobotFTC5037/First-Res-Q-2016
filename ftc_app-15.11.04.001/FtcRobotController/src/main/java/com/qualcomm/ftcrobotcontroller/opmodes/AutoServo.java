package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotAuto
//

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Provide a basic autonomous operational mode that uses the left and right
 * drive motors and associated encoders implemented using a state machine for
 * the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class AutoServo extends OpMode
{
    Servo servo;

    public void init()
    {
        servo = hardwareMap.servo.get("servo_1");
    } //Init

    @Override public void start()
    {
        servo.setPosition(1);
    } // start

    @Override public void loop()
    {
        telemetry.addData("Servo", servo.getPosition());
    } // loop

    @Override public void stop()
    {

    } // stop


} // PushBotAuto
