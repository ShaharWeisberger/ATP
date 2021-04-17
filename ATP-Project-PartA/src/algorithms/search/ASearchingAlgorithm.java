package algorithms.search;

import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    public abstract int compareState(AState state1, AState state2);
    public abstract void handleState(AState state);

    protected PriorityQueue<AState> openList;
    private int visitedNode;
    private HashMap<String, AState> stateHash;
    private String nameOfAlgo;
    private AState goalState;
    private int openListCounter=0;


    public ASearchingAlgorithm(String name) {
        nameOfAlgo = name;
        this.visitedNode = 0;
        stateHash = new HashMap<String, AState>();

        Comparator<AState> stateComparator = new Comparator<AState>() {
            public int compare(AState o1, AState o2) {
                return compareState(o1, o2);
            }
        };
        openList = new PriorityQueue<>(1000, stateComparator);
    }

    public String getName() {
        return nameOfAlgo;
    }

    public AState getGoalState() {
        return goalState;
    }

    public void setGoalState(AState state){
        goalState = state;
    }

    public int getNumberOfNodesEvaluated() {
        return visitedNode;
    }

    @Override
    public AState search(ISearchable s) {
        setGoalState(s.getGoalState());
        addToOpenList(s.getStartState());
        while (!openList.isEmpty()) {
            AState curr = popOpenList();
            //System.out.println(String.format("polled cost %d counter %d ", curr.getCost(), curr.getPriorityCounter()));
            markAsClosed(curr);
            if (curr.equals(getGoalState())) {
                return curr;
            }
            ArrayList<AState> arrL = s.getAllSuccessors(curr);
            for (AState next : arrL) {
                if (next.equals(getGoalState())) {
                    return next;
                }
                handleState(next);
            }
        }
        return null;
    }

    public void markAsClosed(AState s) {
        if (s != null) {
            if (!stateHash.containsKey(s.getKey())) {
                stateHash.put(s.getKey(), s);
            }
            stateHash.get(s.getKey()).setInCloseList(true);
        }
    }

    public boolean isInClosedList(AState s) {
        if (s == null||!stateHash.containsKey(s.getKey()))
            return false;
        return stateHash.get(s.getKey()).isInCloseList();
    }

    public boolean isInOpenList(AState s) {
        if (s == null||!stateHash.containsKey(s.getKey()))
            return false;
        return stateHash.get(s.getKey()).isInOpenList();
    }

    protected AState popOpenList() {
        if (openList.isEmpty()) {
            return null;
        }
        visitedNode++;
        for (AState next : openList) {
            //  System.out.println(String.format("currently in openList %d  ", next.getPriorityCounter()));
        }
        //System.out.println(String.format(" before poll"));

        AState state = openList.poll();
        //System.out.println(String.format(" after poll"));

        state.setInOpenList(false);
        return state;
    }

    protected void addToOpenList(AState state) {
        state.setPriorityCounter(openListCounter);
        openListCounter++;
        openList.add(state);
        state.setInOpenList(true);

        if (!stateHash.containsKey(state.getKey())) {
            stateHash.put(state.getKey(), state);
        }
    }

    public AState getStateFromHash(AState s){
        if(s == null||!stateHash.containsKey(s.getKey()))
            return null;
        return stateHash.get(s.getKey());
    }


    @Override
    public Solution solve(ISearchable domain) {
        return new Solution(search(domain));
    }


}

