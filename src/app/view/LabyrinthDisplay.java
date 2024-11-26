package app.view;

import app.model.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
    public void updatePlayer(HashMap<Player, Vector2D> player)
    {
        for (Map.Entry<Player, Vector2D> entry : player.entrySet())
        {
            display(entry.getKey().toString());
            display("Le joueur est à la position (" + entry.getValue().getX() + "," + entry.getValue().getY()+")");
        }
    }
}
