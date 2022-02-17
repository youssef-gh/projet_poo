package interfaceGraphique;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entitees.Client;
public class InterfaceClient extends Panel {
	Client cl = new Client();
	
	public InterfaceClient() {
		super();
		tableName = "client";
		idText = "numeroclient";
		tableHeader = new String[] { idText, "Nom", "Prenom", "Adresse", "Telephone"};
		initTableau(tableHeader);
		idLabel.setText(idText + ": ");
		chargerTableau(tableHeader.length, "SELECT * FROM client ORDER BY " + idText);
	}

	public void setLabels() {
		jLabels[0].setText("Nom");
		jLabels[1].setText("Prénom");
		jLabels[2].setText("Adresse");
		jLabels[3].setText("Téléphone");
	}
	// verification de contenu des champs, est-ce qu'elles sont non vides, respecte
	public boolean verifier() {
		if( txtFields[0].getText().isEmpty() || 
			txtFields[1].getText().isEmpty() ||
			txtFields[2].getText().isEmpty() ||
			txtFields[3].getText().isEmpty()
	) {
    	donneesIncompleteFenetre();
    	return false;
		}
		
		if(!txtFields[3].getText().matches("\\d{10}")) { // Si le telephone contenir quelque chose autre que des nombres.
			Util.afficherInfo("Telephone doit contenir 10 nombres.", "Nombre de telephone invalide.");
			return false;
		}
		return true;
	}
	
	public void remplirClient() {
		cl.nom = txtFields[0].getText();
		cl.prenom = txtFields[1].getText();
		cl.adresse = txtFields[2].getText();
		cl.telephone = txtFields[3].getText();
	}
	
	public void EcouterBoutons() {
		super.EcouterBoutons();
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!verifier()) return;
				
				remplirClient();
				// Remplir les information de client.
				String requete = "INSERT INTO Client(nom,prenom,adresse,telephone)"
						+ "VALUES('" + cl.nom + "', '" + cl.prenom + "', '" + 
						cl.adresse + "', '" + cl.telephone + "')";
				if(DB.executeUpdate(requete) == -1) return;

				ajouterLigne(tableHeader.length, "SELECT * FROM client ORDER BY numeroclient DESC LIMIT 1");
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
				remplirClient();
				String requete = "UPDATE client"
						+ " SET nom= '" + cl.nom + "'"
						+ ", prenom= '" + cl.prenom + "'"
						+ ", adresse= '" + cl.adresse + "'"
						+ ", telephone= '" + cl.telephone + "'"
						+ " WHERE numeroclient=" + id;
				
				if(DB.executeUpdate(requete) == -1) return;

				modifierLigne(new String[] {id, cl.nom, cl.prenom, cl.adresse, cl.telephone}, y);
			}
		});
	}
}
