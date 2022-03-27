import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BuyGameForm extends JInternalFrame implements ActionListener {

	JLabel headerLabel, gameId, gameName, gameType, gamePrice, gameStock, gameQuantity;
	JTextField gameIdField, gameNameField, gameTypeField, gamePriceField, gameStockField;
	JSpinner gameQuantityField;
	JTable gameTable, cartTable;
	JButton addCartButton, removeSelectedCartButton, clearCartButton, checkOut;
	JPanel headerPanel, bodyPanel, footerPanel;
	JScrollPane gameTableScroll, cartTableScroll;
	DefaultTableModel gameTableModel, cartTableModel;
	DbConnection db;
	Game gameSelected;
	int indexSelected, cartIndexSelected;
	String TransactionId;
	User user;
	
	public BuyGameForm(DbConnection db, User user) {
		// TODO Auto-generated constructor stub
		super("Buy Game", false, false, false);
		this.db = db;
		this.user = user;
		
		TransactionId = db.getTransactionId();
		//header
		headerLabel = new JLabel("Buy Game", SwingConstants.CENTER);
		
		headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		gameTabelCreate();

		headerPanel.add(headerLabel, BorderLayout.NORTH);
		headerPanel.add(gameTableScroll, BorderLayout.CENTER);
		//body
		
		gameId = new JLabel("Game Id");
		gameIdField = new JTextField();
		gameIdField.setEnabled(false);
		gamePrice = new JLabel("Game Price");
		gamePriceField = new JTextField();
		gamePriceField.setEnabled(false);
		
		JPanel grid1 = new JPanel(new GridLayout(1,4,10,0));
		grid1.add(gameId);
		grid1.add(gameIdField);
		grid1.add(gamePrice);
		grid1.add(gamePriceField);
		
		
		gameName = new JLabel("Game Name");
		gameNameField = new JTextField();
		gameNameField.setEnabled(false);
		gameStock = new JLabel("Game Stock");
		gameStockField = new JTextField();
		gameStockField.setEnabled(false);
		
		JPanel grid2 = new JPanel(new GridLayout(1,4,10,0));
		grid2.add(gameName);
		grid2.add(gameNameField);
		grid2.add(gameStock);
		grid2.add(gameStockField);
		
		
		gameType = new JLabel("Game Type");
		gameTypeField = new JTextField();
		gameTypeField.setEnabled(false);
		gameQuantity = new JLabel("Game Quantity");
		gameQuantityField = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		
		JPanel grid3 = new JPanel(new GridLayout(1,4,10,0));
		grid3.add(gameType);
		grid3.add(gameTypeField);
		grid3.add(gameQuantity);
		grid3.add(gameQuantityField);
		
		addCartButton = new JButton("Add to Cart");
		addCartButton.addActionListener(this);
		JPanel grid4 = new JPanel();
		grid4.add(addCartButton);
		
		

		bodyPanel = new JPanel(new GridLayout(4,1,0,10));
		bodyPanel.setBorder(new EmptyBorder(10,10,10,10));
		bodyPanel.add(grid1);
		bodyPanel.add(grid2);
		bodyPanel.add(grid3);
		bodyPanel.add(grid4);
		
		//footer
		cartTabelCreate();
		
		removeSelectedCartButton = new JButton("Remove Selected Cart");
		removeSelectedCartButton.addActionListener(this);
		clearCartButton = new JButton("Clear Cart");
		clearCartButton.addActionListener(this);
		checkOut = new JButton("Check Out");
		checkOut.addActionListener(this);
		
		JPanel buttonPane = new JPanel(new GridLayout(1,3,10,0));
		buttonPane.add(removeSelectedCartButton);
		buttonPane.add(clearCartButton);
		buttonPane.add(checkOut);
		
		
		footerPanel = new JPanel(new BorderLayout(0,10));
		footerPanel.setBorder(new EmptyBorder(10,10,10,10));
		footerPanel.add(cartTableScroll, BorderLayout.CENTER);
		footerPanel.add(buttonPane, BorderLayout.SOUTH);
		
		
		add(headerPanel);
		add(bodyPanel);
		add(footerPanel);

		//Frame Setting
		setVisible(true);
		setLayout(new GridLayout(3,1,0,10));
		
	}



	private void gameTabelCreate() {
		// TODO Auto-generated method stub
		ResultSet gameTableData = db.getGameData();
		
		Vector<String> columnName = new Vector<String>();
		columnName.add("Game Id");
		columnName.add("Game Name");
		columnName.add("Game Type");
		columnName.add("Game Price");
		columnName.add("Game Stock");
		
		Vector<Vector<Object>> dataContent = new Vector<Vector<Object>>();
		try {
			while (gameTableData.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(gameTableData.getObject(1));
				record.add(gameTableData.getObject(2));
				record.add(gameTableData.getObject(3));
				record.add(gameTableData.getObject(4));
				record.add(gameTableData.getObject(5));
				
				dataContent.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gameTableModel = new DefaultTableModel(dataContent, columnName);
		gameTable = new JTable(gameTableModel) {
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
				if (gameTable.getSelectedRow() > -1) {
					// TODO Auto-generated method stub
					int index = gameTable.getSelectedRow();
					indexSelected = index;
					gameSelected = new Game(gameTable.getValueAt(index, 0).toString(),
							gameTable.getValueAt(index, 1).toString(), gameTable.getValueAt(index, 2).toString(),
							Integer.valueOf(gameTable.getValueAt(index, 3).toString()),
							Integer.valueOf(gameTable.getValueAt(index, 4).toString()));
					gameIdField.setText(gameSelected.getGameID());
					gameNameField.setText(gameSelected.getGameName());
					gameTypeField.setText(gameSelected.getGameType());
					gamePriceField.setText(String.valueOf(gameSelected.getGamePrice()));
					gameStockField.setText(String.valueOf(gameSelected.getGameStock()));
				}
			}
		});
		gameTable.getTableHeader().setReorderingAllowed(false);
		gameTableScroll = new JScrollPane(gameTable);
		gameTableScroll.setVisible(true);
		
	}
	
	private void cartTabelCreate() {
		
		Vector<Vector<Object>> cartContent = new Vector<Vector<Object>>();
		Vector<String> cartColumn = new Vector<String>();
		cartColumn.add("Game Id");
		cartColumn.add("Game name");
		cartColumn.add("Game Type");
		cartColumn.add("Game Price");
		cartColumn.add("Game Stock");
		cartColumn.add("Game Quantity");
		cartColumn.add("SubTotal");
		
		cartTableModel = new DefaultTableModel(cartContent, cartColumn);
		cartTable = new JTable(cartTableModel) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		cartTable.addMouseListener(new MouseListener() {
			
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
				cartIndexSelected = cartTable.getSelectedRow();
				
				
			}
		});
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTableScroll = new JScrollPane(cartTable);
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addCartButton) {
			if (Integer.valueOf(gameQuantityField.getValue().toString()) != 0) {
				boolean duplicate = false;
				int indexduplicate = 0;
				for (int i = 0; i < cartTable.getRowCount(); i++) {
					if (gameTableModel.getValueAt(indexSelected, 0).equals(cartTableModel.getValueAt(i, 0))) {
						duplicate = true;
						indexduplicate = i;
					}
				}
				if (!duplicate) {
					if (Integer.valueOf(gameStockField.getText().toString()) >= Integer.valueOf(gameQuantityField.getValue().toString())) {
						int subtotal = Integer.valueOf(gameQuantityField.getValue().toString())
								* gameSelected.getGamePrice();
						Object[] newRecord = { gameSelected.getGameID(), gameSelected.getGameName(),
								gameSelected.getGameType(), gameSelected.getGamePrice(), gameSelected.getGameStock(),
								gameQuantityField.getValue(), subtotal };

						int afterQuant = gameSelected.getGameStock()
								- Integer.valueOf(gameQuantityField.getValue().toString());

						cartTableModel.addRow(newRecord);
						gameTableModel.setValueAt(afterQuant, indexSelected, 4);
						gameStockField.setText(String.valueOf(afterQuant));
					} 
				} else {
					if (Integer.valueOf(gameStockField.getText().toString()) >= Integer.valueOf(gameQuantityField.getValue().toString())) {
						int quantcartBefore = Integer.valueOf(cartTableModel.getValueAt(indexduplicate, 5).toString());
						cartTableModel.setValueAt((quantcartBefore + Integer.valueOf(gameQuantityField.getValue().toString())), indexduplicate, 5);
						int afterQuant = gameSelected.getGameStock()
								- Integer.valueOf(cartTableModel.getValueAt(indexduplicate, 5).toString());
						gameTableModel.setValueAt(afterQuant, indexSelected, 4);
						gameStockField.setText(String.valueOf(afterQuant));
					}
				}
				
				
			}
		}
		else if (e.getSource() == removeSelectedCartButton) {
			int indexgame = 0;
			if (cartIndexSelected != -1) {
				for (int i = 0; i < gameTableModel.getRowCount(); i++) {
					System.out.println(cartIndexSelected);
					if (cartTableModel.getValueAt(cartIndexSelected, 0).equals(gameTableModel.getValueAt(i, 0))) {
						indexgame = i;
						break;
					}
				}
				
				gameTableModel.setValueAt(cartTableModel.getValueAt(cartIndexSelected, 4), indexgame, 4);
				gameStockField.setText(cartTableModel.getValueAt(cartIndexSelected, 4).toString());
				cartTableModel.removeRow(cartIndexSelected);
			}
			
		}
		
		else if (e.getSource() == clearCartButton) {
			for (int i = 0; i < cartTableModel.getRowCount(); i++) {
				String id = cartTable.getValueAt(i, 0).toString();
				int quantBefore = Integer.valueOf(cartTable.getValueAt(i, 4).toString());
				
				for (int j = 0; j < gameTableModel.getRowCount(); j++) {
					if (id.equals(gameTableModel.getValueAt(j, 0))) {
						gameTableModel.setValueAt(quantBefore, j, 4);
					}
				}
				
			}
			
			if (cartTableModel.getRowCount() != 0) {
				while (cartTable.getRowCount() != 0) {
					cartTableModel.removeRow(0);
				}
			}
		}
		
		else if (e.getSource() == checkOut) {
			db.inputHeaderTransaction(TransactionId, user);
			
			if (cartTableModel.getRowCount() != 0) {
				for (int i = 0; i < cartTableModel.getRowCount(); i++) {
					db.inputDetailTransaction(TransactionId, cartTable.getValueAt(i, 0).toString(), Integer.valueOf(cartTableModel.getValueAt(i, 5).toString()));
				}
				
				for (int i = 0; i < gameTableModel.getRowCount(); i++) {
					try {
						db.updateGameStock(gameTableModel.getValueAt(i, 0).toString(), Integer.valueOf(gameTableModel.getValueAt(i, 4).toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				while (cartTable.getRowCount() != 0) {
					cartTableModel.removeRow(0);
				}
			}
		}
	}
	
	

}
