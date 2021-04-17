package algorithms.search;

public class BestFirstSearch extends ASearchingAlgorithm {

    public BestFirstSearch() {
        super("BEST_FS");
    }

    @Override
    public int compareState(AState state1, AState state2) {
        return state1.getCost() < state2.getCost() ? -1 : 1;
    }

    @Override
    public void handleState(AState state) {
        if (!isInClosedList(state)){
            if (!isInOpenList(state)){
                addToOpenList(state);
            } else {
                AState state1 = getStateFromHash(state);
                if (state.getCost()<state1.getCost()) {
                    state1.setCost(state.getCost());
                    state1.setCameFrom(state.getCameFrom());
                }
            }
        }
    }

}

