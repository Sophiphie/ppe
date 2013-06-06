import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.event.*;

import metier.DataAccessException;
import metier.Root;
import metier.Utilisateur;


public class Formulaire extends JFrame{
	
	private Utilisateurs utilisateurs ;
	private Root root;
	private JLabel insc = new JLabel("Inscription");
	private JLabel fLogin = new JLabel("Login");
	private JTextField txLogin = new JTextField(10);
	private JLabel fMdp = new JLabel("Mot de passe");
	private JPasswordField txMdp = new JPasswordField(25);
	private JLabel fNum = new JLabel("Téléphone");
	private JTextField txNum = new JTextField(10);
	private JLabel fAdresse = new JLabel("Adresse");
	private JTextField txAdresse = new JTextField();
	private JLabel fAdresseSuite = new JLabel("AdresseSuite");
	private JTextField txAdresseSuite = new JTextField();
	private JLabel fCodeP = new JLabel("Code postal");
	private JTextField txCodeP = new JTextField(10);
	private JLabel fVille = new JLabel("Ville");
	private JTextField txVille = new JTextField(10);
	
	private JLabel fAdmin = new JLabel("Est Admin");
	private ButtonGroup adminButton = new ButtonGroup();
	private JRadioButton optYes = new JRadioButton("oui");
	private JRadioButton optNo = new JRadioButton("non");
	
	private JButton buEnregistrer = new JButton("Enregistrer");
	
	private JButton buAnnuler = new JButton("Annuler");
	

	final static int AJOUT = 0;
	final static int MODIFICATION = 1;
	
/***************************************************/	
	/** méthode changemode pour les formulaires d'ajout et de modif dans le menu utilisateur*/
	
	void changeMode(int mode)
	{
		switch(mode)
		{
		case AJOUT :
			
			txMdp.setEnabled(false);
			buAnnuler.setVisible(true);
			buEnregistrer.setVisible(true);
			buEnregistrer.addActionListener(getAjouterUser());
			break;
		case MODIFICATION :
			setTitle("Modification");
			setMsg("Modification de compte");
			txMdp.setEnabled(true);
			buEnregistrer.setVisible(true);
			
			buAnnuler.setVisible(true);
			break;
			
		}
	}
	

	
	public Formulaire(Root root, Utilisateurs utilisateurs){
		this.utilisateurs = utilisateurs;
		this.root = root;
		setTitle("Formulaire Inscription");
		setSize(500, 300);
		setVisible (true);
		
		adminButton.add(optYes);
		adminButton.add(optNo);
		
		JPanel titre = new JPanel();
		titre.setLayout(new FlowLayout(FlowLayout.CENTER));
		titre.add(insc);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,2));
		panel1.setBackground(Color.WHITE);
		panel1.add(fLogin);
		panel1.add(txLogin);
		panel1.add(fMdp);
		panel1.add(txMdp);
		panel1.add(fNum);
		panel1.add(txNum);
		panel1.add(fAdresse);
		panel1.add(txAdresse);
		panel1.add(fAdresseSuite);
		panel1.add(txAdresseSuite);
		panel1.add(fCodeP);
		panel1.add(txCodeP);
		panel1.add(fVille);
		panel1.add(txVille);
		panel1.add(fAdmin);
		panel1.add(optYes);
		panel1.add(optNo);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(buEnregistrer);
		panel2.add(buAnnuler);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(titre, BorderLayout.NORTH);
		getContentPane().add(panel1, BorderLayout.CENTER);
		getContentPane().add(panel2, BorderLayout.SOUTH);
		

		getAnnuler();
		changeMode(AJOUT);
	
	}

	private void getAnnuler() {
		
		buAnnuler.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Formulaire.this.dispose();
			}
		});
	}

	//TODO remettre setMsg à "inscription" après un mauvais ajout
	/** ajout d'un nouvel utilisateur */
	private ActionListener getAjouterUser()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				
				boolean ajouterUser = !txLogin.getText().equals("")
						&& !txNum.getText().equals("");
						
				Utilisateur utilisateur = (ajouterUser) ?
						new Utilisateur(root, txLogin.getText(), txMdp.getText(), txNum.getText(), txAdresse.getText(),
								txAdresseSuite.getText(), txCodeP.getText(), txVille.getText(), optYes.isSelected())
				        : null; 

						if (ajouterUser)
							
							{
								try 
								{
									
									root.save(utilisateur);
									root.createMDP(utilisateur);
								
									txLogin.setText("");
									txMdp.setText("");
									txNum.setText("");
									txAdresse.setText("");
									txAdresseSuite.setText("");
									txCodeP.setText("");
									txVille.setText("");
									optNo.setSelected(true);
								
									setMsg("Utilisateur ajouté");
									utilisateurs.addItem(utilisateur.getLogin());
								
							    }
								
								catch (DataAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

			                }
						else
						{
							setMsg("Veuillez remplir les champs login, mot de passe et Est Admin");
						}

		  }
	  };
   }
	
	// TODO faire la méthode d'enregistrement des modif
	
	/*private ActionListener getModifierUser()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				
				boolean modifierUser = !txLogin.getText().equals("")
						|| !txMdp.getText().equals("")
						|| !txNum.getText().equals("");
						
				
				
				Utilisateur utilisateur = (modifierUser) ?
						 Utilisateur(root, txLogin.getText(), txMdp.getText(), txNum.getText(), txAdresse.getText(),
								txAdresseSuite.getText(), txCodeP.getText(), txVille.getText(), optYes.isSelected())
				        : null; 

						if (modifierUser)
							
							{
								try 
								{
									root.save(utilisateur);
								
									setMsg("Utilisateur ajouté");
									utilisateurs.addItem(utilisateur.getLogin());
								
							    }
								
								catch (DataAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

			                }
						else
						{
							System.out.println("ERREUR");
						}

		  }
	  };
   }*/
	
	
	
	
	
	private void setMsg(String msg)
	{
		
		insc.setText(msg);
	}

	public void setUtilisateur(Utilisateur u) 
	{
		// TODO Auto-generated method stub
		txLogin.setText(u.getLogin());
		txMdp.setText(u.getMdp1());
		txNum.setText(u.getTelephone());
		txAdresse.setText(u.getAdr1());
		txAdresseSuite.setText(u.getAdr2());
		txCodeP.setText(u.getCp());
		txVille.setText(u.getVille());
		if (u.getEstAdmin())
			optYes.setSelected(true);
		else
			optNo.setSelected(true);	
	}
}	