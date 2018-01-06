package client;

import struct.Ticket;

public interface IClient {

	public boolean connect(String username, String password);
	
	public boolean createTicket(String title, int groupID, String mess);
	
	public boolean answerToTicket(int ticketId, String mess);
	
	public boolean refreshTicketTree();
	
	public Ticket getTicket(int ticketID);
	
	public boolean disconnect();
}
