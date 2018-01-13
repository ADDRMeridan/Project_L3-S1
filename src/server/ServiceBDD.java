package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import struct.Groupe;
import struct.Message;
import struct.Ticket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matthieulenoir
 */
public class ServiceBDD implements IServiceBDD {

	@Override
	public boolean authentification(String idUtilisateur, String motDePasse) {
		boolean authOK;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUtilisateur = "'" + idUtilisateur + "'";
			motDePasse = "'" + motDePasse + "'";
			ResultSet result = state.executeQuery("SELECT * FROM utilisateur WHERE uti_id=" + idUtilisateur
					+ " AND uti_password=" + motDePasse + " LIMIT 1");
			authOK = result.next();
			result.close();
			state.close();
			return authOK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int nextIdFil(int idGrp) {
		int nextID = 0;
		int nbFil = nbFil(idGrp);
		if (nbFil == 0) {
			return nextID;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id=" + idGrp + "");
			for (int i = 0; i < nbFil; i++) {
				result.next();
			}
			nextID = result.getInt(1) + 1;
			result.close();
			state.close();
			return nextID;
		} catch (Exception e) {
			e.printStackTrace();
			return nextID;
		}
	}

	/**
	 * Permet de connaître le nombre de fil présent dans ce groupe
	 * 
	 * @param idGrp
	 *            l'identifiant unique de ce groupe
	 * @return un entier correspondant au nombre de groupe
	 */
	private int nbFil(int idGrp) {
		int nbFil = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id=" + idGrp + "");
			while (result.next())
				nbFil++;
			result.close();
			state.close();
			return nbFil;
		} catch (Exception e) {
			e.printStackTrace();
			return nbFil;
		}
	}

	@Override
	public int nextIdMsg(int idFil, int idGrp) {
		int nextID = 0;
		int nbFil = nbMsg(idFil, idGrp);
		if (nbFil == 0) {
			return nextID;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(
					"SELECT * FROM message WHERE msg_fil_id=" + idFil + " AND msg_groupe_id=" + idGrp + "");
			for (int i = 0; i < nbFil; i++) {
				result.next();
			}
			nextID = result.getInt(1) + 1;
			result.close();
			state.close();
			return nextID;
		} catch (Exception e) {
			e.printStackTrace();
			return nextID;
		}
	}

