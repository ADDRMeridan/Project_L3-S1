package struct;

import java.util.List;

public class Ticket {

	private String title;
	private int id;
	private Message firstMess;
	private List<Message> messages;
	
	public Ticket(String title, int id, String firstMess, Message ...messages) {
		
		this.title = title;
		this.id = id;
		this.firstMess = new Message(firstMess);
		for (Message e : messages) {
			this.messages.add(e);
		}
	}
	
	public Ticket(String title, int id, Message firstMess, Message ...messages) {
		
		this.title = title;
		this.id = id;
		this.firstMess = firstMess;
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

	public Message getFirstMess() {
		return firstMess;
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	
}
