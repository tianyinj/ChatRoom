import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatClient {
	
	static JFrame chatWindow = new JFrame("Chat Application");
	static JTextArea chatArea = new JTextArea(22, 40);
	static JTextField textField = new JTextField(40);
	static JLabel blankLabel = new JLabel("            ");
	static JButton sendButton = new JButton("Send");
	
	static JLabel nameLabel = new JLabel("            ");
	
	static BufferedReader in;
	static PrintWriter out;
	
	ChatClient(){
		chatWindow.setLayout(new FlowLayout());
		
		chatWindow.add(nameLabel);
		chatWindow.add(new JScrollPane(chatArea));
		chatWindow.add(blankLabel);
		chatWindow.add(textField);
		chatWindow.add(sendButton);
		
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatWindow.setSize(500, 500);
		chatWindow.setVisible(true);
		
		textField.setEditable(false);
		chatArea.setEditable(false);
		
		sendButton.addActionListener(new Listener());
		textField.addActionListener(new Listener());
	}
	
	void startChat() throws Exception{
		String addr = JOptionPane.showInputDialog(
				chatWindow, 
				"Enter IP Address: ",
				"IP Address Required", 
				JOptionPane.PLAIN_MESSAGE);
		Socket soc = new Socket(addr, 8899);
		
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		out = new PrintWriter(soc.getOutputStream(), true);
		
		while(true){
			String str = in.readLine();
			
			String name;
			if (str.startsWith("UserNameRequired")){
				name = JOptionPane.showInputDialog(
						chatWindow, 
						"Enter User Name: ",
						"User Name Required", 
						JOptionPane.PLAIN_MESSAGE);
				out.println(name);
			}
			else if (str.startsWith("UserNameExists")){
				name = JOptionPane.showInputDialog(
						chatWindow, 
						"Enter User Name: ",
						"Name Already Exists", 
						JOptionPane.WARNING_MESSAGE);
				out.println(name);
			}
			else if (str.startsWith("UserNameAccepted")) {
				textField.setEditable(true);
				nameLabel.setText("You Are Logged in as: "+str.split(",")[1]);
			}
			else {
				chatArea.append(str+"\n");
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception{
		ChatClient client = new ChatClient();
		client.startChat();
	}
}


class Listener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e){
		ChatClient.out.println(ChatClient.textField.getText());
		ChatClient.textField.setText("");
	}
}



