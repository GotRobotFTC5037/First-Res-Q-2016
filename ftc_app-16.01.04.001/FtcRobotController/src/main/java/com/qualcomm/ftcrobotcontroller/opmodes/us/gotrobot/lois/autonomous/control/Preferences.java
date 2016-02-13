package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control;

public class Preferences
{
    private final double GYRO_ERROR_CORRECTION = 0.05;

    private final double WHEEL_DIAMETER = 5.3;
    private final double GEAR_RATIO = 2;

    public double getGYRO_ERROR_CORRECTION() {
        return GYRO_ERROR_CORRECTION;
    }

    public double getWHEEL_DIAMETER() {
        return WHEEL_DIAMETER;
    }

    public double getGEAR_RATIO() {
        return GEAR_RATIO;
    }
}
