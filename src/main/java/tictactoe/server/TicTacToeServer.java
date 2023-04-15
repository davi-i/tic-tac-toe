package tictactoe.server;

import java.util.Arrays;
import java.util.Optional;

import tictactoe.shared.RoomFullException;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.TicTacToeInterface;

public class TicTacToeServer implements TicTacToeInterface {
    private GameState state;
    private Board board;
    private boolean finished;
    private Optional<Player> playerOne;
    private Optional<Player> playerTwo;

    public TicTacToeServer() {
        state = GameState.START;
        finished = false;
        board = new Board();
        playerOne = Optional.empty();
        playerTwo = Optional.empty();
    }

    @Override
    public PlayerId enterGame(PlayerInterface player) throws RoomFullException {
        if (playerOne.isEmpty()) {
            PlayerId id = new PlayerId(PlayerSymbol.CROSSES);
            playerOne = Optional.of(new Player(id, player));
            return id;
        } else if (playerTwo.isEmpty()) {
            PlayerId id = new PlayerId(PlayerSymbol.NOUGHTS);
            playerTwo = Optional.of(new Player(id, player));
            return id;
        } else {
            throw new RoomFullException();
        }
    }

    @Override
    public void makeMove(PlayerId id, int pos) {
        if (state != id.getTurn()) {
            // TODO: send message to other player
            return;
        }
        if (board.getTile(pos) != TileState.EMPTY) {
            // TODO: send message to current player
            return;
        }
        board.setTile(pos, id.getTile());
        if (checkVictory(pos)) {
            state = id.getVictoryState();
        } else if (checkTie()) {
            state = GameState.TIE;
        } else {
            state = id.getNextTurn();
        }
        // TODO: notify players of state change
    }

    public void update() {
        switch (state) {
            case START:
                if (playerOne.isPresent() && playerTwo.isPresent()) {
                    state = GameState.CROSS_TURN;
                }
                break;
            case CROSS_TURN:
            case NOUGHT_TURN:
                // updated on makeMove
                break;
            case CROSS_WIN:
            case NOUGHT_WIN:
            case TIE:
                state = GameState.RESTART;
                break;
            case RESTART:
                // TODO: ask clients what to do
                break;
            case EXIT:
                finished = true;
                break;
            default:
                break;
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
            case EXIT:
                System.out.println("Now exiting!");
                break;
            case NOUGHT_TURN:
                System.out.println("It's nought turn");
                break;
            case NOUGHT_WIN:
                System.out.println("Nought won!!");
                break;
            case RESTART:
                System.out.println("Do you want to restart the game? [Y/n]");
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

    public boolean hasFinished() {
        return finished;
    }

}
