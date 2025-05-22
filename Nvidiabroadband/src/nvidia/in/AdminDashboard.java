package nvidia.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AdminDashboard extends JFrame
{
	private JButton billGenerator, accountDetails,serviceRequest,profile;
	private JTable DetailsTable;
	private DefaultTableModel tableModel;
	private JPanel displayPanel,panelHolder;
	
	public static String adminName ;
	public static String password;
	
	//BillGenerator TextFields
	JTextField accountIdTextField,planBillTextField,stateTaxTextField,dueAmountTextField,totalAmountTextField;
	
	//BillGenerator TextFields
	JButton totalButton,generateBill;
	double taxAmount = 4.99,dueAmount = 0.00;
	long transactionId;
	
	
	public AdminDashboard() {
		setUpFrame();
		createNavigationPanel();
		createActionListeners();
		//createUserDetailsPanel();
		
		
	}
	
	public void setUpFrame()
	{
		setSize(1920,1080);
		setVisible(true);
		setLayout(new BorderLayout());
		setTitle("Admin DashBoard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelHolder = new JPanel(); 
	    panelHolder.setPreferredSize(new Dimension(700, getHeight()));
	    panelHolder.setBackground(Color.GRAY);

	    add(panelHolder, BorderLayout.CENTER); 
	    
	    displayPanel = new JPanel(); 
	    panelHolder.add(displayPanel);
	    
	}
	
	public void createNavigationPanel()
	{
		JPanel navigationPanel = new JPanel();
		
		navigationPanel.setPreferredSize(new Dimension(300,getHeight()));;
		navigationPanel.setBackground(Color.black);
		
		billGenerator = createStyledButton("Bill Generator", new Color(70,70,70),Color.WHITE);
		accountDetails = createStyledButton("Account Details", new Color(70,70,70),Color.WHITE);
		serviceRequest = createStyledButton("Service Request", new Color(70,70,70),Color.WHITE);
		profile = createStyledButton("Profile", new Color(70,70,70),Color.WHITE);
		
		navigationPanel.add(billGenerator);
		navigationPanel.add(accountDetails);
		navigationPanel.add(serviceRequest);
		navigationPanel.add(profile);
		
		add(navigationPanel,BorderLayout.WEST);
		
		loadPanel(addBillGeneratorPanel(),800,70);
	}

	
	public void createActionListeners()
	{
		
		billGenerator.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {	
	        	
	        	loadPanel(addBillGeneratorPanel(),800,70);
	        }
	    });

		accountDetails.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{		
			
			    loadPanel(addAccountDetailsPanel(),400,120);
			    
			}
		});
		
		serviceRequest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPanel(addServiceRequestPanel(),400,70);
			}
		});

	    profile.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            loadPanel(createProfilePanel(),1000,600); // Load Profile panel
	        }
	    });
	}
	
	
	public JPanel addBillGeneratorPanel()
	{
		JPanel billGeneratorPanel = new JPanel();
		
		billGeneratorPanel.setBackground(Color.WHITE);
		billGeneratorPanel.setLayout(new BoxLayout(billGeneratorPanel, BoxLayout.Y_AXIS));	
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0, 250)));
		billGeneratorPanel.setSize(new Dimension(800,200));
		
		
		accountIdTextField = createStyledTextField(10);
		planBillTextField = createStyledTextField(10);
		stateTaxTextField = createStyledTextField(10);
		stateTaxTextField.setText(Double.toString(taxAmount));
		stateTaxTextField.setForeground(Color.BLACK);
		stateTaxTextField.setFont(new Font("Arial",Font.PLAIN,14));
		
		dueAmountTextField = createStyledTextField(10);
		
		dueAmountTextField.setText(Double.toString(dueAmount));
		
		totalAmountTextField = createStyledTextField(10);
		totalButton = createStyledButton("Calculate Total", new Color(226,235,245),Color.BLACK);
		generateBill = createStyledButton("Generate Bill", new Color(226,235,245),Color.BLACK);
		
		
		
		planBillTextField.setEditable(false);
		stateTaxTextField.setEditable(false);
		totalAmountTextField.setEditable(false);
		
		createFormRow("Account ID : ", accountIdTextField, billGeneratorPanel);
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		createFormRow("Plan Bill (Monthly) : ", planBillTextField, billGeneratorPanel);
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		createFormRow("State Tax : ", stateTaxTextField, billGeneratorPanel);
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		createFormRow("Due Fine : ", dueAmountTextField, billGeneratorPanel);
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		createFormRow("Total Amount : ", totalAmountTextField, billGeneratorPanel);
		
		
		
		billGeneratorPanel.add(Box.createRigidArea(new Dimension(0,190)));
		

		 JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
	       buttonPanel.setOpaque(false);
	       buttonPanel.setPreferredSize(new Dimension(800,200));
	       buttonPanel.add(totalButton);
	       buttonPanel.add(generateBill);
	       
	       
	    billGeneratorPanel.add(buttonPanel);
	    
	    totalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String accountNo = accountIdTextField.getText();
				
				ConnectionJDBC con = new ConnectionJDBC();
				
				try
				{
					ResultSet rs =  con.s.executeQuery("Select planPrice from broadband_plans where accountNumber = '"+accountNo+"';");
					
					if(rs.next())
					{	
						double planPrice = Double.parseDouble(rs.getString("planPrice"));
						dueAmount = Double.parseDouble(dueAmountTextField.getText());
						
						planBillTextField.setText(Double.toString(planPrice));
						
						double total = planPrice+taxAmount+dueAmount;
						
						totalAmountTextField.setText(Double.toString(total));
						
						
					}	
					
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	    
	    generateBill.addActionListener(new ActionListener()
	    {
	    		
	    	ConnectionJDBC con = new ConnectionJDBC();
	    	@Override
			public void actionPerformed(ActionEvent e) 
	    	{
	    		
	    		String accountNo = accountIdTextField.getText();
	    		
	    		double total = Double.parseDouble(totalAmountTextField.getText()); 
	    		String roundedStr = String.format("%.2f", total);
	    		System.out.println(roundedStr);  
	    		
	    		
	    		dueAmount = Double.parseDouble(dueAmountTextField.getText());
	    		
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String today = sdf.format(new Date());
	    		
	    		generateTransactionId();
	    		    		
	    		String query = "INSERT INTO bill VALUES (?, ?, ?, ?, ?, ?, ?)";
	    		
	    		Connection c = con.getConnection();    		
	    		
	    		PreparedStatement ps;
				try 
				{
					
					ps = c.prepareStatement(query);
					
					ps.setString(1, accountNo);  // String value
		    		ps.setString(2, roundedStr);      // Decimal/Double value
		    		ps.setLong(3, transactionId); // Integer value
		    		ps.setString(4, "None");     // String value
		    		ps.setDouble(5, dueAmount);
		    		ps.setDouble(6, taxAmount);
		    		ps.setString(7, today);       // Date should be in 'YYYY-MM-DD' format

		    		int i = ps.executeUpdate();
		    		
		    		if(i > 0)
					{
		    			JOptionPane.showMessageDialog(AdminDashboard.this, "Bill Generated Successfully !!!", "Success",JOptionPane.PLAIN_MESSAGE);
					}	
					else
					{
						JOptionPane.showMessageDialog(AdminDashboard.this, "Bill was not Generated !!!", "Error",JOptionPane.PLAIN_MESSAGE);
					}	
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    		
			}
		});
	    
	    return billGeneratorPanel;
}

	
public JPanel addAccountDetailsPanel()
{	
	   JPanel accountDetailsPanel = new JPanel();
	    accountDetailsPanel.setBackground(Color.GRAY);
	    accountDetailsPanel.setLayout(new BorderLayout());

	    String[] columnNames = {"Mobile Number", "User Name", "Plan Type", "Plan Price", "Account No"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    DetailsTable = new JTable(tableModel);
	    
	    
	    DetailsTable.setPreferredScrollableViewportSize(new Dimension(750, 500));
	    DetailsTable.setFillsViewportHeight(true);

	    JScrollPane scrollPane = new JScrollPane(DetailsTable);
	    accountDetailsPanel.add(scrollPane, BorderLayout.CENTER);

	    fetchUserDetails();

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    buttonPanel.setBackground(Color.GRAY);

	    JButton refreshButton = createStyledButton("Refresh", new Color(226, 235, 245), Color.BLACK);
	    buttonPanel.add(refreshButton);

	    accountDetailsPanel.add(buttonPanel, BorderLayout.SOUTH); 

	    refreshButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            tableModel.setRowCount(0); 
	            fetchUserDetails(); 
	        }
	    });

	    return accountDetailsPanel;
}

