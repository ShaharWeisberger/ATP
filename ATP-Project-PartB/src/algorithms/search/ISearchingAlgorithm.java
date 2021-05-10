package algorithms.search;

public interface ISearchingAlgorithm {
    AState search(ISearchable s);
    //int getNumberOfVisitedNodes();
    Solution solve(ISearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();
}

