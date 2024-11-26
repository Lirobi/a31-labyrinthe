package app.model;

public class Vector2D {
    private int _x;
    private int _y;

    public Vector2D(int x, int y)
    {
        _x = x;
        _y = y;
    }

    public void moveLeft()
    {
        _x--;
    }
    public void moveRight()
    {
        _x++;
    }
    public void moveTop()
    {
        _y--;
    }
    public void moveBottom()
    {
        _y++;
    }
    public int getX(){return _x;}
    public int getY(){return _y;}
}
