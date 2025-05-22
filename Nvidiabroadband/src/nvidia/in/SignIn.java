package nvidia.in;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
public class SignIn extends JFrame {
    private JTextField userNameField, mobileNumberField;
    private JCheckBox termsBox;
    private JButton signInButton, goBackButton;
    private static String mobile;

    public SignIn() {
        setUpFrame();
        initializeComponents();
        addComponents();
       
    }

    private void setUpFrame() {
        setTitle("Sign-In");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        setContentPane(createBackgroundPanelImg());
    }

    private JPanel createBackgroundPanelImg() {
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
    private void initializeComponents() {
        userNameField = createStyledTextField(20);
        mobileNumberField = createStyledTextField(20);

        signInButton = createStyledButton("Sign In", new Color(30, 144, 255));
        goBackButton = createStyledButton("Go Back", new Color(220, 20, 60));

        termsBox = new JCheckBox("I agree to the terms & conditions");
        createStyledCheckBox(termsBox);
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
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        button.setOpaque(true);
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

    private void createStyledCheckBox(JCheckBox checkBox) {
        checkBox.setForeground(Color.WHITE);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
    }

    private void createFormRow(String labelText, JComponent component, JPanel formPanel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(120, 25));

        rowPanel.add(label);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        rowPanel.add(component);

        formPanel.add(rowPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(Box.createVerticalStrut(260));

        JLabel titleLabel = new JLabel("Sign-In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        createFormRow("Username:", userNameField, formPanel);
        createFormRow("Mobile Number:", mobileNumberField, formPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(termsBox);

        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.add(signInButton);
        buttonPanel.add(goBackButton);

        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        add(contentPanel);

        signInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleSignIn();
			}
		});
        goBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleGoBack();
			}
		});
    }

    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\d{10}");
    }

    private void handleSignIn() {
        if (!termsBox.isSelected()) {
            showError("You must accept the terms and conditions!");
  
            return;
        }

        String username = userNameField.getText().trim();
        mobile = mobileNumberField.getText().trim();
        UserDashBoard.phoneNo = Long.parseLong(mobile);
        
        if (!isValidMobile(mobile)) {
            showError("Invalid mobile number! Enter exactly 10 digits.");
            return;
        }

        String query = "Select * from sign_in where userName = '" + username + "' and mobileNumber = '" + mobile
				+ "';";

		ConnectionJDBC connection = new ConnectionJDBC();

		try {
			ResultSet rs = connection.s.executeQuery(query);

			if (rs.next()) 
			{
				JOptionPane.showMessageDialog(this, "Welcome Back " + username, "Success",
						JOptionPane.OK_CANCEL_OPTION);
			
				
				   
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						try 
						{
							new UserDashBoard();
							dispose();
						} 
						catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				showError("Invalid Credentials");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}    
		}

    private void handleGoBack() {
        int choice = JOptionPane.showConfirmDialog(this, "Go Back To Main Menu?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> new HomePage());
            dispose();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new SignIn();
			}
		});
    }
}
