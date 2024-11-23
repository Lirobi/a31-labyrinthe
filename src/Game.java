public class Game {

    private Tile _currentTile;

    public Game()
    {
        createGame();
    }

    public void createGame()
    {
        Tile[] set = new Tile[49];
        TileFactory ft = new TileFactory();
        for (int i = 0; i < 49; i++)
        {
            set[i] = ft.createI();
        }
        Board bd = new Board(set);
        _currentTile = ft.createI();
    }
}
