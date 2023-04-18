package tictactoe.shared;

import java.util.stream.Stream;

import tictactoe.server.TileState;

public interface BoardInterface {
  public void setTile(int index, TileState value);

  public TileState getTile(int index) throws IndexOutOfBoundsException;

  public void clean();

  public Stream<TileState> stream();
}