public JPanel addServiceRequestPanel()
{
	  JPanel serviceRequestPanel = new JPanel();
	    serviceRequestPanel.setBackground(Color.GRAY);
	    serviceRequestPanel.setLayout(new BorderLayout());

	    String[] columnNames = {"Request ID", "User", "Type", "Status", "Date"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    DetailsTable = new JTable(tableModel);

	    DetailsTable.setRowHeight(30); 

	    JScrollPane scrollPane = new JScrollPane(DetailsTable);
	    scrollPane.setPreferredSize(new Dimension(900, 600)); 
	    
	    String[] statusOptions = {"Pending", "In Progress", "Completed", "Rejected"};
	    JComboBox<String> statusDropdown = new JComboBox<>(statusOptions);

	    TableColumn statusColumn = DetailsTable.getColumnModel().getColumn(3);
	    statusColumn.setCellEditor(new DefaultCellEditor(statusDropdown));

	    DetailsTable.getModel().addTableModelListener(e -> {
	        int row = e.getFirstRow();
	        int column = e.getColumn();

	        if (column == 3) {  
	            String requestId = (String) tableModel.getValueAt(row, 0); // Get Request ID
	            String newStatus = (String) tableModel.getValueAt(row, column); // Get new status

	            updateStatusInDatabase(requestId, newStatus);
	        }
	    });

	    serviceRequestPanel.add(scrollPane, BorderLayout.CENTER);

	    fetchServiceRequestDetails();

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    buttonPanel.setBackground(Color.GRAY);

	    JButton refreshButton = createStyledButton("Refresh", new Color(226, 235, 245), Color.BLACK);
	    buttonPanel.add(refreshButton);

	    serviceRequestPanel.add(buttonPanel, BorderLayout.SOUTH); 

	    refreshButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            tableModel.setRowCount(0);
	            fetchServiceRequestDetails(); 
	        }
	    });

	    return serviceRequestPanel;
}



