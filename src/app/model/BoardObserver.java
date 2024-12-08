package app.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface BoardObserver {
    void updateBoard(Tile[][] tiles, HashMap<Player, Vector2D> players);
    void updatePlayer(HashMap<Player, Vector2D> players);
    void  updateCurrentPlayer(Player player);
    void updateTile(Tile tile);
    void updatePossibleDirections(ArrayList<Direction> possibleDirections);
}
