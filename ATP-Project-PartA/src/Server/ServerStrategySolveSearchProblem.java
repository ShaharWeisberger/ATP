package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);
            ObjectOutputStream objectoutputStream = new ObjectOutputStream(outToClient);

            //input
            Object obj = objectInputStream.readObject();
            Maze maze = (Maze) obj;

            //System.out.println("______________\n______________");
            //maze.print();
            //Solve Maze
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            Configurations config = Configurations.getInstance();
            String algoName = config.getMazeSearchingAlgorithm();
            ASearchingAlgorithm algorithm;
            switch (algoName){
                case "BEST_FS":
                    algorithm = new BestFirstSearch();
                    break;
                case "DFS":
                    algorithm = new DepthFirstSearch();
                    break;
                case "BFS":
                    algorithm = new BreadthFirstSearch();
                    break;
                default:
                    algorithm = new BestFirstSearch();
                    break;
            }
            Solution solution = algorithm.solve(searchableMaze);

            try {
                objectoutputStream.writeObject(solution);
                objectoutputStream.flush();
                objectoutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
