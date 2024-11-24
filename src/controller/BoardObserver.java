package controller;

import model.Board;

public interface BoardObserver {
    void updateBoard(Board b);
}
