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

        Tours modele = new Tours(4);
        Affichage vueTours = new Affichage(modele);
        vueTours.setPreferredSize(new Dimension(500,500));

        contentPane.add(vueTours,BorderLayout.CENTER);

        frame.setVisible(true);
        frame.pack();
    }

}
