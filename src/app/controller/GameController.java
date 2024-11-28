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
        
        // Pour l'instant, toutes les tuiles I sont les tuiles qui seront dans le futur aléatoires, le reste sont fixes.

        set[0] = ft.createL();
        set[1] = ft.createI();
        set[2] = ft.createT();
        set[3] = ft.createI();
        set[4] = ft.createT();
        set[5] = ft.createI();
        set[6] = ft.createL();
        set[6].rotate();

        set[7] = ft.createI();
        set[8] = ft.createI();
        set[9] = ft.createI();
        set[10] = ft.createI();
        set[11] = ft.createI();
        set[12] = ft.createI();
        set[13] = ft.createI();

        set[14] = ft.createT();
        set[14].rotate();
        set[14].rotate();
        set[14].rotate();
        set[15] = ft.createI();
        set[16] = ft.createT();
        set[16].rotate();
        set[16].rotate();
        set[16].rotate();
        set[17] = ft.createI();
        set[18] = ft.createT();
        set[19] = ft.createI();
        set[20] = ft.createT();
        set[20].rotate();

        set[21] = ft.createI();
        set[22] = ft.createI();
        set[23] = ft.createI();
        set[24] = ft.createI();
        set[25] = ft.createI();
        set[26] = ft.createI();
        set[27] = ft.createI();

        set[28] = ft.createT();
        set[28].rotate();
        set[28].rotate();
        set[28].rotate();
        set[29] = ft.createI();
        set[30] = ft.createT();
        set[30].rotate();
        set[30].rotate();
        set[31] = ft.createI();
        set[32] = ft.createT();
        set[32].rotate();
        set[33] = ft.createI();
        set[34] = ft.createT();
        set[34].rotate();

        set[35] = ft.createI();
        set[36] = ft.createI();
        set[37] = ft.createI();
        set[38] = ft.createI();
        set[39] = ft.createI();
        set[40] = ft.createI();
        set[41] = ft.createI();

        set[42] = ft.createL();
        set[42].rotate();
        set[42].rotate();
        set[42].rotate();
        set[43] = ft.createI();
        set[44] = ft.createT();
        set[44].rotate();
        set[44].rotate();
        set[45] = ft.createI();
        set[46] = ft.createT();
        set[46].rotate();
        set[46].rotate();
        set[47] = ft.createI();
        set[48] = ft.createL();
        set[48].rotate();
        set[48].rotate();

        
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