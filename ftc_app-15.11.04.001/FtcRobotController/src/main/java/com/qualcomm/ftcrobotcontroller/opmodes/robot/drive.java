package com.qualcomm.ftcrobotcontroller.opmodes.robot;

public class drive {

    private boolean check;
    private int speed;

    // the Bicycle class has
    // one constructor
    private drive(boolean distanceReached, int startSpeed) {
        check = distanceReached;
        speed = startSpeed;
    }

    public boolean getCheck()
    {

        return check;
    }
}