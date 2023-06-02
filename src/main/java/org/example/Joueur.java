package org.example;

import java.util.ArrayList;

public class Joueur {
    private final String nom;
    private ArrayList<Carte> main;

    private int points;
    private Carte carteAJouer;

    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.points = 0;
    }

    public void piocher(Paquet paquetDeCartes) {
        Carte cartePiochee = paquetDeCartes.tirerCarte();
        main.add(cartePiochee);
    }

    public void choisirCarte(Carte carte) {
        carteAJouer = main.remove(main.indexOf(carte));
    }

    public void ajouterPoints(int points) {
        this.points += points;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }

    public Carte getCarteAJouer() {
        return carteAJouer;
    }

    public int getValeurCarteAJouer() {
        return carteAJouer.getNombre();
    }

    public int getPoints() {
        return points;
    }
}
