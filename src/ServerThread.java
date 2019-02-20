import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable{
	
	private Socket clientSocket;
	
	
	public ServerThread(Socket clientSocket) {
	      this.clientSocket = clientSocket;
	}
	
	
	public void run() {		
		System.out.println("Thread "+ this + " started"); 
		System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName()); 
		
		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
	  
			DataOutputStream outToClient = new DataOutputStream(
					clientSocket.getOutputStream());
	  
			String clientSentence = inFromClient.readLine();
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
	 
			inFromClient.close();
			outToClient.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
		
	
}
