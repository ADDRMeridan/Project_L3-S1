package struct;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Ticket {

	private String title;
	private int id;
	private Message firstMess;
	private Set<Message> messages;
	
	public Ticket(String title, Message firstMess) {
		
		this.title = title;
		this.id = -1;
		this.firstMess = firstMess;
		messages = new TreeSet<>();
	}
	
	public Ticket(String title, int id, Message firstMess, Message ...messages) {
		
		this.title = title;
		this.id = id;
		this.firstMess = firstMess;
		this.messages = new TreeSet<>();
		for (Message e : messages) {
			this.messages.add(e);
		}
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Message getFirstMess() {
		return firstMess;
	}

	public Set<Message> getMessages() {
		return messages;
	}
	
	public void addMessage(Message mess) {
		
		messages.add(mess);
	}
}
