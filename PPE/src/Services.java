import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import metier.Cle;
import metier.DataAccessException;
import metier.Root;
import metier.Service;
import metier.Utilisateur;


public class Services extends JFrame{
	
	protected static final int AJOUT = 0;
	protected static final int MODIFICATION = 1;
	
	final private Root root;
	private JLabel lbMenuS = new JLabel("Services: ");
	private JComboBox<String> cComboS;
	private JTextField tLibelle = new JTextField(10);
	
	
	/*champs du bas*/
	private JButton buModifier = new JButton("Modifier");
	private JButton buAjouter = new JButton("Ajouter");
	private JButton buSupprimer = new JButton("Supprimer");
	private JButton buRetour = new JButton("Retour");
	

	/**
	 * constructeur de la classe Services
	 * @param root
	 */
	public Services(Root root){
		
		this.root = root;
		
		rafraichirListe();
		
		setTitle("Services");
		setSize(500, 300);
		setVisible (true);
		
		JPanel titre = new JPanel();
		titre.setLayout(new FlowLayout(FlowLayout.CENTER));
		titre.add(lbMenuS);
		titre.add(cComboS);
		
		
		JPanel gauche = new JPanel();
		gauche.setLayout(new GridLayout(9,2));
		gauche.add(tLibelle);
		
		/*tLibelle.setEnabled(false);*/
		
		JPanel centre = new JPanel();
		centre.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		
		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		bas.add(buModifier);
		bas.add(buAjouter);
		bas.add(buSupprimer);
		bas.add(buRetour);
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(titre, BorderLayout.NORTH);
		getContentPane().add(gauche, BorderLayout.WEST);
		getContentPane().add(bas, BorderLayout.SOUTH);
		getContentPane().add(centre, BorderLayout.CENTER);
		
		
		initialize();
		
		getRetour();
		getAjouter();
		buSupprimer.addActionListener(getSupprimer());
		getModifier();
		
	
	}
	
	/**
	 * recuperation des libelles dans la comboBox
	 * @return
	 */
	private String[] importServices() {
			
			try 
			{
				String[] serviceString = new String[root.getNbServices()];			
				for (int i = 0; i<= root.getNbServices() -1; i++)
					serviceString[i] = root.getServiceByIndex(i).getNomService();
				return serviceString;
			}
			catch (DataAccessException e) 
			{
				
				return null;
			}
		}
	
	
	/**
	 * affiche le libelle du service
	 */
	private void initialize() {
		
		cComboS.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try 
				{	
					Service s = root.getServiceByIndex(cComboS.getSelectedIndex());
					System.out.println(s.getNomService());
					
					tLibelle.setText(s.getNomService());
				 } 
				catch (DataAccessException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
}
	
	
	
	/**
	 * fermeture du menu service et retour sur le menu principal
	 */
	private void getRetour(){
		buRetour.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				Menu fmenu = new Menu(Services.this.root);
				Services.this.dispose();
				
			}
		});
	}
	
	/**
	 * ouverture du formulaire d'ajout vide par le bouton ajouter
	 */
	private void getAjouter(){
		buAjouter.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				FormulaireS formSAjout = new FormulaireS(root, Services.this);
				formSAjout.changeMode(AJOUT);
			}
		});
	}
	
	/**
	 * affiche les infos du service dans le formulaire de modif
	 */
	private void getModifier(){
		buModifier.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				FormulaireS formSModif = new FormulaireS(root, Services.this);
				formSModif.changeMode(MODIFICATION);
				
				try
				{
					Service s = root.getServiceByIndex(cComboS.getSelectedIndex());
					formSModif.setService(s);
				}
				catch (DataAccessException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * suppression du service selectionne par le bouton supprimer
	 * @return
	 */
	private ActionListener getSupprimer()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				try 
				{
					Service s = root.getServiceByIndex(cComboS.getSelectedIndex());
            
					int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer " + s.getNomService() + "?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(option == JOptionPane.OK_OPTION)
						root.delete(s);
	 
				}
				catch (DataAccessException e1) 
				{
					e1.printStackTrace();
				}
	 
			}
		};
	}
	
	
	/**
	 * rafraichit les noms de la comboBox à l'ouverture de la fenêtre services
	 */
	public void rafraichirListe() 
	{
		cComboS  = new JComboBox<String>(importServices());	
		
	}
	
	

	/**
	 * ajoute dans la combobox le nom d'un service après l'ajout
	 * @param s
	 */
	public void addItem(String s)
	{
		cComboS.addItem(s);
	}	

//TODO pouvoir ajouter une clé avec un service
//TODO pouvoir afficher les clés d'un user
//TODO pouvoir afficher les clés d'un service
}
