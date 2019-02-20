import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
	
	public static void main(String[] args) {
		String message; 
		String answer; 
		Scanner sc = new Scanner(System.in); 
		
		boolean on = true; 
		
		
		while(on) {
			System.out.print("Matrikelnummer eingeben: ");
			try {
				message = sc.nextLine(); 
				if(message.equals("q")) {
					on = false; 
					continue; 
				}
				
				//Connect to server
				Socket clientSocket = new Socket("localhost", 6789);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
				
				//write to server
				outToServer.writeBytes(message+'\n');
				
				//get answer from server
				answer = inFromServer.readLine(); 
				
				System.out.println("From Server: " + answer);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
