import app.controller.GameController;
import app.model.Game;
import app.view.LabyrinthDisplay;

public class App {
    public static void main(String[] args) {
        Game game = new Game();
        GameController gameController = new GameController(game);
        LabyrinthDisplay view = new LabyrinthDisplay(gameController);
        game.addObserver(view);
        game.initGame();
    }
}
