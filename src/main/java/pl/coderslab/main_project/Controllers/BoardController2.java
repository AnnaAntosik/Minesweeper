package pl.coderslab.main_project.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class BoardController2 {

  private int cols = 4;
  private int rows = 4;
  private int bombs = 2;

  private Object[][] board = {
    {1, 1, "B", 1},
    {1, 1, 2, 2},
    {0, 1, "B", 1},
    {0, 1, 1, 1}
  };

  @GetMapping("/board")
  public Object[][] getBoard(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    boolean[][] clickedFieldsTable;
    int remainedFieldsWithoutBombs;

    if (session == null) {
      session = request.getSession();
      clickedFieldsTable = new boolean[cols][rows];
      remainedFieldsWithoutBombs = cols * rows - bombs;
    } else {
      clickedFieldsTable = (boolean[][]) session.getAttribute("revealedFields");
      remainedFieldsWithoutBombs = (int) session.getAttribute("remainedFields");
    }

    session.setAttribute("revealedFields", clickedFieldsTable);
    session.setAttribute("remainedFields", remainedFieldsWithoutBombs);

    Object[][] reviledBoard = new Object[cols][rows];
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        if (clickedFieldsTable[i][j]) {
          reviledBoard[i][j] = board[i][j];
        } else {
          reviledBoard[i][j] = false;
        }
      }
    }
    return reviledBoard;
  }

  @PostMapping("/board")
  public Object[][] getClickedValue(int x, int y, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    String clickedCell = board[x][y].toString();

    

    /*if (clickedCell.equals("B")) {
      session.removeAttribute("revealedFields");
      return "failed";

    } else {*/
    boolean[][] clickedFieldsTable;
    int remainedFieldsWithoutBombs;

    if (session == null) {
      session = request.getSession();
      clickedFieldsTable = new boolean[cols][rows];
      remainedFieldsWithoutBombs = cols * rows - bombs;
    } else {
      clickedFieldsTable = (boolean[][]) session.getAttribute("revealedFields");
      remainedFieldsWithoutBombs = (int) session.getAttribute("remainedFields");
    }

    remainedFieldsWithoutBombs =
        revealFields(x, y, clickedFieldsTable, board, remainedFieldsWithoutBombs);
    session.setAttribute("revealedFields", clickedFieldsTable);
    session.setAttribute("remainedFields", remainedFieldsWithoutBombs);

    Object[][] reviledBoard = new Object[cols][rows];
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        if (clickedFieldsTable[i][j]) {
          reviledBoard[i][j] = board[i][j];
        } else {
          reviledBoard[i][j] = false;
        }
      }
    }
    return reviledBoard;

    /*if (remainedFieldsWithoutBombs == 0) {
      return "winner";
    }*/

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
              && x + i < cols
              && y + j >= 0
              && y + j < rows
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
}
