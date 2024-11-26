package app.controller;

import app.model.*;

import java.util.*;

public class GameController {
    private final Board _board;
    private Tile _currentTile;

    public GameController(Board bd)
    {
        _board = bd;
    }
    public void initGame()
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
        _board.initBoard(set);
        _currentTile = ft.createI();

        generatePlayers();
    }

        private void generatePlayers() {

        ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(Goal.values()));
        Collections.shuffle(goals);

        for(int i = 0; i < 4; i++) {
            Stack<Goal> _goals = new Stack<>();

            Vector2D position;
            switch (i) {
                case 0 -> {
                    position = new Vector2D(0, 0);
                }
                case 1 -> {
                    position = new Vector2D(0, 6);
                }
                case 2 -> {
                    position = new Vector2D(6, 0);
                }
                case 3 -> {
                    position = new Vector2D(6, 6);
                }
                default -> position = new Vector2D(666, -666);
            }

            for(int j = 0; j < Goal.values().length/4; j++) {
                _goals.push((goals.getFirst()));
                goals.removeFirst();
            }

            Player player = new Player(_goals);
            _board.addPlayer(player, position);
        }
    }
}
