package tictactoe.shared;

public interface PlayerInterface {

  public void changeState(GameState state, Board board, int player1Score, int player2Score);

  public void setOpponetName(String name);
}
