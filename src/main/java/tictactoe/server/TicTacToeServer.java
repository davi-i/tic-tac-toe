package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

import tictactoe.shared.RoomFullException;
import tictactoe.shared.Board;
import tictactoe.shared.GameState;
import tictactoe.shared.MoveResult;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.TicTacToeInterface;

public class TicTacToeServer extends UnicastRemoteObject implements TicTacToeInterface {
    private GameState state;
    private Board board;
    private Optional<Player> playerOne;
    private Optional<Player> playerTwo;

    public TicTacToeServer() throws RemoteException {
        super();
        state = GameState.START;
        board = new Board();
        playerOne = Optional.empty();
        playerTwo = Optional.empty();
    }

    @Override
    public PlayerId enterGame(PlayerInterface player, String name) throws RoomFullException, RemoteException {
        System.out.println("player " + name + " is trying to enter game");
        PlayerId id;
        if (playerOne.isEmpty()) {
            id = new PlayerId(PlayerSymbol.CROSSES);
            playerOne = Optional.of(new Player(id, player, name));
        } else if (playerTwo.isEmpty()) {
            id = new PlayerId(PlayerSymbol.NOUGHTS);
            playerTwo = Optional.of(new Player(id, player, name));

            // Starts the game
            // changeState(GameState.CROSS_TURN);
        } else {
            throw new RoomFullException();
        }
        System.out.println("player " + name + " entered the game");
        return id;
    }

    @Override
    public MoveResult makeMove(PlayerId id, int pos) throws RemoteException {
        if (state != id.getTurn()) {
            return MoveResult.NOT_YOUR_TURN;
        }
        if (board.getTile(pos) != TileState.EMPTY) {
            return MoveResult.MOVE_NOT_ALLOWED;
        }

        Player player = getPlayer(id);
        System.out.println("player " + player.getName() + " made move at position " + pos);
        board.setTile(pos, id.getTile());
        // TODO: NOTIFY PLAYERS WHO WON
        if (checkVictory(pos)) {
            System.out.println("player " + player.getName() + " won this round");
            player.addScore();
            changeState(GameState.START);
        } else if (checkTie()) {
            System.out.println("Old lady!");
            changeState(GameState.START);
        } else {
            changeState(GameState.START);
        }
        return MoveResult.MOVE_ALLOWED;
    }

    @Override
    public void exitGame(PlayerId id) throws RemoteException {
        board.clean();
        playerOne.get().setScore(0);
        playerTwo.get().setScore(0);

        Player player = getPlayer(id);
        System.out.println("player " + player.getName() + " left the game. :(");

        changeState(GameState.START);
    }

    private void changeState(GameState newState) throws RemoteException {
        state = newState;
        if (playerOne.isPresent()) {
            playerOne.get().changeState(
                    state,
                    board,
                    playerOne.get().getScore(),
                    playerTwo.get().getScore());
        }
        if (playerTwo.isPresent()) {
            playerTwo.get().changeState(
                    state,
                    board,
                    playerOne.get().getScore(),
                    playerTwo.get().getScore());
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
        return !board.stream().anyMatch(TileState.EMPTY::equals);

    }
}
