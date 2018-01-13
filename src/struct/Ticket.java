/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;

import java.util.ArrayList;
import java.util.Date;
/**
 * @author Mael “ADDRMeridan” MOULIN
 * @author matthieulenoir
 */
import java.util.List;

public class Ticket {

	private int idGroupe;
	private String title;
	private int id;
	private Message firstMess;
	private Date dateLastMess;
	private List<Message> messages;

	/**
	 * Constructor to build new Ticket (Client side)
	 * @param idGroupe
	 * @param title
	 * @param firstMess
	 */
	public Ticket(int idGroupe, String title, String firstMess) {

		this.idGroupe = idGroupe;
		this.title = title;
		this.id = -1;
		this.firstMess = new Message(this.id, firstMess);
		this.dateLastMess = this.firstMess.gettWritten();
		this.messages = new ArrayList<>();
	}

	/**
	 * Constructor to rebuild Ticket from DB (Server side)
	 * @param title
	 * @param id
	 * @param idGroupe
	 * @param firstMess
	 * @param messages
	 */
	public Ticket(String title, int id, int idGroupe, Message firstMess, List<Message> messages) {

		this.title = title;
		this.id = id;
		this.idGroupe = idGroupe;
		this.firstMess = firstMess;
		this.idGroupe = idGroupe;
		this.messages = new ArrayList<>();
		this.messages.addAll(messages);
		if(this.messages.isEmpty()) {
			this.dateLastMess = firstMess.gettWritten();
		} else {
			this.dateLastMess = this.messages.get(this.messages.size()).gettWritten();
		}
	}

	public Date getDateLastMess() {
		return dateLastMess;
	}

	public int getIdGroupe() {
		return idGroupe;
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
