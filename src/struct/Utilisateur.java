
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;
import java.io.Serializable;

/** 
 * 
 * @author Mael “ADDRMeridan” MOULIN
 */


public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String mdp;
	private String nom;
	private String prenom;

	public Utilisateur(String username, String mdp, String nom, String prenom) {
		
		this.username = username;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getUsername() {
		return username;
	}

	public String getMdp() {
		return mdp;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	
}