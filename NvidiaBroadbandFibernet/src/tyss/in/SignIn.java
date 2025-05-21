package tyss.in;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.sql.*;

import javax.swing.*;

public class SignIn extends JFrame {
    private JTextField userNameField, mobileNumberField;
    private JCheckBox termsBox;
    private JButton signInButton, goBackButton;
    private static String mobile;
    private Color primaryColor = new Color(75, 0, 130);      // Deep Purple
    private Color accentColor = new Color(0, 191, 255);      // Deep Sky Blue
    private Color errorColor = new Color(220, 20, 60);       // Crimson
    private Font titleFont = new Font("Arial", Font.BOLD, 28);
    private Font labelFont = new Font("Arial", Font.BOLD, 16);
    private Font buttonFont = new Font("Arial", Font.BOLD, 16);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);

    public SignIn() {
        setUpFrame();
        initializeComponents();
        addComponents();
    }

    private void setUpFrame() {
        setTitle("NVIDIA - Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createBackgroundPanelImg());
        
        // Set to full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); // Remove window decorations for true fullscreen
        
        // Add key listener to allow escape to exit fullscreen
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    dispose();
                    System.exit(0);
                }
            }
        });
        
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    private JPanel createBackgroundPanelImg() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Cast to Graphics2D for better rendering
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Draw the background image
                ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.jpg"));
                Image image = icon.getImage();
                
                // Fill the entire screen with the image
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double pwidth = screenSize.getWidth();
                double pheight = screenSize.getHeight();
                
                double imageWidth = image.getWidth(this);
                double imageHeight = image.getHeight(this);
                double scaled = Math.max(pwidth / imageWidth, pheight / imageHeight);
                int scaledWidth = (int) (imageWidth * scaled);
                int scaledHeight = (int) (imageHeight * scaled);
                int x = (int) ((pwidth - scaledWidth) / 2);
                int y = (int) ((pheight - scaledHeight) / 2);
                g2d.drawImage(image, x, y, scaledWidth, scaledHeight, this);
                
                // Create a gradient overlay for the background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 0, 0, 200),
                    getWidth(), getHeight(), new Color(0, 0, 50, 220)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
    }

    private void initializeComponents() {
        userNameField = createStyledTextField(20);
        mobileNumberField = createStyledTextField(20);

        signInButton = createStyledButton("Sign In", accentColor);
        goBackButton = createStyledButton("Go Back", errorColor);

        termsBox = new JCheckBox("I agree to the terms & conditions");
        createStyledCheckBox(termsBox);
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(new Color(245, 245, 245));
        field.setForeground(new Color(33, 33, 33));
        field.setFont(fieldFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 40));
        return field;
    }

    private JButton createStyledButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setBackground(Color.red);
        button.setForeground(Color.BLACK);
        button.setFont(buttonFont);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(background.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(background.darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(background);
            }
        });
        return button;
    }

    private void createStyledCheckBox(JCheckBox checkBox) {
        checkBox.setForeground(Color.WHITE);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 16));
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.setIcon(createCheckBoxIcon(false));
        checkBox.setSelectedIcon(createCheckBoxIcon(true));
    }
    
    private Icon createCheckBoxIcon(boolean selected) {
        return new Icon() {
            private final int SIZE = 18;
            
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (selected) {
                    g2d.setColor(accentColor);
                    g2d.fillRoundRect(x, y, SIZE, SIZE, 4, 4);
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x + 4, y + 9, x + 7, y + 12);
                    g2d.drawLine(x + 7, y + 12, x + 14, y + 5);
                } else {
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.drawRoundRect(x, y, SIZE, SIZE, 4, 4);
                }
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return SIZE;
            }
            
            @Override
            public int getIconHeight() {
                return SIZE;
            }
        };
    }

    private void createFormRow(String labelText, JComponent component, JPanel formPanel) {
        // No longer used - form rows are now created directly in addComponents()
    }

    private void addComponents() {
        // Create a card-like panel to hold the form
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2, true),
                        BorderFactory.createEmptyBorder(30, 40, 30, 40)
                )
        ));
        // Don't set maximum size to avoid clipping issues
        cardPanel.setPreferredSize(new Dimension(500, 600));
        
        // Add a close button in the corner for fullscreen mode
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBackground(new Color(220, 20, 60, 150));
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(new Color(220, 20, 60, 200));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(220, 20, 60, 150));
            }
        });
        
        // Position the close button in the top right
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(closeButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Create NVIDIA logo at the top
        JLabel logoLabel = new JLabel("NVIDIA", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 40));
        logoLabel.setForeground(accentColor);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create title label
        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add logo and title to card
        cardPanel.add(logoLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Create form panel with fixed width for consistent display
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setPreferredSize(new Dimension(400, 150));
        formPanel.setMaximumSize(new Dimension(400, 150));
        
        // Fix text field sizing
        Dimension fieldSize = new Dimension(300, 40);
        userNameField.setPreferredSize(fieldSize);
        userNameField.setMinimumSize(fieldSize);
        userNameField.setMaximumSize(fieldSize);
        
        mobileNumberField.setPreferredSize(fieldSize);
        mobileNumberField.setMinimumSize(fieldSize);
        mobileNumberField.setMaximumSize(fieldSize);

        // Create input panels with GridBagLayout for better control
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 10);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(labelFont);
        usernamePanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        usernamePanel.add(userNameField, gbc);
        
        JPanel mobilePanel = new JPanel(new GridBagLayout());
        mobilePanel.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 10);
        
        JLabel mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setForeground(Color.WHITE);
        mobileLabel.setFont(labelFont);
        mobilePanel.add(mobileLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mobilePanel.add(mobileNumberField, gbc);
        
        formPanel.add(usernamePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(mobilePanel);
        
        // Add terms and conditions in its own panel
        JPanel termsPanel = new JPanel();
        termsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        termsPanel.setOpaque(false);
        termsPanel.add(termsBox);
        
        JLabel termsLink = new JLabel("<html><u>View Terms</u></html>");
        termsLink.setForeground(accentColor);
        termsLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        termsLink.setFont(new Font("Arial", Font.PLAIN, 14));
        termsLink.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        termsPanel.add(termsLink);
        
        // Add form to card
        cardPanel.add(formPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(termsPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Create button panel with fixed sizes
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(350, 45));
        buttonPanel.setMaximumSize(new Dimension(350, 45));
        buttonPanel.setMinimumSize(new Dimension(350, 45));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Ensure buttons have proper size
        signInButton.setPreferredSize(new Dimension(150, 40));
        signInButton.setMinimumSize(new Dimension(150, 40));
        goBackButton.setPreferredSize(new Dimension(150, 40));
        goBackButton.setMinimumSize(new Dimension(150, 40));
        
        buttonPanel.add(signInButton);
        buttonPanel.add(goBackButton);

        cardPanel.add(buttonPanel);
        
        // Create "New User? Register Here" link
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel registerLabel = new JLabel("New User? ");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel registerLink = new JLabel("<html><u>Register Here</u></html>");
        registerLink.setForeground(accentColor);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setFont(new Font("Arial", Font.PLAIN, 14));
        
        registerPanel.add(registerLabel);
        registerPanel.add(registerLink);
        
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(registerPanel);

        // Center panel using absolute positioning for perfect centering
        setLayout(null); // Switch to null layout for precise positioning
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Calculate center position
        int cardWidth = 500;
        int cardHeight = 600;
        int x = (screenSize.width - cardWidth) / 2;
        int y = (screenSize.height - cardHeight) / 2;
        
        // Create a wrapper panel with absolute positioning
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.setBounds(x, y, cardWidth, cardHeight);
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(cardPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);

        // Add action listeners
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
        
        // Add action for the register link
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(SignIn.this, 
                    "Registration feature will be available soon!", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Add action for the terms link
        termsLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(SignIn.this, 
                    "By accepting these terms, you agree to NVIDIA's privacy policy and terms of service.", 
                    "Terms & Conditions", 
                    JOptionPane.INFORMATION_MESSAGE);
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
        
        if (username.isEmpty()) {
            showError("Username cannot be empty!");
            userNameField.requestFocus();
            return;
        }
        
        if (mobile.isEmpty()) {
            showError("Mobile number cannot be empty!");
            mobileNumberField.requestFocus();
            return;
        }
        
        if (!isValidMobile(mobile)) {
            showError("Invalid mobile number! Enter exactly 10 digits.");
            mobileNumberField.requestFocus();
            return;
        }
        
        UserDashBoard.phoneNo = Long.parseLong(mobile);

        String query = "Select * from sign_in where username = '" + username + "' and mobilenumber = '" + mobile + "';";
        ConnectionJDBC connection = new ConnectionJDBC();

        try {
            ResultSet rs = connection.s.executeQuery(query);

            if (rs.next()) {
                // Create a custom success dialog
                JDialog successDialog = new JDialog(this, "Success", true);
                successDialog.setSize(350, 200);
                successDialog.setLocationRelativeTo(this);
                successDialog.setLayout(new BorderLayout());
                
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
                dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                dialogPanel.setBackground(Color.WHITE);
                
                JLabel welcomeLabel = new JLabel("Welcome back " + username + "!");
                welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
                welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JButton okButton = new JButton("Continue");
                okButton.setBackground(accentColor);
                okButton.setForeground(Color.BLACK);
                okButton.setFont(new Font("Arial", Font.BOLD, 14));
                okButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
                okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                okButton.addActionListener(e -> {
                    successDialog.dispose();
                    try {
                        new UserDashBoard();
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
                
                dialogPanel.add(Box.createVerticalStrut(20));
                dialogPanel.add(welcomeLabel);
                dialogPanel.add(Box.createVerticalStrut(30));
                dialogPanel.add(okButton);
                
                successDialog.add(dialogPanel);
                successDialog.setVisible(true);
            } else {
                showError("Invalid credentials. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error occurred. Please try again later.");
        }
    }

    private void handleGoBack() {
        // Create a custom confirmation dialog
        JDialog confirmDialog = new JDialog(this, "Confirm", true);
        confirmDialog.setSize(350, 200);
        confirmDialog.setLocationRelativeTo(this);
        confirmDialog.setLayout(new BorderLayout());
        
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialogPanel.setBackground(Color.WHITE);
        
        JLabel confirmLabel = new JLabel("Go back to Main Menu?");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(accentColor);
        yesButton.setForeground(Color.WHITE);
        yesButton.setFont(new Font("Arial", Font.BOLD, 14));
        yesButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        yesButton.addActionListener(e -> {
            confirmDialog.dispose();
            SwingUtilities.invokeLater(() -> new HomePage());
            dispose();
        });
        
        JButton noButton = new JButton("No");
        noButton.setBackground(new Color(200, 200, 200));
        noButton.setForeground(Color.BLACK);
        noButton.setFont(new Font("Arial", Font.BOLD, 14));
        noButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        noButton.addActionListener(e -> confirmDialog.dispose());
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        
        dialogPanel.add(Box.createVerticalStrut(20));
        dialogPanel.add(confirmLabel);
        dialogPanel.add(Box.createVerticalStrut(30));
        dialogPanel.add(buttonPanel);
        
        confirmDialog.add(dialogPanel);
        confirmDialog.setVisible(true);
    }

    private void showError(String message) {
        // Create a custom error dialog
        JDialog errorDialog = new JDialog(this, "Error", true);
        errorDialog.setSize(350, 200);
        errorDialog.setLocationRelativeTo(this);
        errorDialog.setLayout(new BorderLayout());
        
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialogPanel.setBackground(Color.WHITE);
        
        JLabel errorIcon = new JLabel("⚠");
        errorIcon.setFont(new Font("Arial", Font.BOLD, 36));
        errorIcon.setForeground(errorColor);
        errorIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel errorLabel = new JLabel(message);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton okButton = new JButton("OK");
        okButton.setBackground(errorColor);
        okButton.setForeground(Color.BLACK);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> errorDialog.dispose());
        
        dialogPanel.add(errorIcon);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(errorLabel);
        dialogPanel.add(Box.createVerticalStrut(20));
        dialogPanel.add(okButton);
        
        errorDialog.add(dialogPanel);
        errorDialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignIn();
            }
        });
    }
}

