package nvidia.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;

public class ServiceRequest extends JFrame
{
	JLabel serviceLabel;
	JTextField requestField,userField;
	JComboBox<String> requestType;
	JSpinner dateSpinner;
	JButton submitRequest,clearForm,goBack;
	static long requestId;
	ConnectionJDBC con;
	
	static String user;
	static String reqType ;
	static String date; 
	
	public ServiceRequest() 
	{
		setUpFrame();
		initializeComponents();
		addComponents();
		addActionListeners();
		
	}
	
	public void setUpFrame()
	{
		setSize(1000,750);
		setVisible(true);
		setLayout(new BorderLayout());
		setTitle("User DashBoard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createBackgroundPanelImg());
	}
	
	public void addComponents()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);
		contentPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		serviceLabel = new JLabel("Service Request Form");
		serviceLabel.setFont(new Font("Arial", Font.BOLD, 32));
		serviceLabel.setForeground(Color.WHITE);
		serviceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		contentPanel.add(serviceLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0,150)));
		
		createFormRow("Request ID : ", requestField, contentPanel);
		
		
		generateRequestID();
		 
		 requestField.setText(String.valueOf(requestId));
		 requestField.setEnabled(false);
		
		contentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		createFormRow("User : ", userField, contentPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		createFormRow("Request Type : ", requestType, contentPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		createFormRow("Date : ", dateSpinner, contentPanel);
		
		contentPanel.add(Box.createRigidArea(new Dimension(0,140)));
		
		 JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
	        buttonPanel.setOpaque(false);
	        buttonPanel.add(submitRequest);
	        buttonPanel.add(clearForm);
	        buttonPanel.add(goBack);
	        
	     contentPanel.add(buttonPanel);
	     
	     add(contentPanel);
			
	}
	
	
	 void addActionListeners()
	{
		con = new ConnectionJDBC();
		
		submitRequest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try 
				{
					user = userField.getText();
					reqType = (String)requestType.getSelectedItem();
					Date selectedDate = (Date)dateSpinner.getValue();
					
					if( user == null && reqType == null && date == null)
					{
						JOptionPane.showMessageDialog(ServiceRequest.this, "Fill all the Fields" , "Warning",JOptionPane.WARNING_MESSAGE);
						return;
					}	
					
					String selectQuery = "Select * from sign_in where username = '"+userField.getText()+"'";
					
					ResultSet rs = con.s.executeQuery(selectQuery);
					
					if( rs.next())
					{	
						// Convert Date to MySQL format (yyyy-MM-dd)
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		                date = sdf.format(selectedDate);
						
						String insertQuery = "insert into service_request value ('"+requestId+"','"+user+"','"+reqType+"','"+"Pending"+"','"+date+"');";
						
						con.s.executeUpdate(insertQuery);
							
						JOptionPane.showConfirmDialog(ServiceRequest.this, "Request Added Successfully", "Successfull",JOptionPane.PLAIN_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(ServiceRequest.this, "Please Enter Valid UserName","User Not Present",JOptionPane.ERROR_MESSAGE);
					}
					
				}	
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});
		
		clearForm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				generateRequestID();
				
				requestField.setText(String.valueOf(requestId));
				
				userField.setText("");
				
				requestType.setSelectedIndex(0);
				
				dateSpinner.setValue(new Date());
			}
		});
		
		
		goBack.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        dispose(); // Close the current ServiceRequest frame
		        try {
					new UserDashBoard();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Open the UserDashboard frame
		    }
		});

	}
	
	private void createFormRow(String labelText, JComponent component, JPanel formPanel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setPreferredSize(new Dimension(800,30));
        rowPanel.setMinimumSize(new Dimension(200,30));
        
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setPreferredSize(new Dimension(150, 25));

        rowPanel.add(label);
        rowPanel.add(Box.createRigidArea(new Dimension(120, 0)));
        rowPanel.add(component);

        formPanel.add(rowPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
	
	 private JPanel createBackgroundPanelImg() {
	        return new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	            	
	            	 super.paintComponent(g);

	            	    Graphics2D g2d = (Graphics2D) g;
	            	    
	            	    Color topColor = new Color(60, 140, 190, 255);  // Lighter shade
	            	    
	            	    Color bottomColor = new Color(10, 20, 100, 255);  // Darker shade

	            	    GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);

	            	    g2d.setPaint(gradient);
	            	    g2d.fillRect(0, 0, getWidth(), getHeight());
	            }
	        };
	    }
	
	 private void initializeComponents() {
		 requestField = createStyledTextField(15);
		 userField = createStyledTextField(15);

		 String[] requests = {"Connection Issue","Speed Problem","Bill Related","Technical Support","Plan Upgrade","General Inquiry"};
		 
		 requestType = new JComboBox<String>(requests);
		 
		 createStyledComboBox(requestType);
		 
		 Date today = new Date();
		 
		 SpinnerDateModel dateModel = new SpinnerDateModel(today, null, today, java.util.Calendar.DAY_OF_MONTH);
		 
		 dateSpinner = new JSpinner(dateModel);
		 
		 JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
         dateSpinner.setEditor(editor);

         submitRequest = createStyledButton("Submit Request", new Color(41,128,185));
         clearForm = createStyledButton("Clear Form",  new Color(41,128,185));
         goBack = createStyledButton("Go Back",  new Color(41,128,185));
		 
//	        signInButton = createStyledButton("Sign In", new Color(30, 144, 255));
//	        goBackButton = createStyledButton("Go Back", new Color(220, 20, 60));
//
//	        termsBox = new JCheckBox("I agree to the terms & conditions");
//	        createStyledCheckBox(termsBox);
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
	 
	 private JButton createStyledButton(String text, Color background) {
	        JButton button = new JButton(text);
	        button.setBackground(background);
	        button.setForeground(Color.WHITE);
	        button.setFont(new Font("Arial", Font.BOLD, 15));

	        button.setOpaque(true);
	        button.setPreferredSize(new Dimension(200,40));
	        
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
	 
	 public void generateRequestID()
		{
			double max = 1000000000;
			double min = 1;
			
			requestId = (long) (Math.random()*(max-min)+1);
		}
	 
	 public void createStyledComboBox(JComboBox<String> cityComboBox)
		{
			cityComboBox.setFont(new Font("Arial",Font.BOLD,20));
			cityComboBox.setBackground(Color.white);
			cityComboBox.setForeground(Color.gray);
		}
	 
	
	 public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ServiceRequest();
				
			}
		});
	}
}