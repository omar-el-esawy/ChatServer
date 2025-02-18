package com.giza.simpleServer.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class RequestHandler implements Runnable {
    private final Socket incoming;
    private final List<OutputStream> clientOutputStreams;

    public RequestHandler(Socket incoming, List<OutputStream> clientOutputStreams) {
        this.incoming = incoming;
        this.clientOutputStreams = clientOutputStreams;
    }

    @Override
    public void run() {
        try (InputStream in = incoming.getInputStream();
             OutputStream out = incoming.getOutputStream()) {

            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

            writer.println("To exit, type 'exit'");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                broadcastMessage("Echo from server: " + line);
                if (line.equals("exit")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {
        for (OutputStream clientOut : clientOutputStreams) {
            PrintWriter clientWriter = new PrintWriter(new OutputStreamWriter(clientOut, StandardCharsets.UTF_8), true);
            clientWriter.println(message);
        }
    }
}

