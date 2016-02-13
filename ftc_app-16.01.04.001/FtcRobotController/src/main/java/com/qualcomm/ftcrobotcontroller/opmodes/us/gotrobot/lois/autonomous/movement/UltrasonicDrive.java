package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class UltrasonicDrive
{
    StateManager stateManager;
    MotorManager motorManager;
    SensorManager sensorManager;

    double rightCorrection = 0;
    double leftCorrecion = 0;

    private final double robotGearRatio = 2;
    private final double robotWheelDiameter = 5.3;

    public UltrasonicDrive(Preferences prsets, StateManager stateManager, MotorManager motorManager, SensorManager sensorManager)
    {
        this.stateManager = stateManager;
        this.motorManager = motorManager;
        this.sensorManager = sensorManager;
    }

    public double getRobotGearRatio() {
        return robotGearRatio;
    } //getGearRatio

    public double getRobotWheelDiameter() {
        return robotWheelDiameter;
    } //getWheelDiameter

    private double calcEncoderValue(double inches)
    {
        return (1440 * (inches/(Math.PI * getRobotWheelDiameter()))) * getRobotGearRatio();
    } //calcEncoderValue

    public void ultrasonicDriveWithCorrection(double speed, double ThreshholdDistance)
    {
        double errorCorrection = speed/10;

        if (stateManager.getStateStage() == 0)
        {
            motorManager.getRightMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
            motorManager.getLeftMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);

            motorManager.getLeftMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            motorManager.getRightMotor2().setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

            if (speed > 0)
            {
                motorManager.getLeftMotor1().setPower(speed);
                motorManager.getRightMotor1().setPower(-speed);
                motorManager.getLeftMotor2().setPower(speed);
                motorManager.getRightMotor2().setPower(-speed);
            }
            else if (speed < 0)
            {
                motorManager.getLeftMotor1().setPower(-speed);
                motorManager.getRightMotor1().setPower(speed);
                motorManager.getLeftMotor2().setPower(-speed);
                motorManager.getRightMotor2().setPower(speed);
            }

            stateManager.continueCommand();
        }
        else if (stateManager.getStateStage() == 1)
        {
            if(ThreshholdDistance >= sensorManager.getUltrasonicSensorHolder().getLevel() && sensorManager.getUltrasonicSensorHolder().getLevel() != 0)
            {
                motorManager.getLeftMotor1().setPower(0);
                motorManager.getRightMotor1().setPower(0);
                motorManager.getLeftMotor2().setPower(0);
                motorManager.getRightMotor2().setPower(0);

                stateManager.continueProgram();
            }
                /*else
                {
                    leftCorrecion = speed-(getRotation()*errorCorrection);

                    rightCorrection = speed+(getRotation()*errorCorrection);

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

                    left_motor1.setPower(leftCorrecion);
                    right_motor1.setPower(rightCorrection);
                    left_motor2.setPower(leftCorrecion);
                    right_motor2.setPower(rightCorrection);


                }*/
        }
    }
}
