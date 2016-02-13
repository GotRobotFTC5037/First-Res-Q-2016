package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.programs;

import com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.Lois;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class AutoBlue extends OpMode
{
    private Lois lois = new Lois();

    @Override public void init()
    {
        lois.init();
    } //Init

    @Override public void start()
    {
        lois.start();
    } //start

    @Override public void loop()
    {
        switch (lois.getStateManager().getState())
        {
            case 0:
                lois.robotDrive.driveWithCorrection(-1, 12);
                break;

            case 1:
                lois.robotTurn.pointTurn(1, 40);
                break;

            case 2:
                lois.robotDrive.driveWithCorrection(-1, 40);
                break;

            case 3:
                lois.robotTurn.pointTurn(0.9, 34);

                /*COMMAND IS FOR TESTING PURPUSES!!!
                MAKE SURE TO RESET TO PRIVATE WHEN FINNISHED TESTING!!!
                 */
                lois.getStateManager().state = -1;
                break;

            case 4:
                lois.getServoManager().getGateServo().setPosition(0.575);
                lois.robotUltrasonicDrive.ultrasonicDriveWithCorrection(1, 18);
                break;

            case 5:
                lois.getServoManager().getClimberServo().setPosition(0.75);
                break;

            default:

                break;
        }

        telemetry.addData("Gyro", lois.getSensorManager().getGyroscopeHolder().getRotation());
        //telemetry.addData("Ultrasonic", lois.getSensorManager().getUltrasonicSensorHolder().getLevel());
        //telemetry.addData("I EOPD", lois.getSensorManager().getLightSensorHolder().getInitalEOPDValue());
        //telemetry.addData("EOPD", lois.getSensorManager().getLightSensorHolder().getLightDetected());

    } //loop

    @Override public void stop()
    {

    } //stop

} // PushBotAuto
