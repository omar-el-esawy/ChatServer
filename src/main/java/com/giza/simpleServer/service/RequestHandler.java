package com.giza.simpleServer.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RequestHandler implements Runnable {
    final Socket incaming;

    public RequestHandler(Socket incaming) {
        this.incaming = incaming;
    }

    @Override
    public void run() {

        try (InputStream in = incaming.getInputStream();
             OutputStream out = incaming.getOutputStream()) {

            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

            writer.println("To exit, type 'exit'");

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.println("Echo from server: " + line);
                if (line.equals("exit")) {
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
