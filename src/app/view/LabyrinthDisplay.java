package app.view;

import app.model.*;
import app.controller.GameController;
import app.model.BoardObserver;
import app.model.Player;
import app.model.Tile;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicBorders;

import java.util.HashMap;
import java.util.Map;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    private final JPanel _pnlContentPane = new JPanel();

    private final JPanel _pnlTop = new JPanel();
    private final JPanel _pnlMiddle = new JPanel();
    private final JPanel _pnlBottom = new JPanel();

    private final int _width = 800;
    private final int _height = 1280;



    public LabyrinthDisplay(GameController controller) {
        super("Labyrinthe");
        setSize(_width, _height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


        
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
        

        // Le layout du board doit faire 9x9 (les cases exterieures servent a mettre les boutons pour déplacer les tuiles, et les cases du centre (7x7) contiennent les tuiles)
        _pnlMiddle.setLayout(new GridBagLayout());
        // GENERATION DU BOARD DANS UPDATEBOARD

        _pnlMiddle.setBorder(new BevelBorder(1));
        constraints = new GridBagConstraints();

        _pnlMiddle.setPreferredSize(new Dimension(_width, _width));
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                JPanel panel = new JPanel();
                constraints.gridx = i;
                constraints.gridy = j;
                panel.setBorder(new BevelBorder(1));
                panel.setPreferredSize(new Dimension(Math.round(_width / 9), Math.round(_width / 9)));

                // Partie des tiles
                if(i >= 1 && i <= 7 && j >= 1 && j <= 7) {
                    panel.setBackground(Color.BLUE);
                    Image background = Toolkit.getDefaultToolkit().createImage("Background.png");
                    gamePanel.drawImage(background, 0, 0, null);
                    _pnlMiddle.add(panel, constraints);
                }
            }
        }



        _pnlBottom.setLayout(new BorderLayout());

        _pnlContentPane.setLayout(new GridBagLayout());
        GridBagConstraints contentPaneConstraints = new GridBagConstraints();
        contentPaneConstraints.gridx = 0;

        contentPaneConstraints.gridy = 0;
        contentPaneConstraints.fill = GridBagConstraints.BOTH;
        _pnlContentPane.add(_pnlTop, contentPaneConstraints);
        contentPaneConstraints.gridy = 1;
        _pnlContentPane.add(_pnlMiddle, contentPaneConstraints);
        contentPaneConstraints.gridy = 2;
        _pnlContentPane.add(_pnlBottom, contentPaneConstraints);

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