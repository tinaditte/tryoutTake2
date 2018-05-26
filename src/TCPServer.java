import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {

    private static ServerSocket serverSocket;
    private static final int PORT = 4250;

    public static void main(String[] args) {
        System.out.println("Waiting for client...");

        try{
            serverSocket = new ServerSocket(PORT);
        }catch (IOException ex){
            System.out.println("Failed to attach to port...");
            System.exit(1);
        }do{
            handleClient();
        }//uendelig lykke
        while (true);
    }

    private static void handleClient(){
        Socket link = null;

        try{
            link = serverSocket.accept();

            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            int numberOfMessages = 0;
            String msg = input.nextLine();

            while (!msg.equals("EXIT")){
                System.out.println("Message received.");
                numberOfMessages++;
                output.println("You said: " + msg + " Message count: " + numberOfMessages);
                msg = input.nextLine();
            }
            output.println("Number of messages received: " + numberOfMessages);
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            try {
                System.out.println("Connection is closing...");
                link.close();
            }catch (IOException ex){
                System.out.println("Unable to disconnect, force shutdown now...");
                System.exit(1);
            }
        }

    }
}