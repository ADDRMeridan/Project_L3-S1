package serveroffline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import server.IGestionBDD;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matthieulenoir
 */
public class GestionBDD implements IGestionBDD {
	@Override
	public boolean ajouterUtilisateur(String idUtilisateur, String nom, String prenom, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			nom = "'" + nom + "'";
			prenom = "'" + prenom + "'";
			password = "'" + password + "'";
			idUtilisateur = "'" + idUtilisateur + "'";
			state.executeUpdate("INSERT INTO utilisateur(uti_id, uti_nom, uti_prenom, uti_password) VALUES ("
					+ idUtilisateur + "," + nom + "," + prenom + "," + password + ");");
			state.close();
			conn.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idUtilisateur déjà utiliser !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean supprimerUtilisateur(String idUtilisateur) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			idUtilisateur = "'" + idUtilisateur + "'";
			state.executeUpdate("DELETE FROM utilisateur_has_groupe WHERE utilisateur_uti_id=" + idUtilisateur + "");
			state.executeUpdate(
					"DELETE FROM utilisateur_has_unread_ticket WHERE utilisateur_uti_id=" + idUtilisateur + "");
			state.executeUpdate("DELETE FROM utilisateur WHERE uti_id=" + idUtilisateur + "");
			state.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean ajouterGroupe(int idGroupe, String nom) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			nom = "'" + nom + "'";
			state.executeUpdate("INSERT INTO groupe (grp_id, grp_nom) VALUES (" + idGroupe + "," + nom + ");");
			state.close();
			conn.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idGroupe déjà utiliser !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean supprimerGroupe(int idGroupe) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			state.executeUpdate("DELETE FROM message WHERE msg_groupe_id=" + idGroupe + "");
			state.executeUpdate("DELETE FROM fil_de_discussion WHERE fil_groupe_id=" + idGroupe + "");
			state.executeUpdate("DELETE FROM utilisateur_has_groupe WHERE groupe_grp_id=" + idGroupe + "");
			state.executeUpdate("DELETE FROM groupe WHERE grp_id=" + idGroupe + "");
			state.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean ajouterUtilisateurAGroupe(int idGroupe, String idUtilisateur) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			state.executeUpdate("INSERT INTO utilisateur_has_groupe (utilisateur_uti_id, groupe_grp_id) VALUES ("
					+ idUtilisateur + "," + idGroupe + ");");
			state.close();
			conn.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idGroupe et idUtilisateur déjà associer !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean modifierUtilisateur(String idUtilisateur, String nom, String prenom, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			nom = "'" + nom + "'";
			prenom = "'" + prenom + "'";
			password = "'" + password + "'";
			idUtilisateur = "'" + idUtilisateur + "'";
			state.executeUpdate("UPDATE utilisateur SET uti_nom= " + nom + " WHERE uti_id=" + idUtilisateur + ";");
			state.executeUpdate(
					"UPDATE utilisateur SET uti_prenom= " + prenom + " WHERE uti_id=" + idUtilisateur + ";");
			state.executeUpdate(
					"UPDATE utilisateur SET uti_password= " + password + " WHERE uti_id=" + idUtilisateur + ";");
			state.close();
			conn.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idUtilisateur déjà utiliser !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean reset() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(" Unable to load driver. ");
		}
		String url = "jdbc:mysql://localhost:8889/mydb";
		String username = "root";
		String passd = "root";
		try {
			Connection conn = DriverManager.getConnection(url, username, passd);
			Statement state = conn.createStatement();
			state.executeUpdate("DELETE FROM utilisateur_has_unread_ticket ");
			state.executeUpdate("DELETE FROM utilisateur_has_groupe ");
			state.executeUpdate("DELETE FROM utilisateur ");
			state.executeUpdate("DELETE FROM message ");
			state.executeUpdate("DELETE FROM fil_de_discussion ");
			state.executeUpdate("DELETE FROM groupe ");
			state.close();
			conn.close();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("idUtilisateur déjà utiliser !");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
