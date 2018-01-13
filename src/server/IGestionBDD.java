package server;

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author matthieulenoir
 *
 */
public interface IGestionBDD {
	/**
	 * Crée et ajoute un utilisateur à la base de donnée
	 * @param idUtilisateur l'identifiant unique de cet utilisateur
	 * @param nom le nom de cet utilisateur
	 * @param prenom le prenom de cet utilisateur
	 * @param password le mot de passe de cet utilisateur
	 * @return un booléen indiquant si la création et l'ajout de l'utilisateur ont fonctionné*/
	public boolean ajouterUtilisateur(String idUtilisateur,String nom,String prenom,String password);
	/**
	 * Supprime un utilisateur de la base de donnée
	 * @param idUtilisateur l'identifiant unique de l'utilisateur que l'on souhaite supprimer
	 * @return un booléen indiquant si la suppression a fonctionné
	 * */
	public boolean supprimerUtilisateur(String idUtilisateur);
	/**
	 * Crée et ajoute un groupe à la base de donnée
	 * @param idGroupe l'identifiant unique de ce groupe
	 * @param nom le nom de ce groupe
	 * @return booléen indiquant si la création et l'ajout du groupe ont fonctionné
	 * */
	public boolean ajouterGroupe(int idGroupe,String nom);
	/**
	 * Supprime un groupe de la base de donnée
	 * @param idGroupe l'identifiant unique du groupe que l'on souhaite supprimer
	 * @return un booléen indiquant si la suppression a fonctionné
	 * */
	public boolean supprimerGroupe(int idGroupe);
	/**
	 * Ajoute un utilisateur déjà présent dans la base à un groupe
	 * @param idGroupe l'identifiant unique de ce groupe
	 * @param idUtilisateur l'identifiant unique de cet utilisateur
	 * @return booléen indiquant si l'ajout a fonctionné
	 * */
	public boolean ajouterUtilisateurAGroupe(int idGroupe,String idUtilisateur);
	/**
	 * Modifie les données d'un utilisateur présent dans la base de donnée
	 * @param idUtilisateur l'identifiant unique de cet utilisateur
         * @param nom le nom de cet utilisateur
	 * @param prenom le prenom de cet utilisateur
	 * @param password le mot de passe de cet utilisateur
	 * @return booléen indiquant si la modification a fonctionné
	 * */
	public boolean modifierUtilisateur(String idUtilisateur,String nom,String prenom,String password);
}

