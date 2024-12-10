    package app.model;

import java.util.ArrayList;
import java.util.Stack;

public class Tile {
    private final String _pathImg;
    private final ArrayList<Direction> _possibleDirections = new ArrayList<>();
    private Goal _goal;

    public Tile(Goal goal, Stack<Direction> form, String path)
    {
        _pathImg = path;
        while (!form.empty())
            _possibleDirections.add(form.pop());
        _goal = goal;
    }

    public void rotate() {
        _possibleDirections.replaceAll(Direction::next);
    }

    public boolean existGoal()
    {
        return _goal != null;
    }

    public ArrayList<Direction> getDirection()
    {
        return _possibleDirections;
    }

    public void setGoal(Goal goal) 
    {
        _goal = goal;
    }

    public void deleteGoal() 
    {
        _goal = null;
    }

    public Goal getGoal()
    {
        if (existGoal())
            return _goal;
        else return null;
    }

    public boolean isDirectionPossible(Direction dir)
    {
        return _possibleDirections.contains(dir);
    }

    public String getPathImg()
    {
        return _pathImg;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (Direction possibleDirection : _possibleDirections){ str.append(possibleDirection.toString()); str.append(" ");}
        return str.toString();
    }
}
