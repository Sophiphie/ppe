import javax.swing.*;

import metier.Root;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame{
	private final Root root;
	private GridLayout layout = new GridLayout(4, 2);
	private JLabel lbMenu = new JLabel("Menu");
	private JButton buUsers = new JButton("Utilisateurs");
	private JButton buServices = new JButton("Services");
	private JButton buFermer = new JButton("Fermer");
	private JPanel pMenu = new JPanel();
	private JPanel pUsers = new JPanel();
	private JPanel pServices = new JPanel();
	private JPanel pFermer = new JPanel();
	private FlowLayout lMenu = new FlowLayout(FlowLayout.CENTER);

	/**
	 * Constructeur de Menu
	 * @param root
	 */
	public Menu(Root root){
		this.root = root;
		setSize(500,300);
		setVisible(true);
		pMenu.add(lbMenu);
		pMenu.setLayout(lMenu);
		pUsers.add(buUsers);
		pServices.add(buServices);
		pFermer.add(buFermer);
		setLayout(layout);
		add(pMenu);
		add(pUsers);
		add(pServices);
		add(pFermer);
		
		
		
		
		buUsers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Utilisateurs fusers = new Utilisateurs(Menu.this.root);
				Menu.this.dispose();		
			}
		});	
		
		
		buServices.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Services fservices = new Services(Menu.this.root);
				Menu.this.dispose();
				
			
			}
		});	
		
		
		buFermer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Menu.this.dispose();
			}
		});	
	
	}

	
}
