import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditProfile extends JInternalFrame{

	DbConnection db;
	JLabel idLabel, userNameLabel, emailLabel, phoneLabel, addressLabel, passwordLabel, genderLabel, roleLabel;
	JTextField idlField, userNameField, emailField, phoneField;
	JTextArea addressField;
	JPasswordField passwordField;

	public EditProfile(DbConnection db, User user) {
		super("Your Profile", false, false, false);
		this.db = db; 
		// TODO Auto-generated constructor stub
		
		
		//header
		JLabel header = new JLabel("Edit your Profile");
		header.setFont(new Font("Serif", Font.PLAIN, 20));
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10,0,10,0));
		headerPanel.add(header);
		
		//body;
		idLabel	= new JLabel("ID");
		userNameLabel = new JLabel("User Name");
		emailLabel = new JLabel("Email");
		phoneLabel = new JLabel("Phone");
		addressLabel = new JLabel("Adress");
		passwordLabel = new JLabel("Password");
		genderLabel = new JLabel("Gender");
		roleLabel = new JLabel("Role");
		
		
		
		idlField = new JTextField();
		idlField.setText(user.getUserId());
		idlField.setEnabled(false);
		userNameField = new JTextField();
		userNameField.setText(user.getUserName());
		emailField = new JTextField();
		emailField.setText(user.getUserEmail());
		phoneField = new JTextField();
		phoneField.setText(user.getUserPhone());
		addressField = new JTextArea();
		addressField.setText(user.getUserAdddress());
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		JScrollPane addressform = new JScrollPane(addressField);
//		addressField.setPreferredSize(new Dimension(300,150));
//		JPanel address = new JPanel();
//		address.add(addressField);
		passwordField = new JPasswordField();
		passwordField.setText(user.getUserPassword());
		
		JRadioButton male = new JRadioButton("Male");
		JRadioButton female = new JRadioButton("Female");
		
		ButtonGroup gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		
		if (user.getUserGender().equals("Male")) {
			male.setSelected(true);
		} else {
			female.setSelected(true);
		}
	
		JPanel genderPanel = new JPanel(new GridLayout(1,2));
		genderPanel.add(male);
		genderPanel.add(female);
		
		String[] combodata = {"Admin", "Customer"};
		
		JComboBox<String> role = new JComboBox<String>(combodata);
		role.setSelectedItem(user.getUserRole());
		
		JPanel bodyPanel = new JPanel(new GridLayout(8,2,0,15));
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
		JButton submit = new JButton("Update");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					boolean valid = false;
					
					if (userNameField.getText().length() > 5 && userNameField.getText().length() < 30) {
						if (validation()) {
							if (phoneField.getText().length() >= 12) {
								if (addressField.getText().length() >= 10 && addressField.getText().endsWith(" Street")) {
									if (passwordvalidation()) {
										valid = true;
									} else {
										error("password");
									}
								} else {
									error("Address");
								}
							} else {
								error("Phone Number");
							}
						} else {
							error("Email");
						}
					} else {
						error("Username");
					}
					
					
					String gender = (male.isSelected()) ? "Male" : "Female";
					
				if (valid) {
					User user = new User(idlField.getText(), userNameField.getText(), emailField.getText(), String.valueOf(passwordField.getPassword()), gender, addressField.getText(), phoneField.getText(), role.getSelectedItem().toString());
					db.updateuser(user);
					}
				
				}
		});
		
		JPanel footer = new JPanel();
		footer.setBorder(new EmptyBorder(10,30,10,30));
		footer.add(submit);
		
				
		//Frame Setting
		add(footer, BorderLayout.SOUTH);
		add(headerPanel, BorderLayout.NORTH);
		add(bodyPanel, BorderLayout.CENTER);

		setVisible(true);
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
	
	private void error(String formname) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Please input valid " + formname, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
}
