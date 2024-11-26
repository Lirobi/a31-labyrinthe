package app.model;

import java.util.ArrayList;
import java.util.Stack;

public class TileFactory {
    public TileFactory()
    {

    }
    public Tile createT(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.EAST);
        directions.push(Direction.NORTH);
        directions.push(Direction.WEST);
        return new Tile(goal, directions);
    }
    public Tile createT()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.EAST);
        directions.push(Direction.NORTH);
        directions.push(Direction.WEST);
        return new Tile(null, directions);
    }
    public Tile createI(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.NORTH);
        directions.push(Direction.SOUTH);
        return new Tile(goal, directions);
    }
    public Tile createI()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.NORTH);
        directions.push(Direction.SOUTH);
        return new Tile(null, directions);
    }
    public Tile createL(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.EAST);
        directions.push(Direction.NORTH);
        return new Tile(goal, directions);
    }
    public Tile createL()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.EAST);
        directions.push(Direction.NORTH);
        return new Tile(null, directions);
    }
}
