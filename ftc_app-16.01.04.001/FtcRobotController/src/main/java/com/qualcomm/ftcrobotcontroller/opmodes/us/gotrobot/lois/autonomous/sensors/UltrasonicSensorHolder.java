package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors;


import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class UltrasonicSensorHolder
{
    private UltrasonicSensor ultrasonicSensor;

    public UltrasonicSensor getUltrasonicSensor()
    {
        return ultrasonicSensor;
    }

    public void setUltrasonicSensor(UltrasonicSensor ultrasonicSensor)
    {
        this.ultrasonicSensor = ultrasonicSensor;
    }

    public double getLevel()
    {
        return ultrasonicSensor.getUltrasonicLevel();
    }
}
