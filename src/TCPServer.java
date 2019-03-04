
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {

    public static final int port = 53212;

    public void run() {
        System.out.printf("Running new instance");
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (Exception e) {
            Logger.getLogger().log(e.getMessage());
        }
    }
}