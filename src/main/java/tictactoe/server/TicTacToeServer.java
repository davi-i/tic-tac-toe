package tictactoe.server;

import java.util.Arrays;
import java.util.Optional;

import tictactoe.shared.RoomFullException;
import tictactoe.shared.MoveResult;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.TicTacToeInterface;

public class TicTacToeServer implements TicTacToeInterface {
    private GameState state;
    private Board board;
    private Optional<Player> playerOne;
    private Optional<Player> playerTwo;

    public TicTacToeServer() {
        state = GameState.START;
        board = new Board();
        playerOne = Optional.empty();
        playerTwo = Optional.empty();
    }

    @Override
    public PlayerId enterGame(PlayerInterface player, String name) throws RoomFullException {
        if (playerOne.isEmpty()) {
            PlayerId id = new PlayerId(PlayerSymbol.CROSSES);
            playerOne = Optional.of(new Player(id, player, name));
            return id;
        } else if (playerTwo.isEmpty()) {
            PlayerId id = new PlayerId(PlayerSymbol.NOUGHTS);
            playerTwo = Optional.of(new Player(id, player, name));
            return id;
        } else {
            throw new RoomFullException();
        }
    }

    @Override
    public MoveResult makeMove(PlayerId id, int pos) {
        if (state != id.getTurn()) {
            return MoveResult.NOT_YOUR_TURN;
        }
        if (board.getTile(pos) != TileState.EMPTY) {
            return MoveResult.MOVE_NOT_ALLOWED;
        }
        board.setTile(pos, id.getTile());
        if (checkVictory(pos)) {
            changeState(id.getVictoryState());
            Player player = getPlayer(id);
            player.addScore();
            changeScore(player.getName(), player.getScore());
        } else if (checkTie()) {
            changeState(GameState.TIE);
        } else {
            changeState(id.getNextTurn());
        }
        return MoveResult.MOVE_ALLOWED;
    }

    public void update() {
        switch (state) {
            case START:
                if (playerOne.isPresent() && playerTwo.isPresent()) {
                    changeState(GameState.CROSS_TURN);
                }
                break;
            case CROSS_TURN:
            case NOUGHT_TURN:
                // updated on makeMove
                break;
            case CROSS_WIN:
            case NOUGHT_WIN:
            case TIE:
                changeState(GameState.START);
                //TODO:: Notificar jogadores que a rodada acabou.
                break;
            default:
                break;
        }
    }
    
    @Override
    public void exitGame(PlayerId id) {
        changeState(GameState.START);
        board.clean();
        //TODO:: zerar futuro scores;
    }

    private void changeState(GameState newState) {
        state = newState;
        if (playerOne.isPresent()) {
            playerOne.get().changeState(state);
        }
        if (playerTwo.isPresent()) {
            playerTwo.get().changeState(state);
        }
    }

    private void changeScore(String name, int score) {
        if (playerOne.isPresent()) {
            playerOne.get().changeScore(name, score);
        }
        if (playerTwo.isPresent()) {
            playerTwo.get().changeScore(name, score);
        }
    }

    private Player getPlayer(PlayerId id) {
        switch (id.getSymbol()) {
            case CROSSES:
                return playerOne.get();
            case NOUGHTS:
                return playerTwo.get();
            default:
                throw new RuntimeException();

        }
    }

    private boolean checkVictory(int pos) {
        TileState tile = board.getTile(pos);
        switch (pos) {
            case 0:
                return ((board.getTile(1) == tile && board.getTile(2) == tile)
                        || (board.getTile(4) == tile && board.getTile(8) == tile)
                        || (board.getTile(3) == tile && board.getTile(6) == tile));
            case 1:
                return ((board.getTile(0) == tile && board.getTile(2) == tile)
                        || (board.getTile(4) == tile && board.getTile(7) == tile));
            case 2:
                return ((board.getTile(0) == tile && board.getTile(1) == tile)
                        || (board.getTile(4) == tile && board.getTile(6) == tile)
                        || (board.getTile(5) == tile && board.getTile(8) == tile));
            case 3:
                return ((board.getTile(4) == tile && board.getTile(5) == tile)
                        || (board.getTile(0) == tile && board.getTile(7) == tile));
            case 4:
                return ((board.getTile(3) == tile && board.getTile(5) == tile)
                        || (board.getTile(1) == tile && board.getTile(7) == tile)
                        || (board.getTile(0) == tile && board.getTile(8) == tile)
                        || (board.getTile(3) == tile && board.getTile(6) == tile));
            case 5:
                return ((board.getTile(3) == tile && board.getTile(4) == tile)
                        || (board.getTile(2) == tile && board.getTile(8) == tile));
            case 6:
                return ((board.getTile(7) == tile && board.getTile(8) == tile)
                        || (board.getTile(0) == tile && board.getTile(3) == tile)
                        || (board.getTile(2) == tile && board.getTile(4) == tile));
            case 7:
                return ((board.getTile(6) == tile && board.getTile(8) == tile)
                        || (board.getTile(1) == tile && board.getTile(4) == tile));
            case 8:
                return ((board.getTile(6) == tile && board.getTile(7) == tile)
                        || (board.getTile(2) == tile && board.getTile(5) == tile)
                        || (board.getTile(0) == tile && board.getTile(4) == tile));
            default:
                // TODO: Error
                return false;
        }
    }

    private boolean checkTie() {
        return !Arrays.stream(this.board.board)
                .flatMap(row -> Arrays.stream(row))
                .anyMatch(TileState.EMPTY::equals);

    }

    public void render() {
        switch (state) {
            case CROSS_TURN:
                System.out.println("It's cross turn");
                break;
            case CROSS_WIN:
                System.out.println("Cross won!!");
                break;
            case NOUGHT_TURN:
                System.out.println("It's nought turn");
                break;
            case NOUGHT_WIN:
                System.out.println("Nought won!!");
                break;
            case START:
                System.out.println("Starting game");
                break;
            case TIE:
                System.out.println("It's a tie! Well played.");
                break;
            default:
                break;
        }
    }

}
