package struct;

import java.time.OffsetDateTime;

public class Message {

	private int idTicket;
	private String mess;
	private OffsetDateTime tWritten;
	
	public Message(int idTicket, String message) {
		this.idTicket = idTicket;
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
}
