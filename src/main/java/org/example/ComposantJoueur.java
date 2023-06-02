package org.example;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ComposantJoueur extends HBox {
    private Joueur joueur;
    private Label etiquetteNom;
    private Label etiquetteScore;

    public ComposantJoueur(Joueur joueur) {
        this.joueur = joueur;
        this.etiquetteNom = new Label();
        this.etiquetteScore = new Label();

        this.getChildren().addAll(etiquetteNom, etiquetteScore);
    }

    public void mettreAJour(Jeu jeu) {
        etiquetteNom.setText(joueur.getNom());
        etiquetteScore.setText(" Score : " + joueur.getPoints());
    }
}
