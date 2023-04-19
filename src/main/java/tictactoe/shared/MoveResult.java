package tictactoe.shared;

public enum MoveResult implements MoveResultInterface {
  INVALID_MOVE, TILE_NOT_EMPTY, MOVE_ALLOWED, TIE, VICTORY;
}
