package interfaceGraphique;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	static Statement stmt = null;
	
	public static void demarrer() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/poo_db","root","");
        	stmt = con.createStatement();
        } catch(ClassNotFoundException ex) {
        	Util.afficherErreur("Erreur lors de chargement de drive: " + ex.getMessage());
            System.exit(1);
        }
        catch(SQLException ex) {
        	Util.afficherErreur(ex.getMessage());
            System.exit(1);
        }
	}
	
	public static int executeUpdate(String req) {
		try {
			return stmt.executeUpdate(req);
		} catch (SQLException e) {
			Util.afficherErreur("Erreur lors de l'execution de la requete " + e.getMessage());
			return -1;
		}
	}
	
	public static ResultSet executeQuery(String req) {
		try {
			return stmt.executeQuery(req);
		} catch (SQLException e) {
			Util.afficherErreur("Erreur lors de l'execution de la commande SQL " + e.getMessage());
			return null;
		}
	}
}
