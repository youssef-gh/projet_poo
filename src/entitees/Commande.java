package entitees;

public class Commande {
		public int numerocommande;
		public String datecommande;
		public int fk_numeroclient;
		public int fk_numeroproduit;
		
		public Commande() {
			numerocommande = 0;
			datecommande = "";
			fk_numeroclient = 0;
			fk_numeroproduit = 0;	
	}
}
