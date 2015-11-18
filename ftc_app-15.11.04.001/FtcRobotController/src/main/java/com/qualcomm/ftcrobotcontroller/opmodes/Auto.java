package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotAuto
//

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Provide a basic autonomous operational mode that uses the left and right
 * drive motors and associated encoders implemented using a state machine for
 * the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class Auto extends OpMode
{
    DcMotor right_motor;
    DcMotor left_motor;
    GyroSensor gyro;

    int direction = 0;
    int v_state = 0;

    public Auto()
    {
    } //Auto

    public void init()
    {
        right_motor = hardwareMap.dcMotor.get("motor_1");
        right_motor.setDirection(DcMotor.Direction.FORWARD);
        left_motor = hardwareMap.dcMotor.get("motor_2");
        left_motor.setDirection(DcMotor.Direction.FORWARD);
        gyro = hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();
        gyro.resetZAxisIntegrator();
        direction = gyro.getHeading();
    } //Init

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()
    {
        direction = gyro.getHeading();
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
        //
        // Synchronize the state machine and hardware.
        //
        case 0:

            right_motor.setPower(-0.1);
            left_motor.setPower(0.1);
            v_state++;
            break;
        //
        // Drive forward until the encoders exceed the specified values.
        //
        case 1:
            if (direction >= 180)
            {
                right_motor.setPower(0);
                left_motor.setPower(0);
                v_state++;
            }

            break;
        default:
            //
            // The autonomous actions have been accomplished (i.e. the state has
            // transitioned into its final state.
            //
            break;
        }
        telemetry.addData ("1", "State: " + v_state);
        telemetry.addData("Heading", String.format("%03d", direction));

    } // loop

    //--------------------------------------------------------------------------
    //
    // v_state
    //
    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */


} // PushBotAuto
