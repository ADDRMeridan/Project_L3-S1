package struct;

import java.time.OffsetDateTime;

public class Message {

	private String mess;
	private OffsetDateTime tWritten;
	
	public Message(String message) {
		this.mess = message;
		this.tWritten = OffsetDateTime.now();
	}

	public String getMessage() {
		return mess;
	}

	public OffsetDateTime gettWritten() {
		return tWritten;
	}
}
