package pl.coderslab.main_project.dto;

public class CellDto {

    public Integer getNeighbourBombs() {
        return neighbourBombs;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public Boolean isBomb() {
        return isBomb;
    }

    public Integer neighbourBombs;
    public boolean isRevealed;
    public Boolean isBomb;
}