	/**
	 * Permet de connaître le nombre de message présent dans un ticket
	 * 
	 * @param idFil
	 *            l'identifiant du ticket
	 * @param idGrp
	 *            l'identifiant unique du groupe auquel appartient le ticket
	 * @return un entier correspondant au nombre de message
	 */
	private int nbMsg(int idFil, int idGrp) {
		int nbMsg = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(
					"SELECT * FROM message WHERE msg_fil_id=" + idFil + " AND msg_groupe_id=" + idGrp + "");
			while (result.next())
				nbMsg++;
			result.close();
			state.close();
			return nbMsg;
		} catch (Exception e) {
			e.printStackTrace();
			return nbMsg;
		}
	}

	@Override
	public boolean ajouterFil(int idFil, String nom, int idGrp, int idMsg, String contenuMsg, Date date) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			nom = "'" + nom + "'";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(date);
			currentTime = "'" + currentTime + "'";
			state.executeUpdate(
					"INSERT INTO fil_de_discussion (fil_id, fil_nom,fil_groupe_id,fil_premier_msg_id,fil_date_dernier_msg) VALUES ("
							+ idFil + "," + nom + "," + idGrp + "," + idMsg + "," + currentTime + ");");
			state.close();
			ServiceBDD s = new ServiceBDD();
			return s.ajouterMsg(idMsg, contenuMsg, idFil, idGrp, date);
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idFil déjà utiliser !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean ajouterMsg(int idMsg, String contenuMsg, int idFil, int idGrp, Date date) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			contenuMsg = "'" + contenuMsg + "'";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(date);
			currentTime = "'" + currentTime + "'";
			state.executeUpdate("INSERT INTO message (msg_id, msg_date,msg_contenu,msg_fil_id,msg_groupe_id) VALUES ("
					+ idMsg + "," + currentTime + "," + contenuMsg + "," + idFil + "," + idGrp + ");");
			state.executeUpdate("UPDATE fil_de_discussion SET fil_date_dernier_msg= " + currentTime + " WHERE fil_id="
					+ idFil + " AND fil_groupe_id=" + idGrp + "");
			state.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idFil déjà utiliser pour un message!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Message> getListeMessage(int idFil, int idGrp) {
		List<Message> l = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(
					"SELECT * FROM message WHERE msg_fil_id=" + idFil + " AND msg_groupe_id=" + idGrp + "");
			while (result.next()) {
				int id = result.getInt(1);
				String contenu = result.getObject(3).toString();
				DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
				Date date = (Date) formatter.parse(result.getObject(2).toString());
				Message m = new Message(id, contenu, date, idGrp, idFil);
				l.add(m);
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Permet d'obtenir sous la forme d'une structure Message un message de la base
	 * de donnée
	 * 
	 * @param idGrp
	 *            l'identifant unique du groupe auquel appartient le message
	 * @param idFil
	 *            l'identifiant du ticket auquel apprtient le message
	 * @param msgId
	 *            l'identifiant du message
	 * @return une structure Message avec toutes les informations du message présent
	 *         dans le base de donnée
	 */
	private Message getMessage(int idGrp, int idFil, int msgId) {
		Message m;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM message WHERE msg_groupe_id=" + idGrp
					+ " AND msg_fil_id=" + idFil + " AND msg_id=" + msgId + " LIMIT 1");
			result.next();
			String contenu = result.getObject(3).toString();
			DateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			Date date = (Date) formatter.parse(result.getObject(2).toString());
			m = new Message(msgId, contenu, date, idGrp, idFil);
			result.close();
			state.close();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new NullPointerException();
	}

	@Override
	public List<Ticket> getListeTicket(int idGrp) {
		List<Ticket> l = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_groupe_id=" + idGrp + "");
			while (result.next()) {
				int idFil = result.getInt(1);
				String nom = result.getObject(2).toString();
				int msgId = result.getInt(4);
				Ticket t = new Ticket(nom, idFil, idGrp, getMessage(idGrp, idFil, msgId), new ArrayList<>());
				l.add(t);
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Permet d'obtenir le nom d'un groupe
	 * 
	 * @param idGroupe
	 *            l'identifiant unique de ce groupe
	 * @return un string contenant le nom du groupe
	 */
	private String getNomGroupe(int idGroupe) {
		String nom = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM groupe WHERE grp_id=" + idGroupe + " LIMIT 1");
			result.next();
			nom = result.getObject(2).toString();
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nom;
	}

	@Override
	public List<Groupe> getListeGroupe(String idUti) {
		List<Groupe> l = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUti = "'" + idUti + "'";
			ResultSet result = state
					.executeQuery("SELECT * FROM utilisateur_has_groupe WHERE utilisateur_uti_id=" + idUti + "");
			while (result.next()) {
				int gId = result.getInt(2);
				Groupe g = new Groupe(getNomGroupe(gId), gId);
				l.add(g);
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public List<Ticket> getListeTicket(String idUti) {
		List<Ticket> l = new ArrayList<>();
		List<Groupe> l2 = getListeGroupe(idUti);
		for (Groupe g : l2) {
			l.addAll(getListeTicket(g.getIdGroupe()));
		}
		return l;
	}

	@Override
	public boolean utilisateurExiste(String idUti) {
		boolean existe;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUti = "'" + idUti + "'";
			ResultSet result = state.executeQuery("SELECT * FROM utilisateur WHERE uti_id=" + idUti + " LIMIT 1");
			existe = result.next();
			result.close();
			state.close();
			return existe;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean ajouterTicketNonLu(String idUti, int idFil, int idGrp) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUti = "'" + idUti + "'";
			state.executeUpdate(
					"INSERT INTO utilisateur_has_unread_ticket (utilisateur_uti_id, fil_de_discussion_fil_id,fil_de_discussion_groupe_id) VALUES ("
							+ idUti + "," + idFil + "," + idGrp + ");");
			state.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("Utilisateur et message déjà associer");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean supprimerTicketNonLu(String idUti, int idFil, int idGrp) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUti = "'" + idUti + "'";
			state.executeUpdate("DELETE FROM utilisateur_has_unread_ticket WHERE utilisateur_uti_id=" + idUti
					+ " AND fil_de_discussion_fil_id=" + idFil + " AND fil_de_discussion_groupe_id=" + idGrp + "");
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Permet d'obtenir sous la forme d'une structure Ticket un ticket de la base de
	 * donnée
	 * 
	 * @param idFil
	 *            l'identifiant de ce ticket
	 * @param idGrp
	 *            l'identifiant unique du groupe auquel appartient ce ticket
	 * @return une structure Ticket avec toutes les informations du ticket présent
	 *         dans le base de donnée
	 */
	private Ticket getInfoTicket(int idFil, int idGrp) {
		Ticket t;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM fil_de_discussion WHERE fil_id=" + idFil
					+ " AND fil_groupe_id=" + idGrp + " LIMIT 1");
			result.next();
			String nom = result.getObject(2).toString();
			int msgId = result.getInt(4);
			t = new Ticket(nom, idFil, idGrp, getMessage(idGrp, idFil, msgId), new ArrayList<>());
			state.close();
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new NullPointerException();
	}

	@Override
	public List<Ticket> listeTicketNonLu(String idUti) {
		List<Ticket> l = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:8889/mydb";
			String user = "root";
			String passwd = "root";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			idUti = "'" + idUti + "'";
			ResultSet result = state
					.executeQuery("SELECT * FROM utilisateur_has_unread_ticket WHERE utilisateur_uti_id=" + idUti + "");
			while (result.next()) {
				int idFil = result.getInt(2);
				int idGrp = result.getInt(3);
				Ticket t = getInfoTicket(idFil, idGrp);
				l.add(t);
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

}
