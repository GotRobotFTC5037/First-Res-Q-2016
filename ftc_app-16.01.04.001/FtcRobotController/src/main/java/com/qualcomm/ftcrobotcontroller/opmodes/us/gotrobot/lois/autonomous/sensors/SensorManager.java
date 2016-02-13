package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors;

public class SensorManager
{
    private ColorSensorHolder colorSensorHolder = new ColorSensorHolder();
    private LightSensorHolder lightSensorHolder = new LightSensorHolder();
    private GyroscopeHolder gyroscopeHolder = new GyroscopeHolder();
    private UltrasonicSensorHolder ultrasonicSensorHolder = new UltrasonicSensorHolder();
    private AccelerometerHolder accelerometerHolder = new AccelerometerHolder();

    public ColorSensorHolder getColorSensorHolder() {
        return colorSensorHolder;
    }

    public void setColorSensorHolder(ColorSensorHolder colorSensorHolder) {
        this.colorSensorHolder = colorSensorHolder;
    }

    public LightSensorHolder getLightSensorHolder() {
        return lightSensorHolder;
    }

    public void setLightSensorHolder(LightSensorHolder lightSensorHolder) {
        this.lightSensorHolder = lightSensorHolder;
    }

    public GyroscopeHolder getGyroscopeHolder() {
        return gyroscopeHolder;
    }

    public void setGyroscopeHolder(GyroscopeHolder gyroscopeHolder) {
        this.gyroscopeHolder = gyroscopeHolder;
    }

    public UltrasonicSensorHolder getUltrasonicSensorHolder() {
        return ultrasonicSensorHolder;
    }

    public void setUltrasonicSensorHolder(UltrasonicSensorHolder ultrasonicSensorHolder) {
        this.ultrasonicSensorHolder = ultrasonicSensorHolder;
    }

    public AccelerometerHolder getAccelerometerHolder() {
        return accelerometerHolder;
    }

    public void setAccelerometerHolder(AccelerometerHolder accelerometerHolder) {
        this.accelerometerHolder = accelerometerHolder;
    }
}