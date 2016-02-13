package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class DefaultTurn
{
    StateManager stateManager;
    MotorManager motorManager;
    SensorManager sensorManager;

    public DefaultTurn(StateManager stateManager, MotorManager motorManager, SensorManager sensorManager)
    {
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }


    public void pointTurn(double speed, int degrees)
    {
        if (stateManager.getStateStage() == 0)
        {
            motorManager.getLeftMotor2().setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            motorManager.getRightMotor2().setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

            if (degrees < 0)
            {
                motorManager.getLeftMotor1().setPower(speed);
                motorManager.getRightMotor1().setPower(speed);
                motorManager.getLeftMotor2().setPower(speed);
                motorManager.getRightMotor2().setPower(speed);
            }
            else if (degrees > 0)
            {
                motorManager.getLeftMotor1().setPower(-speed);
                motorManager.getRightMotor1().setPower(-speed);
                motorManager.getLeftMotor2().setPower(-speed);
                motorManager.getRightMotor2().setPower(-speed);
            }

            stateManager.continueCommand();
        }
        else if(stateManager.getStateStage() == 1)
        {
            if (degrees > 0)
            {
                if (sensorManager.getGyroscopeHolder().getRotation() >= degrees)
                {
                    motorManager.getLeftMotor1().setPowerFloat();
                    motorManager.getRightMotor2().setPowerFloat();
                    motorManager.getLeftMotor2().setPowerFloat();
                    motorManager.getRightMotor2().setPowerFloat();

                    sensorManager.getGyroscopeHolder().resetRotation();

                    stateManager.continueProgram();
                }
            }
            else if (degrees < 0)
            {
                if (sensorManager.getGyroscopeHolder().getRotation() <= degrees)
                {
                    motorManager.getLeftMotor1().setPowerFloat();
                    motorManager.getRightMotor2().setPowerFloat();
                    motorManager.getLeftMotor2().setPowerFloat();
                    motorManager.getRightMotor2().setPowerFloat();

                    sensorManager.getGyroscopeHolder().resetRotation();

                    stateManager.continueProgram();
                }
            }
        }
    }
}
