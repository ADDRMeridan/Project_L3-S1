package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import struct.Message;
import struct.Ticket;
import struct.Utilisateur;

public class ServNet extends Thread{

	static final int SERVERPORT = 9000;
	private ServerSocket socketServer;
	boolean serverOn = true;
	
	//For thread
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean logIn = false;
	private int userId = -1;
	
	private ServNet(Socket socket) throws IOException {
		
		this.socket = socket;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public ServNet() throws IOException {
		
		socketServer = new ServerSocket(SERVERPORT);
		System.out.println("Server Launched.");
		while(serverOn) {
			try {
				Socket socketClient = socketServer.accept();
				ServNet t = new ServNet(socketClient);
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
				serverOn = false;
			}
		}
	}
	
	@Override
	public void run() {
		
		while(serverOn) {
			try {
				waitForNextMessage();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				serverOn = false;
			}
		}
	}
	
	private void logIn() {
		
		try {
			NetPackage pack = (NetPackage) input.readObject();
			if(pack.getObjType() == ObjectType.LOGIN) {
				Utilisateur user = (Utilisateur) pack.getObj();
				if(/*TODO Check in data base if user exists to proceed*/) {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(true));
					logIn = true;
					userId = //TODO Get userId in database 
				} else {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(false));
				}
				output.writeObject(pack);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			serverOn = false;
		}
	}
	
	private void waitForNextMessage() throws ClassNotFoundException, IOException {
		
		if(!logIn) {
			logIn();
		} else {
			NetPackage pack = (NetPackage) input.readObject();
			switch (pack.getObjType()) {
			case LOGOFF:
				logOff();
				break;
			case NEW_TICKET:
				newTicket((Ticket) pack.getObj());
				break;
			case NEW_MESSAGE:
				updateTicket((Message) pack.getObj());
				break;
			case REFRESH_TREE:
				fetchUserTickets();
				break;
			default:
				System.err.println("Not server operation.");
				break;
			}
		}
		
	}
	
	private void logOff() throws IOException {
		logIn = false;
		serverOn = false;
		socket.close();
		input.close();
		output.close();
	}
	
	private void newTicket(Ticket tick) {
		
		//TODO store new ticket in dataBase
	}
	
	private void updateTicket(Message mess) {
		
		//TODO update ticket message list in dataBase
	}
	
	private void fetchUserTickets() {
		
		if(userId != -1) {
			Set<Ticket> tree = ;//TODO Get user Ticket Tree (using userId)
			NetPackage pack = new NetPackage(ObjectType.TREESET, tree);
			output.writeObject(pack);
		}
	}
}
