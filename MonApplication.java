import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MonApplication implements Runnable {

    private int lignes;
    private int colonnes;

    public MonApplication(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
    }

    static Image charge(String nom) {
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            return ImageIO.read(cl.getResourceAsStream(nom));
        } catch (Exception e) {
            System.err.println("Erreur imprévue");
            System.exit(1);
            return null;
        }
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Miam Miam");
        Jeu jeu = new Jeu(frame, lignes, colonnes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lignes, colonnes;

        while (true) {
            System.out.print("Entrez le nombre de lignes (1-50) : ");
            lignes = scanner.nextInt();
            if (lignes < 1 || lignes > 50) {
                System.out.println("Erreur : le nombre de lignes doit être entre 1 et 50.");
                continue; 
            }
            break;
        }

        while (true) {
            System.out.print("Entrez le nombre de colonnes (1-50) : ");
            colonnes = scanner.nextInt();
            if (colonnes < 1 || colonnes > 50) {
                System.out.println("Erreur : le nombre de colonnes doit être entre 1 et 50.");
                continue;
            }
            break;
        }

        scanner.close();

        SwingUtilities.invokeLater(new MonApplication(lignes, colonnes));
    }
}
