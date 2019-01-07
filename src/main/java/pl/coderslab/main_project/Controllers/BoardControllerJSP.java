package pl.coderslab.main_project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.main_project.Services.BoardService;
import pl.coderslab.main_project.dto.CellDto;
import pl.coderslab.main_project.dto.GameplayStatus;

@Controller
public class BoardControllerJSP {

  @Autowired BoardService boardService;

  @GetMapping("/coveredBoard")
  public String getCoveredBoard(Model model) {
    CellDto[][] board = boardService.getReviledBoard().cells;
    model.addAttribute("board", board);
    return "buttonsBoard";
  }

  @PostMapping("/coveredBoard")
  public String getClickedValue(int x, int y) {

    GameplayStatus status = boardService.setReviledField(x, y);

    if (status == GameplayStatus.LOST) {
      return "failed";
    } else if (status == GameplayStatus.WIN) {
      return "winner";
    }

    return "redirect:coveredBoard";
  }
}
