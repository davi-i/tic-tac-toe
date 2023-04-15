package tictactoe.server;

import tictactoe.shared.PlayerInterface;

public class Player implements PlayerInterface {
  private PlayerId id;
  private PlayerInterface player;

  public Player(PlayerId id, PlayerInterface player) {
    this.id = id;
    this.player = player;
  }

  @Override
  public void changeState(GameState state) {
    player.changeState(state);
  }

}
