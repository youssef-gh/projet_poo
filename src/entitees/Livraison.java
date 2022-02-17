package entitees;

public class Livraison {
	public int numeroLivraison;
	public String dateLivraison;
	public int fk_numerocommande;
	
	public Livraison() {
		numeroLivraison = 0;
		dateLivraison = "";
		fk_numerocommande = 0;
	}
}
