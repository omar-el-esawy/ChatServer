package com.giza.simpleServer;

import com.giza.simpleServer.service.RequestHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleServer {

    private static final List<OutputStream> clientOutputStreams = new CopyOnWriteArrayList<>();


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Server is listening on port 8080...");

            while (true) {
                Socket incoming = serverSocket.accept();
                clientOutputStreams.add(incoming.getOutputStream());

                Runnable requestHandler = new RequestHandler(incoming, clientOutputStreams);
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
