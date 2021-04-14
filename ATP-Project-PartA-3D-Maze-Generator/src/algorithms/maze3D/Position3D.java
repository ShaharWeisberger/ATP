package algorithms.maze3D;

public class Position3D {


    private int depthIndex;
    private int rowIndex;
    private  int columnIndex;

    public Position3D(int depth, int row,int column) {

        this.depthIndex = depth;
        this.rowIndex = row;
        this.columnIndex = column;
    }

    public int getDepthIndex() {
        return depthIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getKey(){
        return ""+getDepthIndex() +"$"+getRowIndex() +"$"+getColumnIndex();
    }

    public String toString() { return "{" + depthIndex + "," + rowIndex + "," + columnIndex + "}"; }

}
