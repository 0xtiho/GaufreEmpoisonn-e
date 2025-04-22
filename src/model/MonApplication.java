package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class MonApplication implements Runnable {
    public static Image charge(String nom) {
        try {
            // Try to load directly from the res/img directory
            File imgFile = new File("res/img/" + nom);
            if (imgFile.exists()) {
                return ImageIO.read(imgFile);
            } else {
                System.err.println("File not found: " + imgFile.getAbsolutePath());
                System.exit(1);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erreur imprévue: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Miam Miam");
        Jeu jeu = new Jeu(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MonApplication());
    }
}
