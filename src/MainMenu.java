import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenu extends JFrame{

	DbConnection db;
	public MainMenu(DbConnection db, User user) {
		// TODO Auto-generated constructor stub
		this.db = db;
		
		if (user.getUserRole().equals("Admin")) {
			
			JFrame adminFrame = new JFrame();
			JMenuBar menubar = new JMenuBar();
			JMenu profile = new JMenu("Profile");
			JMenu manage = new JMenu("Manage");
			JMenuItem editProfile = new JMenuItem("Edit Profile");
			editProfile.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					adminFrame.getContentPane().removeAll();
					adminFrame.add(new EditProfile(db, user));
				}
			});
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new Login(db);
					adminFrame.dispose();
				}
			});
			JMenuItem manageGame = new JMenuItem("Manage Game");
			manageGame.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					adminFrame.getContentPane().removeAll();
					adminFrame.add(new ManageGame(db));
				}
			});
			JLabel body;
			
			profile.add(editProfile);
			profile.add(exit);
			
			manage.add(manageGame);
			
			menubar.add(profile);
			menubar.add(manage);
			
			
			
			
			
			String bodyText = String.format("Welcome to DoPe Game Store, %s", user.getUserName());
			
			body = new JLabel(bodyText, SwingConstants.CENTER);
			body.setFont(new Font("SansSerif", Font.PLAIN, 20));
			
			adminFrame.add(body, BorderLayout.CENTER);
			adminFrame.setJMenuBar(menubar);
			
			adminFrame.setVisible(true);
			adminFrame.setResizable(false);
			adminFrame.setLocationRelativeTo(null);
			adminFrame.setSize(700,600);
			adminFrame.setTitle("DoPe Game Store");
			adminFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		
		} else if (user.getUserRole().equals("Customer")) {
			JFrame custFrame = new JFrame();
			JMenuBar menubar = new JMenuBar();
			JMenu profile = new JMenu("Profile");
			JMenu transaction = new JMenu("Transaction");
			JMenuItem editProfile = new JMenuItem("Edit Profile");
			editProfile.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					custFrame.getContentPane().removeAll();
					custFrame.add(new EditProfile(db, user));	
				}
			});
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new Login(db);
					custFrame.dispose();
				}
			});
			JMenuItem buyGame = new JMenuItem("Buy Game");
			buyGame.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					custFrame.getContentPane().removeAll();
					custFrame.add(new BuyGameForm(db, user));
				}
			});
			JMenuItem viewHistory = new JMenuItem("View Transaction History");
			viewHistory.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					custFrame.getContentPane().removeAll();
					custFrame.add(new TransactionForm(db));
				}
			});
			JLabel body;
			
			profile.add(editProfile);			
			profile.add(exit);
			
			
			transaction.add(buyGame);
			transaction.add(viewHistory);
			
			menubar.add(profile);
			menubar.add(transaction);
			
			
			
			
			String bodyText = String.format("Welcome to DoPe Game Store, %s", user.getUserName());
			
			body = new JLabel(bodyText, SwingConstants.CENTER);
			body.setFont(new Font("SansSerif", Font.PLAIN, 20));
			
			custFrame.add(body, BorderLayout.CENTER);
			custFrame.setJMenuBar(menubar);
			
			custFrame.setVisible(true);
			custFrame.setResizable(false);
			custFrame.setLocationRelativeTo(null);
			custFrame.setSize(900,900);
			custFrame.setTitle("DoPe Game Store");
			custFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
	}

}
