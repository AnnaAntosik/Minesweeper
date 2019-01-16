package pl.coderslab.main_project.dto;

public class CellDto {

    public Integer neighbouringBombs;
    public boolean isRevealed;
    public Boolean isBomb;
    public boolean isFlagged;

    public Integer getNeighbouringBombs() {
        return neighbouringBombs;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public Boolean isBomb() {
        return isBomb;
    }

    public boolean isFlagged() {
        return isFlagged;
    }
}
