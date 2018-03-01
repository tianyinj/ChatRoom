import java.awt.FlowLayout;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatClient {
	
	static List<String> userNames = new ArrayList<>();
	static List<PrintWriter> printWriters = new ArrayList<>();

	static JFrame chatWindow = new JFrame("Chat Application");
	static JTextArea chatArea = new JTextArea(22, 40);
	static JTextField textField = new JTextField(40);
	static JLabel blankLabel = new JLabel("            ");
	static JButton sendButton = new JButton("Send");
	
	static BufferedReader in;
	static PrintWriter out;
	
	ChatClient(){
		chatWindow.setLayout(new FlowLayout());
		
		chatWindow.add(new JScrollPane(chatArea));
		chatWindow.add(blankLabel);
		chatWindow.add(textField);
		chatWindow.add(sendButton);
		
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatWindow.setSize(475, 500);
		chatWindow.setVisible(true);
		
		textField.setEditable(false);
		chatArea.setEditable(false);
	}
	
	void startChat() throws Exception{
		String addr = JOptionPane.showInputDialog(chatWindow, 
				"Enter IP Address: ",
				"Establishing Server", 
				JOptionPane.PLAIN_MESSAGE);
		Socket soc = new Socket(addr, 8899);
		
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		out = new PrintWriter(soc.getOutputStream(), true);
		
		while(true){
			
		}
	}
	
	public static void main(String[] args) throws Exception{
		ChatClient client = new ChatClient();
		client.startChat();
	}
}
