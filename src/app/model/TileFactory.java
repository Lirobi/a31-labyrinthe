package app.model;

import java.util.Stack;

public class TileFactory {
    public TileFactory()
    {

    }
    public Tile createT(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.WEST);
        directions.push(Direction.SOUTH);
        directions.push(Direction.EAST);
        return new Tile(goal, directions, "");
    }
    public Tile createT()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.WEST);
        directions.push(Direction.SOUTH);
        directions.push(Direction.EAST);
        return new Tile(null, directions, "");
    }
    public Tile createI(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.NORTH);
        directions.push(Direction.SOUTH);
        return new Tile(goal, directions, "");
    }
    public Tile createI()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.NORTH);
        directions.push(Direction.SOUTH);
        return new Tile(null, directions, "");
    }
    public Tile createL(Goal goal)
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.SOUTH);
        directions.push(Direction.EAST);
        return new Tile(goal, directions, "");
    }
    public Tile createL()
    {
        Stack<Direction> directions = new Stack<>();
        directions.push(Direction.SOUTH);
        directions.push(Direction.EAST);
        return new Tile(null, directions, "");
    }
}