public JPanel createProfilePanel() 
{
	JPanel profilePanel = new JPanel();
	
	JTextField adminNameFeild,adminPassword;
	JButton updateProfileButton;
	
	profilePanel.setBackground(Color.WHITE);
	profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));	
	profilePanel.setSize(new Dimension(800,200));
	
	adminNameFeild = createStyledTextField(10);
	adminPassword = createStyledTextField(10);
	
	updateProfileButton = createStyledButton("Update Profile",new Color(226,235,245) , Color.BLACK);
	
	profilePanel.add(Box.createRigidArea(new Dimension(0, 30)));
	
	createFormRow("Name : ", adminNameFeild, profilePanel);
	profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
	
	adminNameFeild.setText(adminName);
	
	createFormRow("Password : ", adminPassword, profilePanel);
	profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
	
	adminPassword.setText(password);
	adminPassword.setEditable(false);
	
	 JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
     buttonPanel.setOpaque(false);
     buttonPanel.setPreferredSize(new Dimension(800,200));
     buttonPanel.add(updateProfileButton);
     
     
     updateProfileButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			ConnectionJDBC con = new ConnectionJDBC();
			
			
			adminName = adminNameFeild.getText();
			
			String query = "Update admin_sign_in set username = '"+adminName+"' where password = '"+password+"';";
			
			try 
			{
				int i = con.s.executeUpdate(query);
				
				if(i>0)
				{
					JOptionPane.showMessageDialog(AdminDashboard.this, "Admin Profile Updated Successfully !!" , "Success",JOptionPane.OK_OPTION);
				}
				else
				{
					JOptionPane.showMessageDialog(AdminDashboard.this, "Admin Profile is not Updated !!" , "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
     
     profilePanel.add(buttonPanel);
     
     return profilePanel;
}
	
public void loadPanel(JPanel newPanel,int widthAdjustValue,int heightAdjustValue) 
{
	    if (displayPanel != null ) 
	    {
	        panelHolder.remove(displayPanel); // Remove the old panel
	    }

	    displayPanel = newPanel;
	    displayPanel.setPreferredSize(new Dimension(getWidth() - widthAdjustValue, getHeight() - heightAdjustValue));
	    displayPanel.setBackground(Color.GRAY);

	    panelHolder.add(displayPanel);
	    panelHolder.revalidate();
	    panelHolder.repaint();
}

public void generateTransactionId()
{
	double max = 1000000000;
	double min = 1;
	
	transactionId = (long) (Math.random()*(max-min)+1);
}

public void updateStatusInDatabase(String requestId, String newStatus) {
    ConnectionJDBC con = new ConnectionJDBC();
    
    Connection c = con.getConnection();
    
    String query = "UPDATE service_request SET status = ? WHERE request_id = ?";
    
    try {
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, newStatus);
        pstmt.setString(2, requestId);

        int updatedRows = pstmt.executeUpdate();
        
        if (updatedRows > 0) {
            System.out.println("Status updated successfully for Request ID: " + requestId);
        } else {
            System.out.println("Error: Request ID not found!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public void fetchUserDetails() {
    ConnectionJDBC con = new ConnectionJDBC();

    String query = "SELECT sign_in.mobilenumber, sign_in.username, broadband_plans.planType, broadband_plans.planPrice, broadband_plans.accountNumber FROM sign_in JOIN broadband_plans ON sign_in.mobilenumber = broadband_plans.mobilenumber;";

    try {
        ResultSet rs = con.s.executeQuery(query);

        while (rs.next()) {
            String mobile = rs.getString("mobileNumber");
            String userName = rs.getString("userName"); // Fetching userName as it's in the query
            String planType = rs.getString("planType");
            double planPrice = rs.getDouble("planPrice");
            String accountNo = rs.getString("accountNumber");

            tableModel.addRow(new Object[]{mobile, userName, planType, planPrice, accountNo});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void fetchServiceRequestDetails()
{
	 ConnectionJDBC con = new ConnectionJDBC();

	    String query = "SELECT * from service_request";

	    try {
	        ResultSet rs = con.s.executeQuery(query);

	        while (rs.next()) {
	            String mobile = rs.getString("request_id");
	            String userName = rs.getString("user"); // Fetching userName as it's in the query
	            String planType = rs.getString("type");
	            String planPrice = rs.getString("status");
	            String accountNo = rs.getString("date");

	            tableModel.addRow(new Object[]{mobile, userName, planType, planPrice, accountNo});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
}

private void createFormRow(String labelText, JComponent component, JPanel formPanel) 
{
    JPanel rowPanel = new JPanel();
    rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
    rowPanel.setOpaque(false);
    rowPanel.setPreferredSize(new Dimension(300,30));
    
    JLabel label = new JLabel(labelText);
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Arial", Font.PLAIN, 18));
    label.setPreferredSize(new Dimension(200, 15));

    component.setPreferredSize(new Dimension(300,15));
    
    rowPanel.add(label);
    rowPanel.add(Box.createRigidArea(new Dimension(120, 0)));
    rowPanel.add(component);
    
    formPanel.add(rowPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
}

private JTextField createStyledTextField(int columns) {
    JTextField field = new JTextField(columns);
    field.setBackground(Color.WHITE);
    field.setForeground(new Color(33, 33, 33));
    field.setFont(new Font("Arial", Font.PLAIN, 14));
    field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    
   
    return field;
}

 private JButton createStyledButton(String text, Color background,Color foreGround) 
 {
	        JButton button = new JButton(text);
	        button.setBackground(background);
	        button.setForeground(foreGround);
	        button.setFont(new Font("Arial", Font.BOLD, 15));

	        button.setFocusPainted(false);
	        button.setOpaque(true);
	        button.setPreferredSize(new Dimension(230,40));
	        
	        button.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                button.setBackground(background.brighter());
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                button.setBackground(background);
	            }
	        });
	        return button;
}
	
	
 public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new AdminDashboard();
			}
		});
		
	}
}