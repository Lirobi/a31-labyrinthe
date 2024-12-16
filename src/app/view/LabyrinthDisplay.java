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
    private final JPanel _pnlPlayerInfo = new JPanel();
    private final JPanel _pnlRotateTile = new JPanel();
    private final JPanel _movementPanel = new JPanel(new GridBagLayout());
    private final int WIDTH = 800;
    private final int HEIGHT = 900;

    private JPanel _currentTilePanel;
    private Tile _currentTile;
    private final GameController _controller;
    private final JLabel[] _playerLabels = new JLabel[4];
    private final JLabel _lblCurrentPlayer = new JLabel("Current player: ");
    private final JLabel _lblCururentGoal = new JLabel("Current goal:");
    private final JLabel[] _lblPlayer = new JLabel[4];

    public LabyrinthDisplay(GameController controller) {
        super("Labyrinthe");
        _controller = controller;
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension((int)(WIDTH * 0.7), (int)(WIDTH * 1)));
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the startup dialog after the main window is visible
        SwingUtilities.invokeLater(this::showStartupDialog);

        initializeTopPanel();
        initializeMiddlePanel();
        initializeBottomPanel();
        layoutMainPanel();

        setContentPane(_pnlContentPane);
        setVisible(true);
        
    }

    private void initializeTopPanel() {
        _pnlTop.setLayout(new BorderLayout());
        
        // Current tile display
        _currentTilePanel = new JPanel();
        _currentTilePanel.setPreferredSize(new Dimension(80, 80));

        // Rotation buttons
        JButton btnRotateTileLeft = new JButton("⤾");
        JButton btnRotateTileRight = new JButton("⤿");

        btnRotateTileLeft.addActionListener(e -> _controller.rotateLeft());
        btnRotateTileRight.addActionListener(e -> _controller.rotateRight());

        // Layout components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);


        _pnlTop.setMinimumSize(new Dimension(70,70));
        _pnlRotateTile.setMinimumSize(new Dimension(70,70));
        _pnlRotateTile.setLayout(new GridBagLayout());
        constraints.gridx = 2;
        _pnlRotateTile.add(_currentTilePanel, constraints);

        constraints.gridx = 1;
        _pnlRotateTile.add(btnRotateTileLeft, constraints);
        
        constraints.gridx = 3;
        _pnlRotateTile.add(btnRotateTileRight, constraints);
        
        _pnlTop.add(_pnlPlayerInfo, BorderLayout.WEST);
        _pnlTop.add(_pnlRotateTile, BorderLayout.CENTER);
        JPanel _pnlCurrentPlayer = new JPanel();
        _pnlCurrentPlayer.setLayout(new GridLayout(2, 1));
        _pnlCurrentPlayer.add(_lblCurrentPlayer);
        _pnlCurrentPlayer.add(_lblCururentGoal);
        _pnlTop.add(_pnlCurrentPlayer, BorderLayout.EAST);


    }

    private void initializeMiddlePanel() {
        _pnlMiddle.setLayout(new GridBagLayout());
        _pnlMiddle.setPreferredSize(new Dimension(700, 700));
        _pnlMiddle.setMinimumSize(new Dimension((int)(WIDTH * 0.7), (int)(WIDTH * 0.7)));

        int tileSize = (int)(_pnlMiddle.getWidth() / 9);

        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                JPanel pnl = new JPanel();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = i;
                gbc.gridy = j;
                pnl.setPreferredSize(new Dimension(tileSize, tileSize));
                _pnlMiddle.add(pnl, gbc);
            }
        }

    }

    private void initializeBottomPanel() {
        _pnlBottom.setLayout(new BorderLayout());
        
        

        // Player information panel
        _pnlPlayerInfo.setLayout(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            _playerLabels[i] = new JLabel("Player " + (i + 1));
            _pnlPlayerInfo.add(_playerLabels[i]);
        }

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(e -> _controller.endTurn());

        _pnlBottom.add(_movementPanel, BorderLayout.CENTER);
        _pnlBottom.add(endTurnButton, BorderLayout.SOUTH);
    }

    private void layoutMainPanel() {
        _pnlContentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        _pnlContentPane.add(_pnlTop, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        _pnlContentPane.add(_pnlMiddle, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0.0;
        _pnlContentPane.add(_pnlBottom, gbc);
    }



    public BufferedImage drawPlayerOnImage(BufferedImage image, Player player) {

        String imagePath = "./assets/images/pion-";
        imagePath += player.getName();
        imagePath += ".png";

        BufferedImage playerImage = null;

        try {
            playerImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Error: Unable to load player image from resource.");
            e.printStackTrace();
        }
        try {
            image = ImageHelper.merge(image, playerImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
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
    public void updateBoard(Tile[][] tiles, HashMap<Player, Vector2D> players) {
        GridBagConstraints constraints = new GridBagConstraints();
        _pnlMiddle.removeAll(); // Clear the panel before adding new components
        
        // Calculate tile size - slightly smaller than 1/9th of panel size to allow for gaps
        int tileSize = (int)(_pnlMiddle.getWidth() / 9); // Fixed size for tiles
        
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                constraints.gridx = j;
                constraints.gridy = i;
                
                if((i < 1 || i > 7) || (j < 1 || j > 7)) {
                    if(_controller.isMovable(i+1) && _controller.isMovable(j+1) && !(i == 0 && j == 0) && !(i == 0 && j == 8) &&
                       !(i == 8 && j == 0) && !(i == 8 && j == 8)) {
                        String buttonText;
                        Direction dir;
                        int numRowCol;
                        if(j == 0) {
                            buttonText = "→";
                            dir = Direction.NORTH;
                            numRowCol = i-1;
                        }
                        else if(j == 8) {
                            buttonText = "←";
                            dir = Direction.SOUTH;
                            numRowCol = i-1;
                        }
                        else if(i == 0) {
                            buttonText = "↓";
                            dir = Direction.WEST;
                            numRowCol = j-1;
                        }
                        else {
                            buttonText = "↑";
                            dir = Direction.EAST;
                            numRowCol = j-1;
                        }
    
                        JButton button = new JButton(buttonText);
                        button.setPreferredSize(new Dimension(tileSize, tileSize));
                        button.addActionListener(e -> _controller.pushCardsOnBoard(dir, numRowCol));
                        _pnlMiddle.add(button, constraints);
                    }
                } else {

                    Tile currentTile  = tiles[i - 1][j - 1];

                    BufferedImage image = getTileImage(currentTile);
                    for (Map.Entry<Player, Vector2D> entry : players.entrySet()) {
                        if(entry.getValue().getX() == i - 1 && entry.getValue().getY() == j - 1) {
                            image = drawPlayerOnImage(image, entry.getKey());
                        }
                    }
                    BufferedImage finalImage = image;

                    
                    JPanel panel = new JPanel() {  
                        
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g); 
                            
                            if (finalImage != null) {
                                // Use better quality rendering
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
                                
                                g2d.drawImage(finalImage, 0, 0, getWidth(), getHeight(), this);
                            }
                        }
                    };

                    if(currentTile.getGoal() != null) {
                        panel.setLayout(new BorderLayout());
                        JLabel lbl = new JLabel(currentTile.getGoal().toString());
                        lbl.setHorizontalAlignment(SwingConstants.CENTER);
                        lbl.setForeground(Color.BLACK);
                        panel.add(lbl);
                    }

                    panel.setName("tilePanel");
                    panel.setPreferredSize(new Dimension(tileSize, tileSize));
                    panel.setBackground(Color.WHITE); // Add background color
                    _pnlMiddle.add(panel, constraints);
                }
            }
        }
        _pnlMiddle.revalidate();
        _pnlMiddle.repaint();


    }
    @Override
    public void updatePlayer(HashMap<Player, Vector2D> players)
    {
        int i = 0;
        for (Map.Entry<Player, Vector2D> entry : players.entrySet()) {
            Player player = entry.getKey();
            Vector2D pos = entry.getValue();
            _playerLabels[i].setText(String.format("Player %s - Goals: %d/%d",
                    player.getName(),
                    player.getGoalSuccessful(),
                    player.getGoalSuccessful() + player.getGoalMaxNumber()));
            i++;
        }
    }

    @Override
    public void updateTile(Tile tile) {
        _currentTile = tile;
        
        // Remove any existing components
        _currentTilePanel.removeAll();

        _currentTilePanel.setMinimumSize(new Dimension(_pnlRotateTile.getHeight(), _pnlRotateTile.getHeight()));

        int tileSize = (int)(_currentTilePanel.getWidth());
        
        // Create new panel with painting capability
        JPanel tileDisplay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (_currentTile != null) {
                    BufferedImage image = getTileImage(_currentTile);
                    if (image != null) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                           RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                                           RenderingHints.VALUE_RENDER_QUALITY);
                        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            }
        };
        if(_currentTile.getGoal() != null) {
            tileDisplay.setLayout(new BorderLayout());
            JLabel lbl = new JLabel(_currentTile.getGoal().toString());
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setForeground(Color.BLACK);
            tileDisplay.add(lbl);
        }
        tileDisplay.setPreferredSize(new Dimension(70,70)); // Match the game board tile size
        tileDisplay.setBackground(Color.WHITE);
        
        // Add the tile display to the panel
        _currentTilePanel.add(tileDisplay);
        
        // Refresh the display
        _currentTilePanel.revalidate();
        _currentTilePanel.repaint();
    }

    @Override
    public void updateCurrentPlayer(Player player){
        setTitle("Labyrinthe - Player " + player.getName());
        _lblCurrentPlayer.setText("Current player: " + player.getName());
        _lblCururentGoal.setText("Current goal: " + player.getCurrentGoal().toString());

        switch(player.getName()) {
            case "jaune" -> _lblCurrentPlayer.setForeground(Color.YELLOW);
            case "bleu" -> _lblCurrentPlayer.setForeground(Color.BLUE);
            case "vert" -> _lblCurrentPlayer.setForeground(Color.GREEN);
            case "rouge" -> _lblCurrentPlayer.setForeground(Color.RED);
        }
    }

    @Override
    public void updatePossibleDirections(ArrayList<Direction> possibleDirections)
    {
        _movementPanel.removeAll();
        // Player movement controls
        String[] directions = {"↑", "→", "↓", "←"};
        Direction[] moveDirections = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < directions.length; i++) {
            JButton btn = new JButton(directions[i]);
            final Direction dir = moveDirections[i];

            gbc.gridx = i == 1 ? 2 : (i == 3 ? 0 : 1);
            gbc.gridy = i == 0 ? 0 : (i == 2 ? 2 : 1);
            if(possibleDirections.contains(dir)) {
                btn.addActionListener(e -> _controller.movePlayer(dir));
                btn.setForeground(Color.BLACK);
            } else {
                btn.setForeground(Color.RED);
            }
            _movementPanel.add(btn, gbc);
        }
        _movementPanel.repaint();
        _movementPanel.revalidate();
    }

    private void showStartupDialog() {
        JDialog dialog = new JDialog(this, "Welcome to Labyrinth", true); // true makes it modal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Labyrinth!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, gbc);

        // Add game instructions
        JTextArea instructions = new JTextArea(
            "Game Instructions:\n\n" +
            "1. Use arrow buttons to move your player\n" +
            "2. Rotate and insert tiles to create paths\n" +
            "3. Collect all your goals to win\n" +
            "4. End your turn when you're done moving"
        );
        instructions.setEditable(false);
        instructions.setBackground(panel.getBackground());
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setMargin(new Insets(10, 10, 10, 10));
        panel.add(instructions, gbc);

        // Add start button
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            dialog.dispose();
        });
        panel.add(startButton, gbc);

        dialog.add(panel);
        dialog.pack();
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    @Override
    public void updateGameStatus(GameStatus status) {

    }

    @Override
    public void updateGameStatus(GameStatus status, Player winner) {
        if (status == GameStatus.ENDED) {
            JDialog dialog = new JDialog(this, "Game Over!", true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel winnerLabel = new JLabel("Player " + winner.getName() + " wins!", SwingConstants.CENTER);
            winnerLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(winnerLabel, gbc);

            JLabel congratsLabel = new JLabel("Congratulations!", SwingConstants.CENTER);
            congratsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(congratsLabel, gbc);

            JButton exitButton = new JButton("Exit Game");
            exitButton.addActionListener(e -> {
                dialog.dispose();
            });
            panel.add(exitButton, gbc);

            dialog.add(panel);
            dialog.pack();
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(this);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        }
    }
}



