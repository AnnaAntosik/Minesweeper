package pl.coderslab.main_project.Components;

import pl.coderslab.main_project.models.Board;

public interface GameState {

  void setBoard(Board board);

  int getRemainedFields();

  void setRemainedFields(int newValue);

  boolean[][] getRevealedFields();

  Board getBoard();

  void setRevealedFields(boolean[][] clickedFieldsTable);
}
