import controleurs.ControleurAffichage;
import modeles.Jeu;
import modeles.Selection;
import modeles.Tours;
import vues.Affichage;

import javax.swing.*;
import java.awt.*;

public class Principale {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tours de Hanoï");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        Selection selection = new Selection();

        Tours modeleTours = new Tours(4);
        Affichage vueTours = new Affichage(modeleTours, selection);
        modeleTours.addObserver(vueTours);
        selection.addObserver(vueTours);

        Jeu modele = new Jeu(modeleTours, selection);

        ControleurAffichage controleurAffichage = new ControleurAffichage(modele, vueTours, selection);
        vueTours.addMouseListener(controleurAffichage);

        vueTours.setPreferredSize(new Dimension(500,500));

        contentPane.add(vueTours,BorderLayout.CENTER);

        frame.setVisible(true);
        frame.pack();
    }

}
