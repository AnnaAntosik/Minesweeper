package pl.coderslab.main_project.dto;

public class CellDto {

    public Integer neighbouringBombs;
    public boolean isRevealed;
    public Boolean isBomb;

    public Integer getNeighbouringBombs() {
        return neighbouringBombs;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public Boolean isBomb() {
        return isBomb;
    }

}
