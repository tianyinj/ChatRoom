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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


