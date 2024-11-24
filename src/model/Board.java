package model;

import controller.BoardObserver;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<BoardObserver> _observers = new ArrayList<BoardObserver>();

    private final int size = 7;
    private final Tile[][] _board = new Tile[size][size];
    private final boolean[][][] _paths = new boolean[size][size][4];

    public Board(Tile[] setTiles)
    {
        createBoard(setTiles);
        createPaths();

        notifyObservers();

    }

    private void createBoard(Tile[] setTiles)
    {
        for (int i = 0; i < size*size; i++)
        {
            _board[i/size][i%size] = setTiles[i];
        }
    }

    private void createPaths() {
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                _paths[i][j - 1][2] = _board[i][j - 1].getForm()[2] && _board[i][j].getForm()[0];
                _paths[i][j][0] = _board[i][j - 1].getForm()[2] && _board[i][j].getForm()[0];
            }
        }
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size; j++) {
                _paths[j][i - 1][1] = _board[j][i - 1].getForm()[1] && _board[j][i].getForm()[3];
                _paths[j][i][3] = _board[j][i - 1].getForm()[1] && _board[j][i].getForm()[3];
            }
        }
        for (int i = 0; i < size; i++)
            _paths[0][i][3] = false;
        for (int i = 0; i < size; i++)
            _paths[size-1][i][1] = false;
        for (int i = 0; i < size; i++)
            _paths[i][0][0] = false;
        for (int i = 0; i < size; i++)
            _paths[i][size-1][3] = false;

    }

    // Possibilité de mutualiser le code
    public Tile changeTileCol(int x, Tile newTile)
    {
        if (x % 2 == 0)
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        for (int i = 0; i < size - 1; i++)
        {
            _board[x][i] = _board[x][i+1];
        }
        _board[x][0] = newTile;
        changePathsCol(x);

        notifyObservers();

        return _board[x][size-1];
    }

    public Tile changeTileRow(int y, Tile newTile)
    {
        if (y % 2 == 0)
            throw  new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");
        for (int i = 0; i < size - 1; i++)
        {
            _board[i][y] = _board[i][y+1];
        }
        _board[0][y] = newTile;
        changePathRow(y);

        notifyObservers();

        return _board[size-1][y];
    }

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

    public Tile getTileAtPosition(int x, int y)
    {
        return _board[x][y];
    }

    /**
     * This function is used for tests
     * @return the path to test
     */
    boolean[][][] getPaths()
    {
        return _paths;
    }

    public void notifyObservers() {
        for (BoardObserver obs : _observers) {
            obs.updateBoard(this);
        }
    }

    public void addObserver(BoardObserver observer) {
        _observers.add(observer);
    }

    public void removeObserver(BoardObserver observer) {
        _observers.remove(observer);
    }

}
