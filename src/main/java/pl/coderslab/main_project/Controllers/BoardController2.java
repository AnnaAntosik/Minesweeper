package pl.coderslab.main_project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.main_project.Services.BoardService;
import pl.coderslab.main_project.dto.BoardDto;
import pl.coderslab.main_project.dto.Difficulty;

@RestController
@RequestMapping("/")
public class BoardController2 {

  @Autowired
  BoardService boardService;

  @PostMapping("/newgame")
  public BoardDto startNewGame(@RequestBody String difficulty) {
    Difficulty gameDifficulty = Difficulty.valueOf(difficulty);
    boardService.createBoard(gameDifficulty);
    return boardService.getRevealedBoard();
  }

  @GetMapping("/board")
  public BoardDto getBoard() {
    return boardService.getRevealedBoard();
  }

  @PostMapping("/board")
  public BoardDto getClickedValue(int x, int y) {
    boardService.setReviledField(x, y);
    return boardService.getRevealedBoard();
  }

    @PostMapping("/flag")
    public BoardDto setFlaggedField(int x, int y) {
        //boardService.setReviledField(x, y);
        return boardService.getRevealedBoard();
    }

}
