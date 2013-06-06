import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

import metier.DataAccessException;
import metier.Root;
import metier.Service;

public class FormulaireS extends JFrame implements KeyListener{
	
	private Root root;
	private JLabel service = new JLabel("Nouveau Service");
	private JLabel fLibelle = new JLabel("Libellé");
	private JTextField txLibelle = new JTextField(10); 
	
	private JButton buEnregistrer = new JButton("Enregistrer");
	private JButton buSupprimer = new JButton("Supprimer");
	private JButton buAnnuler = new JButton("Annuler");
	
	
	final static int AJOUT = 0;
	final static int MODIFICATION = 1;
	
/***************************************************/	
	/* métohde changemode pour les formulaires d'ajout et de modif dans le menu utilisateur*/
	
	void changeMode(int mode)
	{
		switch(mode)
		{
		case AJOUT :
			
			buAnnuler.setVisible(true);
			buEnregistrer.setVisible(true);
			buSupprimer.setVisible(false);
			break;
		case MODIFICATION :
			
			buEnregistrer.setVisible(true);
			buAnnuler.setVisible(true);
			buSupprimer.setVisible(true);
			
			break;
			
		}
	}
	
	public FormulaireS(Root root){
		if (root == null)
			System.out.println("ACHTUNG ROOT IS NULL");

		this.root = root;
		addKeyListener(this);
		setTitle("Formulaire Inscription");
		setSize(400, 300);
		setVisible (true);
		
		JPanel form1 = new JPanel();
		form1.setLayout(new FlowLayout(FlowLayout.CENTER));
		form1.add(service);
		
		JPanel form2 = new JPanel();
		form2.setLayout(new GridLayout(5,2));
		form2.add(fLibelle);
		form2.add(txLibelle);
		
		
		JPanel form3 = new JPanel();
		form3.setLayout(new FlowLayout());
		form3.add(buEnregistrer);
		form3.add(buSupprimer);
		form3.add(buAnnuler);
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(form1, BorderLayout.NORTH);
		getContentPane().add(form2, BorderLayout.CENTER);
		getContentPane().add(form3, BorderLayout.SOUTH);
		
		
		getAnnuler();
		buEnregistrer.addActionListener(getAjouterService());
		getSupprimer();
		changeMode(AJOUT);
		
		
	}
	
	private void getAnnuler() {
		
		buAnnuler.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FormulaireS.this.dispose();
			}
		});
}

	
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
						System.out.println("Service ajouté");
						
					}	
					
					catch (DataAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else
					setMsg("Veuillez remplir le champ");
			}
		};
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
	
	private void getSupprimer(){
		buSupprimer.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane confirm;
				confirm = new JOptionPane();
				confirm.showConfirmDialog(null, "Voulez vous vraiment supprimer ?", "confirmer", JOptionPane.YES_NO_OPTION);
			
				/* si "oui" --> requête de suppression de données
				 * si "non" --> retour à la fenêtre, aucune requête*/
				 
			}
		});
	}
	
	
	private void setMsg(String msg)
	{
		System.out.println(msg);
		service.setText(msg);
	}

}