package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorManager
{
    Preferences presets;

    private DcMotor rightMotor1;
    private DcMotor leftMotor1;
    private DcMotor leftMotor2;
    private DcMotor rightMotor2;

    HardwareMap hardwareMap = new HardwareMap();

    public MotorManager(Preferences presets)
    {
        this.presets = presets;
    }

    private DcMotor getRightMotor1() {
        return rightMotor1;
    }

    public void setRightMotor1(DcMotor rightMotor1) {
        this.rightMotor1 = rightMotor1;
    }

    private DcMotor getLeftMotor1() {
        return leftMotor1;
    }

    public void setLeftMotor1(DcMotor leftMotor1) {
        this.leftMotor1 = leftMotor1;
    }

    private DcMotor getLeftMotor2() {
        return leftMotor2;
    }

    public void setLeftMotor2(DcMotor leftMotor2) {
        this.leftMotor2 = leftMotor2;
    }

    private DcMotor getRightMotor2() {
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
           Math.abs(calculateEncoderValue(targetDistance)) &&
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

    public void setLeftPower(double speed)
    {
        getLeftMotor1().setPower(speed);
        getLeftMotor2().setPower(speed);
    }

    public void setRightPower(double speed)
    {
        getRightMotor1().setPower(-speed);
        getRightMotor2().setPower(-speed);
    }

    public void stopAllMotors()
    {
        getLeftMotor1().setPower(0);
        getLeftMotor2().setPower(0);
        getRightMotor1().setPower(0);
        getRightMotor2().setPower(0);
    }

    public void refreshEncoders()
    {
        getRightMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
        getLeftMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);

        getLeftMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        getRightMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void runWithoutEncoders()
    {
        getLeftMotor2().setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        getRightMotor2().setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public void init() {
        /* */
        setLeftMotor1(hardwareMap.dcMotor.get("motor_left_1"));
        getLeftMotor1().setDirection(DcMotor.Direction.REVERSE);

        /* */
        setRightMotor1(hardwareMap.dcMotor.get("motor_right_1"));
        getRightMotor1().setDirection(DcMotor.Direction.REVERSE);

        /* */
        setLeftMotor2(hardwareMap.dcMotor.get("motor_left_2"));
        getLeftMotor2().setDirection(DcMotor.Direction.REVERSE);

        /* */
        setLeftMotor2(hardwareMap.dcMotor.get("motor_right_2"));
        getLeftMotor2().setDirection(DcMotor.Direction.FORWARD);

        /* */
        getRightMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
        getLeftMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }
}
