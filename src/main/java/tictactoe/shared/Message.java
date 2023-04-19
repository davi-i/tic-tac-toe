package tictactoe.shared;

public enum Message {
  OPPONENT_LEFT, YOU_WON, TIE, YOU_LOST, WAITING, INVALID_MOVE, TILE_NOT_EMPTY;

  @Override
  public String toString() {
    switch (this) {
      case OPPONENT_LEFT:
        return "The opponent has left the game. Waiting for someone else to connect...";
      case YOU_WON:
        return "You won the game. :)";
      case TIE:
        return "The old lady won";
      case WAITING:
        return "Waiting for other player...";
      case YOU_LOST:
        return "You lost the game. :(";
      case INVALID_MOVE:
        return "This move is invalid.";
      case TILE_NOT_EMPTY:
        return "You must choose an empty tile.";
      default:
        return "You can do anything you set your mind to.";
    }
  }
}
