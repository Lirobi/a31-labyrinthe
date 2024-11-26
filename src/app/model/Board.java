package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Vector;

public class Board {

    private final List<BoardObserver> _observers = new ArrayList<>();

    // Modifier le tableau d'entier en class vector2D
    // Créer un patron de conception pour le changement de tuiles du plateau car redondance
    private final HashMap<Player, Vector2D> _playersPositions = new HashMap<>();

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
    }

    private void createBoard(Tile[] setTiles)
    {
        for (int i = 0; i < SIZE * SIZE; i++)
        {
            _board[i/ SIZE][i% SIZE] = setTiles[i];
        }
        notifyObserversBoard();
    }
/*
    private void createPaths() {

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                _paths[i][j] = new ArrayList<>();
                if (i != size-1 && _board[i+1][j].isDirectionPossible(Direction.WEST) && _board[i][j].isDirectionPossible(Direction.EAST))
                    _paths[i][j].add(Direction.EAST);

                if (j != size-1 &&_board[i][j+1].isDirectionPossible(Direction.NORTH)&& _board[i][j].isDirectionPossible(Direction.SOUTH))
                    _paths[i][j].add(Direction.SOUTH);

                if (i != 0 && _board[i-1][j].isDirectionPossible(Direction.EAST)&& _board[i][j].isDirectionPossible(Direction.WEST))
                    _paths[i][j].add(Direction.WEST);

                if (j != 0 && _board[i][j-1].isDirectionPossible(Direction.SOUTH)&& _board[i][j].isDirectionPossible(Direction.NORTH))
                    _paths[i][j].add(Direction.NORTH);
            }
        }
    }
*/

    public Tile changeTileCol(int x, Tile newTile)
    {
        if (x % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[x][SIZE -1];
        for (int i = SIZE -1; i > 0; i--)
        {
            _board[x][i] = _board[x][i-1];
        }
        _board[x][0] = newTile;
    //    changePathsCol(x);

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeTileCol2(int x, Tile newTile)
    {
        if (x % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[x][0];
        for (int i = 0; i < SIZE -1; i++)
        {
            _board[x][i] = _board[x][i+1];
        }
        _board[x][SIZE -1] = newTile;
        //    changePathsCol(x);

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeTileRow2(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[0][y];
        for (int i = 0; i < SIZE -1; i++)
        {
            _board[i][y] = _board[i][y+1];
        }
        _board[SIZE -1][y] = newTile;
        //    changePathsCol(x);

        notifyObserversBoard();

        return tempRetour;
    }

    public Tile changeTileRow(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw  new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[SIZE -1][y];
        for (int i = SIZE; i > 0; i--)
        {
            _board[i][y] = _board[i][y-1];
        }
        _board[0][y] = newTile;
     //   changePathRow(y);

        notifyObserversBoard();

        return tempRetour;
    }
/*
    private void changePathsCol(int x)
    {
        for (int j = 1; j < size; j++) {
            _paths[x][j - 1][2] = _board[x][j - 1].getForm()[2] && _board[x][j].getForm()[0];
            _paths[x][j][0] = _board[x][j - 1].getForm()[2] && _board[x][j].getForm()[0];
        }

        notifyObservers();

    }
    private void changePathRow(int y)
    {
        for (int j = 0; j < size; j++) {
            _paths[j][y - 1][1] = _board[j][y - 1].getForm()[1] && _board[j][y].getForm()[3];
            _paths[j][y][3] = _board[j][y - 1].getForm()[1] && _board[j][y].getForm()[3];
        }

        notifyObservers();

    }
*/

    /**
     *
     * @param x for the columns
     * @param y for the rows
     * @return the Tile at the position (x, y) position
     */
    public Tile getTileAtPosition(int x, int y)
    {
        return _board[x][y];
    }
    public Tile[][] getBoard(){
        return _board;
    }
    /**
     * This function is used for tests
     * @return the path to test
    boolean[][][] getPaths()
    {
        return _paths;
    }
     */

   /* public Boolean isAvailableMoving(int x, int y, Direction dir)
    {
        return _board[x][y].isDirectionPossible(dir);
    }*/

    public int getSize() {
        return SIZE;
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

    public void addPlayer(Player player, Vector2D position) {
        _playersPositions.put(player, position);
        notifyObserversPlayer();
    }
    public HashMap<Player, Vector2D> getPlayer()
    {
        return _playersPositions;
    }

    // Le joueur ne peut se déplacer que d'une case à la fois
    /*
    public void movePlayer(Player player, Integer[] position) {
        if(!_playersPositions.containsKey(player))
            throw new IllegalArgumentException("Le joueur n'est pas sur le plateau");

        if(position[0] > this.getSize() || position[1] > this.getSize() || position[0] < 0 || position[1] < 0)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");

        _playersPositions.put(player, position);

        notifyObserversPlayer();
    }
    */

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
