package pl.coderslab.main_project.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
  int cols = 4;
  int rows = 4;
  int bombs = 2;

  public Object[][] board = {
    {1, 1, "B", 1},
    {1, 1, 2, 2},
    {0, 1, "B", 1},
    {0, 1, 1, 1}
  };

  @GetMapping("/board")
  public String getBoard(Model model) {
    model.addAttribute("board", board);
    return "mainBoard";
  }

  @GetMapping("/coveredBoard")
  public String getCoveredBoard(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    boolean[][] clickedFieldsTable;

    if (session == null) {
      clickedFieldsTable = new boolean[4][4];
    } else {
      clickedFieldsTable = (boolean[][]) session.getAttribute("revealedFields");
    }

    model.addAttribute("clickedFieldsBoard", clickedFieldsTable);
    model.addAttribute("board", board);
    return "buttonsBoard";
  }

  @PostMapping("/coveredBoard")
  public String getClickedValue(int x, int y, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    String clickedCell = board[x][y].toString();

    if (clickedCell.equals("B")) {
      session.removeAttribute("revealedFields");
      return "failed";

    } else {
      boolean[][] clickedFieldsTable;
      int remainedFieldsWithoutBombs;

      if (session == null) {
        session = request.getSession();
        clickedFieldsTable = new boolean[4][4];
        remainedFieldsWithoutBombs = cols * rows - bombs;
      } else {
        clickedFieldsTable = (boolean[][]) session.getAttribute("revealedFields");
        remainedFieldsWithoutBombs = (int) session.getAttribute("remainedFields");
      }

      remainedFieldsWithoutBombs = revealFields(x, y, clickedFieldsTable, board, remainedFieldsWithoutBombs);
      session.setAttribute("revealedFields", clickedFieldsTable);
      session.setAttribute("remainedFields", remainedFieldsWithoutBombs);

      if (remainedFieldsWithoutBombs == 0) {
        return "winner";
      }

      return "redirect:coveredBoard";
    }
  }

  private int revealFields(int x, int y, boolean[][] clickedFieldsTable, Object[][] board, int remainedFieldsWithoutBombs) {
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
            remainedFieldsWithoutBombs = revealFields(x + i, y + j, clickedFieldsTable, board, remainedFieldsWithoutBombs);
          }
        }
      }
    }
    return remainedFieldsWithoutBombs;
  }
}
