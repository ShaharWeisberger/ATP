package algorithms.search;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    private LinkedList<AState> ListOfStates;

    public BreadthFirstSearch() {
        super();
        setName("BFS");
        ListOfStates = new LinkedList<>();
        openList = ListOfStates;
    }


    @Override
    public AState search(ISearchable s) {
        String goalState = s.getGoalState().getKey();
        openList.add(s.getStartState());
        while (!openList.isEmpty()) {
            AState curr = openList.poll();
            //System.out.println("Current key = " + curr.getKey());
            markAsClosed(curr);
            if (curr.getKey().equals(goalState)) {
                return curr;
            }
            ArrayList<AState> arrL = s.getAllSuccessors(curr);
            for (AState next : arrL) {
                if (!isInClosedList(next))
                    ListOfStates.add(next);
            }
        }
        return null;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return 0;
    }
}
