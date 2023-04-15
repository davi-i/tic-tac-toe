package tictactoe.shared;

import tictactoe.server.TileState;

public class Board {
  private static final int TILE_NUMBER = 3;

  protected TileState[][] board = new TileState[TILE_NUMBER][TILE_NUMBER];

  public Board() {
    clean();
  }

  public void setTile(int index, TileState value) {
    int i = index / TILE_NUMBER;
    int j = index % TILE_NUMBER;

    board[i][j] = value;
  }

  public TileState getTile(int index) {
    int i = index / TILE_NUMBER;
    int j = index % TILE_NUMBER;

    return board[i][j];
  }

  @Override
  public String toString() {
    String result = "";

    for (int i = 0; i < TILE_NUMBER; i++) {
      for (int j = 0; j < TILE_NUMBER; j++) {
        String symbol;
        switch (board[i][j]) {
          case CROSS:
            symbol = "\u001B[31mX\u001B[0m";
            break;
          case EMPTY:
            symbol = String.valueOf(j + TILE_NUMBER * i);
            break;
          case NOUGHT:
            symbol = "\u001B[34mO\u001B[0m";
            break;
          default:
            symbol = " ";
            break;
        }
        result += " " + symbol + " ";
        if (j < TILE_NUMBER - 1) {
          result += "|";
        }
      }
      result += '\n';
      if (i < TILE_NUMBER - 1) {
        String line = "─".repeat(3);
        for (int j = 0; j < TILE_NUMBER; j++) {
          result += line;
          if (j < TILE_NUMBER - 1) {
            result += '+';
          }
        }
        result += '\n';
      }
    }

    return result;
  }

  public void clean() {
    for (int i = 0; i < TILE_NUMBER; i++) {
      for (int j = 0; j < TILE_NUMBER; j++) {
        board[i][j] = TileState.EMPTY;
      }
    }
  }
}
