package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class JavaClient {
    public static void main(String[] args) {
        int port = 1234;
        InetAddress localHost = null;
        Socket client = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                client = new Socket("192.168.1.136", 1234);
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                System.out.println();
                System.out.print(">> ");
                String msg = consoleIn.readLine();

                //send the message read from console to the server
                pw.println(msg);
                System.out.print("<< ");
                String text = br.readLine();
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == '\t') {
                        System.out.println();
                    } else {
                        System.out.print(text.charAt(i));
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
