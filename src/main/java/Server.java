import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(49251);) {
            String town = null;
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    System.out.println("Server started");

                    out.println(town == null ? "???" : town);

                    String stringFromSocket = in.readLine();
                    if (town == null){
                        town = stringFromSocket;
                        out.println("OK");
                        System.out.println("Accepted answer: " + town);
                    }
                    else {
                        if (town.toLowerCase().endsWith(stringFromSocket.toLowerCase().substring(0,1))){
                            //right answer
                            town = stringFromSocket;
                            out.println("OK");
                            System.out.println("Accepted answer: " + town);
                        }
                        else {
                            //wrong answer
                            out.println("NOT OK");
                            System.out.println("Denied answer: " + stringFromSocket);
                        }
                    }
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't start server");
            e.printStackTrace();
        }
    }
}
