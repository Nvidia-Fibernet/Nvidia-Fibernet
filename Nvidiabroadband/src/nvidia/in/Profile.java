package nvidia.in;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Profile extends JFrame {
    JLabel serviceLabel, subscriptionLabel;
    JTextField userNameTextField, mobileNumberTextField;
    JButton updateProfile;
    JComboBox<String> cityCombobox;
    ConnectionJDBC con;
    JPanel subscriptionPanel, userPanel;
    String name, city;
    private long mobilenumber;

    public Profile(long mobilenumber) {
        this.mobilenumber = mobilenumber; // ✅ Store passed mobile number
        setUpFrame();
        initializeComponents();
        addComponents();
        fetchSubscriptionDetails();
    }

    public void setUpFrame() {
        setSize(1000, 750);
        setVisible(true);
        setLayout(new BorderLayout());
        setTitle("User Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createBackgroundPanelImg());
    }

    public void addComponents() {
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        serviceLabel = new JLabel("Profile Page");
        serviceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        serviceLabel.setForeground(Color.WHITE);
        serviceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.add(serviceLabel);
        userPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        createFormRow("User Name: ", userNameTextField, userPanel);
        userPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        createFormRow("Mobile Number: ", mobileNumberTextField, userPanel);
        mobileNumberTextField.setEditable(false); // read-only
        userPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        createFormRow("City: ", cityCombobox, userPanel);
        userPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        fetchUserDetails();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.add(updateProfile);
        userPanel.add(buttonPanel);

        addActionListener();
        add(userPanel, BorderLayout.NORTH);

        subscriptionPanel = new JPanel();
        subscriptionPanel.setLayout(new BoxLayout(subscriptionPanel, BoxLayout.Y_AXIS));
        subscriptionPanel.setOpaque(false);
        subscriptionPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 50));
        add(subscriptionPanel, BorderLayout.CENTER);
    }

    public void fetchUserDetails() {
        con = new ConnectionJDBC();

        String query = "SELECT * FROM sign_in WHERE mobilenumber = ?";

        try {
            PreparedStatement pst = con.getConnection().prepareStatement(query);
            pst.setLong(1, this.mobilenumber); // ✅ Use instance variable
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                name = rs.getString("userName");
                city = rs.getString("city");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        userNameTextField.setText(name);
        mobileNumberTextField.setText(Long.toString(this.mobilenumber));
        cityCombobox.setSelectedItem(city);
    }

    public void addActionListener() {
        updateProfile.addActionListener(e -> {
            con = new ConnectionJDBC();
            name = userNameTextField.getText();
            city = (String) cityCombobox.getSelectedItem();

            String query = "UPDATE sign_in SET username = ?, city = ? WHERE mobilenumber = ?";

            try {
                PreparedStatement pst = con.getConnection().prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, city);
                pst.setLong(3, this.mobilenumber);

                int i = pst.executeUpdate();

                if (i > 0) {
                    JOptionPane.showMessageDialog(Profile.this, "Profile Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Profile.this, "Profile not updated!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void fetchSubscriptionDetails() {
        con = new ConnectionJDBC();

        try {
            String query = "SELECT planType, planData, speed, planDuration, planPrice FROM broadband_plans WHERE mobilenumber = ?";
            PreparedStatement pst = con.getConnection().prepareStatement(query);
            pst.setLong(1, this.mobilenumber); // ✅ Use instance variable
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JLabel subscriptionLabel = new JLabel("Subscription Details");
                subscriptionLabel.setFont(new Font("Arial", Font.BOLD, 22));
                subscriptionLabel.setForeground(Color.WHITE);
                subscriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                subscriptionPanel.add(subscriptionLabel);
                subscriptionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                addSubscriptionDetail("Plan Name: ", rs.getString("planType"));
                addSubscriptionDetail("Plan Data : ", rs.getString("planData"));
                addSubscriptionDetail("Speed : ", rs.getString("speed"));
                addSubscriptionDetail("Plan Duration: ", rs.getString("planDuration"));
                addSubscriptionDetail("Plan Price: ", "₹" + rs.getString("planPrice"));
            }

            revalidate();
            repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching subscription details", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSubscriptionDetail(String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setOpaque(false);
        rowPanel.setPreferredSize(new Dimension(500, 30));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.YELLOW);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        rowPanel.add(titleLabel);
        rowPanel.add(valueLabel);
        subscriptionPanel.add(rowPanel);
    }

    private void createFormRow(String labelText, JComponent component, JPanel formPanel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setPreferredSize(new Dimension(500, 30));
        rowPanel.setMinimumSize(new Dimension(200, 30));

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

                Color topColor = new Color(60, 140, 190, 255);
                Color bottomColor = new Color(10, 20, 100, 255);

                GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private void initializeComponents() {
        userNameTextField = createStyledTextField(10);
        mobileNumberTextField = createStyledTextField(10);

        String[] cities = {
                "Select a City", "Austin", "Texas", "Raleigh", "North", "Carolina",
                "New York City", "Kansas City", "Missouri", "Brooklyn", "Manhattan"
        };
        cityCombobox = new JComboBox<>(cities);

        updateProfile = createStyledButton("Update Profile", new Color(41, 128, 185));
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
        button.setPreferredSize(new Dimension(200, 40));

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
    	SwingUtilities.invokeLater(()->{
    		long exampleMobile = 9876543210L;
    		new Profile(exampleMobile);
    	});
	}

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            long exampleMobile = 9876543210L; // Replace with actual number after login
//            new Profile(exampleMobile);
//        });
//    }
}