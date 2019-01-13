package pl.coderslab.main_project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.main_project.Components.GameState;
import pl.coderslab.main_project.dto.BoardDto;
import pl.coderslab.main_project.dto.CellDto;
import pl.coderslab.main_project.dto.Difficulty;
import pl.coderslab.main_project.dto.GameplayStatus;
import pl.coderslab.main_project.models.Board;
import pl.coderslab.main_project.models.Cell;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    GameState gameState;

    @Autowired
    CellService cellService;

    @Override
    public void createBoard(Difficulty difficulty) { // TODO difficulty
        if (gameState.getBoard() != null) return;
        int xSize = 0;
        int ySize = 0;
        int bombs = 0;
        switch (difficulty) {
            case EASY:
                xSize = 9;
                ySize = 9;
                bombs = 10;
                break;
            case MEDIUM:
                xSize = 16;
                ySize = 16;
                bombs = 40;
                break;
            case HARD:
                xSize = 30;
                ySize = 16;
                bombs = 99;
                break;
        }
        Cell[][] cells = cellService.generateBoard(xSize, ySize, bombs);
        Board board = new Board(cells, xSize, ySize, bombs);
        gameState.setBoard(board);
    }

    @Override
    public BoardDto getRevealedBoard() {

        BoardDto boardDto = new BoardDto();
        boolean boom = false;
        boolean[][] clickedFieldsTable = gameState.getRevealedFields();
        Board board = gameState.getBoard();
        boardDto.cells = new CellDto[board.sizeX][board.sizeY];
        for (int i = 0; i < gameState.getBoard().sizeX; i++) {
            for (int j = 0; j < gameState.getBoard().sizeY; j++) {
                if (clickedFieldsTable[i][j]) {
                    CellDto cell = new CellDto();
                    if (board.cells[i][j].hasBomb) {
                        cell.isBomb = true;
                        boom = true;
                    } else {
                        cell.neighbouringBombs = board.cells[i][j].adjacentBombs;
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
        Board board = gameState.getBoard();
        boolean[][] revealedFields = gameState.getRevealedFields();
        int remainedFieldsWithoutBombs = gameState.getRemainedFields();

        remainedFieldsWithoutBombs =
                revealFields(x, y, revealedFields, board, remainedFieldsWithoutBombs);

        gameState.setRevealedFields(revealedFields);
        gameState.setRemainedFields(remainedFieldsWithoutBombs);
        if (board.cells[x][y].hasBomb) {
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
            Board board,
            int remainedFieldsWithoutBombs) {
        remainedFieldsWithoutBombs--;
        if (board.cells[x][y].adjacentBombs != 0) {
            clickedFieldsTable[x][y] = true;
        } else {
            clickedFieldsTable[x][y] = true;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x + i >= 0
                            && x + i < gameState.getBoard().sizeX
                            && y + j >= 0
                            && y + j < gameState.getBoard().sizeY
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
        Board board = gameState.getBoard();
        for (int i = 0; i < gameState.getBoard().sizeX; i++) {
            for (int j = 0; j < gameState.getBoard().sizeY; j++) {
                if (board.cells[i][j].hasBomb) {
                    looserBoard.cells[i][j].isBomb = true;
                    looserBoard.cells[i][j].isRevealed = true;
                }
            }
        }
        return looserBoard;
    }
}
