import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatServer {
	
	static List<String> userNames = new ArrayList<>();
	static List<PrintWriter> printWriters = new ArrayList<>();
	
	public static void main(String[] args){
		
		System.out.println("Waiting for clients..");
		ServerSocket ss;
		try {
			ss = new ServerSocket(8899);
			while(true){
				Socket soc = ss.accept();
				System.out.println("Connection established!");
				ConversationHandler handler = new ConversationHandler(soc);
				handler.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ConversationHandler extends Thread{
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	String name;
	
	ConversationHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			int count = 0;
			while(true){
				if (count > 0) out.println("UserNameExists");
				else out.println("UserNameRequired");
				
				name = in.readLine();
				
				if (name == null) return;
				
				if (!ChatServer.userNames.contains(name)){
					ChatServer.userNames.add(name);
					break;
				}
				count++;
			}
			out.println("UserNameAccepted");
			ChatServer.printWriters.add(out);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


