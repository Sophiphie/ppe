import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

import metier.DataAccessException;
import metier.Root;
import metier.Service;
import metier.Utilisateur;

public class FormulaireS extends JFrame implements KeyListener
{
	private Services services;
	private Root root;
	private JLabel service = new JLabel("Nouveau Service");
	private JLabel fLibelle = new JLabel("Libellé");
	private JTextField txLibelle = new JTextField(10);
	private JLabel fCle = new JLabel("Clé");
	private JTextField txCle = new JTextField(10);
	
	private JButton buEnregistrer = new JButton("Enregistrer");
	private JButton buAnnuler = new JButton("Annuler");
	
	
	final static int AJOUT = 0;
	final static int MODIFICATION = 1;
	
	/**
	 * méthode de changement de la disposition du formulaire selon le cas ajout ou modification
	 * @param mode
	 */
	void changeMode(int mode)
	{
		switch(mode)
		{
		case AJOUT :
			
			buAnnuler.setVisible(true);
			buEnregistrer.setVisible(true);
			buEnregistrer.addActionListener(getAjouterService());
			break;
			
		case MODIFICATION :
			
			setTitle("Modification");
			buEnregistrer.setVisible(true);
			buAnnuler.setVisible(true);
			break;
			
		}
	}
	
	/**
	 * constructeur de la classe FormulaireS
	 * @param root
	 */
	public FormulaireS(Root root, Services services)
	{
		this.services = services;
		this.root = root;
		addKeyListener(this);
		setTitle("Formulaire services");
		setSize(400, 300);
		setVisible (true);
		
		JPanel form1 = new JPanel();
		form1.setLayout(new FlowLayout(FlowLayout.CENTER));
		form1.add(service);
		
		JPanel form2 = new JPanel();
		form2.setLayout(new GridLayout(5,2));
		form2.add(fLibelle);
		form2.add(txLibelle);
		form2.add(fCle);
		form2.add(txCle);
		
		JPanel form3 = new JPanel();
		form3.setLayout(new FlowLayout());
		form3.add(buEnregistrer);
		
		form3.add(buAnnuler);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(form1, BorderLayout.NORTH);
		getContentPane().add(form2, BorderLayout.CENTER);
		getContentPane().add(form3, BorderLayout.SOUTH);
		
		
		getAnnuler();
		
		changeMode(AJOUT);
	}
	
	/**
	 * fermeture du formulaire et retour au menu utilisateur
	 */
	private void getAnnuler() {
		
		buAnnuler.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FormulaireS.this.dispose();
			}
		});
}

	/**
	 * methode pour ajouter un service dans la BDD
	 * @return
	 */
	private ActionListener getAjouterService()
	{
		return new ActionListener()
		{	
			public void actionPerformed(ActionEvent arg0)
			{
				boolean ajouterService = !txLibelle.getText().equals("");
						
				String nomService = txLibelle.getText();
				
						
				if (ajouterService)
				{
					Service service = new Service (root, nomService); 
					System.out.println(nomService);
					
					
					
					try
					{
						root.save(service);
						txLibelle.setText("");
						setMsg("Service ajouté");
						services.addItem(service.getNomService());
					}	
					
					catch (DataAccessException e) 
					{	
						e.printStackTrace();
					}
				}
				
				else
					setMsg("Veuillez remplir le champ");
			}
		};
	}
	
	
	public void setService(Service s) 
	{
		txLibelle.setText(s.getNomService());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * permet le changement du label titre du formulaire
	 * @param msg
	 */
	private void setMsg(String msg)
	{
		System.out.println(msg);
		service.setText(msg);
	}

}