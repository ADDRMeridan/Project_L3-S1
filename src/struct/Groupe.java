/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;

import java.io.Serializable;

/**
 *
 * @author matthieulenoir
 */
public class Groupe implements Serializable {
    
    /**
	 * DEFAULT
	 */
	private static final long serialVersionUID = 1L;
	private int idGroupe;
    private String nom;

    public Groupe(String nom,int idGroupe){
        this.nom=nom;
        this.idGroupe=idGroupe;
    }
    
    public String getNom() {
        return nom;
    }

    public int getIdGroupe() {
        return idGroupe;
    }
    
}
