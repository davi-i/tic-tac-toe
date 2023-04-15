package tictactoe.shared;

import tictactoe.server.GameState;

public interface PlayerInterface {

  public void changeState(GameState state);

  public void changeScore(String name, int score);
}
