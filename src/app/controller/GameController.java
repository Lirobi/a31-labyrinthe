package app.controller;

import app.model.*;
import java.util.*;

public class GameController {
    private final Board _board;


    public GameController(Board bd)
    {
        _board = bd;
    }

    public void initGame()
    {
        _board.initGame();
    }

    public void rotateLeft()
    {
        _board.rotateAloneTile();
    }
    public void rotateRight()
    {
        _board.rotateAloneTile();
        _board.rotateAloneTile();
        _board.rotateAloneTile();
    }
    public void pushCardsOnBoard(Direction dir, int numRowCol)
    {
        _board.setAloneTile(_board.changeByDirection(dir, numRowCol, _board.getAloneTile()));
    }
    public void movePlayer(Direction direction)
    {
        _board.movePlayer(_board.getCurrentPlayer(), direction);
    }
    public void endTurn() {
        Tile tile = _board.getTileAtPosition(_board.getPlayer().get(_board.getCurrentPlayer()).getX(), _board.getPlayer().get(_board.getCurrentPlayer()).getY());
        if (tile.existGoal())
        {
            if (tile.getGoal() == _board.getCurrentPlayer().getCurrentGoal())
                _board.getCurrentPlayer().nextGoal();
        }

        if (!_board.getCurrentPlayer().isRestGoal())
            System.exit(0);
        else {
            _board.nextPlayer();
        }
    }

    public boolean isMovable(int index)
    {
        return _board.isMovable(index);
    }
}
