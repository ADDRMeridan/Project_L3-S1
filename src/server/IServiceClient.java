/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import struct.Message;
import struct.Ticket;
import struct.Groupe;
import java.util.List;
/**
 *
 * @author matthieulenoir
 */
public interface IServiceClient {
    /**
     * Permet à un client de s'authentifier
     * @param idUtilisateur l'identifiant de l'utilisateur 
     * @param motDePasse le mot de passe de l'utilisateur 
     * @return boléen indiquant le succés ou non de l'authentification 
     */
    public boolean authentification(int idUtilisateur,String motDePasse);
    /**
     * Permet de génerer un idFil unique
     * @return l'idFil généré
     */
    public int nextIdFil ();
    /**
     * Permet de génerer un idMsg unique
     * @return l'idMsg généré
     */
    public int nextIdMsg();
    /**
     * Permet d'ajouter un fil de discussion dans la base de donnée
     * @param idFil l'indentifiant unique de ce fil de discussion
     * @param nom   le nom de ce fil de discussion
     * @param idGrp l'identifiant unique du groupe auquel appartient ce fil
     * @param idMsg l'identifiant unique du premier message de ce fil
     * @param contenuMsg le contenu du premier message de ce fil
     * @return un boléen indiquant la réussite de la création du fil
     */
    public boolean ajouterFil(int idFil,String nom,int idGrp,int idMsg,String contenuMsg);
    /**
     * Permet d'ajouter un message à un fil de discussion dans la base de donnée
     * @param idMsg l'indentifiant unique de ce message
     * @param contenuMsg    le contenu de ce message 
     * @param idFil l'indentifiant du fil auquel appartient ce message
     * @return un booléen idiquant la réussite de création du message
     */
    public boolean ajouterMsg(int idMsg,String contenuMsg,int idFil);
    /**
    * Retourne la liste des messages d'un ticket présent dans la base de donnée
    * @param idFil l'identifiant unique de ce fil
    * @return la liste des messages de ce fil
    */
    public List <Message> getListeMessage(int idFil);
    /**
     * Retourne la liste des tickets appartenant à un groupe présent dans la base de donnée
     * @param idGrp l'identifiant unique de ce groupe
     * @return la liste des tickets de ce groupe
     */
    public List <Ticket> getListeTicket(int idGrp);
    /**
     * Retourne la liste des groupes auxquels appartient un utilisateur présent dans la base de donnée
     * @param idUti l'identifiant unique de cet utilisateur
     * @return la liste des groupes auxquels appartient cet utilisateur
     */
    public List <Groupe> getListeGroupe(int idUti);
    /**
     * Indique si un utilisateur est présent dans la base de donnée
     * @param idUti l'indentifiant unique de l'utilisateur 
     * @return un booléen indiquant ou non la présence de l'utilisateur dans la base de donnée
     */
    public boolean utilisateurExiste(int idUti);
}
