package app.controller;

import app.model.*;
import app.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class GameController {



    private Board _board;
    private Stack<Goal> _goals;
    private Tile _currentTile;

    public GameController()
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
        _board = new Board(set);
        _currentTile = ft.createI();

        View view = new View(_board);
        _board.addObserver(view);

        generateGoals();
        generatePlayers();
    }

    private void generateGoals() {
        _goals = new Stack<>();
        ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(Goal.values()));
        for(int i = 24; i > 0; i--) {
            Goal goal = goals.get((int)(Math.random() * goals.size()));
            goals.remove(goal);
            _goals.push(goal);
        }
    }


    private void generatePlayers() {
        for(int i = 0; i < 4; i++) {

            ArrayList<Goal> goals = new ArrayList<>();

            Integer[] position = new Integer[2];
            switch(i) {
                case 0:
                    position[0] = 0;
                    position[1] = 0;
                    break;
                case 1:
                    position[0] = 0;
                    position[1] = 6;
                    break;
                case 2:
                    position[0] = 6;
                    position[1] = 0;
                    break;
                case 3:
                    position[0] = 6;
                    position[1] = 6;
                    break;

            }

            for(int j = 0; j < 3; j++) {
                goals.add(_goals.pop());
            }
            Player player = new Player(goals);

            _board.addPlayer(player, position);
        }
    }







}
