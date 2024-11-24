import model.Board;
import model.Tile;
import model.TileFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BoardTest {

    @Test
    void testPathsOfBoardI()
    {
        Tile[] set = new Tile[49];
        TileFactory ft = new TileFactory();
        for (int i = 0; i < 49; i++)
        {
            set[i] = ft.createI();
        }
        Board bd = new Board(set);

        boolean[][][] form = {
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false},{true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, true, false}, {true, false, false, false}},
                {{false, false, true, false}, {true, false, true, false},{true, false, true, false},{true, false, true, false},{true, false, true, false},{true, false, true, false},{true, false, false, false}},
        };
        Assertions.assertArrayEquals(bd.getPaths(), form);
    }
}
