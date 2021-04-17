package algorithms.search;


import java.util.ArrayList;

public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        super("DFS");
    }

    @Override
    public int compareState(AState state1, AState state2) {
        if (state1.getPriorityCounter() == state2.getPriorityCounter())
            return 0;
        return state1.getPriorityCounter() > state2.getPriorityCounter() ?-1:1;
    }

    @Override
    public boolean handleState(AState state) {
        if (!isInClosedList(state)&&!isInOpenList(state)) {
            addToOpenList(state);
        }
        return true;
    }
}

