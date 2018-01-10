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

import java.util.Date;

public class Message {

        private int idGroupe;
        private int idTicket;
        private int id;
	private String mess;
	private Date tWritten;
	
	public Message(String message) {
		this.mess = message;
		this.tWritten = new Date();
	}
        
        public Message(String message,Date date,int idGroupe,int idTicket,int id) {
		this.mess = message;
		this.tWritten = date;
                this.idGroupe=idGroupe;
                this.id=id;
                this.idTicket=idTicket;
	}
        
        public int getId(){
            return id;
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

	public Date gettWritten() {
		return tWritten;
	}
}
