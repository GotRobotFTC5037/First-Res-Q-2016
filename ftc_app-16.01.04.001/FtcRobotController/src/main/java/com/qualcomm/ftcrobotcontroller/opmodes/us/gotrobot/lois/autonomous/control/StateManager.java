package com.qualcomm.ftcrobotcontroller.opmodes.us.gotrobot.lois.autonomous.control;

public class StateManager
{
    public StateManager()
    {

    }

    public int state = 0; //Change this
    private int stateStage = 0;

    public int getStateStage()
    {
        return stateStage;
    } //getStateStage

    private void setStateStage(int stateStage) {
        this.stateStage = stateStage;
    } //setStateStage

    public int getState() {
        return state;
    } //getState

    private void setState(int state) {
        this.state = state;
    } //setState

    private void nextState()
    {
        setState(getState()+1);
    } //nextState

    private void nextStateStage()
    {
        setStateStage(getStateStage()+1);
    } //nextStateStage

    private void resetStateStage()
    {
        setStateStage(0);
    } // resetStateStage

    public void continueCommand()
    {
        nextStateStage();
    } //continueCommand

    public void continueProgram()
    {
        nextState();
        resetStateStage();
    } //continueProgram
}
