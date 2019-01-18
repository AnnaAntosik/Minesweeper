package pl.coderslab.main_project.models;

public class Board {

    public Cell[][] cells;
    public int sizeX;
    public int sizeY;
    public int bombsCount;

    public Board(Cell[][] cells, int xSize, int ySize, int bombs) {
        this.cells = cells;
        this.sizeX = xSize;
        this.sizeY = ySize;
        bombsCount = bombs;
    }
}
