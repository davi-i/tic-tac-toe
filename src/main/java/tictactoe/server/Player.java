package tictactoe.server;

import tictactoe.shared.Board;
import tictactoe.shared.PlayerInterface;

public class Player implements PlayerInterface {
  private PlayerId id;
  private PlayerInterface player;
  private String name;
  private int score;

  public Player(PlayerId id, PlayerInterface player, String name) {
    this.id = id;
    this.player = player;
    this.name = name;
    this.score = 0;
  }

  @Override
  public void changeState(GameState state, Board board, int player1Score, int player2Score) {
    player.changeState(state, board, player1Score, player2Score);
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void addScore() {
    score++;
  }
}
