package model;

import javax.swing.*;
import java.awt.*;
import view.Vue;
import control.*;

public class Jeu {
    static int ligne;
    static int colonne;
    static Vue vue;
    static Gauffre gauffre;
    static contolMouseMotion controleSouris;
    private static JFrame frame;
    public JLabel turn;
    private static JPanel boutonsPanel;
    static IA ia=null ;
    private static boolean vsAI = false; // false par défaut (2 joueurs)

    public Jeu(JFrame frame) {
        this.frame = frame;
        this.boutonsPanel = new JPanel();
        showConfigScreen();
    }
    public static void showConfigScreen() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(240, 240, 245));

        JLabel titleLabel = new JLabel("Configuration de la Gauffre", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        // Ajout des boutons pour le choix du mode de jeu
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel modeLabel = new JLabel("Mode de jeu :");
        modeLabel.setFont(labelFont);
        configPanel.add(modeLabel, gbc);

        gbc.gridy = 1;
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        modePanel.setBackground(new Color(240, 240, 245));
        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton onePlayerBtn = new JRadioButton("1 Joueur (contre l'IA)");
        JRadioButton twoPlayersBtn = new JRadioButton("2 Joueurs");
        onePlayerBtn.setFont(labelFont);
        twoPlayersBtn.setFont(labelFont);
        onePlayerBtn.setBackground(new Color(240, 240, 245));
        twoPlayersBtn.setBackground(new Color(240, 240, 245));
        modeGroup.add(onePlayerBtn);
        modeGroup.add(twoPlayersBtn);
        twoPlayersBtn.setSelected(true); // Par défaut 2 joueurs
        modePanel.add(onePlayerBtn);
        JComboBox<String> iaLevelCombo = new JComboBox<>(new String[]{"IA Facile", "IA Moyenne", "IA Difficile"});
        iaLevelCombo.setFont(labelFont);
        iaLevelCombo.setEnabled(false);
        onePlayerBtn.addActionListener(e -> iaLevelCombo.setEnabled(true));
        twoPlayersBtn.addActionListener(e -> {
            iaLevelCombo.setEnabled(false);
            iaLevelCombo.setSelectedIndex(0);});
        modePanel.add(iaLevelCombo);
        modePanel.add(twoPlayersBtn);
        configPanel.add(modePanel, gbc);

        // Configuration du plateau
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel rowsLabel = new JLabel("Nombre de lignes :");
        rowsLabel.setFont(labelFont);
        configPanel.add(rowsLabel, gbc);

        gbc.gridx = 1;
        JTextField rowsField = new JTextField();
        styleTextField(rowsField);
        configPanel.add(rowsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel colsLabel = new JLabel("Nombre de colonnes :");
        colsLabel.setFont(labelFont);
        configPanel.add(colsLabel, gbc);

        gbc.gridx = 1;
        JTextField colsField = new JTextField();
        styleTextField(colsField);
        configPanel.add(colsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 0, 10);

        JButton validateButton = new JButton("Commencer le jeu");
        styleButton(validateButton);
        configPanel.add(validateButton, gbc);

        mainPanel.add(configPanel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);

        validateButton.addActionListener(e -> {
            try {
                ligne = Integer.parseInt(rowsField.getText());
                colonne = Integer.parseInt(colsField.getText());
                vsAI = onePlayerBtn.isSelected(); // Sauvegarde du mode de jeu
                int iaLevel = iaLevelCombo.getSelectedIndex() + 1;
                if (vsAI) {
                    ia = new IA(gauffre, iaLevel);
                } else {
                    ia = null;
                }

                if (ligne < 2 || colonne < 2) {
                    JOptionPane.showMessageDialog(frame,
                            "Utilisez au moins 2 lignes et 2 colonnes",
                            "Configuration minimale",
                            JOptionPane.WARNING_MESSAGE);
                }

                if (ligne <= 0 || colonne <= 0) {
                    throw new NumberFormatException();
                }

                startGame();
            } catch (NumberFormatException ex) {
                rowsField.setBackground(new Color(255, 200, 200));
                colsField.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(frame,
                        "Veuillez entrer des nombres entiers valides",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                rowsField.setBackground(Color.WHITE);
                colsField.setBackground(Color.WHITE);
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private static void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textField.setPreferredSize(new Dimension(100, 30));
    }

    private static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 150, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }
    private static void startGame() {
        frame.getContentPane().removeAll();
        gauffre = new Gauffre(ligne, colonne);
        vue = new Vue(ligne, colonne, gauffre);
        if(vsAI){
            ia = new IA(gauffre,3);
        }

        controleSouris = new contolMouseMotion(gauffre, vue);
        controlSouris controlSouris = new controlSouris(gauffre, vue,ia);
        controlRestart restart = new controlRestart(vue,gauffre);
        controlannule annule = new controlannule(vue, gauffre);
        controlrefais refais = new controlrefais(vue, gauffre);


        JPanel panel = new JPanel();

        boutonsPanel=new JPanel();
        boutonsPanel.add(vue.turn);
        JButton b1 = new JButton("RESTART");
        b1.setFocusable(false);
        b1.addActionListener(restart);
        JButton b2 = new JButton("<-");
        b2.setFocusable(false);
        b2.addActionListener(annule);
        JButton b3 = new JButton("->");
        b3.setFocusable(false);
        b3.addActionListener(refais);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(vue.turn);
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.SOUTH);
        vue.addMouseMotionListener(controleSouris);
        vue.addMouseListener(controlSouris);
        container.add(vue, BorderLayout.CENTER);

        frame.add(container);
        frame.revalidate();
        frame.repaint();
    }
}