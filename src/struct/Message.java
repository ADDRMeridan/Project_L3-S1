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

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * DEFAULT
	 */
	private static final long serialVersionUID = 1L;
	private int idGroupe;
	private int idTicket;
	private int id;
	private String mess;
	private Date tWritten;

	/**
	 * Constructor to build new Message (Client side)
	 * @param message
	 * @param idTicket
	 */
	public Message(int idTicket, String message) {

		this.mess = message;
		this.tWritten = new Date();
		this.idTicket = idTicket;
		this.idGroupe = -1;
		this.id = -1;
	}

	/**
	 * Constructor to rebuild Message from DB (Server side)
	 * @param message
	 * @param date
	 * @param idGroupe
	 * @param idTicket
	 * @param id
	 */
	public Message(int id, String message, Date date, int idGroupe, int idTicket) {

		this.mess = message;
		this.tWritten = date;
		this.idGroupe = idGroupe;
		this.id = id;
		this.idTicket = idTicket;
	}

	public int getId() {
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
