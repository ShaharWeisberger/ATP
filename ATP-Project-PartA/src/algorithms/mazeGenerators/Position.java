package algorithms.mazeGenerators;

public class Position {

    private int rowIndex;
    private  int columnIndex;

    public Position(int row,int column) {
        this.rowIndex = row;
        this.columnIndex = column;
    }

    public int getRowIndex() { return  rowIndex; }
    public int getColumnIndex() { return  columnIndex; }

    public String toString() { return "{" + rowIndex + "," + columnIndex + "}"; }

}
