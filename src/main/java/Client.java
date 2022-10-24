import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 49251);
             OutputStream outputStream = socket.getOutputStream();
             DataOutputStream out = new DataOutputStream(outputStream);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ) {
            socket.setSoTimeout(1000);
            String serverResponse = in.readLine();
            System.out.println("Previous town is: " + serverResponse);

            System.out.println("Enter the town name:");
            Scanner scanner = new Scanner(System.in);
            String town = scanner.nextLine();

            out.writeBytes(town);
            out.flush();

            System.out.print("Server response is: ");
            try {
                serverResponse = in.readLine();
            }
            catch (SocketTimeoutException e){
                serverResponse = "NO RESPONSE";
            }
            finally {
                System.out.println(serverResponse);
            }
        }
        catch (SocketException e){
            System.out.println("Can't connect to server");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
