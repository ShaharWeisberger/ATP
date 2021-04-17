package algorithms.search;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    protected Queue<AState> openList;
    private int visitedNode;
    private HashMap<String, Boolean> inClosedList;
    private String nameOfAlgo;


    public ASearchingAlgorithm() {
        this.visitedNode = 0;
        inClosedList = new HashMap<String, Boolean>();
    }

    public void setName(String name){
        nameOfAlgo = name;
    }
    @Override
    public String getName(){
        return nameOfAlgo;
    }

    public void markAsClosed(AState s){
        if(s != null)
            inClosedList.put(s.getKey(), true);
    }

    public boolean isInClosedList(AState s){
        if(s == null)
            return false;
        return inClosedList.containsKey(s.getKey());
    }

    protected AState popOpenList(){
        visitedNode++;
        return openList.poll();
    }

    @Override
    public Solution solve(ISearchable domain) {
        return new Solution(search(domain));
    }

    @Override
    public int getNumberOfVisitedNodes() {
        return visitedNode;
    }
}
