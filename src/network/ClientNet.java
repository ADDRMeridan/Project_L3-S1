package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Set;

import struct.Message;
import struct.Ticket;
import struct.Utilisateur;

public class ClientNet {

	static final int SERVERPORT = 9000;
	static final String SERVERIP = "127.0.0.1";
	
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public ClientNet() throws IOException {
	
		socket = new Socket(InetAddress.getByName(SERVERIP), SERVERPORT);
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public boolean connect(String username, String password) throws IOException, ClassNotFoundException {
		
		Utilisateur tmp = new Utilisateur(-1, username, password, "", "");
		NetPackage pack = new NetPackage(ObjectType.LOGIN, tmp);
		output.writeObject(pack);
		pack = (NetPackage) input.readObject();
		if(pack.getObjType() == ObjectType.LOGIN_ANS) {
			return (Boolean) pack.getObj();
		}
		return false;
	}
	
	public void disconnect() throws IOException {
		
		NetPackage pack = new NetPackage(ObjectType.LOGOFF, null);
		output.writeObject(pack);
		destroySocket();
	}
	
	public void destroySocket() throws IOException {
		
		socket.close();
		input.close();
		output.close();
	}
	
	public void createTicket(String title, String firstMess) throws IOException {
		
		Ticket tick = new Ticket(title, new Message(-1, firstMess));
		NetPackage pack = new NetPackage(ObjectType.NEW_TICKET, tick);
		output.writeObject(pack);
	}
	
	public void answerTicket(int idTicket, String mess) throws IOException {
		
		Message tmp = new Message(idTicket, mess);
		NetPackage pack = new NetPackage(ObjectType.NEW_MESSAGE, tmp);
		output.writeObject(pack);
	}
	
	@SuppressWarnings("unchecked")
	public Set<Ticket> refreshTree() throws ClassNotFoundException, IOException {
		
		NetPackage pack = new NetPackage(ObjectType.REFRESH_TREE, null);
		output.writeObject(pack);
		pack = (NetPackage) input.readObject();
		if(pack.getObjType() == ObjectType.TREESET) {
			return (Set<Ticket>) pack.getObj();
		}
		return null;
	}
}
