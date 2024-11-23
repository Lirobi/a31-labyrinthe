public class TileFactory {
    public TileFactory()
    {

    }
    public Tile createT(Goal goal)
    {
        boolean[] form = {true, true, false, true};
        return new Tile(goal, form);
    }
    public Tile createT()
    {
        boolean[] form = {true, true, false, true};
        return new Tile(null, form);
    }
    public Tile createI(Goal goal)
    {
        boolean[] form = {true, false, true, false};
        return new Tile(goal, form);
    }
    public Tile createI()
    {
        boolean[] form = {true, false, true, false};
        return new Tile(null, form);
    }
    public Tile createL(Goal goal)
    {
        boolean[] form = {true, true, false, false};
        return new Tile(goal, form);
    }
    public Tile createL()
    {
        boolean[] form = {true, true, false, false};
        return new Tile(null, form);
    }
}
