package tictactoe.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

import tictactoe.shared.RoomFullException;
import tictactoe.shared.Board;
import tictactoe.shared.GameState;
import tictactoe.shared.Message;
import tictactoe.shared.MoveResult;
import tictactoe.shared.NameAlreadyUsedException;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.TicTacToeInterface;

public class TicTacToeServer extends UnicastRemoteObject implements TicTacToeInterface {
    private GameState state;
    private Board board;
    private Optional<Player> playerOne;
    private Optional<Player> playerTwo;

    public TicTacToeServer() throws RemoteException {
        super();
        state = GameState.WAITING;
        board = new Board();
        playerOne = Optional.empty();
        playerTwo = Optional.empty();
    }

    @Override
    public PlayerId enterGame(PlayerInterface player, String name)
            throws RoomFullException, RemoteException, NameAlreadyUsedException {
        PlayerId id;
        if (playerOne.isEmpty()) {
            id = new PlayerId(PlayerSymbol.CROSSES);
            playerOne = Optional.of(new Player(this, id, player, name));

            player.sendMessage(Message.WAITING);
        } else if (playerTwo.isEmpty()) {
            if (playerOne.get().getName().equals(name)) {
                throw new NameAlreadyUsedException();
            }
            id = new PlayerId(PlayerSymbol.NOUGHTS);
            playerTwo = Optional.of(new Player(this, id, player, name));
        } else {
            throw new RoomFullException();
        }
        System.out.println("player " + name + " entered the game");
        return id;
    }

    @Override
    public void exitGame(PlayerId id) throws RemoteException {
        String playerName = "";
        switch (id.getSymbol()) {
            case CROSSES:
                playerName = playerOne.get().getName();
                playerOne = playerTwo;
                break;
            case NOUGHTS:
                playerName = playerTwo.get().getName();
                break;
        }
        System.out.println("player " + playerName + " left the game. :(");
        playerTwo = Optional.empty();
        if (playerOne.isPresent()) {
            playerOne.get().getId().setSymbol(PlayerSymbol.CROSSES);
            playerOne.get().resetScore();
            playerOne.get().sendMessage(Message.OPPONENT_LEFT);
        }

        board.clean();
        state = GameState.WAITING;
    }

    protected void startGame() {
        while (true) {
            // For some reason, it only works when this line is here
            System.out.print("");
            if (state == GameState.WAITING && playerOne.isPresent() && playerTwo.isPresent()) {
                try {
                    processGame();
                } catch (RemoteException e) {
                    System.err.println(e);
                }
            }
        }
    }

    private void processGame() throws RemoteException {
        playerOne.get().setOpponentName(playerTwo.get().getName());
        playerTwo.get().setOpponentName(playerOne.get().getName());

        state = GameState.PROCESSING;
        Player playerOne = this.playerOne.get();
        Player playerTwo = this.playerTwo.get();
        outer: while (state == GameState.PROCESSING) {
            while (true) {
                MoveResult move = getMove(playerOne, playerTwo);
                if (move == MoveResult.MOVE_ALLOWED) {
                    break;
                } else if (move == MoveResult.TIE || move == MoveResult.VICTORY) {
                    board.clean();
                    continue outer;
                }
            }

            while (true) {
                MoveResult move = getMove(playerTwo, playerOne);
                if (move == MoveResult.MOVE_ALLOWED) {
                    break;
                } else if (move == MoveResult.TIE || move == MoveResult.VICTORY) {
                    board.clean();
                    continue outer;
                }
            }
        }
    }

    public MoveResult getMove(Player player, Player opponent) throws RemoteException {
        player.updateBoard(board);
        opponent.updateBoard(board);
        int pos = player.getMove();
        TileState tile;
        try {
            tile = board.getTile(pos);
        } catch (IndexOutOfBoundsException e) {
            player.sendMessage(Message.INVALID_MOVE);
            return MoveResult.INVALID_MOVE;
        }
        if (tile != TileState.EMPTY) {
            player.sendMessage(Message.TILE_NOT_EMPTY);
            return MoveResult.TILE_NOT_EMPTY;
        }

        System.out.println("player " + player.getName() + " made move at position " + pos);
        board.setTile(pos, player.getId().getTile());
        if (checkVictory(pos)) {
            System.out.println("player " + player.getName() + " won this round");
            player.incrementScore();
            opponent.incrementOpponentScore();

            player.sendMessage(Message.YOU_WON);
            opponent.sendMessage(Message.YOU_LOST);

            return MoveResult.VICTORY;
        } else if (checkTie()) {
            System.out.println("Old lady!");

            player.sendMessage(Message.TIE);
            opponent.sendMessage(Message.TIE);

            return MoveResult.TIE;
        }
        player.sendMessage(Message.WAITING);
        return MoveResult.MOVE_ALLOWED;
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
                        || (board.getTile(0) == tile && board.getTile(6) == tile));
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
                return false;
        }
    }

    private boolean checkTie() {
        return !board.stream().anyMatch(TileState.EMPTY::equals);

    }
}
