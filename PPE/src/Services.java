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

import metier.Root;


public class Services extends JFrame{
	
	protected static final int AJOUT = 0;
	protected static final int MODIFICATION = 1;
	
	final private Root root;
	/* tableau des valeurs de la comboBox */
	String[] petStrings = { " ", "Clé Wi-FI", "Mot de passe application"};
	
	private JTextArea listeService = new JTextArea();
	
	private JLabel lbMenuS = new JLabel("Menu Services");
	private JComboBox cComboS = new JComboBox(petStrings);
	private JTextField tChoisiS = new JTextField (10);
	
	/*champs du bas*/
	private JButton buModifier = new JButton("Modifier");
	private JButton buAjouter = new JButton("Ajouter");
	private JButton buRetour = new JButton("Retour");
	
	
	private JTextArea tListeS = new JTextArea();

	public Services(Root root){
		this.root = root;
		setTitle("Services");
		setSize(500, 300);
		setVisible (true);
		
		JPanel titre = new JPanel();
		titre.setLayout(new FlowLayout(FlowLayout.CENTER));
		titre.add(lbMenuS);
		
		JPanel gauche = new JPanel();
		gauche.setLayout(new GridLayout(9,2));
		gauche.add(cComboS);
		gauche.add(listeService);
		listeService.setColumns(10);
		listeService.setRows(10);
		listeService.setVisible(false);
		
		JPanel centre = new JPanel();
		centre.setLayout(new FlowLayout(FlowLayout.CENTER));
		centre.add(tListeS);
		tListeS.setColumns(10);
		tListeS.setRows(10);
		
		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		bas.add(buModifier);
		bas.add(buAjouter);
		bas.add(buRetour);
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(titre, BorderLayout.NORTH);
		getContentPane().add(gauche, BorderLayout.WEST);
		getContentPane().add(bas, BorderLayout.SOUTH);
		getContentPane().add(centre, BorderLayout.CENTER);
		
		
		initialize();
		getRetour();
		getAjouter();
		getModifier();
		
	
	}
	
	private void initialize() {
		
		cComboS.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				listeService.setVisible(true);
				listeService.setText((String)"bob");
			}
		});
}

	private void getRetour(){
		buRetour.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				Services.this.dispose();
				Menu fmenu = new Menu(root);
			}
		});
	}
	
	private void getAjouter(){
		buAjouter.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				FormulaireS formAjout = new FormulaireS(root);
				formAjout.changeMode(AJOUT);
			}
		});
	}
	
	private void getModifier(){
		buModifier.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				FormulaireS formAjout = new FormulaireS(root);
				formAjout.changeMode(MODIFICATION);
			}
		});
	}
	
	


}
