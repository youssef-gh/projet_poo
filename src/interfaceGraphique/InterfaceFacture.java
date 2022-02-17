package interfaceGraphique;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entitees.Facture;
public class InterfaceFacture extends Panel {
	Facture fct = new Facture();
	
	public InterfaceFacture() {
		super();
		txtFields[3].setVisible(false);
		dateFormat.setVisible(true);
		tableName = "facture";
		idText = "numerofacture";
		tableHeader = new String[] { idText, "date Facture", "Montant", "Num. Commande"};
		initTableau(tableHeader);
		idLabel.setText(idText + ": ");
		chargerTableau(tableHeader.length, "SELECT * FROM facture ORDER BY " + idText);
	}

	public void setLabels() {
		jLabels[0].setText("Date Facture");
		jLabels[1].setText("Montant");
		jLabels[2].setText("Num. Commande");
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
			Util.afficherInfo("La date de facture doit etre une date !.", "Date invalide.");
			return false;
		}
		if(!txtFields[1].getText().matches("\\d*")) {
			Util.afficherInfo("Le numero de commande doit etre un nombre entier.", "Numero invalide.");
			return false;
		
		}
		return true;
	}
	
	public void remplirFacture() {
		fct.dateFacture = txtFields[0].getText();
		fct.montant = Double.parseDouble(txtFields[1].getText());
		fct.fk_numerocommande = Integer.parseInt(txtFields[2].getText());
	}
	
	public void EcouterBoutons() {
		super.EcouterBoutons();
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!verifier()) return;
				
				remplirFacture();
				String requete = "INSERT INTO Facture(datefacture,montant,numerocommande)"
						+ "VALUES('" + fct.dateFacture + "', '" + 
						fct.montant + "', '" + fct.fk_numerocommande + "')";
				if(DB.executeUpdate(requete) == -1) return;
				ajouterLigne(tableHeader.length, "SELECT * FROM facture ORDER BY numerofacture DESC LIMIT 1");
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
				remplirFacture();
				String requete = "UPDATE facture"
						+ " SET nom= '" +fct.dateFacture + "'"
						+ ", prenom= '" + fct.montant + "'"
						+ ", adresse= '" + fct.fk_numerocommande + "'"
						+ " WHERE numeroclient=" + id;
				
				if(DB.executeUpdate(requete) == -1) return;
				modifierLigne(new String[] {id, fct.dateFacture, Double.toString(fct.montant),Integer.toString(fct.fk_numerocommande)}, y);
			}
		});
	}
}
