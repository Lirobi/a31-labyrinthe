package app.view;

import app.model.BoardObserver;
import app.model.Board;

import javax.swing.*;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    public LabyrinthDisplay() {

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
