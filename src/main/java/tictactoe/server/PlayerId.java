package tictactoe.server;

import tictactoe.shared.GameState;

public class PlayerId {
  private PlayerSymbol symbol;

  protected PlayerId(PlayerSymbol symbol) {
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

  protected GameState getTurn() {
    switch (symbol) {
      case CROSSES:
        return GameState.CROSS_TURN;
      case NOUGHTS:
        return GameState.NOUGHT_TURN;
      default:
        throw new RuntimeException();
    }
  }

  protected GameState getNextTurn() {
    switch (symbol) {
      case CROSSES:
        return GameState.NOUGHT_TURN;
      case NOUGHTS:
        return GameState.CROSS_TURN;
      default:
        throw new RuntimeException();
    }
  }
}
