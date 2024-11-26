import app.controller.GameController;
import app.model.Board;
import app.view.LabyrinthDisplay;

public class App {
    public static void main(String[] args) {
        Board bd = new Board();
        LabyrinthDisplay view = new LabyrinthDisplay();
        bd.addObserver(view);
        GameController game = new GameController(bd);

    }
}
