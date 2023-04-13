package tictactoe.server;

import tictactoe.shared.PlayerInterface;

public class Player {
  private PlayerId id;
  private PlayerInterface player;

  public Player(PlayerId id, PlayerInterface player) {
    this.id = id;
    this.player = player;
  }

}
