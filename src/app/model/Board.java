package app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {

    private final List<BoardObserver> _observers = new ArrayList<>();

    private final HashMap<Player, Vector2D> _playersPositions = new HashMap<>();
    private Tile aloneTile;
    private final int SIZE = 7;
    private final Tile[][] _board = new Tile[SIZE][SIZE];
   // private final ArrayList<Direction>[][] _paths = new ArrayList[size][size];

    public Board()
    {

    }
    public void initBoard(Tile[] setTiles)
    {
        createBoard(setTiles);
        notifyObserversBoard();
        TileFactory ft = new TileFactory();
        aloneTile = ft.createI();
    }

    private void createBoard(Tile[] setTiles)
    {
        for (int i = 0; i < SIZE * SIZE; i++)
        {
            _board[i/ SIZE][i% SIZE] = setTiles[i];
        }
        notifyObserversBoard();
    }

    public Tile changeByNorth(int x, Tile newTile)
    {
        if (x % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[x][SIZE -1];
        for (int i = SIZE -1; i > 0; i--)
        {
            _board[x][i] = _board[x][i-1];
        }
        _board[x][0] = newTile;

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeBySouth(int x, Tile newTile)
    {
        if (x % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[x][0];
        for (int i = 0; i < SIZE -1; i++)
        {
            _board[x][i] = _board[x][i+1];
        }
        _board[x][SIZE -1] = newTile;

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeByEast(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[0][y];
        for (int i = 0; i < SIZE -1; i++)
        {
            _board[i][y] = _board[i][y+1];
        }
        _board[SIZE -1][y] = newTile;

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeByWest(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw  new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[SIZE -1][y];
        for (int i = SIZE; i > 0; i--)
        {
            _board[i][y] = _board[i][y-1];
        }
        _board[0][y] = newTile;

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile getTileAtPosition(int x, int y)
    {
        return _board[x][y];
    }
    public Tile[][] getBoard(){
        return _board;
    }

    public int getSize() {
        return SIZE;
    }

    public Tile getAloneTile()
    {
        return aloneTile;
    }
    public void setAloneTile(Tile tile)
    {
        aloneTile = tile;
        notifyObserversTile();
    }

    public void notifyObserversBoard() {
        for (BoardObserver obs : _observers) {
            obs.updateBoard(this.getBoard());
        }
    }

    public void notifyObserversPlayer() {
        for (BoardObserver obs : _observers) {
            obs.updatePlayer(this.getPlayer());
        }
    }
    public void notifyObserversTile() {
        for (BoardObserver obs : _observers) {
            obs.updateTile(this.getAloneTile());
        }
    }

    public void addPlayer(Player player, Vector2D position) {
        _playersPositions.put(player, position);
        notifyObserversPlayer();
    }
    public HashMap<Player, Vector2D> getPlayer()
    {
        return _playersPositions;
    }
    public void rotate()
    {
        aloneTile.rotate();
        notifyObserversTile();
    }

    public void movePlayer(Player player, Direction direction) {
        if(!_playersPositions.containsKey(player))
            throw new IllegalArgumentException("Le joueur n'est pas sur le plateau");
        Vector2D vector2 = _playersPositions.get(player);

        if(vector2.getX() == 0 && direction == Direction.NORTH)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if(vector2.getX() == SIZE && direction == Direction.SOUTH)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if(vector2.getY() == 0 && direction == Direction.WEST)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if(vector2.getY() == SIZE && direction == Direction.EAST)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");

        if (!_board[vector2.getX()-1][vector2.getY()].isDirectionPossible(direction))
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if (!_board[vector2.getX()+1][vector2.getY()].isDirectionPossible(direction))
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if (!_board[vector2.getX()-1][vector2.getY()+1].isDirectionPossible(direction))
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");
        if (!_board[vector2.getX()-1][vector2.getY()-1].isDirectionPossible(direction))
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");

        switch (direction)
        {
            case EAST ->_playersPositions.get(player).moveRight();
            case WEST ->_playersPositions.get(player).moveLeft();
            case NORTH ->_playersPositions.get(player).moveTop();
            case SOUTH -> _playersPositions.get(player).moveBottom();
        }
        notifyObserversPlayer();
    }

    public void addObserver(BoardObserver observer) {
        _observers.add(observer);
    }

    public void removeObserver(BoardObserver observer) {
        _observers.remove(observer);
    }
    public void removeObserver(int index) {
        _observers.remove(index);
    }

}
