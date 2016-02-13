package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors;

import com.qualcomm.robotcore.hardware.GyroSensor;

public class GyroscopeHolder
{
    private GyroSensor gyroSensor;

    private double gyroHeading = 0;
    private double lastRawGyroValue = 0;
    private double rawGyroValue = 0;

    public GyroSensor getGyroSensor() {
        return gyroSensor;
    }

    public void setGyroSensor(GyroSensor gyroSensor) {
        this.gyroSensor = gyroSensor;
    }

    private double getGyroHeading()
    {
        return gyroHeading;
    } //getGyroHeading

    private void setGyroHeading(double gyroHeading)
    {
        this.gyroHeading = gyroHeading;
    } //setGyroHeading

    private double getLastRawGyroValue()
    {
        return lastRawGyroValue;
    } //getLastRawGyroValue

    private void setLastRawGyroValue(double lastRawGyroValue)
    {
        this.lastRawGyroValue = lastRawGyroValue;
    } //setLastRawGyroValue

    public double getRawGyroValue()
    {
        return rawGyroValue;
    } //getRawGyroValue

    public void setRawGyroValue(double rawGyroValue)
    {
        this.rawGyroValue = rawGyroValue;
    } //setRawGroValue

    public void resetRotation()
    {
        gyroSensor.resetZAxisIntegrator();
        setGyroHeading(0);
        setRawGyroValue(0);
        setLastRawGyroValue(0);
    } //resetRotation

    public double getRotation()
    {
        setRawGyroValue(gyroSensor.getHeading());

        //359<-0
        if(getRawGyroValue()-180>getLastRawGyroValue())
        {
            setGyroHeading(getGyroHeading() + getRawGyroValue() - 360 - getLastRawGyroValue());
        }
        //359->0
        else if(getRawGyroValue()+180<getLastRawGyroValue())
        {
            setGyroHeading(getGyroHeading() + getRawGyroValue() + 360 - getLastRawGyroValue());
        }
        else
        {
            setGyroHeading(getGyroHeading() + getRawGyroValue() - getLastRawGyroValue());
        }

        setLastRawGyroValue(rawGyroValue);

        return getGyroHeading();
    } //getRotation
}
