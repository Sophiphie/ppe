import java.io.IOException;
import java.sql.SQLException;

import metier.DataAccessException;
import metier.Root;





public class Main {
	
	
	public static void main(String[] args) throws IOException, DataAccessException 
	{
		Root root;
		try {
			root = new Root();
			
			/*new Utilisateurs(root);*/
			Fenetre f = new Fenetre(root);
			f.affiche();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
