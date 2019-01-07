package pl.coderslab.main_project.Services;


import pl.coderslab.main_project.dto.BoardDto;
import pl.coderslab.main_project.dto.GameplayStatus;

public interface BoardService {
    BoardDto getReviledBoard();
    GameplayStatus setReviledField(int x, int y);
}
