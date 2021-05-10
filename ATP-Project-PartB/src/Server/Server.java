package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private int listeningInterval;
    private int port;
    private IServerStrategy ServerStrategy;
    private volatile boolean stop;
    private ExecutorService threadPool;

    public Server(int port,int listeningInterval, IServerStrategy clientHandler) {
        this.listeningInterval=listeningInterval;
        this.port = port;
        this.ServerStrategy = clientHandler;
        this.stop = false;
        Configurations config = Configurations.getInstance();
        int threadNum = config.getMaxThread();
        this.threadPool = Executors.newFixedThreadPool(threadNum);
    }
    public void start() {
        new Thread(() -> {
            run();
        }).start();
    }
    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            while (!stop)
            {
                try {
                    // accepting connection
                    Socket clientSocket = serverSocket.accept();
                    // handling connection
                    threadPool.execute(()->{
                        clientHandle(clientSocket);
                    });
                }
                catch (IOException e) {
                    System.out.println("no more clients");
                }
            }
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            ServerStrategy.ServerStrategy(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        System.out.println("server has stopped");
        this.stop = true;
    }
}
