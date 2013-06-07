import java.awt.*;
import java.awt.event.*;

import java.sql.*;
import java.util.UUID;


import javax.swing.*;

import metier.DataAccessException;
import metier.Root;

import db.Connect;


public class Fenetre extends JFrame
{	
	
	private Connect connect;
	private Root root;
	private GridLayout layout = new GridLayout(4,2);
	private JLabel lbTitre = new JLabel("Connexion Admin");
	private JLabel lbLogin = new JLabel("Login");
	private JTextField tfLogin = new JTextField (20);
	private JLabel lbMdp = new JLabel("Mot de passe");
	private JPasswordField tfMdp = new JPasswordField(16);
	private JButton buConnect = new JButton("OK");
	private JButton buAnnuler = new JButton("Annuler");
	
	private JPanel pTitre = new JPanel();
	private JPanel pLogin = new JPanel();
	private JPanel pMdp = new JPanel();
	private JPanel pConnect = new JPanel();
	
	
	private FlowLayout lTitre = new FlowLayout(FlowLayout.CENTER);
	private FlowLayout lLogin = new FlowLayout(FlowLayout.CENTER);
	private FlowLayout lMdp = new FlowLayout(FlowLayout.CENTER);
	
	/**
	 * Constructeur de Fenetre
	 * @param root
	 */
	public Fenetre(Root root)
	{
		this.root = root;
		setSize(500,300);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(true);
		
		pTitre.add(lbTitre);
		pTitre.setLayout(lTitre);
		pLogin.add(lbLogin);
		pLogin.add(tfLogin);
		pLogin.setLayout(lLogin);
		pMdp.add(lbMdp);
		pMdp.add(tfMdp);
		pMdp.setLayout(lMdp);
		pConnect.add(buConnect);
		pConnect.add(buAnnuler);
		setLayout(layout);
		
		tfLogin.setToolTipText("Saisissez votre login");
		tfMdp.setToolTipText("Saisissez votre mot de passe");
		
		add(pTitre);
		add(pLogin);
		add(pMdp);
		add(pConnect);
		
		buConnect.addActionListener(getOkAction());
		buAnnuler.addActionListener(getQuitterAction());
		
	}
	
	/**
	 * Fermeture de la fenetre
	 * @return
	 */
	private ActionListener getQuitterAction()
	{
		return new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		};
	}
	
	/**
	 * action de connexion, verifie le login, le mdp et si l'utilisateur est admin
	 * si les 3 conditions: ouverture du menu
	 * sinon messages d'erreur
	 * @return
	 */
	private ActionListener getOkAction()
	{
		return new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				boolean connect = !tfLogin.getText().equals("")
						&& !tfMdp.getText().equals("");
				String login = tfLogin.getText();
				String mdp = tfMdp.getText();
				
				if(connect)
				{
					try 
					{
						if (root.AdminConnexion(login, mdp) != null)
						{
							Menu fmenu = new Menu(root);
							Fenetre.this.dispose();
						}
						else
							JOptionPane.showMessageDialog(null, "Autorisation refusée. Recommencez", "Erreur", JOptionPane.ERROR_MESSAGE);
						
					}
					catch(DataAccessException e) 
					{
					e.printStackTrace();
					}
						
				}
				else
					JOptionPane.showMessageDialog(null, "Veuillez entrer votre login et votre mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);
					
			}
		};
	}
	
	/**
	 * fonction de visibilité de la fenetre
	 */
	public void affiche()
	{
		setVisible(true);
	}
	
}
