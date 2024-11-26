package app.model;

import java.util.HashMap;
import java.util.Vector;

public interface BoardObserver {
    void updateBoard(Tile[][] tiles);
    void updatePlayer(HashMap<Player, Vector2D> player);
}
