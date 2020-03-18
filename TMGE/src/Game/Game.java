package Game;

import GridControl.Coordinates;


public abstract class Game{
    public int row;
    public int column;
    public Game(int row, int column){
        this.row = row;
        this.column = column;
    }
    public void initValidBoard(){ }
    public Boolean isMovePossible() { return true;}
    public boolean isInBound(Coordinates coords) {
        if (coords == null)
            return false;
        if (coords.row < 0 || coords.column < 0)
            return false;
        return coords.row < this.row && coords.column < this.column;
    }
    public void printBoard(){}
}
