package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AppJeu extends Application {

    private Jeu jeu;
    private CountDownLatch verrou;
    private Carte carteChoisie;
    BorderPane racine;
    VBox composantsJoueurs;
    ComposantPlateau composantPlateau;

    @Override
    public void start(Stage primaryStage) {
        List<String> nomsJoueurs = Arrays.asList("Victoire", "Hermione", "Clara");
        jeu = new Jeu(nomsJoueurs);

        // Créez vos composants GUI ici et ajoutez-les à la racine
        racine = new BorderPane();

        composantPlateau = new ComposantPlateau();
        racine.setCenter(composantPlateau);

        composantsJoueurs = new VBox();
        for (Joueur joueur : jeu.getJoueurs()) {
            ComposantJoueur composantJoueur = new ComposantJoueur(joueur);
            composantsJoueurs.getChildren().add(composantJoueur);
        }
        racine.setTop(composantsJoueurs);

        Scene scene = new Scene(racine, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("6 Qui Prend!");
        primaryStage.show();

        new Thread(this::jouerPartie).start();
    }

    private void jouerPartie() {
        while (!jeu.estFinDePartie()) {
            Platform.runLater(this::mettreAJourInterfaceGraphique);
            for (Joueur joueur : jeu.getJoueurs()) {
                choisirCarte(joueur);
            }

            jeu.jouerTour();

            if (jeu.estFinDePartie()) {
                Joueur gagnant = jeu.determinerGagnant();
                Platform.runLater(() -> {
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("Partie Terminée !");
                    alerte.setHeaderText("La Partie est Finie!");
                    alerte.setContentText("Le gagnant est " + gagnant.getNom() + " avec " + gagnant.getPoints() + " points.");
                    alerte.showAndWait();
                });
            }
        }
    }

    private void choisirCarte(Joueur joueur) {
        verrou = new CountDownLatch(5);
        Platform.runLater(() -> {
            Dialog<Carte> dialog = new Dialog<>();
            dialog.setTitle("Choisissez une carte");
            dialog.setHeaderText("Joueur " + joueur.getNom() + ", choisissez une carte à placer.");

            ButtonType boutonJouer = new ButtonType("Jouer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(boutonJouer, ButtonType.CANCEL);
            dialog.getDialogPane().lookupButton(boutonJouer).setVisible(false);
            dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

            HBox boite = new HBox();
            boite.setSpacing(10);

            for (Carte carte : joueur.getMain()) {
                Button bouton = new Button(Integer.toString(carte.getNombre()));
                bouton.setOnAction(e -> gererChoixCarte(carte, dialog));
                boite.getChildren().add(bouton);
            }

            dialog.getDialogPane().setContent(boite);

            dialog.showAndWait();
        });
        try {
            verrou.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        joueur.choisirCarte(carteChoisie);
    }

    private void gererChoixCarte(Carte carte, Dialog<Carte> dialog) {
        carteChoisie = carte;
        dialog.close();
        mettreAJourInterfaceGraphique();
        verrou.countDown();
    }

    private void mettreAJourInterfaceGraphique() {
        composantPlateau.mettreAJour(jeu);
        for (Node node : composantsJoueurs.getChildren()) {
            ((ComposantJoueur)node).mettreAJour(jeu);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
