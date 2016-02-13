package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.movement;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control.StateManager;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoManager

{
    private Servo climberServo;
    private Servo gateServo;
    private Servo mountainServoLeft;
    private Servo mountainServoRight;
    private Servo beaconServo;

    public Servo getClimberServo() {
        return climberServo;
    }

    public void setClimberServo(Servo climberServo) {
        this.climberServo = climberServo;
    }

    public Servo getGateServo() {
        return gateServo;
    }

    public void setGateServo(Servo gateServo) {
        this.gateServo = gateServo;
    }

    public Servo getMountainServoLeft() {
        return mountainServoLeft;
    }

    public void setMountainServoLeft(Servo mountainServoLeft) {
        this.mountainServoLeft = mountainServoLeft;
    }

    public Servo getMountainServoRight() {
        return mountainServoRight;
    }

    public void setMountainServoRight(Servo mountainServoRight) {
        this.mountainServoRight = mountainServoRight;
    }

    public Servo getBeaconServo()
    {
        return beaconServo;
    }

    public void setBeaconServo(Servo beaconServo) {
        this.beaconServo = beaconServo;
    }
}
