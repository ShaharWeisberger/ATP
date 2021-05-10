package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    private Position position;
    public MazeState(String pos, AState cameFrom, int cost) {
        super(pos, cameFrom, cost); // aaa
        String[] arrS= getKey().split("\\$");
        position = new Position(Integer.parseInt(arrS[0]), Integer.parseInt(arrS[1]));
    }
    public Position GetPosition(){
        return position;
    }
}

