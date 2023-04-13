package tictactoe.shared;

import tictactoe.server.PlayerId;

public interface TicTacToeInterface {
  public PlayerId enterGame(PlayerInterface player) throws RoomFullException;

  public void makeMove(PlayerId id, int pos);
}
