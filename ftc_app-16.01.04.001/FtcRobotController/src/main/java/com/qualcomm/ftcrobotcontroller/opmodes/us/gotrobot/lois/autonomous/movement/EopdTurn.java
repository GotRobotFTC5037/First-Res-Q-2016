package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class EopdTurn
{
    StateManager stateManager;
    MotorManager motorManager;
    SensorManager sensorManager;

    public EopdTurn(StateManager stateManager, MotorManager motorManager, SensorManager sensorManager)
    {
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }

    public void lightTurn(double speed, int maxTurn)
    {
        if (stateManager.getStateStage() == 0)
        {
            motorManager.runWithoutEncoders();

            if (speed < 0)
            {
                motorManager.setRightPower(-speed);
                motorManager.setLeftPower(speed);
            }
            else if (speed > 0)
            {
                motorManager.setRightPower(speed);
                motorManager.setLeftPower(-speed);
            }

            stateManager.continueCommand();
        }
        else if(stateManager.getStateStage() == 1)
        {
            if (sensorManager.getLightSensorHolder().getInitalEOPDValue()+15 <
                sensorManager.getLightSensorHolder().getLightDetected() ||
                Math.abs(sensorManager.getGyroscopeHolder().getRotation()) >= Math.abs(maxTurn))
            {
                motorManager.stopAllMotors();

                sensorManager.getGyroscopeHolder().resetRotation();

                stateManager.continueProgram();
            }
        }
    }
}
