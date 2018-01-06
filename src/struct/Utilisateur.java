package struct;

public class Utilisateur {

	private int id;
	private String username;
	private String mdp;
	private String nom;
	private String prenom;

	public Utilisateur(int id, String username, String mdp, String nom, String prenom) {
		this.id = id;
		this.username = username;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
	}

	public int getId() {
		return id;
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
