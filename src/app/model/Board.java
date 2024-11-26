package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Board {

    private final List<BoardObserver> _observers = new ArrayList<>();

    // Modifier le tableau d'entier en class vector2D
    // Créer un patron de conception pour le changement de tuiles du plateau car redondance
    private final HashMap<Player, Integer[]> _playersPositions = new HashMap<>();

    private final int size = 7;
    private final Tile[][] _board = new Tile[size][size];
   // private final ArrayList<Direction>[][] _paths = new ArrayList[size][size];

    public Board()
    {

    }
    public void initBoard(Tile[] setTiles)
    {
        createBoard(setTiles);
        notifyObservers();
    }

    private void createBoard(Tile[] setTiles)
    {
        for (int i = 0; i < size*size; i++)
        {
            _board[i/size][i%size] = setTiles[i];
        }
        notifyObservers();
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

        Tile tempRetour = _board[x][size-1];
        for (int i = size-1; i > 0; i--)
        {
            _board[x][i] = _board[x][i-1];
        }
        _board[x][0] = newTile;
    //    changePathsCol(x);

        notifyObservers();

        return tempRetour;
    }

    public Tile changeTileCol2(int x, Tile newTile)
    {
        if (x % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[x][0];
        for (int i = 0; i < size-1; i++)
        {
            _board[x][i] = _board[x][i+1];
        }
        _board[x][size-1] = newTile;
        //    changePathsCol(x);

        notifyObservers();

        return tempRetour;
    }

    public Tile changeTileRow2(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[0][y];
        for (int i = 0; i < size-1; i++)
        {
            _board[i][y] = _board[i][y+1];
        }
        _board[size-1][y] = newTile;
        //    changePathsCol(x);

        notifyObservers();

        return tempRetour;
    }

    public Tile changeTileRow(int y, Tile newTile)
    {
        if (y % 2 == 1)
            throw  new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = _board[size-1][y];
        for (int i = size; i > 0; i--)
        {
            _board[i][y] = _board[i][y-1];
        }
        _board[0][y] = newTile;
     //   changePathRow(y);

        notifyObservers();

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

    /**
     * This function is used for tests
     * @return the path to test
    boolean[][][] getPaths()
    {
        return _paths;
    }
     */

    public Boolean isAvailableMoving(int x, int y, Direction dir)
    {
        return _board[x][y].isDirectionPossible(dir);
    }

    public int getSize() {
        return size;
    }

    public void notifyObservers() {
        for (BoardObserver obs : _observers) {
            obs.updateBoard(this);
        }
    }

    public void addPlayer(Player player, Integer[] position) {
        _playersPositions.put(player, position);
    }

    // Le joueur ne peut se déplacer que d'une case à la fois
    public void movePlayer(Player player, Integer[] position) {
        if(!_playersPositions.containsKey(player))
            throw new IllegalArgumentException("Le joueur n'est pas sur le plateau");

        if(position[0] > this.getSize() || position[1] > this.getSize() || position[0] < 0 || position[1] < 0)
            throw new IllegalArgumentException("Le joueur ne peut pas se déplacer à cet endroit");

        _playersPositions.put(player, position);

        notifyObservers();
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
