package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

import tictactoe.shared.RoomFullException;
import tictactoe.shared.Board;
import tictactoe.shared.GameState;
import tictactoe.shared.MoveResult;
import tictactoe.shared.PlayerIdInterface;
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
    public PlayerIdInterface enterGame(PlayerInterface player, String name) throws RoomFullException, RemoteException {
        System.out.println("player " + name + " is trying to enter game");
        PlayerId id;
        if (playerOne.isEmpty()) {
            id = new PlayerId(PlayerSymbol.CROSSES);
            playerOne = Optional.of(new Player(id, player, name));
        } else if (playerTwo.isEmpty()) {
            id = new PlayerId(PlayerSymbol.NOUGHTS);
            playerTwo = Optional.of(new Player(id, player, name));

            // Starts the game
            processGame();
        } else {
            throw new RoomFullException();
        }
        System.out.println("player " + name + " entered the game");
        return id;
    }

    @Override
    public void exitGame(PlayerIdInterface id) throws RemoteException {
        board.clean();
        playerOne.get().setScore(0);
        playerTwo.get().setScore(0);

        Player player = getPlayer(id);
        System.out.println("player " + player.getName() + " left the game. :(");

        state = GameState.START;
    }

    private void processGame() throws RemoteException {
        while (state == GameState.PROCESS) {
            MoveResult move;
            do {
                move = getMove(playerOne.get());
                // TODO: notify the player of wrong move
            } while (move != MoveResult.MOVE_ALLOWED);

            do {
                move = getMove(playerTwo.get());
                // TODO: notify the player of wrong move
            } while (move != MoveResult.MOVE_ALLOWED);
        }
    }

    public MoveResult getMove(Player player) throws RemoteException {
        int pos = player.getMove(board);
        TileState tile;
        try {
            tile = board.getTile(pos);
        } catch (IndexOutOfBoundsException e) {
            return MoveResult.INVALID_MOVE;
        }
        if (tile != TileState.EMPTY) {
            return MoveResult.TILE_NOT_EMPTY;
        }

        System.out.println("player " + player.getName() + " made move at position " + pos);
        board.setTile(pos, player.getId().getTile());
        // TODO: NOTIFY PLAYERS WHO WON
        if (checkVictory(pos)) {
            System.out.println("player " + player.getName() + " won this round");
            player.addScore();
            state = GameState.START;
        } else if (checkTie()) {
            System.out.println("Old lady!");
            state = GameState.START;
        }
        return MoveResult.MOVE_ALLOWED;
    }

    // private void changeState(GameState newState) throws RemoteException {
    // state = newState;
    // if (playerOne.isPresent()) {
    // playerOne.get().changeState(
    // state,
    // board,
    // playerOne.get().getScore(),
    // playerTwo.get().getScore());
    // }
    // if (playerTwo.isPresent()) {
    // playerTwo.get().changeState(
    // state,
    // board,
    // playerOne.get().getScore(),
    // playerTwo.get().getScore());
    // }
    // }

    private Player getPlayer(PlayerIdInterface id) throws RemoteException {
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
