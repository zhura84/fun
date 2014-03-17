package com.test.task1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final static int DEFAULT_PORT = 8080;

    private final static int DEFAULT_THREAD_POOL_SIZE = 10;

    private final int port;
    private final MainServerWorker mainWorker = new MainServerWorker();

    private ServerSocket serverSocket;
    private ExecutorService executor;
    private boolean isWork = false;

    public static void main(String args[]) throws IOException {
        Server server = new Server();
        server.start();
        Scanner scanerCommand = new Scanner(System.in);
        while (!scanerCommand.nextLine().equalsIgnoreCase("stop") ) {
            System.out.println("Use command \"stop\" for shutdown.");
        }
        scanerCommand.close();
        server.stop();
    }

    public Server() throws IOException {
        this(DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        this.port = port;
    }

    public synchronized void start() throws IOException {
        if (!isWork) {
            isWork = true;
            serverSocket = new ServerSocket(port);
            executor = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
            new Thread(mainWorker).start();
            System.out.println("Server started.");
        }
    }

    public synchronized void stop() {
        if (!isWork) {
            return;
        }
        isWork = false;
        if (executor != null || !executor.isShutdown()) {
            executor.shutdown();
        }
        if (serverSocket != null || !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return this.port;
    }

    private class MainServerWorker implements Runnable {

        @Override
        public void run() {
            try {
                while (isWork) {
                    Socket s = serverSocket.accept();
                    executor.execute(new RequestHandler(s));
                }
            } catch (SocketException e) {
                System.out.println("Server stopped.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stop();
            }
        }
    }
}