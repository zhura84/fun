package com.test.task1;

import java.io.*;
import java.net.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.test.task1.Messages.HelloRequest;
import com.test.task1.Messages.HelloResponse;

public class RequestHandler implements Runnable {

    private final Socket socket;

    public RequestHandler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            HelloRequest request = HelloRequest.parseFrom(in);
            HelloResponse response = HelloResponse
                    .newBuilder()
                    .setResponse("Hello, " + request.getName())
                    .build();
            response.writeTo(out);
        } catch (InvalidProtocolBufferException e) {
            System.out.println("Invalid Protocol Buffer message.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket.isConnected()) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
