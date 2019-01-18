package pl.coderslab.main_project.Services;


import pl.coderslab.main_project.models.Cell;

public interface CellService {
    Cell[][] generateBoard(int xSize, int ySize, int bombs);
}
