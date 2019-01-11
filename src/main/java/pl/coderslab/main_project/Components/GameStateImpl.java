package pl.coderslab.main_project.Components;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameStateImpl implements GameState {
  GameStateImpl() {
    System.out.println("Start");
  }

  private int cols = 4;
  private int rows = 4;
  private int bombs = 3;
  private int remainedFields = cols * rows - bombs;

  private Object[][] board = {
    {1, "B", "B", 1},
    {1, 3, 3, 2},
    {0, 1, "B", 1},
    {0, 1, 1, 1}
  };

  private boolean[][] revealedFields = new boolean[cols][rows];

  @Override
  public int getRemainedFields() {
    return remainedFields;
  }

  @Override
  public void setRemainedFields(int newValue) {
    remainedFields = newValue;
  }

  @Override
  public int getBoardCols() {
    return cols;
  }

  @Override
  public int getBoardRows() {
    return rows;
  }

  @Override
  public boolean[][] getRevealedFields() {
    return revealedFields;
  }

  @Override
  public Object[][] getBoard() {
    return board;
  }

  @Override
  public void setRevealedFields(boolean[][] revealedFields) {
    this.revealedFields = revealedFields;
  }
}
