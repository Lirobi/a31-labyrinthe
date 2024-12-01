package app.view;

import app.controller.GameController;
import app.helpers.ImageHelper;
import app.model.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    private final JPanel _pnlContentPane = new JPanel();

    private final JPanel _pnlTop = new JPanel();
    private final JPanel _pnlMiddle = new JPanel();
    private final JPanel _pnlBottom = new JPanel();

    private final int WIDTH = 800;
    private final int HEIGHT = 1000;

    public LabyrinthDisplay(GameController controller) {
        super("Labyrinthe");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
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

        _pnlMiddle.setMinimumSize(new Dimension((int) getSize().getWidth(), (int) getSize().getWidth()));


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
    
    public BufferedImage getTileImage(Tile tile) {
        String path = tile.getPathImg();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Error: Unable to load image from resource.");
            e.printStackTrace();
        }

        ArrayList<Direction> direction = tile.getDirection();

        if(tile.getPathImg().contains("T")) {
            if(!direction.contains(Direction.SOUTH)) {
                image = ImageHelper.rotate(image, Math.PI);
            }
            else if(!direction.contains(Direction.EAST)) {
                image = ImageHelper.rotateClockwise(image);
            }
            else if(!direction.contains(Direction.WEST)) {
                image = ImageHelper.rotateCounterClockwise(image);
            }
        }
        else if(path.contains("I")) {
            // Si la direction est a l'horizontale, alors il faut faire pivoter l'image
            if(direction.contains(Direction.EAST)) {
                image = ImageHelper.rotateClockwise(image);
            }
        } 
        else {
            if(direction.contains(Direction.WEST)) {
                if(direction.contains(Direction.SOUTH)) {
                    image = ImageHelper.rotateClockwise(image);
                } 
                else {
                    image = ImageHelper.rotate(image, Math.PI);
                }
            }
            else if(direction.contains(Direction.EAST) && direction.contains(Direction.NORTH)) {
                image = ImageHelper.rotateCounterClockwise(image);
            }
            
        }
        if (image == null) {
            System.err.println("Error: Unable to load image at path: " + path);
        }

        return image;
    }
    /**
     * @param tiles is a tab
     */
    @Override
    public void updateBoard(Tile[][] tiles) {
        GridBagConstraints constraints = new GridBagConstraints();
        _pnlMiddle.removeAll(); // Clear the panel before adding new components
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                constraints.gridx = j;
                constraints.gridy = i;
                if((i < 1 || i > 7) || (j < 1 || j > 7)) {
                    if(i % 2 == 0 && j % 2 == 0 && !(i == 0 && j == 0) && !(i == 0 && j == 8) && !(i == 8 && j == 0) && !(i == 8 && j == 8)) {
                        String buttonText = "";
                        if(j == 0) {
                            buttonText = ">";
                        } else if(j == 8) {
                            buttonText = "<";
                        }
    
                        if(i == 0) {
                            buttonText = "v";
                        } else if(i == 8) {
                            buttonText = "^";
                        }
    
                        JButton button = new JButton(buttonText);
                        _pnlMiddle.add(button, constraints);
                    }
                } else {
                    BufferedImage image = getTileImage(tiles[i - 1][j - 1]);
    
                    JPanel panel = new JPanel() {  
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g); 
                            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                            
                        }
                    };
                    panel.setPreferredSize(new Dimension(getWidth() / 9, getWidth() / 9));
                    _pnlMiddle.add(panel, constraints);
                }
            }
        }
        _pnlMiddle.revalidate();
        _pnlMiddle.repaint();
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

    @Override
    public void updateTile(Tile tile)
    {
        // quand la tuile seule fait une rotation
    }
}