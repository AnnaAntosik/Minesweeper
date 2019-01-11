package pl.coderslab.main_project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.main_project.Components.GameState;
import pl.coderslab.main_project.dto.BoardDto;
import pl.coderslab.main_project.dto.CellDto;
import pl.coderslab.main_project.dto.GameplayStatus;

@Service
public class BoardServiceImpl implements BoardService {

  @Autowired GameState gameState;

  @Override
  public BoardDto getReviledBoard() {

    BoardDto boardDto = new BoardDto();
    boolean boom = false;
    boolean[][] clickedFieldsTable = gameState.getRevealedFields();
    Object[][] board = gameState.getBoard();
    boardDto.cells = new CellDto[gameState.getBoardCols()][gameState.getBoardRows()];
    for (int i = 0; i < gameState.getBoardCols(); i++) {
      for (int j = 0; j < gameState.getBoardRows(); j++) {
        if (clickedFieldsTable[i][j]) {
          CellDto cell = new CellDto();
          if (board[i][j].equals("B")) {
            cell.isBomb = true;
            boom = true;
          } else {
            cell.neighbourBombs = (int) board[i][j];
          }
          cell.isRevealed = true;
          boardDto.cells[i][j] = cell;
        } else {
          boardDto.cells[i][j] = new CellDto();
        }
      }
    }
    int remainedFieldsWithoutBombs = gameState.getRemainedFields();
    if (boom) {
      boardDto.status = GameplayStatus.LOST;
      boardDto = getBombsBoard(boardDto);
    } else if (remainedFieldsWithoutBombs == 0) {
      boardDto.status = GameplayStatus.WIN;
    } else {
      boardDto.status = GameplayStatus.CONTINUE;
    }
    return boardDto;
  }

  @Override
  public GameplayStatus setReviledField(int x, int y) {
    Object[][] board = gameState.getBoard();
    boolean[][] revealedFields = gameState.getRevealedFields();
    int remainedFieldsWithoutBombs = gameState.getRemainedFields();

    remainedFieldsWithoutBombs =
        revealFields(x, y, revealedFields, board, remainedFieldsWithoutBombs);

    gameState.setRevealedFields(revealedFields);
    gameState.setRemainedFields(remainedFieldsWithoutBombs);
    if (board[x][y].equals("B")) {
      return GameplayStatus.LOST;
    }
    if (remainedFieldsWithoutBombs == 0) {
      return GameplayStatus.WIN;
    }
    return GameplayStatus.CONTINUE;
  }

  private int revealFields(
      int x,
      int y,
      boolean[][] clickedFieldsTable,
      Object[][] board,
      int remainedFieldsWithoutBombs) {
    remainedFieldsWithoutBombs--;
    if (!board[x][y].equals(0)) {
      clickedFieldsTable[x][y] = true;
    } else {
      clickedFieldsTable[x][y] = true;
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          if (x + i >= 0
              && x + i < gameState.getBoardCols()
              && y + j >= 0
              && y + j < gameState.getBoardRows()
              && (i != 0 || j != 0)
              && !clickedFieldsTable[x + i][y + j]) {
            remainedFieldsWithoutBombs =
                revealFields(x + i, y + j, clickedFieldsTable, board, remainedFieldsWithoutBombs);
          }
        }
      }
    }
    return remainedFieldsWithoutBombs;
  }

  private BoardDto getBombsBoard(BoardDto looserBoard) {
    Object[][] board = gameState.getBoard();
    for (int i = 0; i < gameState.getBoardCols(); i++) {
      for (int j = 0; j < gameState.getBoardRows(); j++) {
        if (board[i][j].equals("B")) {
          looserBoard.cells[i][j].isBomb = true;
          looserBoard.cells[i][j].isRevealed = true;
        }
      }
    }
    return looserBoard;
  }
}
