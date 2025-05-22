package nvidia.in;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

public class BuyConnectionn extends JFrame
{
	public static long accountNo,getPhoneNo;
	Plan selectedPlan;
	
	static final Color NVIDIA_GREEN = new Color(118, 185,0);
	static final Font TITLE_FONT = new Font("Segoe UI",Font.BOLD,28);
	static final Font SUBTITLE_FONT = new Font("Segoe UI",Font.PLAIN,14);
	static final Font PRICE_FONT = new Font("Segoe UI",Font.BOLD,24);
	
	public BuyConnectionn()
	{
		setTitle("Nvidia Fibernet Broadband");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 800);
		setBackground(Color.WHITE);
		//setVisible(true);
		
		GradientPanel mainPanel = new GradientPanel("/icons/logo.jpg");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setPreferredSize(new Dimension(950, 1200));
		
		addHeader(mainPanel);
		addPlans(mainPanel);
		addRegistrationForm(mainPanel);
		
		JScrollPane scrollPane = new JScrollPane(mainPanel);
	    scrollPane.setBorder(null);
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    add(scrollPane);
	    setLocationRelativeTo(null);
	    setVisible(true);
		//add(mainPanel);	
	}
	
	public void generateAccountNo()
	{
		double max = 1000000000;
		double min = 1;
		
		accountNo = (long) (Math.random()*(max-min)+1);
	}
	
	public void addHeader(JPanel mainPanel)
	{
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel("Nvidia Fiberner");
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(NVIDIA_GREEN);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel subtitle = new JLabel("Experience Lightning-Fast Internet");
		subtitle.setFont(SUBTITLE_FONT);
		subtitle.setForeground(Color.DARK_GRAY);
		subtitle.setAlignmentX(CENTER_ALIGNMENT);
		
		headerPanel.add(titleLabel);
		headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		headerPanel.add(subtitle);
		headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		mainPanel.add(headerPanel);
	}
	
	public ArrayList<Plan> createPlans()
	{
		ArrayList<Plan> plans = new ArrayList<>();
		plans.add(new Plan("Basic", 49.99, "500GB", "100 Mbps", "30 Days",
		           new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime Basic")),
		           new Color(135, 206, 235)));
		       plans.add(new Plan("Standard", 69.99, "1000GB", "300 Mbps", "30 Days",
		           new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Standard")),
		           new Color(100, 149, 237)));
		       plans.add(new Plan("Premium", 89.99, "2000GB", "500 Mbps", "30 Days",
		           new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Premium", "HBO Max")),
		           new Color(65, 105, 225)));
		       plans.add(new Plan("Ultra", 119.99, "4000GB", "1 Gbps", "30 Days",
		           new ArrayList<>(Arrays.asList("Peacock", "Amazon Prime", "Hulu Premium", "HBO Max", "ESPN+")),
		           new Color(0, 0, 139)));
		       plans.add(new Plan("Gamer Pro", 149.99, "Unlimited", "2 Gbps", "30 Days",
		           new ArrayList<>(Arrays.asList("All Streaming Apps", "Gaming Server Priority", "Cloud Gaming")),
		           NVIDIA_GREEN));

		
		return plans;
	}
	
	public void addPlans(JPanel mainPanel)
	{
		JPanel plansPanel = new JPanel();
		plansPanel.setLayout(new GridLayout(0, 3, 20, 20));
		plansPanel.setOpaque(false);
		plansPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));
		
		ArrayList<Plan> plans = createPlans();
		ButtonGroup planGroup = new ButtonGroup();
		
		for (int i = 0; i < plans.size(); i++) 
		{
	           Plan plan = plans.get(i);
	          
	           RoundPanel planCard = new RoundPanel(Color.WHITE);
	           planCard.setLayout(new BoxLayout(planCard, BoxLayout.Y_AXIS));
	           planCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	           
	           // Plan type header
	           JLabel typeLabel = new JLabel(plan.planType);
	           typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	           typeLabel.setForeground(plan.themeColor);
	           typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	           
	           // Price
	           JLabel priceLabel = new JLabel("\u0024" + String.format("%.2f", plan.planPrice) + "/month");
	           priceLabel.setFont(PRICE_FONT);
	           priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	           
	           // Features panel
	           JPanel featuresPanel = new JPanel();
	           featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
	           featuresPanel.setOpaque(false);
	           addFeature(featuresPanel, "\uD83D\uDE80 " + plan.planSpeed);
	           addFeature(featuresPanel, "\uD83D\uDCCA " + plan.planData);
	           addFeature(featuresPanel, "\u23F1\uFE0F " + plan.planDuration);
	           addFeature(featuresPanel, "\uD83D\uDCF1 Apps: " + String.join(", ", plan.appSubscriptions));
	           
	           // Radio button
	           JRadioButton radioBtn = new JRadioButton("Select Plan");
	           radioBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	           radioBtn.setForeground(plan.themeColor);
	           radioBtn.addActionListener(new ActionListener() 
	           {
	               public void actionPerformed(ActionEvent e) 
	               {
	                   selectedPlan = plan;
	               }
	           });
	           planGroup.add(radioBtn);
	           
	           // Add components to card
	           planCard.add(typeLabel);
	           planCard.add(Box.createRigidArea(new Dimension(0, 10)));
	           planCard.add(priceLabel);
	           planCard.add(Box.createRigidArea(new Dimension(0, 15)));
	           planCard.add(featuresPanel);
	           planCard.add(Box.createRigidArea(new Dimension(0, 15)));
	           planCard.add(radioBtn);
	           plansPanel.add(planCard);
	           
	           mainPanel.add(plansPanel);
	       }	
	}
	
	private void addFeature(JPanel panel, String feature)
	{
		JLabel title = new JLabel(feature);
		title.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		title.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		panel.add(title);
	}
	
	private void addRegistrationForm(JPanel mainPanel)
	{
		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,20));
		
		JLabel formTitle = new JLabel("Complete Your Registration");
		formTitle.setFont(new Font("Segoe", Font.BOLD, 20));
		formTitle.setAlignmentX(CENTER_ALIGNMENT);
		formTitle.setForeground(NVIDIA_GREEN);
		
		JTextField mobilefield = addStyledTextFields("Enter Mobile Number");
		JTextField userNameField = addStyledTextFields("Enter Username");
		
		JComboBox<String> cities = createStyledComboBox();
		
		JButton submitButton = new JButton("Register Now");
		submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		submitButton.setForeground(Color.BLACK);
		submitButton.setBackground(NVIDIA_GREEN);
		submitButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		submitButton.setAlignmentX(CENTER_ALIGNMENT);
		submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		submitButton.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleSubmitButton(mobilefield, userNameField, cities);
			}
		});
		
		formPanel.add(formTitle);
		formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		formPanel.add(mobilefield);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		formPanel.add(userNameField);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		formPanel.add(cities);
		formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		formPanel.add(submitButton);
		mainPanel.add(formPanel);
	}
	
	public void handleSubmitButton(JTextField mobileField, JTextField userNameField, JComboBox<String> cityCombo)
	{
		if(selectedPlan == null)
		{
			JOptionPane.showMessageDialog(null, "Please Select a Plan First!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(mobileField.getText().isEmpty()||  userNameField.getText().isEmpty() || cityCombo.getSelectedIndex()==0)
		{
			JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		long phoneNo;
		try 
		{
			phoneNo = Long.parseLong(mobileField.getText().trim());
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Invalid Phone number", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(phoneNo != getPhoneNo)
		{
			System.out.println(phoneNo);
			System.out.println(getPhoneNo);
			JOptionPane.showMessageDialog(null, "Please Use the same phone number to register");
			return;
		}
		
		//String query=null;
		String insertquery=null;
		String updatequery=null;
		String selectquery=null;
		
		
	       String planType =selectedPlan.planType;
	       double planPrice=selectedPlan.planPrice;
	       String planData=selectedPlan.planData;
	       String planDuration=selectedPlan.planDuration;
	       String speed=selectedPlan.planSpeed;
	     
	       ConnectionJDBC con = new ConnectionJDBC();
	       
	      // generateAccountNo();
	       
	       selectquery = "select * from broadband_plans where mobilenumber = '"+phoneNo+"'";
	       
	       try 
	       {
	    	   ResultSet rs = con.s.executeQuery(selectquery);
	    	   
	    	   if(rs.next())
	    	   {
	    		   
	    			int option = JOptionPane.showConfirmDialog(this, "Do You Want To Update Your Plan ?", "Confirm", JOptionPane.YES_NO_OPTION,
	    					JOptionPane.QUESTION_MESSAGE);
	    			
	    			if (option == JOptionPane.YES_OPTION) 
	    			{
	    				updatequery = "UPDATE broadband_plans " + "SET planType = '" + planType + "', " + "planPrice = '" + planPrice + "', " + "planData = '" + planData + "', " +"speed = '" + speed + "', " + "planDuration = '" + planDuration + "' " + "WHERE mobileNumber = '" + phoneNo + "';";

	  	    		  	con.s.executeUpdate(updatequery);

	  	    		  	showSuccess("Plan Updated!\nPlan: " + selectedPlan.planType +"\nCity: " + cityCombo.getSelectedItem()+"/n"+"Please Re-Login To Load The New Plan");
	    			} 
	    			else 
	    			{
	    				
	    			}
	    		  
	    	   }  
	    	   else
	    	   {
	    		   generateAccountNo();
	    		   
	    		   insertquery="insert into broadband_plans (mobilenumber, planType, planPrice, planData, speed, planDuration, accountNumber)" + "\n" +
	    		       		"values('"  + phoneNo + "'," + "'" + planType + "'," + "'" + planPrice + "'," + "'" + planData + "'," + "'" + speed + "'," + "'" + planDuration+"'," + "'"+accountNo+"');";
	    		   
	    		   con.s.executeUpdate(insertquery);
	    		   
	    		   showSuccess("Registration successful!\nPlan: " + selectedPlan.planType +
   	               "\nCity: " + cityCombo.getSelectedItem()+"/n"+"Please Re-Login To Load The New Plan");
	    	   }
	       } 
	       catch (SQLException e) 
	       {
	    	   	e.printStackTrace();
	       }
	       
	       
	       
//	       insertquery="insert into broadband_plans (mobileNumber, planType, planPrice, planData, speed, planDuration, accountNo)" + "\n" +
//	       		"values('"  + phoneNo + "'," + "'" + planType + "'," + "'" + planPrice + "'," + "'" + planData + "'," + "'" + speed + "'," + "'" + planDuration+"'," + "'"+accountNo+"');";
//	      
//	       try 
//	       {
//	    	   con.s.executeUpdate(insertquery);
//	       } 
//	       catch (SQLException e) 
//	       {
//				e.printStackTrace();
//			}
//	      
//	       showSuccess("Registration successful!\nPlan: " + selectedPlan.planType +
//	               "\nCity: " + cityCombo.getSelectedItem()+"/n"+"Please Re-Login To Load The New Plan");
	}
	
	public void checkExistingAccount(ConnectionJDBC con)
	{
		
		String query = "Select * from broadband_plans where mobilenumber = '"+getPhoneNo+"'";
		
		try {
			con.s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void showSuccess(String msg)
	{
		JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public JComboBox<String> createStyledComboBox()
	{
		String [] cities = {"Select City" , "Austin", "Texas", "Raleigh" , "North Carolina" , "Kansas City", "Missouri", "New York City", "Brooklyn", "Manhattan - New York City"};
		JComboBox<String> combo = new JComboBox<String>(cities);
		combo.setMaximumSize(new Dimension(300, 40));
		combo.setAlignmentX(CENTER_ALIGNMENT);
		combo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		return combo;
	}
	
	private void setPlaceHolder(final JTextComponent textComponent, String placeholder)
	{
		textComponent.setText(placeholder);
		textComponent.setForeground(Color.GRAY);
		
		textComponent.addFocusListener(new FocusListener() 
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				if (textComponent.getText().equals(placeholder)) 
				{
					textComponent.setText("");
	                textComponent.setForeground(Color.DARK_GRAY); 
	            }
			}
			@Override
			public void focusLost(FocusEvent e) 
			{
				if (textComponent.getText().isEmpty()) 
				{
					textComponent.setText(placeholder);
	                textComponent.setForeground(Color.GRAY);                
	            }

			}
		});
	}
	
	public JTextField addStyledTextFields(String placeholder)
	{
		JTextField textField = new JTextField(20);
		textField.setFont(new Font("Arial", Font.PLAIN, 16));
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		textField.setMaximumSize(new Dimension(300, 40));
		textField.setAlignmentX(CENTER_ALIGNMENT);
		setPlaceHolder(textField, placeholder);
		return textField;
	}
	
	public static void main(String[] args) 
	{
		new BuyConnectionn();
	}
	
}
	
	class RoundPanel extends JPanel
	{
		private Color backgroundColor;
		private int radius;
		
		public RoundPanel(Color backgroundColor)
		{
			this.backgroundColor = backgroundColor;
			setOpaque(false);
			
		}
		
		@Override
		protected void paintComponent(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(backgroundColor);
			g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
		}
	}
	
	
	class GradientPanel extends JPanel
	{
		private Image backgroundImage;
		
		public GradientPanel(String imagePath)
		{
			java.net.URL imageURL = getClass().getResource(imagePath);
			
			if(imageURL != null)
			{
				backgroundImage = new ImageIcon(imageURL).getImage();
			}
			else
			{
				System.out.println("Image Not Found");
			}
			setOpaque(false);
		}
		
		@Override
		protected void paintComponent(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
			if(backgroundImage != null)
			{
				g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(),this);
			}
			
			int w = getWidth();
		    int h = getHeight();
		    Color color1 = new Color(76, 161, 175, 200); // Added alpha for transparency
		    Color color2 = new Color(196, 224, 229, 200); // Added alpha for transparency
		    GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
		    g2.setPaint(gp);
		    g2.fillRect(0, 0, w, h);	
		}
	}
	
	
	class Plan 
	{
		String planType;
		double planPrice;
		String planData;
		String planSpeed;
		String planDuration;
		ArrayList<String> appSubscriptions;
		Color themeColor;
		public Plan(String planType, double planPrice, String planData, String planSpeed, String planDuration, ArrayList<String> appSubscriptions, Color themeColor) 
		{
			this.planType = planType;
			this.planPrice = planPrice;
			this.planData = planData;
			this.planSpeed = planSpeed;
			this.planDuration = planDuration;
			this.appSubscriptions = appSubscriptions;
			this.themeColor = themeColor;
		}
		
	}