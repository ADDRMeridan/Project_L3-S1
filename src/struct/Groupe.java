/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struct;

/**
 *
 * @author matthieulenoir
 */
public class Groupe {
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
