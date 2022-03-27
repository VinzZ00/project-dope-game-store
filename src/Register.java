import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame implements ActionListener{

	JLabel headerlabel, idLabel, userNameLabel, emailLabel, phoneLabel, addressLabel, passwordLabel, genderLabel, roleLabel;
	JTextField idlField, userNameField, emailField, phoneField;
	JPasswordField passwordField;
	JRadioButton female, male;
	ButtonGroup gender;
	JComboBox<String> role;
	JTextArea addressField;
	JButton register, signIn;
	JPanel headerPanel, bodyPanel, footerPanel;
	DbConnection db;
	
	public Register(DbConnection db) {
		// TODO Auto-generated constructor stub
		
		this.db = db;
		
		//header
		headerlabel = new JLabel("Register Form");
		headerlabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		
		headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10,0,10,0));
		
		headerPanel.add(headerlabel);
		
		//body
		idLabel	= new JLabel("ID");
		userNameLabel = new JLabel("User Name");
		emailLabel = new JLabel("Email");
		phoneLabel = new JLabel("Phone");
		addressLabel = new JLabel("Adress");
		passwordLabel = new JLabel("Password");
		genderLabel = new JLabel("Gender");
		roleLabel = new JLabel("Role");
		
		
		
		idlField = new JTextField();
		idlField.setText(db.getNewUniqueId());
		idlField.setEnabled(false);
		userNameField = new JTextField();
		emailField = new JTextField();
		phoneField = new JTextField();
		addressField = new JTextArea();
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		JScrollPane addressform = new JScrollPane(addressField);
//		addressField.setPreferredSize(new Dimension(300,150));
//		JPanel address = new JPanel();
//		address.add(addressField);
		passwordField = new JPasswordField();
		
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		
		gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		
		male.setSelected(true);
	
		JPanel genderPanel = new JPanel(new GridLayout(1,2));
		genderPanel.add(male);
		genderPanel.add(female);
		
		String[] combodata = {"Admin", "Customer"};
		
		role = new JComboBox<String>(combodata);
		
		bodyPanel = new JPanel(new GridLayout(8,2,0,15));
		bodyPanel.add(idLabel);
		bodyPanel.add(idlField);
		bodyPanel.add(userNameLabel);
		bodyPanel.add(userNameField);
		bodyPanel.add(emailLabel);
		bodyPanel.add(emailField);
		bodyPanel.add(phoneLabel);
		bodyPanel.add(phoneField);
		bodyPanel.add(addressLabel);
		bodyPanel.add(addressform);
		bodyPanel.add(passwordLabel);
		bodyPanel.add(passwordField);
		bodyPanel.add(genderLabel);
		bodyPanel.add(genderPanel);
		bodyPanel.add(roleLabel);
		bodyPanel.add(role);
		
		bodyPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		//footer
		register = new JButton("Register");
		register.setPreferredSize(new Dimension(120,30));
		register.addActionListener(this);
		JPanel registerpane = new JPanel();
		registerpane.add(register);
		signIn = new JButton("Sign In");
		signIn.addActionListener(this);
		signIn.setContentAreaFilled(false);
		signIn.setBorder(null);
		
		footerPanel = new JPanel(new GridLayout(2,1,0,10));
		footerPanel.setBorder(new EmptyBorder(10,10,10,10));
		footerPanel.add(registerpane);
		footerPanel.add(signIn);
		
		add(headerPanel, BorderLayout.NORTH);
		add(bodyPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		setResizable(false) ;
		setLocationRelativeTo(null);
		setSize(500,600);
		setTitle("Register Form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == signIn) {
			new Login(db);
			this.dispose();
		}
		else if (e.getSource() == register) {
			boolean valid = false;
			
			if (userNameField.getText().length() > 5 && userNameField.getText().length() < 30) {
				if (validation()) {
					if (phoneField.getText().length() >= 12) {
						if (addressField.getText().length() >= 10 && addressField.getText().endsWith(" Street")) {
							if (passwordvalidation()) {
								valid = true;
							} else {
								JOptionPane.showMessageDialog(this, "Please Input a valid password", "Message", JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(this, "Please Input a valid Address", "Message", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Please Input a valid phone number", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please fill with valid Email", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please input name between 5 - 30 character", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			String gender = (male.isSelected()) ? "Male" : "Female";
			
		if (valid) {
			User user = new User(idlField.getText(), userNameField.getText(), emailField.getText(), String.valueOf(passwordField.getPassword()), gender, addressField.getText(), phoneField.getText(), role.getSelectedItem().toString());
			db.insertUser(user);
			new Login(db);
			this.dispose();
			}
		
		}
	}
	
	private boolean validation() {
		// TODO Auto-generated method stub
		boolean validation = false;
		
		String email = emailField.getText();
		if (!(email.startsWith("@") || email.startsWith(".") || email.endsWith("@") || email.endsWith("."))) {
			if (checkmorethanone(email, '@') == 1) {
				String subs = email.substring(email.indexOf('@'));
				if (checkmorethanone(subs, '.') == 1) {
					validation = true;
				} else {
					validation = false;
				}
			} else {
				validation = false;
			}
		} else {
			validation = false;
		}
		
		return validation;
	}
	
	private boolean passwordvalidation() {
		// TODO Auto-generated method stub
		boolean alp = false;
		boolean number = false;
		String lowerPass = String.valueOf(passwordField.getPassword()).toLowerCase();
		char[] password = lowerPass.toCharArray();
		
		for (char c : password) {
			if (c >= 97 && c <= 122) {
				alp = true;
			}
		}
		
		for (char c : password) {
			if (c >= 48 && c <= 57) {
				number = true;
			}
		}
		
		boolean valid = (alp && number) ? true:false;
		return valid;
	}
	
	private int checkmorethanone(String arg, char check) {
		// TODO Auto-generated method stub
		char[] chars = arg.toCharArray();
		
		int checker = 0;
		
		for (char c : chars) {
			if (c == check) {
				checker += 1;
			}
		}
	
		return checker;	
	}

	
}
