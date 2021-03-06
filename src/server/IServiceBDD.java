package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import struct.Message;
import struct.Ticket;
import struct.Utilisateur;
import struct.Groupe;
import java.util.List;
import java.util.Date;

/**
 *
 * @author matthieulenoir
 */
public interface IServiceBDD {
    /**
     * Permet à un client de s'authentifier
     * @param idUtilisateur l'identifiant de l'utilisateur 
     * @param motDePasse le mot de passe de l'utilisateur 
     * @return boléen indiquant le succés ou non de l'authentification 
     */
    public boolean authentification(String idUtilisateur,String motDePasse);
    /**
     * Permet de génerer un idFil unique
     * @param idGrp l'id du groupe auquel appartiendra le fil
     * @return l'idFil généré
     */
    public int nextIdFil (int idGrp);
    /**
     * Permet de génerer un idMsg unique
     * @param idGrp l'id du groupe auquel appartiendra le message
     * @param  idFil l'id du fil auquel appartiendra le message
     * @return l'idMsg généré
     */
    public int nextIdMsg(int idFil,int idGrp);
    /**
     * Permet d'ajouter un fil de discussion dans la base de donnée
     * @param idFil l'indentifiant de ce fil de discussion
     * @param nom   le nom de ce fil de discussion
     * @param idGrp l'identifiant unique du groupe auquel appartient ce fil
     * @param idMsg l'identifiant du premier message de ce fil
     * @param contenuMsg le contenu du premier message de ce fil
     * @param date la date du premier message
     * @return un boléen indiquant la réussite de la création du fil
     */
    public boolean ajouterFil(int idFil,String nom,int idGrp,int idMsg,String contenuMsg,Date date);
    /**
     * Permet d'ajouter un message à un fil de discussion dans la base de donnée
     * @param idMsg l'indentifiant unique de ce message
     * @param contenuMsg    le contenu de ce message 
     * @param idFil l'indentifiant du fil auquel appartient ce message
     * @param idGrp l'indentifiant du groupe auquel appartient ce message
     * @param date la date de ce message
     * @return un booléen idiquant la réussite de création du message
     */
    public boolean ajouterMsg(int idMsg,String contenuMsg,int idFil,int idGrp,Date date);
    /**
    * Retourne la liste des messages d'un ticket présent dans la base de donnée
    * @param idFil l'identifiant de ce fil
    * @param idGrp l'identifiant du groupe auquel appartient ce fil
    * @return la liste des messages de ce fil
    */
    public List <Message> getListeMessage(int idFil,int idGrp);
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
    public List <Groupe> getListeGroupe(String idUti);
    /**
     * Retourne la liste des tickets auxquels appartient un utilisateur présent dans la base de donnée
     * @param idUti l'identifiant unique de cet utilisateur
     * @return la liste des tickets auxquels appartient cet utilisateur
     */
    public List <Ticket> getListeTicket(String idUti);
    /**
     * Indique si un utilisateur est présent dans la base de donnée
     * @param idUti l'indentifiant unique de l'utilisateur 
     * @return un booléen indiquant ou non la présence de l'utilisateur dans la base de donnée
     */
    public boolean utilisateurExiste(String idUti);
    /**
     * Permet d'associer un utilisateur à un message qu'il n'a pas encore lu dans la base de donnée
     * @param idUti l'identifiant unique de l'utilisateur
     * @param idFil l'identifiant du fil
     * @param idGrp l'identifiant unique du groupe
     * @return un booléen indiquant le succès ou non de l'ajout
     */
    public boolean ajouterTicketNonLu(String idUti,int idFil,int idGrp);
    /**
     * Permet de supprimer ticket non lu dans la base de donnée
     * @param idUti l'identifiant unique de l'utilisateur
     * @param idFil l'identifiant du fil
     * @param idGrp l'identifiant unique du groupe
     * @return un booléen indiquant le succès ou non de l'ajout
     */
    public boolean supprimerTicketNonLu(String idUti,int idFil,int idGrp);
    /**
     * Permet d'obtenir la liste des tickets qu'un utilisateur n'a pas encore lu
     * @param idUti l'identifiant unique de l'utilisateur
     * @return la liste des tickets non lu
     */
    public List <Ticket> listeTicketNonLu(String idUti);
    /**
     * Permet d'obtenir la liste des utilisateurs present dans un groupe
     * @param idGrp l'identifiant unique de ce groupe
     * @return la liste des utilisateur présen dans le groupe 
     */
    public List<Utilisateur> getListeUtilisateur(int idGrp);
}
