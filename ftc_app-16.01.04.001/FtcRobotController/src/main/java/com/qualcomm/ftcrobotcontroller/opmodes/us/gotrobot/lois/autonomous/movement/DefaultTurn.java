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
            motorManager.runWithoutEncoders();

            if (degrees < 0)
            {
                motorManager.setRightPower(-speed);
                motorManager.setLeftPower(speed);
            }
            else if (degrees > 0)
            {
                motorManager.setRightPower(speed);
                motorManager.setLeftPower(-speed);
            }

            stateManager.continueCommand();
        }
        else if(stateManager.getStateStage() == 1)
        {
            if (degrees > 0)
            {
                if (sensorManager.getGyroscopeHolder().getRotation() >= degrees)
                {
                    motorManager.stopAllMotors();

                    sensorManager.getGyroscopeHolder().resetRotation();

                    stateManager.continueProgram();
                }
            }
            else if (degrees < 0)
            {
                if (sensorManager.getGyroscopeHolder().getRotation() <= degrees)
                {
                    motorManager.stopAllMotors();

                    sensorManager.getGyroscopeHolder().resetRotation();

                    stateManager.continueProgram();
                }
            }
        }
    }
}
