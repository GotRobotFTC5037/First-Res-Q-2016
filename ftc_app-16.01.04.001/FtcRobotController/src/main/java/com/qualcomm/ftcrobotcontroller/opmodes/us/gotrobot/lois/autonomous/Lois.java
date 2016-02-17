package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous;

import android.content.Context;
import android.hardware.Sensor;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.Preferences;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.MotorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.PressBeaconButon;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.sensors.SensorManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.ServoManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.DefaultDrive;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.EopdTurn;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.DefaultTurn;
import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement.UltrasonicDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lois
{
    HardwareMap hardwareMap = new HardwareMap();

    private Preferences presets = new Preferences();
    private StateManager stateManager = new StateManager();
    private SensorManager sensorManager = new SensorManager();
    private ServoManager servoManager = new ServoManager();
    private MotorManager motorManager = new MotorManager(presets);

    public ServoManager getServoManager()
    {
        return servoManager;
    }

    public SensorManager getSensorManager()
    {
        return sensorManager;
    }

    public MotorManager getMotorManager()
    {
        return motorManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public Preferences getPresets() {
        return presets;
    }

    public DefaultDrive robotDrive = new DefaultDrive(getPresets(), getStateManager(),
            getMotorManager(), getSensorManager());

    public UltrasonicDrive robotUltrasonicDrive = new UltrasonicDrive(getPresets(),
            getStateManager(), getMotorManager(), getSensorManager());

    public DefaultTurn robotTurn = new DefaultTurn(getStateManager(), getMotorManager(),
            getSensorManager());

    public EopdTurn robotLightTurn = new EopdTurn(getStateManager(), getMotorManager(),
            getSensorManager());

    public PressBeaconButon pressBeaconButon = new PressBeaconButon(getPresets(), getStateManager(),
            getMotorManager(), getServoManager(), getSensorManager());

    public void init()
    {
        /* */
        getServoManager().setClimberServo(hardwareMap.servo.get("climber"));

        /* */
        getServoManager().setGateServo(hardwareMap.servo.get("gateservo"));
        getServoManager().getGateServo().setPosition(.575);

        /* */
        getServoManager().setMountainServoLeft(hardwareMap.servo.get("msLeft"));

        /* */
        getServoManager().setMountainServoRight(hardwareMap.servo.get("msRight"));
        getServoManager().getMountainServoRight().setPosition(1.0);

        /* */
        getMotorManager().setLeftMotor1(hardwareMap.dcMotor.get("motor_left_1"));
        getMotorManager().getLeftMotor1().setDirection(DcMotor.Direction.REVERSE);

        /* */
        getMotorManager().setRightMotor1(hardwareMap.dcMotor.get("motor_right_1"));
        getMotorManager().getRightMotor1().setDirection(DcMotor.Direction.REVERSE);

        /* */
        getMotorManager().setLeftMotor2(hardwareMap.dcMotor.get("motor_left_2"));
        getMotorManager().getLeftMotor2().setDirection(DcMotor.Direction.REVERSE);

        /* */
        getMotorManager().setLeftMotor2(hardwareMap.dcMotor.get("motor_right_2"));
        getMotorManager().getLeftMotor2().setDirection(DcMotor.Direction.FORWARD);

        /* */
        getMotorManager().getRightMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);
        getMotorManager().getLeftMotor2().setMode(DcMotorController.RunMode.RESET_ENCODERS);

        /* */
        getSensorManager().getLightSensorHolder().setLightSensor
                (hardwareMap.lightSensor.get("ODS"));

        getSensorManager().getLightSensorHolder().setInitalEOPDValue(getSensorManager().
                getLightSensorHolder().getLightDetected());

        /* */
        getSensorManager().getUltrasonicSensorHolder().setUltrasonicSensor
                (hardwareMap.ultrasonicSensor.get("ultrasonicSensorHolder"));

        /* */
        getSensorManager().getColorSensorHolder().setColorSensor
                (hardwareMap.colorSensor.get("color"));

        /* */
        getServoManager().setBeaconServo(hardwareMap.servo.get("beaconservo"));

        /* */
        getSensorManager().getGyroscopeHolder().setGyroSensor(hardwareMap.gyroSensor.
                get("gyroscopeHolder"));

        getSensorManager().getGyroscopeHolder().getGyroSensor().calibrate();
        getSensorManager().getGyroscopeHolder().getGyroSensor().resetZAxisIntegrator();
    }

    public void start()
    {
        servoManager.getGateServo().setPosition(1.0);
    }
}