package pl.coderslab.main_project.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class BoardController {
  int cols = 4;
  int rows = 4;
  int bombs = 3;

  public Object[][] board = {{1, 2, "B", 1}, {1, "B", 3, 2}, {1, 2, "B", 1}, {0, 1, 1, 1}};

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
  public String getClickedValue(
      int x, int y, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    String clickedCell = board[x][y].toString();
    if (clickedCell.equals("B")) {
      session.removeAttribute("revealedFields");
      return "failed";

    } else {
      boolean[][] clickedFieldsTable;

      if (session == null) {
        session = request.getSession();
        clickedFieldsTable = new boolean[4][4];
      } else {
        clickedFieldsTable = (boolean[][]) session.getAttribute("revealedFields");
      }

      revealFields(x, y, clickedFieldsTable, board);
      session.setAttribute("revealedFields", clickedFieldsTable);

      return "redirect:coveredBoard";
    }
  }

  private void revealFields(int x, int y, boolean[][] clickedFieldsTable, Object[][] board) {
    if(x < 0 || y < 0 || x >= rows || y >= cols)
    {
      return;
    }
    if (!board[x][y].equals(0)) {
      clickedFieldsTable[x][y] = true;
    } else {
      clickedFieldsTable[x][y] = true;
      revealFields(x-1, y-1, clickedFieldsTable, board);
      revealFields(x, y-1, clickedFieldsTable, board);
      revealFields(x-1, y, clickedFieldsTable, board);
      revealFields(x+1, y+1, clickedFieldsTable, board);
      revealFields(x, y+1, clickedFieldsTable, board);
      revealFields(x+1, y, clickedFieldsTable, board);
      revealFields(x+1, y-1, clickedFieldsTable, board);
      revealFields(x-1, y+1, clickedFieldsTable, board);
    }
  }
}
