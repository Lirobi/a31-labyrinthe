package app.model;

import java.util.Stack;

public class Player {

    private final Stack<Goal> _goalsStack;
    private int _goalSuccessful = 0;
    private int _goalNotSuccessful;

    public Player(Stack<Goal> goals)
    {
        _goalNotSuccessful = goals.size();
        _goalsStack = goals;

    }
    public int getGoalSuccessful()
    {
        return _goalSuccessful;
    }
    public int getGoalNotSuccessful()
    {
        return _goalNotSuccessful;
    }
    public boolean isRestGoal()
    {
        return !_goalsStack.isEmpty();
    }

    /**
     * When a goal is successful the player moves on to the next objective
     */
    public void nextGoal()
    {
        _goalsStack.pop();
        _goalSuccessful++;
        _goalNotSuccessful--;
    }

    public Goal getCurrentGoal()
    {
        return _goalsStack.peek();
    }

    @Override
    public String toString()
    {
        return "Le joueur a "+_goalSuccessful+" succès réussi(s) et a "+_goalNotSuccessful+" succès a encore valider";
    }
}
