package nvidia.in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class AdminSignIn extends JFrame {

	private JTextField userNameField;
	private JPasswordField passwordField;
	private JCheckBox termsBox;
	private JButton signInButton, goBackButton, adminSignUp;

	public AdminSignIn() {
		setUpFrame();
		initializeComponents();
		addComponents();

	}

	private void setUpFrame() {
		setTitle("Admin Sign-In");
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
				ImageIcon icon = new ImageIcon(getClass().getResource("/icons/nvidia.jpg"));
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
		Color baseColor = new Color(128, 128, 128); // Standard grey

		userNameField = createStyledTextField(20);
		passwordField = createStyledPasswordField(20);

		signInButton = createStyledButton("Sign In", baseColor);
		goBackButton = createStyledButton("Go Back", baseColor);
		adminSignUp = createStyledButton("Admin Sign - Up", baseColor);

		termsBox = new JCheckBox("I agree to the terms & conditions");
		createStyledCheckBox(termsBox);
	}

	private JTextField createStyledTextField(int columns) {
		JTextField field = new JTextField(columns);
		field.setBackground(Color.WHITE);
		field.setForeground(new Color(33, 33, 33));
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		return field;
	}

	private JPasswordField createStyledPasswordField(int columns) {
		JPasswordField field = new JPasswordField(columns);
		field.setBackground(Color.WHITE);
		field.setForeground(new Color(33, 33, 33));
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		return field;
	}

	private JButton createStyledButton(String text, Color background) {
		JButton button = new JButton(text);
		button.setBackground(background);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 15));
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createEmptyBorder(12, 25, 12, 25)));
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

		JLabel titleLabel = new JLabel("Admin Sign-In", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setOpaque(false);
		formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		createFormRow("Username : ", userNameField, formPanel);
		createFormRow("Password : ", passwordField, formPanel);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		contentPanel.add(formPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
		buttonPanel.setOpaque(false);
		buttonPanel.add(signInButton);
		buttonPanel.add(goBackButton);
		buttonPanel.add(adminSignUp);

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

		adminSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				handleSignUp();
			}
		});
	}

	private void handleSignIn() {

		String username = userNameField.getText().trim();
		String password = new String(passwordField.getPassword());

		String query = "Select * from admin_sign_in where username = '" + username + "' and password = '"
				+ password + "';";

		ConnectionJDBC connection = new ConnectionJDBC();

		try {
			ResultSet rs = connection.s.executeQuery(query);

			if (rs.next()) {
				JOptionPane.showMessageDialog(this, "Welcome Back " + username, "Success",
						JOptionPane.OK_CANCEL_OPTION);
				
				AdminDashboard.adminName = userNameField.getText().trim();
				AdminDashboard.password = new String(passwordField.getPassword());

//				AdminDashboard.adminName = userNameField.getText().trim();
//				AdminDashboard.password = new String(passwordField.getPassword());

				new AdminDashboard();

				dispose();

			} else {
				showError("Invalid Credentials");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleGoBack() {
		int choice = JOptionPane.showConfirmDialog(this, "Go Back To Main Menu?", "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			SwingUtilities.invokeLater(() -> new HomePage());
			dispose();
		}
	}

	private void handleSignUp() {
		int choice = JOptionPane.showConfirmDialog(this, "Go Back To Main Menu?", "Confirm", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			new AdminSignUp();
			dispose();
		}
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AdminSignIn();
			}
		});
	}

}