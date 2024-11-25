package app.model;

public class Tile {
    private final boolean[] _form;
    private final Goal _goal;

    public Tile(Goal goal, boolean[] form)
    {
        _form = form;
        _goal = goal;
    }

    public void rotate()
    {
        boolean temp = _form[0];
        _form[0] = _form[3];
        boolean temp2 = _form[2];
        _form[2] = _form[1];
        _form[1] = temp;
        _form[3] = temp2;
    }

    public boolean existGoal()
    {
        return _goal != null;
    }

    public Goal getGoal()
    {
        if (existGoal())
            return _goal;
        else return null;
    }

    public boolean[] getForm()
    {
        return _form;
    }

    @Override
    public String toString()
    {
        return _form[0] + " : " + _form[1] + " : " + _form[2] + " : " + _form[3];
    }
}
