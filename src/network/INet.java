package network;

import struct.Message;

public interface INet {
	
	public boolean connectToServ();
	
	public boolean acceptClient(String username, String password);
	
	public boolean sendTicket();
	
	public boolean sendMessage(Message mess);
	
	public boolean disconnect();
}
