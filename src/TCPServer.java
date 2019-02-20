
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	
   public static void main(String args[]) throws Exception { 
      ServerSocket serverSocket = new ServerSocket(6789);
      System.out.println("Listening");
      
      while (true) {
         Socket socket = serverSocket.accept();
         System.out.println("Connected");
         new Thread(new ServerThread(socket)).start();
      }
   }
}