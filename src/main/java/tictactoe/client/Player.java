package tictactoe.client;

import tictactoe.server.GameState;
import tictactoe.shared.Board;
import tictactoe.shared.PlayerInterface;
import tictactoe.shared.TicTacToeInterface;

public class Player implements PlayerInterface {

    private TicTacToeInterface server;
    private Board board;
    private GameState state;
    private int player1Score;
    private int player2Score;
    //TODO; NOMES DOS JOGADORES

    @Override
    public void changeState(GameState state, Board board, int player1Score, int player2Score) {
        this.board = board;
        this.state = state;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        
    }

    public void printGame() {
        System.out.println("| SCORE: P1 "+ player1Score +" | p2: "+ player2Score + " |");
        System.out.println(board);
        System.out.println("[Digite sua jogada] OU ['q' para encerrar o jogo]");
    }







    
}
