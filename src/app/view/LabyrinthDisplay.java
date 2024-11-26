package app.view;

import app.model.*;
import app.controller.GameController;
import app.model.BoardObserver;
import app.model.Player;
import app.model.Tile;

import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    private final JPanel _pnlContentPane = new JPanel();

    private final JPanel _pnlTop = new JPanel();
    private final JPanel _pnlMiddle = new JPanel();
    private final JPanel _pnlBottom = new JPanel();


    public LabyrinthDisplay(GameController controller) {
        super("Labyrinthe");
        setSize(1280, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        
        // Initialisation de la fenêtre

        _pnlTop.setLayout(new GridBagLayout());

        JButton btnRotateTileLeft = new JButton("Rotate tile left");
        JButton btnRotateTileRight = new JButton("Rotate tile right");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        _pnlTop.add(btnRotateTileLeft, constraints);
        constraints.gridy = 1;
        _pnlTop.add(btnRotateTileRight, constraints);
        
        constraints.gridwidth = 3;


        _pnlMiddle.setLayout(new GridLayout(9,9));


        _pnlBottom.setLayout(new BorderLayout());

        _pnlContentPane.setLayout(new BorderLayout());
        _pnlContentPane.add(_pnlTop, BorderLayout.NORTH);
        _pnlContentPane.add(_pnlMiddle, BorderLayout.CENTER);
        _pnlContentPane.add(_pnlBottom, BorderLayout.SOUTH);

        setContentPane(_pnlContentPane);

        setVisible(true);
    }


    public void display(String message) {
        System.out.println(message);
    }

    /**
     * @param tiles is a tab
     */
    @Override
    public void updateBoard(Tile[][] tiles) {
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tiles.length; j++) {
                display(tile[j].toString());
            }
        }
    }

    @Override
    public void updatePlayer(HashMap<Player, Vector2D> player)
    {
        for (Map.Entry<Player, Vector2D> entry : player.entrySet())
        {
            display(entry.getKey().toString());
            display("Le joueur est à la position (" + entry.getValue().getX() + "," + entry.getValue().getY()+")");
        }
    }
}
