package pl.coderslab.main_project.Components;

public interface GameState {
    int getRemainedFields();
    void setRemainedFields(int newValue);

    int getBoardRows();
    int getBoardCols();

    boolean[][] getRevealedFields();
    Object[][] getBoard();

    void setRevealedFields(boolean[][] clickedFieldsTable);
}
