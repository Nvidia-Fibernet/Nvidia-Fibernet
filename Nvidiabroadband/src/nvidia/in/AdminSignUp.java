package nvidia.in;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class AdminSignUp extends JFrame
{
	private JTextField usernameTextField,passwordTextField;
	private JComboBox<String> cityComboBox;
	private JCheckBox termBox;
	private JButton signUp,goBack;
	
	public AdminSignUp()
	{
		setUpFrame();
		
		initializeComponents();
		addComponents();
		setUpListeners();
	}
	
	private void setUpFrame()
	{
		setSize(1366,768);
		setVisible(true);
		setContentPane(createBackGroundImage());
	}
	
	private void addComponents()
	{
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        JLabel label = new JLabel("Admin Regsiteration");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.white);
        titlePanel.add(label);
        
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(50));
        
        addForm("Admin Name : ", usernameTextField, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        addForm("Password : ", passwordTextField, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
//        mainPanel.add(termBox);
//        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        addComboBox("City : ", cityComboBox, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(signUp);
        buttonPanel.setPreferredSize(new Dimension(10, 50));
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(goBack);
       
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
	}
	
	
	private void addForm(String Label,JComponent comp, JPanel formPanel)
	{
		JPanel rowPanel = new JPanel();
		
		rowPanel.setLayout(new BoxLayout(rowPanel,BoxLayout.X_AXIS));
		
		JLabel theLabel = new JLabel(Label,SwingConstants.CENTER);
		
		theLabel.setFont(new Font("Arail",Font.BOLD,18));
		theLabel.setForeground(Color.white);
		theLabel.setPreferredSize(new Dimension(150, 0));
		rowPanel.add(theLabel);
		rowPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		rowPanel.add(comp);
		formPanel.add(rowPanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		rowPanel.setOpaque(false);
	}
	
	private void addComboBox(String Label,JComponent comp, JPanel formPanel)
	{
		JPanel rowPanel = new JPanel();
		
		rowPanel.setLayout(new BoxLayout(rowPanel,BoxLayout.X_AXIS));
		
		JLabel theLabel = new JLabel(Label,SwingConstants.CENTER);
		
		theLabel.setFont(new Font("Arail",Font.BOLD,18));
		theLabel.setForeground(Color.white);
		theLabel.setPreferredSize(new Dimension(170,0));
		rowPanel.add(theLabel);
		rowPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		rowPanel.add(comp);
		formPanel.add(rowPanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		rowPanel.setOpaque(false);
	}
	
	private JTextField createStyledTextFields(int width)
	{
		JTextField textField = new JTextField(width);
		textField.setFont(new Font("Arail",Font.BOLD,25));
		textField.setBackground(new Color(255,255,255));
		textField.setForeground(new Color(33,33,33));
		return textField;
	}
	
	public void createStyledComboBox(JComboBox<String> cityComboBox)
	{
		cityComboBox.setFont(new Font("Arial",Font.BOLD,20));
		cityComboBox.setBackground(Color.white);
		cityComboBox.setForeground(Color.gray);
	}
	
	private void initializeComponents()
	
	{
		usernameTextField = createStyledTextFields(13);
		passwordTextField = createStyledTextFields(13);
		
		String[] cities = {"Select a City","Pune" , "Banglore","Mangalore","Chennai"};
		
		cityComboBox = new JComboBox<String>(cities);
		
		createStyledComboBox(cityComboBox);
		
		termBox = createStyledCheckBox("I Agree to the terms and Conditions");
		Color baseColor = new Color(128, 128, 128); // Standard grey

		
		signUp = createStyledButton("Sign Up", baseColor);
		goBack = createStyledButton("Sign In", baseColor);
	}
	
	private JCheckBox createStyledCheckBox(String text)
	{
		JCheckBox checkBox = new JCheckBox(text);
		checkBox.setFont(new Font("Arial",Font.BOLD,18));
		checkBox.setForeground(Color.white);
		checkBox.setOpaque(false);
		return checkBox;
	}

	public JButton createStyledButton(String buttonTitle, Color baseColor) 
	{
	    JButton button = new JButton(buttonTitle);
	    button.setFont(new Font("Arial", Font.BOLD, 15));
	    button.setForeground(Color.white); // White text
	    button.setBackground(baseColor);   // Base grey color

	    button.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(Color.white, 2), // White border
	        BorderFactory.createEmptyBorder(12, 25, 12, 25)
	    ));

	    button.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            button.setBackground(Color.LIGHT_GRAY); // Light grey on hover
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            button.setBackground(baseColor); // Revert to base grey
	        }
	    });

	    button.setFocusPainted(false);
	    button.setBorderPainted(true);
	    button.setOpaque(true);
	    button.setContentAreaFilled(true);
	    button.setAlignmentX(JButton.CENTER_ALIGNMENT);
	    button.setPreferredSize(new Dimension(300, 50));
	    button.setMaximumSize(new Dimension(300, 50));

	    return button;
	}

	 
	 private void setUpListeners()
	 {
		 
		 goBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new HomePage();
			}
		});
		 
		 signUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleSignUp();
			}
		});
		 
		
	 }
	 
	
	 
	 private void handleSignUp()
		{
//			if(!termBox.isSelected())
//			{
//				errorMessage("Agree to Terms & Conditions");
//				return;
//			}
			
			
			String userName = usernameTextField.getText().trim();
			String password = passwordTextField.getText();
			long phno;
			
//			if(password.isEmpty())
//			{
//				phno = 0 ;
//			}	
//			else
//			{
//				phno = Long.parseLong(password);
//			}
			
//			
          	String city = (String)cityComboBox.getSelectedItem();
			
			
			if(userName.isEmpty() || password.isEmpty())
			{
				errorMessage("Please Fill the Required feilds firstly");
				return;
			}
			
			if(password.length()>=6)
			{
				ConnectionJDBC conn = new ConnectionJDBC();
					
					try 
					{
						int n = conn.s.executeUpdate("INSERT INTO admin_sign_in (username, password, city) VALUES ('" + userName + "', '" + password + "', '" + city + "');");
						
						if(n > 0)
						{
							JOptionPane.showMessageDialog(this, "Account Created","Account Creation",JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							errorMessage("Account Already Exists");
						}
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
			}
			else
			{
				errorMessage("Enter Valid Number");
			}
		}	
	 
	 private void errorMessage(String message)
		{
			JOptionPane.showMessageDialog(this, message , "ERROR",JOptionPane.ERROR_MESSAGE);
		}
	
	private JPanel createBackGroundImage()
	{
		return new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g) 
			{
				ImageIcon icon = new ImageIcon(getClass().getResource("/icons/nvidia.jpg"));
				Image image = icon.getImage();
				
				double pWidth = getWidth();
				double pHeight = getHeight();
				
				double imageWidth = image.getWidth(this);
				double imageHeight = image.getHeight(this);
				
				double scale = (Math.max(pWidth/imageWidth,pHeight/imageHeight));
				
				int scaledWidth = ((int)(imageWidth * scale));
				int scaledHeight = ((int)(imageHeight * scale));
				
				int x = ((int)((pWidth-scaledWidth)/2));
				int y = ((int) ((pHeight-scaledHeight)/2));
				
				g.drawImage(image, x,y,scaledWidth,scaledHeight,this);
				g.setColor(new Color(0,0,0,150));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new AdminSignUp();
				
			}
		});
		
	}
	
}