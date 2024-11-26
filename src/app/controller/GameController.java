package app.controller;

import app.model.*;

import java.util.*;

public class GameController {
    private final Board _board;
    private Tile _currentTile;

    public GameController(Board bd)
    {
        _board = bd;
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

        Random alea = new Random();
        ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(Goal.values()));

        for(int i = 0; i < 4; i++) {
            Stack<Goal> _goals = new Stack<>();

            Integer[] position = new Integer[2];
            switch (i) {
                case 0 -> {
                    position[0] = 0;
                    position[1] = 0;
                }
                case 1 -> {
                    position[0] = 0;
                    position[1] = 6;
                }
                case 2 -> {
                    position[0] = 6;
                    position[1] = 0;
                }
                case 3 -> {
                    position[0] = 6;
                    position[1] = 6;
                }
            }

            for(int j = 0; j < Goal.values().length/4; j++) {
                int index = alea.nextInt(goals.size());
                _goals.push((goals.get(index)));
                goals.remove(index);
            }

            Player player = new Player(_goals);
            _board.addPlayer(player, position);
        }
    }
}
