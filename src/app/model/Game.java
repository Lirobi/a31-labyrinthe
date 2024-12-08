package app.model;

import java.util.*;

public class Game {

    public Game()
    {
        initGame();
    }

    private final List<BoardObserver> _observers = new ArrayList<>();

    private final HashMap<Player, Vector2D> _playersPositions = new HashMap<>();
    private final Player[] _players = new Player[4];
    private Integer _currentPlayer = 0;
    private Tile _aloneTile;
    private final Board _board = new Board();
    private final ArrayList<Direction> _possibleDirections = new ArrayList<>();

    public void initGame()
    {
        // generate the board
        _board.initBoard();
        notifyObserversBoard();
        // generate the alone tile
        TileFactory ft = new TileFactory();
        _aloneTile = ft.createI();
        notifyObserversTile();
        // generate players
        generatePlayers();
        nextPlayer(); // pour retourner sur le premier joueur sinon c'est le joueur 4 qui joue, mais il existe pas
        notifyObserversPlayer();
    }

    private void generatePlayers()
    {
        ArrayList<Goal> goals = new ArrayList<>(Arrays.asList(Goal.values()));
        Collections.shuffle(goals);

        String[] tabName = {"jaune", "bleu", "vert", "rouge"};

        for(int i = 0; i < 4; i++) {
            Stack<Goal> _goals = new Stack<>();

            Vector2D position;
            switch (i) {
                case 0 -> position = new Vector2D(0, 0);
                case 1 -> position = new Vector2D(0, 6);
                case 2 -> position = new Vector2D(6, 0);
                case 3 -> position = new Vector2D(6, 6);
                default -> position = new Vector2D(666, -666);
            }

            for(int j = 0; j < Goal.values().length/4; j++) {
                _goals.push((goals.getFirst()));
                goals.removeFirst();
            }

            Player player = new Player(_goals, tabName[i]);
            addPlayer(player, position);
        }
    }

    public Tile changeByDirection(Direction dir, int numRowCol, Tile newTile)
    {
        if (!_board.isMovable(numRowCol))
            throw new IllegalArgumentException("Vous n'avez pas le droit de pousser une carte à cette endroit");

        Tile tempRetour = switch (dir){
            case NORTH -> _board.changeByNorth(numRowCol, newTile);
            case EAST -> _board.changeByEast(numRowCol, newTile);
            case SOUTH -> _board.changeBySouth(numRowCol, newTile);
            case WEST -> _board.changeByWest(numRowCol, newTile);
        };
        notifyObserversBoard();
        return tempRetour;
    }

    public void addPlayer(Player player, Vector2D position) {
        if (_currentPlayer == 4)
            throw new IllegalArgumentException("Il y a trop de joueurs");
        _playersPositions.put(player, position);
        _players[_currentPlayer] = player;
        _currentPlayer++;
    }

    public Player getCurrentPlayer()
    {
        return _players[_currentPlayer];
    }
    public void nextPlayer()
    {
        _currentPlayer++;
        if (_currentPlayer >= 4)
            _currentPlayer = 0;
        notifyObserversCurrentPlayer();
        changePossibleDirection();
    }
    public void notifyObserversCurrentPlayer() {
        for (BoardObserver obs : _observers) {
            obs.updateCurrentPlayer(getCurrentPlayer());
        }
    }
    public HashMap<Player, Vector2D> getPlayer()
    {
        return _playersPositions;
    }
    public Tile getAloneTile()
    {
        return _aloneTile;
    }
    public void setAloneTile(Tile tile)
    {
        _aloneTile = tile;
        notifyObserversTile();
    }

    public void notifyObserversBoard() {
        for (BoardObserver obs : _observers) {
            obs.updateBoard(_board.getBoard(), _playersPositions);
        }
    }

    public void notifyObserversPlayer() {
        for (BoardObserver obs : _observers) {
            obs.updatePlayer(this.getPlayer());
        }
    }
    public void notifyObserversTile() {
        for (BoardObserver obs : _observers) {
            obs.updateTile(this.getAloneTile());
        }
    }

    public void notifyPossibleDirections() {
        for (BoardObserver obs : _observers) {
            obs.updatePossibleDirections(this._possibleDirections);
        }
    }

    public void rotateAloneTile()
    {
        _aloneTile.rotate();
        notifyObserversTile();
    }

    public void changePossibleDirection()
    {
        _possibleDirections.clear();
        Player player = getCurrentPlayer();
        Vector2D vector2 = _playersPositions.get(player);
        int x = vector2.getX();
        int y = vector2.getY();

        if(x != 0 && _board.getTileAtPosition(x-1, y).getDirection().contains(Direction.SOUTH))
            _possibleDirections.add(Direction.NORTH);
        if(x != _board.getSize() && _board.getTileAtPosition(x+1, y).getDirection().contains(Direction.NORTH))
            _possibleDirections.add(Direction.SOUTH);
        if(y != 0 && _board.getTileAtPosition(x, y-1).getDirection().contains(Direction.EAST))
            _possibleDirections.add(Direction.WEST);
        if(x != _board.getSize() && _board.getTileAtPosition(x, y+1).getDirection().contains(Direction.WEST))
            _possibleDirections.add(Direction.EAST);
        notifyPossibleDirections();
    }

    public void movePlayer(Direction direction) {
        Player player = getCurrentPlayer();
        switch (direction)
        {
            case EAST -> _playersPositions.get(player).moveRight();

            case WEST -> _playersPositions.get(player).moveLeft();

            case NORTH -> _playersPositions.get(player).moveTop();

            case SOUTH -> _playersPositions.get(player).moveBottom();}

        notifyObserversPlayer();
        changePossibleDirection();
    }

    public void nextGoalCurrentPlayer()
    {
        Tile tile = getTileCurrentPlayer();
        if (tile.existGoal())
        {
            if (tile.getGoal() == getCurrentPlayer().getCurrentGoal())
                getCurrentPlayer().nextGoal();
        }
    }

    public boolean ifCurrentPlayerWin()
    {
        return !getCurrentPlayer().isRestGoal();
    }

    public Tile getTileCurrentPlayer()
    {
        return _board.getTileAtPosition(_playersPositions.get(getCurrentPlayer()).getX(), _playersPositions.get(getCurrentPlayer()).getY());
    }

    public void addObserver(BoardObserver observer) {
        _observers.add(observer);
    }

    public void removeObserver(BoardObserver observer) {
        _observers.remove(observer);
    }
    public void removeObserver(int index) {
        _observers.remove(index);
    }

    public boolean isMovable(int numRowCol) {
        return _board.isMovable(numRowCol);
    }

}

