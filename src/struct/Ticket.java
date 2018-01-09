package struct;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Ticket implements Serializable, Comparable<Ticket>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int idTicket;
	private int idGroup;
	private Message firstMess;
	private TreeSet<Message> messages;
	private OffsetDateTime tLastMessage;
	
	public Ticket(String title, int idGroup, Message firstMess) {
		
		this.title = title;
		this.idTicket = -1;
		this.firstMess = firstMess;
		messages = new TreeSet<>();
	}
	
	public Ticket(String title, int id, Message firstMess, Message ...messages) {
		
		this.title = title;
		this.idTicket = id;
		this.firstMess = firstMess;
		this.messages = new TreeSet<>();
		for (Message e : messages) {
			this.messages.add(e);
		}
	}

	public String getTitle() {
		return title;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setId(int id) {
		this.idTicket = id;
	}

	public Message getFirstMess() {
		return firstMess;
	}

	public Set<Message> getMessages() {
		return messages;
	}
	
	public void addMessage(Message mess) {
		
		messages.add(mess);
		tLastMessage = OffsetDateTime.now();
	}

	public OffsetDateTime gettLastMessage() {
		return tLastMessage;
	}
	
	@Override
	public int compareTo(Ticket o) {
		return this.tLastMessage.compareTo(o.gettLastMessage());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Ticket) {
			Ticket tmp = (Ticket) o;
			return (this.idTicket == tmp.idTicket);
		}
		return false;
	}

	public int getIdGroup() {
		return idGroup;
	}

}
 