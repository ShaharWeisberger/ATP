package algorithms.maze3D;
import algorithms.search.AState;

public class Maze3DState extends AState{

    private Position3D position;
    public Maze3DState(String pos, AState cameFrom, int cost) {
        super(pos, cameFrom, cost); // aaa
        String[] arrS= getKey().split("\\$");
        position = new Position3D(Integer.parseInt(arrS[0]), Integer.parseInt(arrS[1]), Integer.parseInt(arrS[2]));
    }
    public Position3D GetPosition(){
        return position;
    }

}

