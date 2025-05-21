package tyss.in;

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

public class UserRegistration extends JFrame
{
	private JTextField usernameTextField, mobileNumberTextField;
	private JComboBox<String> cityComboBox;
	private JCheckBox termBox;
	private JButton signUp,signIn;
	
	public UserRegistration()
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

        JLabel label = new JLabel("User Regsiteration");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.white);
        titlePanel.add(label);
        
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(50));
        
        addForm("User Name : ", usernameTextField, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        addForm("Mobile Number :", mobileNumberTextField, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        addComboBox("City :", cityComboBox, mainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(termBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(signIn);
        buttonPanel.setPreferredSize(new Dimension(10, 50));
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(signUp);
       
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
		mobileNumberTextField = createStyledTextFields(13);
		
		String[] cities = {"Select a City", "Austin", "Texas", "Raleigh", "North","Carolina",
				"New York City","kansas City","Missouri","Brooklyn","Manhattan"};
		
		cityComboBox = new JComboBox<String>(cities);
		
		createStyledComboBox(cityComboBox);
		
		termBox = createStyledCheckBox("I Agree to the terms and Conditions");
		
		signUp = createStyledButton("Sign Up", new Color(30, 144, 255));
		signIn = createStyledButton("Sign In", new Color(220, 20, 60));
	}
	
	private JCheckBox createStyledCheckBox(String text)
	{
		JCheckBox checkBox = new JCheckBox(text);
		checkBox.setFont(new Font("Arial",Font.BOLD,18));
		checkBox.setForeground(Color.white);
		checkBox.setOpaque(false);
		return checkBox;
	}

	 public JButton createStyledButton(String buttonTitle, Color color) 
	 {
	        JButton button = new JButton(buttonTitle);
	        button.setFont(new Font("Arial", Font.BOLD, 15));
	        button.setBackground(Color.white);
	        button.setForeground(Color.black);

	        button.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(color.darker(), 3),
	            BorderFactory.createEmptyBorder(12, 25, 12, 25)
	        ));
	        button.setBackground(color);

	        button.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	            	button.setBorder(BorderFactory.createCompoundBorder(
	                        BorderFactory.createLineBorder(color.brighter(), 3),
	                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
	                    ));
	            	button.setBackground(color.darker());
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	            	button.setBorder(BorderFactory.createCompoundBorder(
	                        BorderFactory.createLineBorder(color.darker(), 3),
	                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
	                    ));
	            	button.setBackground(color);
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
		 
		 signIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new SignIn();
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
			if(!termBox.isSelected())
			{
				errorMessage("Agree to Terms & Conditions");
				return;
			}
			
			
			String userName = usernameTextField.getText().trim();
			String mobileNumber = mobileNumberTextField.getText();
			long phno;
			
			if(mobileNumber.isEmpty())
			{
				phno = 0 ;
			}	
			else
			{
				phno = Long.parseLong(mobileNumber);
			}
			
			
			String city = (String)cityComboBox.getSelectedItem();
			
			
			if(userName.isEmpty() || mobileNumber.isEmpty() || city.equals("Select a City"))
			{
				errorMessage("Please Fill the Required feilds firstly");
				return;
			}
			
			if((mobileNumber.charAt(0) == '9' || mobileNumber.charAt(0) == '8' || mobileNumber.charAt(0) == '7' || mobileNumber.charAt(0) == '6') && mobileNumber.length()==10)
			{
				ConnectionJDBC conn = new ConnectionJDBC();
					
					try 
					{
						int n = conn.s.executeUpdate("Insert into sign_in value('"+userName+"' , '"+phno+"' , '"+city+"');");
						
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
				ImageIcon icon = new ImageIcon(getClass().getResource("/icons/nvidiabackground.jpg"));
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
				new UserRegistration();
				
			}
		});
	}
	
}

