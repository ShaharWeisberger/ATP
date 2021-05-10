package test;
import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
// prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(100/*rows*/,100/*columns*/)));
// generate another maze
        Maze maze = mazeGenerator.generate(9/*rows*/, 9/*columns*/);
// prints the maze
        maze.print();
        System.out.println("-----------");
        byte [] bytes = maze.toByteArray();
        Maze test_maze = new Maze(bytes);
        test_maze.print();
        System.out.println("Is both constructor makes the same maze : " + test_maze.equals(maze));
        // KKK
// get the maze entrance
        Position startPosition = maze.getStartPosition();
// print the start position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"
// prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}