import app.controller.GameController;
import app.model.Board;
import app.model.Direction;
import app.model.Tile;
import app.model.TileFactory;
import app.view.LabyrinthDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void test()
    {
        Board bd = new Board();
        bd.initGame();
        GameController game = new GameController(bd);
        game.movePlayer(Direction.EAST);
    }
}
