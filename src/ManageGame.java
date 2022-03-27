import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageGame extends JInternalFrame implements ActionListener{

	DbConnection db;
	JLabel headerLabel, newGameIdLabel, newGameNameLabel, newGameTypeLabel, newGamePriceLabel, newGameStockLabel, gameIdLabel, gameNameLabel, gameTypeLabel, gamePriceLabel, gameStockLabel, addStockLabel;
	JTable gameTable;
	JScrollPane gameTableScrollPane;
	DefaultTableModel gametableModel;
	JTextField newGameIdField, newGameNameField, newGamePriceField, gameIdField, gameNameField, gamePriceField, gameStockField;
	JComboBox<String> newGameTypeCombo, gameTypeCombo;
	JSpinner newGameStock, addstock;
	JButton insertGame, reset, updateGame, deleteGame, addStock;
	JPanel header, body, footer;
	Vector<Vector<Object>> tableContent = new Vector<Vector<Object>>();
	int indexChoosed;
	
	public ManageGame(DbConnection db) {
		super("Manage Game", false, false, false);
		// TODO Auto-generated constructor stub
		String[] gametype = {"RPG", "TPS", "MOBA", "FPS"};
		this.db = db;
		//header
		headerLabel = new JLabel("Manage Game");
		header = new JPanel();
		header.setBorder(new EmptyBorder(10,10,10,10));
		header.add(headerLabel);
		
		//body
		createTable(db.getGameData());
		
		newGameIdLabel = new JLabel("New Game Id");
		newGameNameLabel = new JLabel("New Game Name");
		newGameTypeLabel = new JLabel("New Game Type");
		newGamePriceLabel = new JLabel("New Game Price");
		newGameStockLabel = new JLabel("New Game Stock");
		
		
		newGameIdField = new JTextField();
		newGameIdField.setText(db.getNewGameId());
		newGameIdField.setEnabled(false);
		
		newGameNameField = new JTextField();
		newGameNameField = new JTextField();
		
		
		newGameTypeCombo = new JComboBox<>(gametype);
		
		newGamePriceField = new JTextField();
		
		newGameStock = new JSpinner(new SpinnerNumberModel(0,0,null,1));
		
		JPanel panelNewGame = new JPanel(new GridLayout(5,2,0,10));
		panelNewGame.add(newGameIdLabel);
		panelNewGame.add(newGameIdField);
		panelNewGame.add(newGameNameLabel);
		panelNewGame.add(newGameNameField);
		panelNewGame.add(newGameTypeLabel);
		panelNewGame.add(newGameTypeCombo);
		panelNewGame.add(newGamePriceLabel);
		panelNewGame.add(newGamePriceField);
		panelNewGame.add(newGameStockLabel);
		panelNewGame.add(newGameStock);
		
		gameIdLabel = new JLabel("Game Id");
		gameNameLabel = new JLabel("Game Name");
		gameTypeLabel =  new JLabel ("Game Type");
		gamePriceLabel = new JLabel("Game Price");
		gameStockLabel = new JLabel("gameStock");
		
		gameIdField = new JTextField();
		gameIdField.setEditable(false);
		gameNameField = new JTextField();
		
		gameTypeCombo = new JComboBox<String>(gametype);
		
		gamePriceField = new JTextField();
		gameStockField = new JTextField();
		gameStockField.setEditable(false);
		
		JPanel panelgame = new JPanel(new GridLayout(5,2,0,10));
		panelgame.add(gameIdLabel);
		panelgame.add(gameIdField);
		panelgame.add(gameNameLabel);
		panelgame.add(gameNameField);
		panelgame.add(gameTypeLabel);
		panelgame.add(gameTypeCombo);
		panelgame.add(gamePriceLabel);
		panelgame.add(gamePriceField);
		panelgame.add(gameStockLabel);
		panelgame.add(gameStockField);
		
		JPanel formsPanel = new JPanel(new GridLayout(1,2,10,0));
		formsPanel.add(panelNewGame);
		formsPanel.add(panelgame);
		
		
		body = new JPanel(new GridLayout(2,1,0,10));
		body.setBorder(new EmptyBorder(10,10,10,10));
		body.add(gameTableScrollPane);
		body.add(formsPanel);
		
		
		//footer
		insertGame = new JButton("Insert Game");
		insertGame.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		
		JPanel buttonPaneLeft = new JPanel(new GridLayout(2,1,0,10));
		buttonPaneLeft.add(insertGame);
		buttonPaneLeft.add(reset);
		
		JPanel buttonPanelRight = new JPanel(new GridLayout(2,1,0,10));
		
		updateGame = new JButton("Update Game");
		updateGame.addActionListener(this);
		deleteGame = new JButton("Delete Game");
		deleteGame.addActionListener(this);
		
		addStockLabel = new JLabel("Add Stock");
		addstock = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		addStock = new JButton("Add Stock");
		addStock.addActionListener(this);
		
		JPanel rightUpper = new JPanel(new GridLayout(1,2,5,0));
		rightUpper.add(updateGame);
		rightUpper.add(deleteGame);
		
		JPanel rightLower = new JPanel(new GridLayout(1,3,5,0));
		rightLower.add(addStockLabel);
		rightLower.add(addstock);
		rightLower.add(addStock);

		buttonPanelRight.add(rightUpper);
		buttonPanelRight.add(rightLower);
		
		
		footer = new JPanel(new GridLayout(1,2,10,0));
		footer.setBorder(new EmptyBorder(10,10,10,10));
		footer.add(buttonPaneLeft);
		footer.add(buttonPanelRight);
		
		//Frame Setting
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	private void createTable(ResultSet gamedata) {
		// TODO Auto-generated method stub

		
		Vector<String> columnName = new Vector<String>();
		columnName.add("Game Id");
		columnName.add("Game Name");
		columnName.add("Game Type");
		columnName.add("Game Price");
		columnName.add("Game Stock");
		
		
		try {
			while (gamedata.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(gamedata.getObject(1));
				record.add(gamedata.getObject(2));
				record.add(gamedata.getObject(3));
				record.add(gamedata.getObject(4));
				record.add(gamedata.getObject(5));
				
				tableContent.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gametableModel = new DefaultTableModel(tableContent, columnName);
		gameTable = new JTable(gametableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		gameTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (gameTable.getSelectedRow() != -1) {
					int ind = gameTable.getSelectedRow();
					gameIdField.setText(gameTable.getValueAt(ind, 0).toString());
					gameNameField.setText(gameTable.getValueAt(ind, 1).toString());
					gameTypeCombo.setSelectedItem(gameTable.getValueAt(ind, 2));
					gamePriceField.setText(gameTable.getValueAt(ind, 3).toString());
					gameStockField.setText(gametableModel.getValueAt(ind, 4).toString());
					
					indexChoosed = ind;
				}
			}
		});
		gameTable.getTableHeader().setReorderingAllowed(false);
		gameTableScrollPane = new JScrollPane(gameTable);
		gameTableScrollPane.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == reset) {
			newGameNameField.setText("");
			newGameTypeCombo.setSelectedIndex(0);
			newGamePriceField.setText("");
			newGameStock.setValue(0);
		}
		else if (e.getSource() == insertGame) {
			boolean valid = validategame();
			
			if (valid) {
				
				Game game = new Game(newGameIdField.getText(), newGameNameField.getText(), newGameTypeCombo.getSelectedItem().toString(), Integer.valueOf(newGamePriceField.getText()), Integer.valueOf(newGameStock.getValue().toString()));
				db.insertgame(game);
				Object[] newrow = {newGameIdField.getText(), newGameNameField.getText(), newGameTypeCombo.getSelectedItem().toString(), Integer.valueOf(newGamePriceField.getText()), Integer.valueOf(newGameStock.getValue().toString())};
				gametableModel.addRow(newrow);
				newGameIdField.setText(db.getNewGameId());
			} else {
				JOptionPane.showMessageDialog(this, "Please input a valid game data", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource() == updateGame) {
			
			
			int confirmDial = JOptionPane.showConfirmDialog(this, "Are you sure you wanna update this game?", "Confirmation", JOptionPane.YES_NO_OPTION);
			
			boolean confirm = (confirmDial == JOptionPane.YES_OPTION) ? true : false;
			
			
			if (confirm) {
				Game game = new Game(gameIdField.getText(), gameNameField.getText(),
						gameTypeCombo.getSelectedItem().toString(),
						Integer.valueOf(gamePriceField.getText().toString()),
						Integer.valueOf(gameStockField.getText().toString()));
				db.updateGame(game);
				gametableModel.setValueAt(game.getGameID(), indexChoosed, 0);
				gametableModel.setValueAt(game.getGameName(), indexChoosed, 1);
				gametableModel.setValueAt(game.getGameType(), indexChoosed, 2);
				gametableModel.setValueAt(game.getGamePrice(), indexChoosed, 3);
				gametableModel.setValueAt(game.getGameStock(), indexChoosed, 4);
			}
		}
		else if (e.getSource() == deleteGame) {
			
			int confirmDial = JOptionPane.showConfirmDialog(this, "Are you sure you wanna delete this game?", "Confirmation", JOptionPane.YES_NO_OPTION);
			
			boolean confirm = (confirmDial == JOptionPane.YES_OPTION) ? true : false;
			
			if (confirm) {
				String gameId = gameIdField.getText();
				try {
					db.deleteGame(gameId);
					gametableModel.removeRow(indexChoosed);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		}
		else if (e.getSource() == addStock) {
			String gameId = gameIdField.getText();
			int stockBefore = Integer.valueOf(gameTable.getValueAt(indexChoosed, 4).toString());
			int stockTotal = (Integer.valueOf(addstock.getValue().toString()) + stockBefore);  
			
			try {
				db.updateGameStock(gameId, stockTotal);
				gameTable.setValueAt(stockTotal, indexChoosed, 4);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	

	private boolean validategame() {
		// TODO Auto-generated method stub
		boolean valid = false;
		if (newGameNameField.getText().length() > 5 && gameNameField.getText().length() < 30) {
			if (Integer.valueOf(newGamePriceField.getText().toString()) > 0) {
				valid = true;
			}
		}
		
		return valid;
		
	}

}
