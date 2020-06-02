import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SimpleEchoer {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;

    public SimpleEchoer(int port) {
        while (true) {
            try {
                this.serverSocket = new ServerSocket(port);
                this.socket = serverSocket.accept();
                this.scanner = new Scanner(new InputStreamReader(socket.getInputStream()));
                this.printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("Welcome to chat! Your client ID is");
                System.out.println("Connected to client on port " + port);
                break;
            } catch (IOException e) {
                System.out.println("Waiting for client");
            }
        }
    }

    public static void main(String[] args) {
        SimpleEchoer simpleEchoer = new SimpleEchoer(1337);
        while (true) {
            try {
                String message = simpleEchoer.scanner.nextLine();
                System.out.println("Received message: " + message);
                simpleEchoer.printWriter.println(message);
            } catch (NoSuchElementException e) {
                System.out.println("Error accepting message, closing");
                break;
            }
        }
    }
}
