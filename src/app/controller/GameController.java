package app.controller;

import app.model.*;
import java.util.*;

public class GameController {
    private final Board _board;
    private final Player[] _players = new Player[4];
    private Integer _currentPlayer;

    public GameController(Board bd)
    {
        _board = bd;
    }
    public void initGame()
    {
        _board.initBoard(generateBoard());
        generatePlayers();
    }

    private void generatePlayers() 
    {
        ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(Goal.values()));
        Collections.shuffle(goals);

        for(int i = 0; i < 4; i++) {
            Stack<Goal> _goals = new Stack<>();

            Vector2D position;
            switch (i) {
                case 0 -> position = new Vector2D(0, 0);
                case 1 -> position = new Vector2D(0, 6);
                case 2 -> position = new Vector2D(6, 0);
                case 3 -> position = new Vector2D(6, 6);
                default -> position = new Vector2D(666, -666);
            }

            for(int j = 0; j < Goal.values().length/4; j++) {
                _goals.push((goals.getFirst()));
                goals.removeFirst();
            }

            Player player = new Player(_goals);
            _players[i] = player;
            _board.addPlayer(player, position);
        }
    }

    private Tile[] generateBoard() {
        Tile[] set = new Tile[49];
        TileFactory ft = new TileFactory();
    
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            tiles.add(ft.createL());
        }
        for (int i = 0; i < 12; i++) {
            tiles.add(ft.createI());
        }
        for (int i = 0; i < 6; i++) {
            tiles.add(ft.createT());
        }
    
        Collections.shuffle(tiles);
        for (Tile t : tiles) {
            int rotations = new Random().nextInt(4);
            for (int i = 0; i < rotations; i++) {
                t.rotate();
            }
        }
    
        Stack<Tile> randomTiles = new Stack<>();
        randomTiles.addAll(tiles);
    
        set[0] = ft.createL();
        set[2] = ft.createT();
        set[4] = ft.createT();
        set[6] = ft.createL();
        set[6].rotate();
        set[14] = ft.createT();
        set[14].rotate();
        set[14].rotate();
        set[14].rotate();
        set[16] = ft.createT();
        set[16].rotate();
        set[16].rotate();
        set[16].rotate();
        set[18] = ft.createT();
        set[20] = ft.createT();
        set[20].rotate();
        set[28] = ft.createT();
        set[28].rotate();
        set[28].rotate();
        set[28].rotate();
        set[30] = ft.createT();
        set[30].rotate();
        set[30].rotate();
        set[32] = ft.createT();
        set[32].rotate();
        set[34] = ft.createT();
        set[34].rotate();
        set[42] = ft.createL();
        set[42].rotate();
        set[42].rotate();
        set[42].rotate();
        set[44] = ft.createT();
        set[44].rotate();
        set[44].rotate();
        set[46] = ft.createT();
        set[46].rotate();
        set[46].rotate();
        set[48] = ft.createL();
        set[48].rotate();
        set[48].rotate();
    
        for (int i = 0; i < set.length; i++) {
            if (set[i] == null) {
                set[i] = randomTiles.pop();
            }
        }
    
        return set;
    }

    public void rotateLeft()
    {
        _board.getAloneTile().rotate();
    }
    public void rotateRight()
    {
        _board.getAloneTile().rotate();
        _board.getAloneTile().rotate();
        _board.getAloneTile().rotate();
    }
    public void pushCardsOnBoard(Direction dir, int numRowCol)
    {
        _board.setAloneTile(_board.changeByDirection(dir, numRowCol, _board.getAloneTile()));
    }
    public void movePlayer(Direction direction)
    {
        _board.movePlayer(_players[_currentPlayer], direction);
    }
    public void endTurn() {
        if (!_players[_currentPlayer].isRestGoal())
            System.exit(0);
        else {
            _currentPlayer++;
            if (_currentPlayer == 4)
                _currentPlayer = 0;
        }
    }



}
