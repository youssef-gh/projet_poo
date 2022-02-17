package interfaceGraphique;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entitees.Commande;
public class InterfaceCommande extends Panel {
	Commande cmd = new Commande();
	
	public InterfaceCommande() {
		super();
		txtFields[3].setVisible(false);
		dateFormat.setVisible(true);
		tableName = "commande";
		idText = "numerocommande";
		tableHeader = new String[] { idText, "Date Commande", "num. Client", "num. Produit"};
		initTableau(tableHeader);
		idLabel.setText(idText + ": ");
		chargerTableau(tableHeader.length, "SELECT * FROM commande ORDER BY " + idText);
	}

	public void setLabels() {
		jLabels[0].setText("Date Commande");
		jLabels[1].setText("Num. Client");
		jLabels[2].setText("Num. Produit");
	}
	
	public boolean verifier() {
		if( txtFields[0].getText().isEmpty() ||
			txtFields[1].getText().isEmpty() ||
			txtFields[2].getText().isEmpty()	
		) {
	    	donneesIncompleteFenetre();
	    	return false;
		}
		
		if(!txtFields[0].getText().matches("[1-9][0-9]{3}-[01][1-9]-[0-3][0-9]")) {
			System.out.println(txtFields[0].getText());
			Util.afficherInfo("La date de commande doit etre une date !.", "Date invalide.");
			return false;
		}
		
		if(!txtFields[1].getText().matches("\\d*")) {
			Util.afficherInfo("Le numero de client doit �tre un nombre entier.", "Numero invalide.");
			return false;
		}
		
		if(!txtFields[2].getText().matches("\\d*")) {
			Util.afficherInfo("Le numero de produit doit �tre un nombre entier.", "Numero invalide.");
			return false;
		}
		
		return true;
	}
	
	public void remplirCommande() {
		cmd.datecommande = txtFields[0].getText();
		cmd.fk_numeroclient = Integer.parseInt(txtFields[1].getText());
		cmd.fk_numeroproduit = Integer.parseInt(txtFields[2].getText());
	}
	
	public void EcouterBoutons() {
		super.EcouterBoutons();
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!verifier()) return;
				
				remplirCommande();
				// Remplir les information de commande.
				String requete = "INSERT INTO Commande(datecommande,numeroclient,numeroproduit)"
						+ "VALUES('" + cmd.datecommande + "', '" + 
						cmd.fk_numeroclient + "', '" + cmd.fk_numeroproduit + "')";
				
				if(DB.executeUpdate(requete) == -1) return;
				ajouterLigne(tableHeader.length, "SELECT * FROM commande ORDER BY numerocommande DESC LIMIT 1");
				clearTextFields();
			}
		});
		

		btnModifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model = (DefaultTableModel) tb.getModel();
				String id = idTF.getText();
				if(!verifierId(id , idText) || !verifier()) return;
				int y = getRow(id);
				if(y == -1) {
					Util.afficherInfo(idText + " Inexistant");
					return;
				}
				remplirCommande();
				String requete = "UPDATE commande"
						+ " SET nom= '" + cmd.datecommande + "'"
						+ ", prenom= '" + cmd.fk_numeroclient + "'"
						+ ", adresse= '" + cmd.fk_numeroproduit + "'"
						+ " WHERE numerocommande=" + id;
				
				if(DB.executeUpdate(requete) == -1) return;
				modifierLigne(new String[] {id, cmd.datecommande,  Integer.toString(cmd.fk_numeroclient),  Integer.toString(cmd.fk_numeroproduit)}, y);
			}
		});
	}
}
