package pl.coderslab.main_project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.main_project.Services.BoardService;
import pl.coderslab.main_project.dto.BoardDto;

@RestController
@RequestMapping("/")
public class BoardController2 {

  @Autowired
  BoardService boardService;

  @GetMapping("/board")
  public BoardDto getBoard() {
    return boardService.getReviledBoard();
  }

  @PostMapping("/board")
  public BoardDto getClickedValue(int x, int y) {
    boardService.setReviledField(x, y);
    return boardService.getReviledBoard();
  }

}
