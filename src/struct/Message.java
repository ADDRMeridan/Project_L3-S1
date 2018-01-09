package struct;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class Message implements Serializable, Comparable<Message> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idTicket;
	private int idMessage;
	private String author;
	private String mess;
	private OffsetDateTime tWritten;
	
	public Message(int idTicket, String username, String message) {
		this.idTicket = idTicket;
		this.setIdMessage(-1);
		this.author = username;
		this.mess = message;
		this.tWritten = OffsetDateTime.now();
	}

	public int getIdTicket() {
		return idTicket;
	}

	public String getMessage() {
		return mess;
	}

	public OffsetDateTime getTWritten() {
		return tWritten;
	}
	
	public String getAuthor() {
		return author;
	}

	@Override
	public int compareTo(Message obj) {
		return this.getTWritten().compareTo(obj.getTWritten());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Message) {
			Message tmp = (Message) o;
			return (this.idTicket == tmp.idTicket);
		}
		return false;
	}

	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
}
