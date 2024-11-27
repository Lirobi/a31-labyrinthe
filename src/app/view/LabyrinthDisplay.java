package app.view;

import app.controller.GameController;
import app.model.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class LabyrinthDisplay extends JFrame implements BoardObserver {

    private final JPanel _pnlContentPane = new JPanel();

    private final JPanel _pnlTop = new JPanel();
    private final JPanel _pnlMiddle = new JPanel();
    private final JPanel _pnlBottom = new JPanel();

    private final int _width = 800;
    private final int _height = 1000;



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

        _pnlMiddle.setPreferredSize(new Dimension(_width, _width));
        



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
        GridBagConstraints constraints = new GridBagConstraints();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {

                // Partie des tiles
                if(i >= 1 && i <= 7 && j >= 1 && j <= 7) {
                    constraints.gridx = j;
                    constraints.gridy = i;
                    Image image = getTileImage(tiles[i - 1][j - 1]);

                    JPanel panel = new JPanel() {  
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g); 
                            g.drawImage(image, 0, 0, (int)Math.round(_width / 9), (int)Math.round(((double)_width) / 9), null);
                        }
                    };
                    panel.setPreferredSize(new Dimension((int)Math.round(_width / 9), (int)Math.round(_width / 9)));
                    _pnlMiddle.add(panel, constraints);
                }
                if((i < 1 || i > 7) || (j < 1 || j > 7)) {
                    if(i%2==0 && j%2==0 && !(i == 0 && j == 0) && !(i == 0 && j == 8) && !(i == 8 && j == 0) && !(i == 8 && j == 8)) {
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
                        constraints.gridx = j;
                        constraints.gridy = i;
                        JPanel panel = new JPanel();
                        JButton btn = new JButton(buttonText);
                        btn.setBackground(Color.WHITE);
                        btn.setBorder(null);
                        btn.setForeground(Color.ORANGE);
                        btn.setFont(btn.getFont().deriveFont(Font.BOLD, (float) btn.getFont().getSize() + 10));
                        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        panel.add(btn);
                        _pnlMiddle.add(panel, constraints);
                    }
                }
            }
        }
        _pnlMiddle.revalidate();
        _pnlMiddle.repaint();
    }


    public Image getTileImage(Tile tile) {
        String path = "./assets/images/";
        Image image = null;

        ArrayList<Direction> direction = tile.getDirection();

        if(direction.size() == 3) { // La tile T est la seule a avoir 3 directions
            path += "tile_T.png";
            image = new ImageIcon(path).getImage();

            if(!direction.contains(Direction.SOUTH)) {
                image = ImageHelper.rotate(ImageHelper.toBufferedImage(image), Math.PI);
            }
            else if(!direction.contains(Direction.EAST)) {
                image = ImageHelper.rotateClockwise(ImageHelper.toBufferedImage(image));
            }
            else if(!direction.contains(Direction.WEST)) {
                image = ImageHelper.rotateCounterClockwise(ImageHelper.toBufferedImage(image));
            }


        }
        else {
            // Si la direction est une ligne droite alors c'est un I
            if(direction.contains(Direction.NORTH) && direction.contains(Direction.SOUTH) || direction.contains(Direction.EAST) && direction.contains(Direction.WEST)) {
                path += "tile_I.png";
                image = new ImageIcon(path).getImage();
                


                // Si la direction est a l'horizontale, alors il faut faire pivoter l'image
                if(direction.contains(Direction.EAST)) {
                    image = ImageHelper.rotateClockwise(ImageHelper.toBufferedImage(image));
                }


            } 
            // Sinon, c'est forcément un L
            else {
                path += "tile_L.png";
                image = new ImageIcon(path).getImage();
                
                if(direction.contains(Direction.WEST)) {
                    if(direction.contains(Direction.SOUTH)) {
                        image = ImageHelper.rotateClockwise(ImageHelper.toBufferedImage(image));
                    } 
                    else {
                        image = ImageHelper.rotate(ImageHelper.toBufferedImage(image), Math.PI);
                    }
                }
                else if(direction.contains(Direction.EAST) && direction.contains(Direction.NORTH)) {
                    image = ImageHelper.rotateCounterClockwise(ImageHelper.toBufferedImage(image));
                }
                
            }
        }

        return image;
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