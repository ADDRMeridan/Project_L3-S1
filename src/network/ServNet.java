package network;

import java.io.EOFException;
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
import struct.Groupe;
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
		bdd = new ServiceBDD();
	}

	public ServNet() throws IOException {

		socketServer = new ServerSocket(SERVERPORT);
	}

	public void launchServer() {

		System.out.println("[SERVER] Launched.");
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
			} catch (EOFException e) {
				System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " ended connection: EOF.");
				serverOn = false;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				serverOn = false;
			}
		}
	}

	private void logIn() {

		try {
			NetPackage pack = (NetPackage) input.readObject();
			if (pack.getObjType() == ObjectType.LOGIN) {
				Utilisateur user = (Utilisateur) pack.getObj();
				if (bdd.authentification(user.getUsername(), user.getMdp())) {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(true));
					logIn = true;
					userId = user.getUsername();
					System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " logged In.");
				} else {
					pack = new NetPackage(ObjectType.LOGIN_ANS, new Boolean(false));
				}
				output.writeObject(pack);
			} else if (pack.getObjType() == ObjectType.LOGOFF) {
				logOff();
			}
		} catch (EOFException e) {
			serverOn = false;
			System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " lost connection by EOF.");
		} catch (ClassNotFoundException | IOException e) {
			serverOn = false;
			e.printStackTrace();
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
			case ASK_GRP:
				fetchUserGrp((String) pack.getObj());
				break;
			default:
				System.err.println("Not server operation.");
				break;
			}
		}

	}

	private void logOff() throws IOException {

		System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " logged Off.");
		logIn = false;
		serverOn = false;
		socket.close();
		output.close();
		input.close();
	}

	private void resetUserView(Ticket tick) {

		List<Utilisateur> tmp = bdd.getListeUtilisateur(tick.getIdGroupe());
		for (Utilisateur utilisateur : tmp) {
			bdd.ajouterTicketNonLu(utilisateur.getUsername(), tick.getId(), tick.getIdGroupe());
		}
	}

	private void newTicket(Ticket tick) {

		Message first = tick.getFirstMess();
		int idGrp = tick.getIdGroupe();
		int idFil = bdd.nextIdFil(idGrp);
		bdd.ajouterFil(idFil, tick.getTitle(), idGrp, bdd.nextIdMsg(idFil, idGrp), first.getMessage(),
				first.gettWritten());
		resetUserView(tick);
		bdd.supprimerTicketNonLu(userId, idFil, idGrp);
		System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " added a new ticket in group " + tick.getIdGroupe());
	}

	private void updateTicket(Message mess) {

		List<Ticket> tickets = bdd.getListeTicket(userId);
		Iterator<Ticket> iter = tickets.iterator();
		boolean notFound = true;
		Ticket tick = null;
		while (iter.hasNext() && notFound) {
			tick = iter.next();
			notFound = (tick.getId() != mess.getIdTicket());
		}
		if (!notFound) {
			int idMess = bdd.nextIdMsg(tick.getId(), tick.getIdGroupe());
			bdd.ajouterMsg(idMess, mess.getMessage(), tick.getId(), tick.getIdGroupe(), mess.gettWritten());
			resetUserView(tick);
			bdd.supprimerTicketNonLu(userId, tick.getId(), tick.getIdGroupe());
			System.out.println("[T:" + Thread.currentThread().getId() + "] " + userId + " added a message to ticket " + tick.getId());
		}
	}

	private void fetchUserTickets() throws IOException {

		List<Ticket> tmp = bdd.getListeTicket(userId);
		List<Ticket> tmpNew = bdd.listeTicketNonLu(userId);
		for (Ticket ticket : tmpNew) {
			ticket.setSeen(false);
		}
		Set<Ticket> tree = new TreeSet<>();
		tree.addAll(tmpNew);
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

	private void fetchUserGrp(String username) throws IOException {

		List<Groupe> tmp = bdd.getListeGroupe(username);
		NetPackage pack = new NetPackage(ObjectType.LIST_GRP, tmp);
		output.writeObject(pack);
	}
}
