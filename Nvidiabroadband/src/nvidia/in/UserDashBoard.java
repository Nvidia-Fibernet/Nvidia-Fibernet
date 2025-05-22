package nvidia.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UserDashBoard extends JFrame
{
	static String status;
	static long phoneNo;
	static String planType;
	static double planPrice;
	static String planData;
	static String speed;
	static String planDuration;
	public static long accountNo;
	
	public UserDashBoard() throws SQLException
	{
		setUpFrame();
		JPanel panel = createHeader();
		add(panel,BorderLayout.NORTH);
		
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel rightPanel = createAccountDetailsPanel();
		mainPanel.add(rightPanel,BorderLayout.CENTER);
		
		JPanel leftSide = createPromotionalPanel();
		mainPanel.add(leftSide,BorderLayout.WEST);
		
		add(mainPanel,BorderLayout.CENTER);
		
		getContentPane().setBackground(new Color(240, 240, 240));
		
		System.out.println(phoneNo);
	}
	
	public boolean getStatus() throws SQLException
	{
		ConnectionJDBC con = new ConnectionJDBC();
		
		String query = "Select * from broadband_plans where mobilenumber = '"+phoneNo+"'";
		
		ResultSet rs;
		rs = con.s.executeQuery(query);
		
		if(rs.next())
		{
			phoneNo = rs.getLong("mobileNumber");
			planType = rs.getString("planType");
			planPrice = rs.getDouble("planPrice");
			planData = rs.getString("planData");
			speed = rs.getString("speed");
			planDuration = rs.getString("planDuration");
			accountNo = rs.getLong("accountNumber");
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public JPanel createAccountDetailsPanel() throws SQLException
	{
		JPanel detailsPanel = new JPanel();
		
		detailsPanel.setBackground(Color.WHITE);
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20,20, 20));
		
		if(getStatus())
		{
			status = "ACTIVE";
		}	
		else
		{
			status = "INACTIVE";
			planType = "NA";
			speed = "NA";
			planData = "NA";		
		}
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel statusLabel = new JLabel(UserDashBoard.status);
		statusLabel.setBackground(Color.WHITE);
		statusLabel.setFont(new Font("Arail",Font.BOLD,14));
		statusLabel.setForeground(new Color(40,167,69));
		
		statusPanel.add(statusLabel);
		
		
		JPanel planPanel = new JPanel();
		planPanel.setLayout(new BoxLayout(planPanel, BoxLayout.Y_AXIS));
		planPanel.setBackground(Color.WHITE);
		
		JLabel planName = new JLabel(UserDashBoard.planType);
		planName.setFont(new Font("Arial",Font.BOLD,18));
		planPanel.add(planName);
		
		JPanel duePanel = new JPanel();
		duePanel.setLayout(new BoxLayout(duePanel, BoxLayout.Y_AXIS));
		duePanel.setBackground(Color.WHITE);
		
		JLabel dueDate = new JLabel("25th February 2025");
		dueDate.setFont(new Font("Arial",Font.BOLD,14));
		
		duePanel.add(dueDate);
		
		JLabel dueDateLabel = new JLabel("Due Date");
		dueDateLabel.setFont(new Font("Arial",Font.BOLD,12));
		
		duePanel.add(dueDateLabel);
		
		JPanel usagePanel = new JPanel();
		usagePanel.setBackground(Color.WHITE);
		
		JLabel usageLabel = new JLabel("45.6 GB of 150 GB");
		usageLabel.setFont(new Font("Arial",Font.PLAIN,12));
		
		usagePanel.add(usageLabel);
		
		JPanel upgradePanel = new JPanel();
		
		upgradePanel.setBackground(new Color(255,240,240));
		upgradePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel upgradeLabel = new JLabel("Upgrade to enjoy NetFlix at no Extra Cost , higher speeds & great offers");
		upgradeLabel.setFont(new Font("Arial",Font.PLAIN,12));
		upgradePanel.add(upgradeLabel);
		
		JButton upgradeButton = new JButton("Upgrade");
		upgradeButton.setBackground(Color.WHITE);
		upgradeButton.setFocusPainted(false);
		
		upgradeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new BuyConnectionn();
			}
		});
		
		upgradePanel.add(upgradeLabel);
		upgradePanel.add(upgradeButton);
		
		detailsPanel.add(statusPanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		detailsPanel.add(planPanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		detailsPanel.add(duePanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		detailsPanel.add(usagePanel);
		detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		detailsPanel.add(upgradePanel);
		
		return detailsPanel;
	}
	
	public void setUpFrame()
	{
		setSize(1920,1080);
		setVisible(true);
		setLayout(new BorderLayout());
		setTitle("User DashBoard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JButton createStyledButtons(String text)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Ariail",Font.PLAIN,14));
		button.setForeground(new Color(51,51,51,150));
		button.setBackground(Color.white);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
		
		button.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setFont(new Font("Arail",Font.BOLD,14));
				button.setForeground(new Color(255,51,85));
			}
			
			
			@Override
			public void mouseExited(MouseEvent e) {

				button.setFont(new Font("Arail",Font.PLAIN,14));
				button.setForeground(new Color(51,51,51,150));
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				switch (text) 
				{
	               case "Pay Bills":
	                   SwingUtilities.invokeLater(new Runnable() {
	                       public void run() {
	                           try {
	                               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                           } catch (Exception ex) {
	                               ex.printStackTrace();
	                           }
	                           new PaymentGUI();
	                       }
	                   });
	                   break;
	               case "Service Requests":
	                   if (UserDashBoard.status == "INACTIVE") {
	                       JOptionPane.showMessageDialog(UserDashBoard.this,
	                               "Inactive Plan. Service Request Cannot Be Raised",
	                               "Failed",
	                               JOptionPane.INFORMATION_MESSAGE);
	                   } else {
	                       try {
	                           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                       } catch (Exception ex) {
	                           ex.printStackTrace();
	                       }
	                       new ServiceRequest();
	                   }
	                   break;
	               case "New Connection":
	            	   
	            	  BuyConnectionn.getPhoneNo = phoneNo;
	            	   
	                   try
	                   {
	                       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                   } 
	                   catch (ClassNotFoundException e2) 
	                   {
	                	   e2.printStackTrace();
	                   }
	                   catch (InstantiationException e3) 
	                   {
	                	   e3.printStackTrace();
	                   }
	                   catch (IllegalAccessException e4) 
	                   {
	                	   e4.printStackTrace();
	                   }
	                   catch (UnsupportedLookAndFeelException e5) 
	                   {
	                	   e5.printStackTrace();
	                   }
	                   new BuyConnectionn();
	                   break;
	               case "FAQ":
	            	   new FAQ();
	                   break;
	           }

				
			}
		});
	
		
		return button ;
	}
	
	public JPanel createHeader()
	{
		JPanel header = new JPanel();
		
		header.setLayout(new BorderLayout());
		header.setBackground(Color.WHITE);
		header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		JLabel titleLabel = new JLabel("Nvidia Fibernet");
		titleLabel.setForeground(new Color(255,51,85));
		titleLabel.setFont(new Font("Arial",Font.BOLD,24));
		
		JPanel navMenu = new JPanel();
		navMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		navMenu.setBackground(Color.WHITE);
		
		String[] menuItems = {"Pay Bills","Service Requests","New Connection","FAQ"};
		
		for (String item : menuItems) 
		{
			JButton menuButtons = createStyledButtons(item);
			navMenu.add(menuButtons);
		}
		
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		dropDownPanel.setBackground(Color.WHITE);
		
		JButton dropDownButton = new JButton("Options");
		dropDownButton.setFont(new Font("Arial",Font.PLAIN,14));
		dropDownButton.setForeground(new Color(51,51,51));
		dropDownButton.setBackground(Color.WHITE);
		dropDownButton.setFocusPainted(false);
		dropDownButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JPopupMenu optionMenus = new JPopupMenu();
		
		JMenuItem profileOption = new JMenuItem("Profile");
		JMenuItem logOutOption = new JMenuItem("Log Out");
		
		profileOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new Profile(phoneNo);
			}
		});

		logOutOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int choice = JOptionPane.showConfirmDialog(null,"Do You Really want to Log Out ?","Log Out",JOptionPane.YES_NO_OPTION);
				
				if(choice == JOptionPane.YES_OPTION)
				{
					dispose();
					new HomePage();
				}
				
			}
		});
		
		
		optionMenus.add(profileOption);
		optionMenus.add(logOutOption);
		
		dropDownButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				optionMenus.show(dropDownButton, 0, dropDownButton.getHeight());
			}
		});
		
		
		dropDownPanel.add(dropDownButton);
		
		header.add(titleLabel,BorderLayout.WEST);
		header.add(navMenu,BorderLayout.CENTER);
		header.add(dropDownPanel, BorderLayout.EAST);
		
		return header;
	}
	
	
	public JPanel createPromotionalPanel()
	{
		JPanel promotionalPanel = new JPanel(new BorderLayout());
		promotionalPanel.setBackground(Color.WHITE);
		
		JPanel gradientPanel = new JPanel() {
	         @Override
	         protected void paintComponent(Graphics g) {
	             super.paintComponent(g);
	             Graphics2D g2d = (Graphics2D) g;
	             GradientPaint gradient = new GradientPaint(
	                     0, 0, new Color(255, 100, 150),
	                     getWidth(), getHeight(), new Color(255, 150, 200)
	             );
	             g2d.setPaint(gradient);
	             g2d.fillRect(0, 0, getWidth(), getHeight());
	         }
	     };

	 
	     JLabel promoText = new JLabel("<html><div style='text-align: center;'>" +
	                  "Pay your Nvidia Fibernet<br/>bill via CRED UPI and Get<br/>" +
	                  "<span style='font-size: 24px;'>up to Rs. 250*</span><br/>" +
	                  "Cashback</div></html>");
	          promoText.setHorizontalAlignment(JLabel.CENTER);
	          promoText.setFont(new Font("Arial", Font.BOLD, 18));
	          promoText.setForeground(Color.WHITE);
	          gradientPanel.setLayout(new GridBagLayout());
	          gradientPanel.add(promoText);


	         gradientPanel.add(promoText);
	         promotionalPanel.add(gradientPanel,BorderLayout.CENTER);
	         
	    return promotionalPanel; 
	}
	
	
	public static void main(String[] args) 
	{
		try {
			new UserDashBoard();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}