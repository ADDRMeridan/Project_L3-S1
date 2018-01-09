/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;

/**
 * 
 * @author Mael “ADDRMeridan” MOULIN
 * @author matthieulenoir
 */

import java.time.OffsetDateTime;

public class Message {

        private int idGroupe;
        private int idTicket;
	private String mess;
	private OffsetDateTime tWritten;
	
	public Message(String message) {
		this.mess = message;
		this.tWritten = OffsetDateTime.now();
	}

        public int getIdTicket() {
                return idTicket;
        }
        
        public int getIdGroupe() {
            return idGroupe;
        }

	public String getMessage() {
		return mess;
	}

	public OffsetDateTime gettWritten() {
		return tWritten;
	}
}
