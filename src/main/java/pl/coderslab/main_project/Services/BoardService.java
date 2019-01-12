package pl.coderslab.main_project.Services;


import pl.coderslab.main_project.dto.BoardDto;
import pl.coderslab.main_project.dto.GameplayStatus;

public interface BoardService {
    void createBoard();
    BoardDto getRevealedBoard();
    GameplayStatus setReviledField(int x, int y);
}
