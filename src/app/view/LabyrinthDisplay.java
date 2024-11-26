package app.view;

import app.model.BoardObserver;
import app.model.Board;
import app.model.Player;
import app.model.Tile;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    public LabyrinthDisplay() {

    }
    public void display(String message) {
        System.out.println(message);
    }

    /**
     * @param tiles is a tab
     */
    @Override
    public void updateBoard(Tile[][] tiles) {
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tiles.length; j++) {
                display(tile[j].toString());
            }
        }
    }

    @Override
    public void updatePlayer(HashMap<Player, Integer[]> player)
    {
        for (Map.Entry<Player, Integer[]> entry : player.entrySet())
        {
            display(entry.getKey().toString());
            display("Le joueur est à la position (" + entry.getValue()[0] + "," + entry.getValue()[1]+")");
        }
    }
}
