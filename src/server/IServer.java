package server;

public interface IServer {

	public boolean ajouterUtilisateur(int idUtilisateur, String nom, String prenom, String password);
	
	public boolean supprimerUtilisateur(int idUtilisateur);
	
	public boolean ajouterGroupe(int idGroupe, String nom);
	
	public boolean supprimerGroupe(int idGroupe);
	
	public boolean ajouterUtilisateurAGroupe(int idGroupe, int idUtilisateur);
	
	public boolean modifierUtilisateur(int idUtilisateur);
}
