package com.novaforgestudios.novacraft.core;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovacraftGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private static final String TITLE_CARD = "Title";
    private static final String OPTIONS_CARD = "Options";
    private static final String VERSION = "0.1 Pre-Development"; // Version and development stage
    private static final String COPYRIGHT = "Copyright © 2025 Novaforgestudios. Do not distribute!"; // Copyright notice

    public NovacraftGUI() {
        setTitle("Novacraft " + VERSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // CardLayout setup
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(0x1A2A44)); // Dark blue-gray

        // Add cards
        cardPanel.add(createTitlePanel(), TITLE_CARD);
        cardPanel.add(createOptionsPanel(), OPTIONS_CARD);

        add(cardPanel);
        cardLayout.show(cardPanel, TITLE_CARD); // Start on title card

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(new Color(0x1A2A44));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);

        // Title Label
        JLabel titleLabel = new JLabel("Novacraft");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 64));
        titleLabel.setForeground(new Color(0xFFD700));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        titlePanel.add(titleLabel, gbc);

        // Version Label (below title)
        JLabel versionLabel = new JLabel("Version " + VERSION);
        versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        versionLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        titlePanel.add(versionLabel, gbc);

        // Singleplayer Button (disabled)
        JButton singleplayerButton = new JButton("Singleplayer");
        singleplayerButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        singleplayerButton.setPreferredSize(new Dimension(250, 60));
        singleplayerButton.setBackground(new Color(0x3C5A99));
        singleplayerButton.setForeground(Color.WHITE);
        singleplayerButton.setEnabled(false); // Disabled since not implemented
        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Singleplayer clicked - coming soon!");
            }
        });
        gbc.gridy = 2;
        titlePanel.add(singleplayerButton, gbc);

        // Multiplayer Button (disabled)
        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        multiplayerButton.setPreferredSize(new Dimension(250, 60));
        multiplayerButton.setBackground(new Color(0x3C5A99));
        multiplayerButton.setForeground(Color.WHITE);
        multiplayerButton.setEnabled(false); // Disabled since not implemented
        multiplayerButton.addActionListener(e -> System.out.println("Multiplayer clicked - coming soon!"));
        gbc.gridy = 3;
        titlePanel.add(multiplayerButton, gbc);

        // GitHub Button (disabled)
        JButton githubButton = new JButton("Fork on GitHub");
        githubButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        githubButton.setPreferredSize(new Dimension(250, 60));
        githubButton.setBackground(new Color(0x3C5A99));
        githubButton.setForeground(Color.WHITE);
        githubButton.setEnabled(false); // Disabled since not implemented
        githubButton.addActionListener(e -> System.out.println("GitHub link clicked - placeholder"));
        gbc.gridy = 4;
        titlePanel.add(githubButton, gbc);

        // Options Button
        JButton optionsButton = new JButton("Options");
        optionsButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        optionsButton.setPreferredSize(new Dimension(250, 60));
        optionsButton.setBackground(new Color(0x3C5A99));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.addActionListener(e -> cardLayout.show(cardPanel, OPTIONS_CARD));
        gbc.gridy = 5;
        titlePanel.add(optionsButton, gbc);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        quitButton.setPreferredSize(new Dimension(250, 60));
        quitButton.setBackground(new Color(0x992D2D));
        quitButton.setForeground(Color.WHITE);
        quitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 6;
        titlePanel.add(quitButton, gbc);

        // Add copyright labels in both corners
        JLabel copyrightLabelLeft = new JLabel(COPYRIGHT);
        copyrightLabelLeft.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyrightLabelLeft.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 10, 10, 0);
        titlePanel.add(copyrightLabelLeft, gbc);

        JLabel copyrightLabelRight = new JLabel(COPYRIGHT);
        copyrightLabelRight.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyrightLabelRight.setForeground(Color.WHITE);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        titlePanel.add(copyrightLabelRight, gbc);

        return titlePanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(new Color(0x1A2A44));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Options Title
        JLabel titleLabel = new JLabel("Options");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0xFFD700));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(titleLabel, gbc);

        // Fullscreen Option
        JLabel fullscreenLabel = new JLabel("Fullscreen:");
        fullscreenLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        fullscreenLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        optionsPanel.add(fullscreenLabel, gbc);

        JCheckBox fullscreenCheck = new JCheckBox();
        fullscreenCheck.setSelected(false);
        fullscreenCheck.setBackground(new Color(0x1A2A44));
        fullscreenCheck.addActionListener(e -> System.out.println("Fullscreen toggled: " + fullscreenCheck.isSelected()));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        optionsPanel.add(fullscreenCheck, gbc);

        // Volume Option
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        volumeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        optionsPanel.add(volumeLabel, gbc);

        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setPreferredSize(new Dimension(150, 30));
        volumeSlider.setBackground(new Color(0x1A2A44));
        volumeSlider.addChangeListener(e -> System.out.println("Volume set to: " + volumeSlider.getValue()));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        optionsPanel.add(volumeSlider, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setBackground(new Color(0x3C5A99));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, TITLE_CARD));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(backButton, gbc);

        // Add copyright labels in both corners
        JLabel copyrightLabelLeft = new JLabel(COPYRIGHT);
        copyrightLabelLeft.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyrightLabelLeft.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 10, 10, 0);
        optionsPanel.add(copyrightLabelLeft, gbc);

        JLabel copyrightLabelRight = new JLabel(COPYRIGHT);
        copyrightLabelRight.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyrightLabelRight.setForeground(Color.WHITE);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        optionsPanel.add(copyrightLabelRight, gbc);

        return optionsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NovacraftGUI());
    }
}