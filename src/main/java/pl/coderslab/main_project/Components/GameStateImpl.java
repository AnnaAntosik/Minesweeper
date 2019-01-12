package pl.coderslab.main_project.Components;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.coderslab.main_project.models.Board;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameStateImpl implements GameState {
  GameStateImpl() {
    System.out.println("Start");
  }

  private Board board;
  private int remainedFields;
  private boolean[][] revealedFields;

  public void setBoard(Board board) {
    this.board = board;
    this.remainedFields = board.sizeX * board.sizeY - board.bombsCount;
    this.revealedFields = new boolean[board.sizeX][board.sizeY];
  }

  @Override
  public int getRemainedFields() {
    return remainedFields;
  }

  @Override
  public void setRemainedFields(int newValue) {
    remainedFields = newValue;
  }

  @Override
  public boolean[][] getRevealedFields() {
    return revealedFields;
  }

  @Override
  public Board getBoard() {
    return board;
  }

  @Override
  public void setRevealedFields(boolean[][] revealedFields) {
    this.revealedFields = revealedFields;
  }
}
