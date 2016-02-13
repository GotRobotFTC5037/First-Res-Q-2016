package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors;

import com.qualcomm.robotcore.hardware.LightSensor;

public class LightSensorHolder
{
    private LightSensor distanceSensor;

    public double getLightDetected()
    {
        return distanceSensor.getLightDetected();
    }
    private double initalEOPDValue = 0;

    public double getInitalEOPDValue()
    {
        return initalEOPDValue;
    } //getInitalEOPDValue

    public void setInitalEOPDValue(double initalEOPDValue)
    {
        this.initalEOPDValue = initalEOPDValue;
    } //setInitalEOPDValue

    public void setLightSensor(LightSensor lightSensor)
    {
        this.distanceSensor = lightSensor;
    }
}
