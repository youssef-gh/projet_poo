package entitees;

public class Facture {
	public int numeroFacture;
	public String dateFacture;
	public double montant;
	public int fk_numerocommande;
	
	public Facture() {
		numeroFacture = 0;
		dateFacture = "";
		montant = 0;
		fk_numerocommande = 0;
	}
}

