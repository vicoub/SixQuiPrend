package org.example;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Jeu {
    private final List<Joueur> joueurs;
    private final Plateau plateau;
    private final Paquet paquet;
    private boolean finDePartie;

    public Jeu(List<String> nomsJoueurs) {
        joueurs = new ArrayList<>();
        for (String nom : nomsJoueurs) {
            joueurs.add(new Joueur(nom));
        }
        plateau = new Plateau();
        paquet = new Paquet();
        preparerPlateau();
        distribuerCartes();
    }

    public void jouerTour() {
        List<Joueur> joueursOrdre = determinerOrdre();

        for (Joueur joueur : joueursOrdre) {
            joueur.ajouterPoints(plateau.ajouterCarte(joueur.getCarteAJouer()));
        }

        if (joueurs.get(0).getMain().isEmpty()) {
            if (paquet.estVide()) {
                finDePartie = true;
            } else {
                distribuerCartes();
            }
        }
    }

    private void distribuerCartes() {
        for (Joueur joueur : joueurs) {
            while (joueur.getMain().size() < 10 && !paquet.estVide()) {
                joueur.piocher(paquet);
            }
        }
    }

    public Joueur determinerGagnant() {
        Joueur gagnant = joueurs.get(0);
        for (Joueur joueur : joueurs) {
            if (joueur.getPoints() < gagnant.getPoints()) {
                gagnant = joueur;
            }
        }
        return gagnant;
    }

    private List<Joueur> determinerOrdre() {
        List<Joueur> listeTriee = new ArrayList<>(joueurs);
        listeTriee.sort(Comparator.comparingInt(Joueur::getValeurCarteAJouer));

        return listeTriee;
    }

    private void preparerPlateau() {
        for (List<Carte> rangée : plateau.getRangées()) {
            rangée.add(paquet.tirerCarte());
        }
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean estFinDePartie() {
        return finDePartie;
    }
}
