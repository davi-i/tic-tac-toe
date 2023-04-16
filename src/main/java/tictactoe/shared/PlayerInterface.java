package tictactoe.shared;

import tictactoe.server.GameState;

public interface PlayerInterface {

  public void changeState(GameState state, Board board, int player1Score, int player2Score);

}
