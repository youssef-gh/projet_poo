package interfaceGraphique;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame implements ActionListener {
    JLabel utilisateurLabel = new JLabel("Utilisateur");
    JLabel mdpLabel = new JLabel("Mot de passe");
    JTextField utilisateurTextField = new JTextField();
    JPasswordField mdpTextField = new JPasswordField();
    JButton btn = new JButton("Se connecter");
    
    public LoginFrame() {
       //Calling methods inside constructor.
        setLocationAndSize();
        ajouterComposants();
        btn.addActionListener(this);
        this.setLayout(null);
        this.setTitle("Connexion");
        this.setBounds(10,10,370,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void setLocationAndSize() {
    	utilisateurLabel.setBounds(50,150,100,30);
    	mdpLabel.setBounds(50,220,100,30);
    	utilisateurTextField.setBounds(150,150,150,30);
        mdpTextField.setBounds(150,220,150,30);
        btn.setBounds(75,300,200,30);
    }
    
    public void ajouterComposants() {
        this.add(utilisateurLabel);
        this.add(mdpLabel);
        this.add(utilisateurTextField);
        this.add(mdpTextField);
        this.add(btn);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            String loginText;
            String pwdText;
            loginText = utilisateurTextField.getText();
            pwdText = new String(mdpTextField.getPassword());
            try {
            	ResultSet rs = DB.executeQuery("select login, pwd from users"); 
            	while(rs.next()){
            		if(loginText.equals(rs.getString(1)) && pwdText.equals(rs.getString(2))) {
                        Util.afficherInfo("Connecté avec succés!");
                        this.setVisible(false);
            			new MainFrame().afficher();
            			return;
            		}
            	}
            	Util.afficherInfo("Login ou mot de passe Invalide.");
            }
            catch(SQLException ex) {
            	Util.afficherErreur("Erreur lors de connexion a la BDD de drive: " + ex.getMessage());
                System.exit(1);
            }
        }
    }
}
