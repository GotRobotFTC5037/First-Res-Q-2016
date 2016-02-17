package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorManager
{
    Preferences presets;

    private DcMotor rightMotor1;
    private DcMotor leftMotor1;
    private DcMotor leftMotor2;
    private DcMotor rightMotor2;

    public MotorManager(Preferences presets)
    {
        this.presets = presets;
    }

    public DcMotor getRightMotor1() {
        return rightMotor1;
    }

    public void setRightMotor1(DcMotor rightMotor1) {
        this.rightMotor1 = rightMotor1;
    }

    public DcMotor getLeftMotor1() {
        return leftMotor1;
    }

    public void setLeftMotor1(DcMotor leftMotor1) {
        this.leftMotor1 = leftMotor1;
    }

    public DcMotor getLeftMotor2() {
        return leftMotor2;
    }

    public void setLeftMotor2(DcMotor leftMotor2) {
        this.leftMotor2 = leftMotor2;
    }

    public DcMotor getRightMotor2() {
        return rightMotor2;
    }

    public void setRightMotor2(DcMotor rightMotor2) {
        this.rightMotor2 = rightMotor2;
    }

    public double calculateEncoderValue(double inches)
    {
        return (1440 * (inches/(Math.PI * presets.getWHEEL_DIAMETER()))) * presets.getGEAR_RATIO();
    } //calculateEncoderValue

    public boolean robotReachedTargetDistance(double targetDistance)
    {
        if(Math.abs(getLeftMotor2().getCurrentPosition()) >=
                Math.abs(calculateEncoderValue(targetDistance))&&
                Math.abs(getRightMotor2().getCurrentPosition()) >=
                        Math.abs(calculateEncoderValue(targetDistance)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
