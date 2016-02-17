package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;

public class DefaultDrive
{
    Preferences presets;
    StateManager stateManager;
    MotorManager motorManager;
    SensorManager sensorManager;

    double rightCorrection = 0;
    double leftCorrecion = 0;

    public DefaultDrive(Preferences presets, StateManager stateManager, MotorManager motorManager,
                        SensorManager sensorManager)
    {
        this.presets = presets;
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }

    private GyroCorrection gyroCorrection = new GyroCorrection(presets, stateManager, motorManager,
            sensorManager);

    public void driveWithCorrection(double speed, int targetDistance)
    {
        if (stateManager.getStateStage() == 0)
        {
            motorManager.getRightMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
            motorManager.getLeftMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);

            motorManager.getLeftMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            motorManager.getRightMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

            if (speed > 0)
            {
                motorManager.getLeftMotor1().setPower(speed);
                motorManager.getRightMotor1().setPower(-speed);
                motorManager.getLeftMotor2().setPower(speed);
                motorManager.getRightMotor2().setPower(-speed);
            }
            else if (speed < 0)
            {
                motorManager.getLeftMotor1().setPower(-speed);
                motorManager.getRightMotor1().setPower(speed);
                motorManager.getLeftMotor2().setPower(-speed);
                motorManager.getRightMotor2().setPower(speed);
            }

            stateManager.continueCommand();
        }
        else if (stateManager.getStateStage() == 1)
        {
            if(motorManager.robotReachedTargetDistance(targetDistance))
            {
                motorManager.getLeftMotor1().setPower(0);
                motorManager.getRightMotor1().setPower(0);
                motorManager.getLeftMotor2().setPower(0);
                motorManager.getRightMotor2().setPower(0);

                stateManager.continueProgram();
            }
            else
            {
                gyroCorrection.GyroCorrection(speed);
            }
        }
    }
}
