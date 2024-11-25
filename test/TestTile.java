import app.model.Tile;
import app.model.TileFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTile {

    @Test
    void testRotateI()
    {
        TileFactory factory = new TileFactory();
        Tile myTile = factory.createI();
        myTile.rotate();
        boolean[] form = {false, true, false, true};
        Assertions.assertArrayEquals(myTile.getForm(), form);
    }
    @Test
    void testRotateT()
    {
        TileFactory factory = new TileFactory();
        Tile myTile = factory.createT();
        myTile.rotate();
        boolean[] form = {true, true, true, false};
        Assertions.assertArrayEquals(myTile.getForm(), form);

    }
    @Test
    void testRotateL()
    {
        TileFactory factory = new TileFactory();
        Tile myTile = factory.createL();
        myTile.rotate();
        boolean[] form = {false, true, true, false};
        Assertions.assertArrayEquals(myTile.getForm(), form);

    }
}
