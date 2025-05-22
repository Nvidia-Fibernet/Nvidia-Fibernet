package nvidia.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UserProfile extends JFrame
{
	private static Long mobileNumber;
	private JLabel userNameLabel,mobileNumberLabel,planTypeLabel,planDurationLabel;
	 	
	
	public UserProfile() {
        setUpFrame();
       
       
    }
	
	private void setUpFrame() {
	        setTitle("Sign-In");
	        setSize(1366, 768);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());
	        setVisible(true);

	        setContentPane(createBackgroundPanelImg());
	    }
	
	 private JPanel createBackgroundPanelImg() 
	 {
	        return new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.jpg"));
	                Image image = icon.getImage();
	                double pwidth = getWidth();
	                double pheight = getHeight();
	                double imageWidth = image.getWidth(this);
	                double imageHeight = image.getHeight(this);
	                double scaled = Math.max(pwidth / imageWidth, pheight / imageHeight);
	                int scaledWidth = (int) (imageWidth * scaled);
	                int scaledHeight = (int) (imageHeight * scaled);
	                int x = (int) ((pwidth - scaledWidth) / 2);
	                int y = (int) ((pheight - scaledHeight) / 2);
	                g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
	                g.setColor(new Color(0, 0, 0, 150));
	                g.fillRect(0, 0, getWidth(), getHeight());
	            }
	        };
	    }
	 
	 private JPanel UserDetails() {
	        JPanel panel = new JPanel();
	        panel.setOpaque(false);
	        panel.setLayout(new GridLayout(4, 1, 10, 10));
	        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

	        userNameLabel = new JLabel("User Name: John Doe");
	        mobileNumberLabel = new JLabel("Mobile Number: " + mobileNumber);
	        planTypeLabel = new JLabel("Plan Type: Fiber Premium");
	        planDurationLabel = new JLabel("Plan Duration: 6 Months");

	        Font labelFont = new Font("Arial", Font.BOLD, 22);
	        Color fontColor = Color.WHITE;

	        for (JLabel label : new JLabel[]{userNameLabel, mobileNumberLabel, planTypeLabel, planDurationLabel}) {
	            label.setFont(labelFont);
	            label.setForeground(fontColor);
	            panel.add(label);
	        }

	        return panel;
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(UserProfile::new);
	    }
}