package app.model;

import java.util.HashMap;

public class PlayerManagement {

    private final HashMap<Player, Vector2D> _playersPositions = new HashMap<>();
    private final Player[] _players = new Player[4];
    private Integer _currentPlayer = 0;

    public PlayerManagement()
    {

    }
}
