import java.io.*;
import java.net.*;
import java.nio.file.Paths;
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
	
	PrintWriter logger;
	static FileWriter fw;
	static BufferedWriter bw;
	
	ConversationHandler(Socket socket) throws IOException{
		this.socket = socket;
		
		
		fw = new FileWriter("chat-log", true);
		bw = new BufferedWriter(fw);
		logger = new PrintWriter(bw, true);
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
			out.println("UserNameAccepted,"+name);
			ChatServer.printWriters.add(out);
			
			while(true){
				String msg = in.readLine();
				if (msg == null) return;
				
				logger.println(name + ": " + msg);
				
				for (PrintWriter writer: ChatServer.printWriters){
					writer.println(name + ": " + msg);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


