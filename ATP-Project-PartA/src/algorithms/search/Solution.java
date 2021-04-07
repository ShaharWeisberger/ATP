package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private AState solvedGoal;

    public Solution(AState state) {
        solvedGoal = state;
    }

    public ArrayList<AState> getSolutionPath(){
        ArrayList<AState> solutionPath = new ArrayList<>();
        if(solvedGoal != null){
            AState state = solvedGoal;
            while (state != null){
                solutionPath.add(0 , state);
                state = state.getCameFrom();
            }
        }
        //need to complete
        return solutionPath;
    }

}
