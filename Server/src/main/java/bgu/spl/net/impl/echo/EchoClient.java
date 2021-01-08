package bgu.spl.net.impl.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        byte a = 012;
        if (args.length == 0) {
            args = new String[] { "localhost", "hello" };
        }

        if (args.length < 2) {
            System.out.println("you must supply two arguments: host, message");
            System.exit(1);
        }

        // BufferedReader and BufferedWriter automatically using UTF-8 encoding
        Scanner sc = new Scanner(System.in);
        while (true) {
            // int aa = "alan".getBytes();
            try (Socket sock = new Socket(args[0], 7777);
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {

                System.out.println("Send message to server: ");
                String input = sc.nextLine();
                out.write(input);
                out.newLine();
                out.flush();

            }
            // System.out.println("awaiting response");
            // String line = in.readLine();
            // System.out.println("message from server: " + line);
        }
    }
}
