import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TransactionForm extends JInternalFrame {

	JLabel headerLabel, selectedIdLabel, grandTotal;
	JTable transactionHeader, transactionDetail;
	JTextArea selectedIdField, grandTotalPrice;
	JPanel header, body, footer;
	DefaultTableModel transactionHeaderModel, transactionDetailModel;
	DbConnection db;
	JScrollPane headerScroll, detailScroll;
	
	public TransactionForm(DbConnection db) {
		super("Transaction Form", false, false, false);
		// TODO Auto-generated constructor stub
		
		this.db = db;
		
		//header;
		headerLabel = new JLabel("Transaction History");
		
		header = new JPanel();
		header.add(headerLabel);
		
		//body
		createTransactionHeaderTable();
		body = new JPanel(new BorderLayout());
		body.setBorder(new EmptyBorder(10,10,10,10));
		selectedIdLabel = new  JLabel ("SelectedId");
		selectedIdField = new JTextArea();
		selectedIdField.setEditable(false);
		selectedIdField.setPreferredSize(new Dimension(150,20));
		JPanel merger = new JPanel(new GridLayout(1,2,10,0));
		merger.add(selectedIdLabel);
		merger.add(selectedIdField);
		
		JPanel wraper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		wraper.add(merger);
		
		body.add(headerScroll, BorderLayout.CENTER);
		body.add(wraper, BorderLayout.SOUTH);
		
		//footer
		createTransactionDetailTable();
		grandTotal = new JLabel("Grand Total");
		grandTotalPrice = new JTextArea();
		grandTotalPrice.setPreferredSize(new Dimension(150,20));
		grandTotalPrice.setEditable(false);
		
		JPanel merger2 = new JPanel(new GridLayout(1,2,10,0));
		merger2.add(grandTotal);
		merger2.add(grandTotalPrice);
		
		JPanel wraper2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		wraper2.add(merger2);
		
		footer = new JPanel(new BorderLayout());
		footer.setBorder(new EmptyBorder(10,10,10,10));
		footer.add(detailScroll, BorderLayout.CENTER);
		footer.add(wraper2, BorderLayout.SOUTH);
		
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
		
		//Frame Setting
		setVisible(true);
	}
	
	public void createTransactionDetailTable() {
		Vector<Vector<Object>> contentDetail = new Vector<Vector<Object>>();
		
		Vector<String> headerColumn = new Vector<String>();
		headerColumn.add("Transaction Id");
		headerColumn.add("Game Id");
		headerColumn.add("Game Name");
		headerColumn.add("Game Type");
		headerColumn.add("Game Price");
		headerColumn.add("Game Quantity");
		headerColumn.add("Sub Total");
		
		transactionDetailModel = new DefaultTableModel(contentDetail, headerColumn);
		transactionDetail = new JTable(transactionDetailModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		transactionDetail.getTableHeader().setReorderingAllowed(false);
		detailScroll = new JScrollPane(transactionDetail);
		detailScroll.setVisible(true);
	}
	
	public void createTransactionHeaderTable() {
		Vector<Vector<Object>> contentHeader = new Vector<Vector<Object>>();
		
		Vector<String> headerColumn = new Vector<String>();
		headerColumn.add("Transaction Id");
		headerColumn.add("User Id");
		headerColumn.add("Transaction Date");
	
		ResultSet rs = db.getHeaderTransaction();
		
		try {
			while (rs.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(rs.getObject(1));
				record.add(rs.getObject(2));
				record.add(rs.getObject(3));
				
				contentHeader.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transactionHeaderModel = new DefaultTableModel(contentHeader, headerColumn);
		transactionHeader = new JTable(transactionHeaderModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		transactionHeader.getTableHeader().setReorderingAllowed(false);
		transactionHeader.addMouseListener(new MouseListener() {
			
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
				while (transactionDetailModel.getRowCount() != 0) {
					transactionDetailModel.removeRow(0);
				}
				int grandTotal = 0;
				int indSelected = transactionHeader.getSelectedRow();
				String TransactionID = transactionHeader.getValueAt(indSelected, 0).toString();
				
				ResultSet detailData = db.getDetailTransaction(TransactionID);
				
				try {
					while (detailData.next()) {
						selectedIdField.setText(TransactionID);
						int subTotal = Integer.valueOf(detailData.getObject(5).toString()) * Integer.valueOf(detailData.getObject(6).toString());
						grandTotal += subTotal;	
						Object[] record = {detailData.getObject(1),detailData.getObject(2),detailData.getObject(3),detailData.getObject(4),detailData.getObject(5),detailData.getObject(6), subTotal};
						transactionDetailModel.addRow(record);
					}
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				grandTotalPrice.setText(String.format("Rp.%s,-", String.valueOf(grandTotal)));
			}
		});
		headerScroll = new JScrollPane(transactionHeader);
		headerScroll.setVisible(true);
	}
	
	

}
