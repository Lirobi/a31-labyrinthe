package view;

import controller.BoardObserver;
import model.Board;
import model.TileFactory;

public class View implements BoardObserver {
    private Board _board;
    public View(Board board) {
        this._board = board;
        this._board.addObserver(this);

    }
    public void display(String message) {
        System.out.println(message);
    }




    /**
     * @param board
     */
    @Override
    public void updateBoard(Board board) {
        display("Board updated: " + board.getTileAtPosition(0,0).toString());
    }
}
