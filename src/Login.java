import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login  extends JFrame implements ActionListener {

	JLabel headerLabel, emailLabel, passwordLabel;
	JTextField emailField;
	JPasswordField passwordField;
	JButton	login, register;
	JPanel headerPanel, bodyPanel, footerPanel;
	DbConnection db;
	ResultSet userdata;
	User loggedUser;
	
	public Login(DbConnection db) {
		// TODO Auto-generated constructor stub
		this.db = db;
		
		//header
		headerLabel = new JLabel("Login Form");
		headerLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		
		headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10,0,10,0));
		headerPanel.add(headerLabel);
		
		//body
		emailLabel = new JLabel("Email");
		emailField = new JTextField();
		
		passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField();
		
		bodyPanel = new JPanel(new GridLayout(2,2,0,10));
		bodyPanel.setBorder(new EmptyBorder(10,5,15,5));
		bodyPanel.add(emailLabel);
		bodyPanel.add(emailField);
		bodyPanel.add(passwordLabel);
		bodyPanel.add(passwordField);
		
		//footer
		login = new JButton("Login");
		login.addActionListener(this);
		register = new JButton("Sign Up Here");
		register.addActionListener(this);
		register.setBorder(null);
		register.setContentAreaFilled(false);
		footerPanel = new JPanel(new GridLayout(2,1,0,10));
		footerPanel.add(login);
		footerPanel.add(register);
		footerPanel.setBorder(new EmptyBorder(10,20,10,20));
		
		add(bodyPanel, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
		add(footerPanel, BorderLayout.SOUTH);
		
		
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(500,300);
		setTitle("Login Form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == login) {
			boolean valid = loginvalidation();
			if (valid) {
				new MainMenu(db, loggedUser);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Wrong email / Password!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if (e.getSource() == register) {
			new Register(db);
			this.dispose();
		}
	}
	
	private boolean loginvalidation() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		userdata = db.getuserdata();
		try {
			while (userdata.next()) {
				User user = new User(userdata.getObject(1).toString(), userdata.getObject(2).toString(), userdata.getObject(3).toString(), userdata.getObject(4).toString(), userdata.getObject(6).toString(), userdata.getObject(7).toString(), userdata.getObject(8).toString(), userdata.getObject(9).toString());
				if (emailField.getText().equals(user.getUserEmail()) && String.valueOf(passwordField.getPassword()).equals(user.getUserPassword())) {
						valid = true;
						loggedUser = user;
						break;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return valid;
		
	}

}
