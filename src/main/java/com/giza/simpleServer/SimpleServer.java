package com.giza.simpleServer;

import com.giza.simpleServer.service.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Server is listening on port 8080...");

            while (true) {
                Socket incaming = serverSocket.accept();

                Runnable requestHandler = new RequestHandler(incaming);
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
