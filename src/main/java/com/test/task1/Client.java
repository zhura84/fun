package com.test.task1;

import java.io.*;
import java.net.*;
import com.test.task1.Messages.HelloRequest;
import com.test.task1.Messages.HelloResponse;

public class Client {

    private final static String DEF_NAME = "Andrey";
    private final static String DEF_HOST = "localhost";
    private final String host;
    private final int port;

    public static void main(String args[]) throws IOException {
        Client client = new Client();
        if (args.length == 0) {
            System.out.println("Send name: " + DEF_NAME);
            String response = client.sendHelloRequest(DEF_NAME);
            System.out.println("Received response: " + response);
        } else {
            for (String name: args) {
                System.out.println("Send name: " + name);
                String response = client.sendHelloRequest(name);
                System.out.println("Received response: " + response);
            }
        }
    }

    public Client() {
        this(DEF_HOST, Server.DEFAULT_PORT);
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sendHelloRequest(String name) throws IOException {
        Socket socket = new Socket(host, port);
        try {
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            HelloRequest request = HelloRequest
                .newBuilder()
                .setName(name)
                .build();

            request.writeTo(out);
            socket.shutdownOutput();
            HelloResponse response = HelloResponse.parseFrom(in);
            return response.getResponse();
        } finally {
            socket.close();
        }
    }
}


