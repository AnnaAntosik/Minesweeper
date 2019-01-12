package pl.coderslab.main_project.Services;

import org.springframework.stereotype.Service;
import pl.coderslab.main_project.models.Cell;

import java.util.Random;

@Service
public class CellServiceImpl implements CellService {

  @Override
  public Cell[][] generateBoard(int xSize, int ySize, int bombs) {

    Cell[][] basedBoard = createBasedBoard(xSize, ySize);
    putBombs(basedBoard, xSize, ySize, bombs);
    setAdjacentBombs(basedBoard, xSize, ySize);
    return basedBoard;
  }

  private Cell[][] createBasedBoard(int xSize, int ySize) {
    Cell[][] basedBoard = new Cell[xSize][ySize];

    for (int x = 0; x < basedBoard.length; x++) {
      for (int y = 0; y < basedBoard[x].length; y++) {
        basedBoard[x][y] = new Cell();
      }
    }
    return basedBoard;
  }

  private void putBombs(Cell[][] basedBoard, int xSize, int ySize, int bombs) {
    Random random = new Random();
    for (int currentBombIdx = 0; currentBombIdx < bombs; ) {
      int randomX = random.nextInt(xSize);
      int randomY = random.nextInt(ySize);
      if (!basedBoard[randomX][randomY].hasBomb) {
        basedBoard[randomX][randomY].hasBomb = true;
        currentBombIdx++;
      }
    }
  }

  private void setAdjacentBombs(Cell[][] basedBoard, int xSize, int ySize) {
    for (int x = 0; x < basedBoard.length; x++) {
      for (int y = 0; y < basedBoard[x].length; y++) {
        if (!basedBoard[x][y].hasBomb) {
          basedBoard[x][y].adjacentBombs = getBombsAmount(basedBoard, x, y, xSize, ySize);
        }
      }
    }
  }

  private int getBombsAmount(Cell[][] baseBoard, int x, int y, int xSize, int ySize) {
    int bombsAmount = 0;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (isInBoard(x + i, y + j, xSize, ySize)
            && isDifferentCell(i, j)
            && baseBoard[x + i][y + j].hasBomb) {
          bombsAmount++;
        }
      }
    }
    return bombsAmount;
  }

  private boolean isDifferentCell(int i, int j) {
    return !(i == 0 && j == 0);
  }

  private boolean isInBoard(int x, int y, int xSize, int ySize) {
    return x >= 0 && y >= 0 && x < xSize && y < ySize;
  }
}
