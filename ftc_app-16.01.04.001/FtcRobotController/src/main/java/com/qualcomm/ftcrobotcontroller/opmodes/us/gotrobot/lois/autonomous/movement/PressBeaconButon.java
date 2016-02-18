package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;

public class PressBeaconButon
{
    Preferences presets;
    StateManager stateManager;
    MotorManager motorManager;
    ServoManager servoManager;
    SensorManager sensorManager;

    double rightCorrection = 0;
    double leftCorrecion = 0;

    public PressBeaconButon(Preferences presets, StateManager stateManager, MotorManager motorManager, ServoManager servoManager, SensorManager sensorManager)
    {
        this.presets = presets;
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.servoManager = servoManager;
        this.sensorManager = sensorManager;
    }

    private GyroCorrection gyroCorrection = new GyroCorrection(presets, stateManager, motorManager,
            sensorManager);

    public void driveWithCorrection(double speed, int ThreshholdDistance)
    {
        if (stateManager.getStateStage() == 0)
        {
            if (sensorManager.getColorSensorHolder().getColorSensor().blue() > sensorManager.getColorSensorHolder().getColorSensor().red())
            {
                servoManager.getBeaconServo().setPosition(0); //Update this value
            }
            else
            {
                servoManager.getBeaconServo().setPosition(0); //Update this value
            }

            stateManager.continueCommand();
        }
        else if (stateManager.getStateStage() == 1) {
            motorManager.refreshEncoders();

            if (speed > 0) {
                motorManager.setLeftPower(speed);
                motorManager.setRightPower(speed);
            } else if (speed < 0)
            {
                motorManager.setLeftPower(-speed);
                motorManager.setRightPower(-speed);
            }

            stateManager.continueCommand();
        }
        else if (stateManager.getStateStage() == 2)
        {
            if(ThreshholdDistance >= sensorManager.getUltrasonicSensorHolder().getLevel() &&
               sensorManager.getUltrasonicSensorHolder().getLevel() != 0)
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
