package interfaceGraphique;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entitees.Livraison;
public class InterfaceLivraison extends Panel {
	Livraison liv = new Livraison();

	public InterfaceLivraison() {
		super();
		dateFormat.setVisible(true);
		txtFields[2].setVisible(false);
		txtFields[3].setVisible(false);
		tableName = "livraison";
		idText = "numerolivraison";
		tableHeader = new String[] { idText, "dateLivraison", "num. commande"};
		initTableau(tableHeader);
		idLabel.setText(idText + ": ");
		chargerTableau(tableHeader.length, "SELECT * FROM livraison ORDER BY " + idText);
	}

	public void setLabels() {
		jLabels[0].setText("Date De Livraison");
		jLabels[1].setText("Num. commande");
		
	}
	
	public boolean verifier() {
		if( txtFields[0].getText().isEmpty() ||
			txtFields[1].getText().isEmpty() ) 
		{
    	donneesIncompleteFenetre();
    	return false;
		}
		
		if(!txtFields[0].getText().matches("[1-9][0-9]{3}-[01][1-9]-[0-3][0-9]")) {
			Util.afficherInfo("La date de livraison doit etre une date !.", "Date invalide.");
			return false;
		}
		if(!txtFields[1].getText().matches("\\d*")) {
			Util.afficherInfo("Le numero de commande doit �tre un nombre entier.", "Numero invalide.");
			return false;
		}	
		return true;
	}
	
	public void remplirLivraison() {
		liv.dateLivraison = txtFields[0].getText();
		liv.fk_numerocommande = Integer.parseInt(txtFields[1].getText());
		
	}
	
	public void EcouterBoutons() {
		super.EcouterBoutons();
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!verifier()) return;
				
				remplirLivraison();

				String requete = "INSERT INTO Livraison(dateLivraison,numerocommande)"
						+ "VALUES('" + 
						liv.dateLivraison + "', " + liv.fk_numerocommande + ")";
				
				if(DB.executeUpdate(requete) == -1) return;

				ajouterLigne(tableHeader.length, "SELECT * FROM livraison ORDER BY numeroLivraison DESC LIMIT 1");
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
				remplirLivraison();
				String requete = "UPDATE livraison"
						+ " SET dateLivraison = '" + liv.dateLivraison + "'"
						+ ", numerocommande= " + liv.fk_numerocommande + ""
						+ " WHERE numerolivraison=" + id;
				
				if(DB.executeUpdate(requete) == -1) return;
				modifierLigne(new String[] {id, liv.dateLivraison,  Integer.toString(liv.fk_numerocommande)}, y);
			}
		});
	}
}
