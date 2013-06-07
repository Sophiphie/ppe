import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;

import metier.Cle;
import metier.DataAccessException;
import metier.Root;
import metier.Service;
import metier.Utilisateur;
import metier.Cle;

public class Utilisateurs extends JFrame {
	
	protected static final int AJOUT = 0;
	protected static final int MODIFICATION = 1;
	
	final private Root root;  
	
	private JLabel lbMenu = new JLabel("Utilisateurs: ");
	/*champs du haut*/
	private JComboBox<String> cCombo;
	
	/*champs du bas*/
	private JButton buModifier = new JButton("Modifier");
	private JButton buAjouter = new JButton("Ajouter");
	private JButton buSupprimer = new JButton("Supprimer");
	private JButton buRetour = new JButton("Retour");
	
	/*champs centre*/
	private JTextField tLogin = new JTextField();
	private JPasswordField tMdp = new JPasswordField();
	private JTextField tNumero = new JTextField();
	private JTextField tAdresse1 = new JTextField();
	private JTextField tAdresse2 = new JTextField();
	private JTextField tCP = new JTextField();
	private JTextField tVille = new JTextField();
	private ButtonGroup adminButton = new ButtonGroup();
	private JRadioButton tAdmin = new JRadioButton("Est Admin");
	private JRadioButton tNonAdmin = new JRadioButton("N'est pas Admin");
	private JLabel lServices = new JLabel("Services");
	private JTextArea tServices = new JTextArea();
	
	
	/**
	 * Constructeur de la classe Utilisateurs
	 * @param root
	 */
	public Utilisateurs(Root root){
		
		this.root = root;
		rafraichirListe();
		
		setTitle("Menu Utilisateurs");
		setSize(500, 300);
		setVisible (true);
		
		JPanel titre = new JPanel();
		titre.setLayout(new FlowLayout(FlowLayout.CENTER));
		titre.add(lbMenu);
		titre.add(cCombo);
		
		adminButton.add(tAdmin);
		adminButton.add(tNonAdmin); 
		
		JPanel hautGauche = new JPanel();
		hautGauche.setLayout(new GridLayout(9, 2));
		hautGauche.add(new JLabel("Login"));
		hautGauche.add(tLogin);
		hautGauche.add(new JLabel("Mot de passe"));
		hautGauche.add(tMdp);
		hautGauche.add(new JLabel("Téléphone"));
		hautGauche.add(tNumero);
		hautGauche.add(new JLabel("Adresse"));
		hautGauche.add(tAdresse1);
		hautGauche.add(new JLabel("Adresse suite"));
		hautGauche.add(tAdresse2);
		hautGauche.add(new JLabel("Code Postal"));
		hautGauche.add(tCP);
		hautGauche.add(new JLabel("Ville"));
		hautGauche.add(tVille);
		hautGauche.add(tAdmin);
		hautGauche.add(tNonAdmin);
		tLogin.setEnabled(false);
		tMdp.setEnabled(false);
		tNumero.setEnabled(false);
		tAdresse1.setEnabled(false);
		tAdresse2.setEnabled(false);
		tCP.setEnabled(false);
		tVille.setEnabled(false);
		tAdmin.setEnabled(false);
		tNonAdmin.setEnabled(false);
		
		
		JPanel hautDroite = new JPanel();
		hautDroite.setLayout(new GridLayout(9, 2));
		hautDroite.add(lServices);
		hautDroite.add(tServices);
		tServices.setColumns(1);
		tServices.setColumns(10);
		tServices.setEnabled(false);
		
		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		bas.add(buModifier);
		bas.add(buAjouter);
		bas.add(buSupprimer);
		bas.add(buRetour);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(titre, BorderLayout.NORTH);
		getContentPane().add(hautGauche, BorderLayout.WEST);
		getContentPane().add(hautDroite, BorderLayout.EAST);
		getContentPane().add(bas, BorderLayout.SOUTH);
		
		getSelectionne();
		getRetour();
		getAjouter();
		getModifier();
		buSupprimer.addActionListener(getSupprimer());

	}

	/**
	 * récupération des logins dans la comboBox
	 * @return
	 */
	private String[] importUsers() {
		
		try 
		{
			String[] userString = new String[root.getNbUtilisateurs()];			
			for (int i = 0; i<= root.getNbUtilisateurs() -1; i++)
				userString[i] = root.getUtilisateurByIndex(i).getLogin();
			return userString;
		}
		catch (DataAccessException e) 
		{
			
			return null;
		}
	}
	
	/**
	 * affiche les infos de l'utilisateur selectionne
	 */
	private void getSelectionne() {
		
		cCombo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try 
				{	
					Utilisateur u = root.getUtilisateurByIndex(cCombo.getSelectedIndex());
					int numU = u.getNumUtilisateur();
					System.out.println(u);
					System.out.println(numU);
					//TODO remplacer les zones de textes par des labels
					tLogin.setText(u.getLogin());
					tMdp.setText(u.getMdp1());
					tNumero.setText(u.getTelephone());
					tAdresse1.setText(u.getAdr1());
					tAdresse2.setText(u.getAdr2());
					tCP.setText(u.getCp());
					tVille.setText(u.getVille());
					
					if (u.getEstAdmin())
						tAdmin.setSelected(true);
					else
						tNonAdmin.setSelected(true);

					Cle c = u.getCleByIndex(numU);
					System.out.println(c.getNumeroCle());
					/*tServices.setText(c.getNumeroCle());*/
					
				 } 
				catch (DataAccessException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
}
	/**
	 * fermeture et retour a la fenetre Menu
	 */
	private void getRetour(){
		buRetour.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				Menu fmenu = new Menu(Utilisateurs.this.root);
				Utilisateurs.this.dispose();
			}
		});
	}
	
	/**
	 * affiche un formulaire vide pour un ajout
	 */
	private void getAjouter(){
		buAjouter.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				Formulaire formAjout = new Formulaire(root, Utilisateurs.this);
				formAjout.changeMode(AJOUT);
				//TODO rendre l'ouverture modale
				//formAjout.setModalExclusionType(Dialog.ModalExclusionType.);
			}
		});
	}
	
	/**
	 * affiche les infos de l'utilisateur dans le formulaire de modif
	 */
	private void getModifier(){
		buModifier.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				Formulaire formModif = new Formulaire(root, Utilisateurs.this);
				formModif.changeMode(MODIFICATION);
				
				try 
				{	
					
					Utilisateur u = root.getUtilisateurByIndex(cCombo.getSelectedIndex());
					formModif.setUtilisateur (u);
	
				 } 
				catch (DataAccessException e1) 
				{
					e1.printStackTrace();
				}
				
			}
				
		});
	}
	
	/**
	 * suppression de l'utilisateur selectionne
	 * @return
	 */
	private ActionListener getSupprimer()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				try 
				{
					Utilisateur u = root.getUtilisateurByIndex(cCombo.getSelectedIndex());
            
					int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer " + u.getLogin() + "?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(option == JOptionPane.OK_OPTION)
						root.delete(u);
	 
				}
				catch (DataAccessException e1) 
				{
					e1.printStackTrace();
				}
	 
			}
		};
	}

	/**
	 * met a jour la liste des libelle dans la comboBox
	 */
	public void rafraichirListe() {
		cCombo  = new JComboBox<String>(importUsers());
		
	}

	/**
	 * ajoute dans la combobox le login d'un nouvel utilisateur après l'ajout
	 * @param s
	 */
	public void addItem(String s)
	{
		cCombo.addItem(s);
	}	
}
