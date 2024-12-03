package app.model;

import java.util.HashMap;

public interface BoardObserver {
    void updateBoard(Tile[][] tiles);
    void updatePlayer(HashMap<Player, Vector2D> player);
    void  updateCurrentPlayer(Player player);
    void updateTile(Tile tile);
}
