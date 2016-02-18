package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;

public class UltrasonicDrive
{
    Preferences presets;
    StateManager stateManager;
    MotorManager motorManager;
    SensorManager sensorManager;

    public UltrasonicDrive(Preferences presets, StateManager stateManager, MotorManager motorManager,
                           SensorManager sensorManager)
    {
        this.presets = presets;
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }

    private GyroCorrection gyroCorrection = new GyroCorrection(presets, stateManager, motorManager,
            sensorManager);

    public void ultrasonicDriveWithCorrection(double speed, double ThreshholdDistance)
    {
        if (stateManager.getStateStage() == 0)
        {
            motorManager.refreshEncoders();

            if (speed > 0)
            {
                motorManager.setLeftPower(speed);
                motorManager.setRightPower(speed);
            }
            else if (speed < 0)
            {
                motorManager.setLeftPower(-speed);
                motorManager.setRightPower(-speed);
            }

            stateManager.continueCommand();
        }
        else if (stateManager.getStateStage() == 1)
        {
            if(ThreshholdDistance >= sensorManager.getUltrasonicSensorHolder().getLevel() && sensorManager.getUltrasonicSensorHolder().getLevel() != 0)
            {
                motorManager.stopAllMotors();

                stateManager.continueProgram();
            }
            else
            {
                gyroCorrection.GyroCorrection(speed);
            }
        }
    }
}
