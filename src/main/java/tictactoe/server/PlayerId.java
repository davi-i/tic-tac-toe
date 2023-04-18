package tictactoe.server;

import java.io.Serializable;

public class PlayerId implements Serializable {
  private PlayerSymbol symbol;

  public PlayerId(PlayerSymbol symbol) {
    this.symbol = symbol;
  }

  protected PlayerSymbol getSymbol() {
    return symbol;
  }

  protected TileState getTile() {
    switch (symbol) {
      case CROSSES:
        return TileState.CROSS;
      case NOUGHTS:
        return TileState.NOUGHT;
      default:
        throw new RuntimeException();
    }
  }
}
