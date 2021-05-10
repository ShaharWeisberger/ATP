package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {

        int rows,cols;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);
            ObjectOutputStream objectoutputStream = new ObjectOutputStream(outToClient);

            //input
            Object obj = objectInputStream.readObject();
            int [] arr= new int[2];
            arr= (int [])obj;
            rows=arr[0];
            cols = arr[1];

            //Generate Maze
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(rows, cols);

            //output
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            MyCompressorOutputStream comp = new MyCompressorOutputStream(byteStream);

            try {
                comp.write(maze.toByteArray());
                objectoutputStream.writeObject(byteStream.toByteArray());
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
