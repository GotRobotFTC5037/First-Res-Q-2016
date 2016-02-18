package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class GyroCorrection
{
    MotorManager motorManager;
    SensorManager sensorManager;
    StateManager stateManager;
    Preferences presets;

    double rightCorrection = 0;
    double leftCorrecion = 0;

    public GyroCorrection(Preferences presets, StateManager stateManager, MotorManager motorManager, SensorManager sensorManager)
    {
        this.presets = presets;
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }

    public void GyroCorrection(double speed)
    {
        leftCorrecion = speed - (sensorManager.getGyroscopeHolder().getRotation() *
                presets.getGYRO_ERROR_CORRECTION());
        rightCorrection = speed + (sensorManager.getGyroscopeHolder().getRotation() *
                presets.getGYRO_ERROR_CORRECTION());

        if (leftCorrecion>1)
        {
            leftCorrecion = 1;
        }
        else if (leftCorrecion<-1)
        {
            leftCorrecion = -1;
        }

        if (rightCorrection>1)
        {
            rightCorrection = 1;
        }
        else if (rightCorrection<-1)
        {
            rightCorrection = -1;
        }

        motorManager.setLeftPower(leftCorrecion);
        motorManager.setRightPower(rightCorrection);
    }
}
