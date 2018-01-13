package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import server.IServiceBDD;
import server.ServiceBDD;
import struct.Message;
import struct.Ticket;
import struct.Utilisateur;

public class ServNet extends Thread {

	static final int SERVERPORT = 9000;
	private ServerSocket socketServer;
	boolean serverOn = true;
	IServiceBDD bdd;

	// For thread
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean logIn = false;
	private String userId = "";

	private ServNet(Socket socket) throws IOException {

		this.socket = socket;
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	public ServNet() throws IOException {

		socketServer = new ServerSocket(SERVERPORT);
		System.out.println("Server Launched.");
		bdd = new ServiceBDD();
	}

	public void launchServer() {
		
		while (serverOn) {
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
	
	public String getUsername() {
		return userId;
	}

	@Override
	public void run() {

		while (serverOn) {
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
				if(bdd.authentification(user.getUsername(), user.getMdp())) {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(true));
					logIn = true;
					userId = user.getUsername();
				} else {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(false));
				}
				output.writeObject(pack);
			} else if(pack.getObjType() == ObjectType.LOGOFF){
				logOff();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			serverOn = false;
		}
	}

	private void waitForNextMessage() throws ClassNotFoundException, IOException {

		if (!logIn) {
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
			case NEW_MESS:
				updateTicket((Message) pack.getObj());
				break;
			case REFRESH_TREE:
				fetchUserTickets();
				break;
			case OPEN_TICKET:
				openTicket((Ticket) pack.getObj());
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
		output.close();
		input.close();
	}

	private void resetUserView(Ticket tick) {
		
		//TODO
	}
	
	private void newTicket(Ticket tick) {

		Message first = tick.getFirstMess();
		int idFil = tick.getId();
		int idGrp = tick.getIdGroupe();
		bdd.ajouterFil(bdd.nextIdFil(idGrp), tick.getTitle(), idGrp, bdd.nextIdMsg(idFil, idGrp), first.getMessage(), first.gettWritten());
		resetUserView(tick);
		bdd.supprimerTicketNonLu(userId, idFil, idGrp);
	}

	private void updateTicket(Message mess) {

		bdd.ajouterMsg(mess.getId(), mess.getMessage(), mess.getIdTicket(), mess.getIdGroupe(), mess.gettWritten());
		List<Ticket> tickets = bdd.getListeTicket(userId);
		Iterator<Ticket> iter = tickets.iterator();
		boolean notFound = true;
		Ticket tick = null;
		while(iter.hasNext() && notFound) {
			tick = iter.next();
			notFound = (tick.getId() == mess.getIdTicket());
		}
		if(!notFound) {
			resetUserView(tick);
		}
	}

	private void fetchUserTickets() throws IOException {
		
		List<Ticket> tmp = bdd.getListeTicket(userId);
		Set<Ticket> tree = new TreeSet<>();
		tree.addAll(tmp);
		NetPackage pack = new NetPackage(ObjectType.TREESET, tree);
		output.writeObject(pack);
	}
	
	private void openTicket(Ticket tick) throws IOException {
		
		List<Message> tmp = bdd.getListeMessage(tick.getId(), tick.getIdGroupe());
		NetPackage pack = new NetPackage(ObjectType.LIST_MESS, tmp);
		output.writeObject(pack);
		bdd.supprimerTicketNonLu(userId, tick.getId(), tick.getIdGroupe());
	}
}
