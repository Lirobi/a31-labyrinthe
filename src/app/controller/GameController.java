package app.controller;

import app.model.*;

public class GameController {
    private final Game _game;


    public GameController(Game bd)
    {
        _game = bd;
    }

    public void initGame()
    {
        _game.initGame();
    }

    public void rotateLeft()
    {
        _game.rotateAloneTile();
    }
    public void rotateRight()
    {
        _game.rotateAloneTile();
        _game.rotateAloneTile();
        _game.rotateAloneTile();
    }
    public void pushCardsOnBoard(Direction dir, int numRowCol)
    {
        _game.setAloneTile(_game.changeByDirection(dir, numRowCol, _game.getAloneTile()));
    }
    public void movePlayer(Direction direction)
    {
        _game.movePlayer(direction);
    }
    public void endTurn() {

        if (_game.ifCurrentPlayerWin()) {
            _game.endGame();
        }
        else {
            _game.nextTurn();
        }
    }

    public boolean isMovable(int index)
    {
        return _game.isMovable(index);
    }
}
