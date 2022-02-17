package interfaceGraphique;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public abstract class Panel extends JPanel {
	abstract public void setLabels(); // définir les labels des champs
	abstract public boolean verifier(); // verifier les champs
	
	public String idText; // nom de la clé primaire
	String[] tableHeader = null; // tableau contenant les noms des colonnes de notre tableau
	String tableName = "";  // nom de table au base de donnees
	
	
	JLabel dateFormat = new JLabel("(YYYY-MM-DD)"); // format des dates
	
	JPanel jPanel1 = new JPanel(), // Panel qui contient les champs
			jPanel2 = new JPanel(); // Panel qui contient le tableau
	
	/* Tous les classes contient au plus 4 champs, 
	si une classe contient moins que 4 on va utiliser setVisible pour la cacher */
	JLabel jLabels[] = new JLabel[4];
	JTextField txtFields[] = new JTextField[4];
	
	JLabel idLabel = new JLabel();
	JTextField idTF = new JTextField();
	
	// Tous les classes contient ces boutons
	JButton
	btnAjouter = new JButton("Ajouter"),
	btnModifier = new JButton("Modifier"),
	btnSupprimer = new JButton("Supprimer");
	JButton clearBtn = new JButton("X");
	
	// Tableau
	JTable tb = new JTable();
	JScrollPane sp = new JScrollPane();

    DefaultTableModel model = null;

	
	public Panel() {
		
		// initialiser les elements des deux tableaux jLabels[] et txtFields[]
		for(int i=0; i < jLabels.length; i++) {
			jLabels[i] = new JLabel();
			txtFields[i] = new JTextField();
		}
		
		setLabels();
		ajouterComposants();
		setLocationAndSize();
		EcouterBoutons();
	}

	public void setLocationAndSize() {
		clearBtn.setBounds(0,0,45,45);
		
		jPanel1.setBounds(0,0,300,570);
		jPanel2.setBounds(300,0,800,570);
		
		jLabels[0].setBounds(50, 30, 100, 30);
		dateFormat.setBounds(50, 45, 100, 30);
		jLabels[1].setBounds(50, 90, 100, 30);
		jLabels[2].setBounds(50, 150, 100, 30);
		jLabels[3].setBounds(50, 210, 100, 30);
		
		idLabel.setBounds(50, 400, 100, 30);
		idTF.setBounds(150, 400, 30, 40);
		
		txtFields[0].setBounds(150, 30, 150, 30);
		txtFields[1].setBounds(150, 90, 150, 30);
		txtFields[2].setBounds(150, 150, 150, 30);
		txtFields[3].setBounds(150, 210, 150, 30);
		
		btnAjouter.setBounds(50, 280, 200, 30);
		btnModifier.setBounds(50, 320, 200, 30);
		btnSupprimer.setBounds(50, 360, 200, 30);

		tb.setPreferredScrollableViewportSize(new Dimension(700, 450));
	}
	
	public void ajouterComposants() {
		jPanel1.add(clearBtn);
		jPanel1.add(jLabels[0]);
		jPanel1.add(dateFormat);
		dateFormat.setVisible(false);
		jPanel1.add(jLabels[1]);
		jPanel1.add(jLabels[2]);
		jPanel1.add(jLabels[3]); 
		jPanel1.add(txtFields[0]);
		jPanel1.add(txtFields[1]);
		jPanel1.add(txtFields[2]);
		jPanel1.add(txtFields[3]);
		jPanel1.add(btnAjouter);
		jPanel1.add(btnModifier);
		jPanel1.add(btnSupprimer);
		jPanel1.add(idLabel);
		jPanel1.add(idTF);
		jPanel1.setLayout(null);
		
		sp.setViewportView(tb); 
		jPanel2.add(sp);

		this.add(jPanel1);
		this.add(jPanel2);
		this.setLayout(null);
	}
	
	public void initTableau(String th[]) {
        tb.setModel(new DefaultTableModel( new Object [][] { }, th ) {
        	// Empecher l'utilisateur de modifier les cellules directement
                public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
        });
        
        tb.setCellSelectionEnabled(false);
		ecouterTableau(tb);
	}
	
	public void ecouterTableau(JTable table) {
		// Ecouter l'evenemnt click pour remplire les champs
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tb.setSelectionBackground(Color.red);
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				int columnCount = target.getColumnCount();
				
				// remplire le champ de la cle primaire
				idTF.setText(table.getModel().getValueAt(row, 0).toString());
				
				// remplire les autres champs
				String valeur = "";
				for(int i=1; i<columnCount; i++) {
					valeur = table.getModel().getValueAt(row, i).toString();
					txtFields[i-1].setText(valeur);
				}
			}
		});
	}
	
	public void chargerTableau(int tableauLength, String requete) {
        model = (DefaultTableModel) tb.getModel();
		try {
			ResultSet rs = DB.executeQuery(requete);
			while(rs.next()) {
		        Object[] ligne = new Object[tableauLength];
		        for(int i = 0; i < tableauLength; i++) {
		        	ligne[i] = rs.getString(i+1);
		        }
		        model.addRow(ligne);
			}
		} catch (Exception ex) {
			Util.afficherErreur("Erreur lors de chargement de tableau depuis la BDD: " + ex.getMessage());
		}
	}
	
	public void ajouterLigne(int tableauLength, String requete) {
        model = (DefaultTableModel) tb.getModel();
		try {
			ResultSet rs = DB.executeQuery(requete);
			rs.next();
	        Object[] ligne = new Object[tableauLength];
	        for(int i=0; i < tableauLength; i++) {
	        	ligne[i] = rs.getString(i+1);
			}
	        model.addRow(ligne);
	        clearTextFields();
		} catch (Exception ex) {
			Util.afficherErreur("Erreur lors de l'execution de requete d'ajout au BDD: " + ex.getMessage());
		}
	}
	
	public void modifierLigne(String[] objet, int ligne) {
        model = (DefaultTableModel) tb.getModel();
        for(int i=0; i < tb.getColumnCount(); i++ ) {
        	model.setValueAt(objet[i], ligne, i);       	
        }
	}
	
	public void donneesIncompleteFenetre() {
		Util.afficherInfo("Veuillez remplir tous les champs.", "Données incomplétes.");
	}
	
	public boolean verifierId(String id, String label) {
		if(id.isEmpty()) {
			Util.afficherInfo("Veuillez Entrer le " + idText);
			return false;
		}
		
		if(!id.matches("\\d+")) {
			Util.afficherInfo(label + " doit être un nombre entier.");
			return false;
		}
		
		return true;
	}
	
	public int getRow(String id) {
		int y = -1;
		for(int i=0; i<model.getRowCount(); i++) {
			String id_Tableau = (String) model.getValueAt(i, 0);
			if(id.equals(id_Tableau)) {
				y = i;
				break;
			}
		}
		return y;
	}
	
	public void EcouterBoutons() {
		btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				if(!verifierId(id , idText)) return;
				String requete = "DELETE FROM " + tableName + " WHERE " + idText + "=" + id;
				int y = getRow(id);
				if(y == -1) {
					Util.afficherInfo(idText + " Inexistant");
					return;
				}
				if(DB.executeUpdate(requete) == -1) return;
				model = (DefaultTableModel) tb.getModel();
				model.removeRow(y);
				idTF.setText("");
			}
		});
		
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTextFields();	
			}
		});
	}
	
	public void clearTextFields() {
		for(int i=0; i < 4; i++) {
			txtFields[i].setText("");
		}
		idTF.setText("");
	}
}
