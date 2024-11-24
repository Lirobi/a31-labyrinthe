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
        System.out.print(message);
    }


    /**
     * @param b
     */
    @Override
    public void updateBoard(Board b) {
        for(int i = 0; i < b.getSize(); i++) {
            for(int j = 0; j < b.getSize(); j++) {
                display(b.getTileAtPosition(i,j).toString());
            }
            display("\n");
        }
    }
}
