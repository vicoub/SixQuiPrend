package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Paquet {
    private ArrayList<Carte> cartes;

    public Paquet() {
        cartes = new ArrayList<>();
        for (int i = 1; i <= 104; i++) {
            int points = 1 + ((i % 5 == 0) ? 1 : 0) + (2 * ((i % 10 == 0) ? 1 : 0));
            // Chaque carte a au minimum 1 point, +1 pour les multiples de 5, +1 pour les multiples de 10, +4 pour les nombres à chiffres identiques
            if (i > 10) {
                points += (4 * (String.valueOf(i).substring(0, 1).equals(String.valueOf(i).substring(1)) ? 1 : 0));
            }
            cartes.add(new Carte(i, points));
        }
        melanger();
    }

    public void melanger() {
        Collections.shuffle(cartes);
    }

    public Carte tirerCarte() {
        return cartes.remove(cartes.size() - 1);
    }

    public boolean estVide() {
        return cartes.isEmpty();
    }
}
