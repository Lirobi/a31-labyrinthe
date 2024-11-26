package app.model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    public static Direction next(Direction current)
    {
        return switch (current)
                {
                    case NORTH -> Direction.EAST;
                    case EAST -> Direction.SOUTH ;
                    case SOUTH -> Direction.WEST ;
                    case WEST -> Direction.NORTH ;
                };
    }
}
