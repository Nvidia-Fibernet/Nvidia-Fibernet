package nvidia.in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FAQ extends JFrame // Now extending JFrame directly
{
    public FAQ() 
    {
        setTitle("FAQ - Nvidia Broadband Billing System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Center the frame
        
        JPanel faqPanel = new JPanel();
        faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS)); 
        faqPanel.setBackground(Color.WHITE);

        String[][] faqData = 
        {
            {"How can I pay my Nvidia Broadband bill?", "You can pay online via credit/debit card, net banking, or UPI."},
            {"What payment methods are supported?", "We accept Visa, MasterCard, PayPal, net banking, UPI, and auto-debit options."},
            {"Can I set up auto-payment for my broadband bill?", "Yes, enable auto-payment from your account settings."},
            {"What happens if I miss my payment?", "Your connection may be suspended. A late fee might be applicable."},
            {"How do I view my past bills?", "Check the 'Billing History' section in your account dashboard."},
            {"How do I change my broadband plan?", "Upgrade or downgrade your plan from 'My Plan' in settings."},
            {"Can I temporarily suspend my broadband service?", "Yes, request a temporary suspension via customer support."},
            {"How do I cancel my Nvidia Broadband subscription?", "Go to 'Subscription Management' and request cancellation."},
            {"Is there a penalty for early cancellation?", "If under a contract, an early termination fee may apply."},
            {"How can I update my contact information?", "Update your details in account settings."},
            {"Why is my internet speed slow?", "Speed issues can be due to network congestion or background downloads."},
            {"How do I reset my router?", "Press and hold the reset button on your router for 10 seconds."},
            {"What should I do if my internet is not working?", "Check connections, restart your modem, or contact support."},
            {"Does Nvidia Broadband provide unlimited data plans?", "Yes, we offer unlimited and FUP-based plans."},
            {"How do I check my data usage?", "Track your monthly usage in the 'Usage' section of your dashboard."},
            {"How do I secure my Wi-Fi connection?", "Use a strong password and enable WPA3 encryption."},
            {"Can I block specific devices from using my internet?", "Yes, manage devices through router settings."},
            {"How do I report a service outage?", "Report outages via 'Support' or call our helpline."},
            {"Is there a mobile app for Nvidia Broadband?", "Yes, download the Nvidia Broadband app from Play Store or App Store."},
            {"How can I contact customer support?", "Reach us via phone, email, or live chat on our website."}
        };

        for (String[] faq : faqData) 
        {
            faqPanel.add(createFAQItem(faq[0], faq[1]));
            faqPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between questions
        }

        JScrollPane scrollPane = new JScrollPane(faqPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        setVisible(true);
    }

	private JPanel createFAQItem(String question, String answer) 
    {
        JPanel faqItemPanel = new JPanel();
        faqItemPanel.setLayout(new BorderLayout());
        faqItemPanel.setBorder(BorderFactory.createEtchedBorder());

        JButton questionButton = new JButton(question + " ▼");
        questionButton.setFont(new Font("Arial", Font.BOLD, 14));

        JTextArea answerLabel = new JTextArea(answer);
        answerLabel.setEditable(false);
        answerLabel.setLineWrap(true);
        answerLabel.setWrapStyleWord(true);
        answerLabel.setVisible(false);
        answerLabel.setBackground(Color.LIGHT_GRAY);

        JScrollPane answerScrollPane = new JScrollPane(answerLabel);
        answerScrollPane.setPreferredSize(new Dimension(500, 50));
        answerScrollPane.setVisible(false);

        questionButton.addActionListener(new ActionListener() 
        {
            private boolean expanded = false;

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                expanded = !expanded;
                answerLabel.setVisible(expanded);
                answerScrollPane.setVisible(expanded);
                questionButton.setText(expanded ? question + " ▲" : question + " ▼");
                faqItemPanel.revalidate();
                faqItemPanel.repaint();
            }
        });

        faqItemPanel.add(questionButton, BorderLayout.NORTH);
        faqItemPanel.add(answerScrollPane, BorderLayout.CENTER);

        return faqItemPanel;
    }

    public static void main(String[] args) 
    {
        new FAQ(); // Simply create an instance to open the new window
    }
}