import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerThread_Debug implements Runnable{
	
	private Socket clientSocket;
	private int matNumber; 
	
	public ServerThread_Debug(Socket clientSocket) {
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
			if(clientSentence == null){
				clientSocket.close();
				return;
			}
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
	 
			inFromClient.close();
			outToClient.close();
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
