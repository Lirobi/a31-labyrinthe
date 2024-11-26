import app.controller.GameController;
import app.model.Board;
import app.view.LabyrinthDisplay;

public class App {
    public static void main(String[] args) {
        Board bd = new Board();
        GameController game = new GameController(bd);
        LabyrinthDisplay view = new LabyrinthDisplay(game);
        bd.addObserver(view);
    }
}
