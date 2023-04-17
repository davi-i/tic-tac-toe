package tictactoe.client;

import tictactoe.shared.GameState;
import tictactoe.server.PlayerId;
import tictactoe.shared.Board;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.RoomFullException;
import tictactoe.shared.TicTacToeInterface;

public class Player implements PlayerInterface {

    private TicTacToeInterface server;
    private Board board;
    private GameState state;
    private int player1Score;
    private int player2Score;
    private String myName;
    private String opponentName;
    private PlayerId id;

    public Player(TicTacToeInterface server, String name) throws RoomFullException {
        this.server = server;
        this.myName = name;
        this.id = server.enterGame(this, name);
    }

    @Override
    public void changeState(GameState state, Board board, int player1Score, int player2Score) {
        this.board = board;
        this.state = state;
        this.player1Score = player1Score;
        this.player2Score = player2Score;

    }

    public void printGame() {
        System.out.println("| SCORE | " + myName + ": " + player1Score
                + " | " + opponentName + ": " + player2Score + " |");
        System.out.println(board);
        System.out.println("[Digite sua jogada] OU ['q' para encerrar o jogo]");
    }

    @Override
    public void setOpponetName(String name) {
        this.opponentName = name;
    }

}
