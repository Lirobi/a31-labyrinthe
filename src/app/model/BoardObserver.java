package app.model;

import java.util.HashMap;

public interface BoardObserver {
    void updateBoard(Tile[][] tiles);
    void updatePlayer(HashMap<Player, Integer[]> player);
}
