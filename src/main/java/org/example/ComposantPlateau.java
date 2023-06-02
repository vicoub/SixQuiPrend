package org.example;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ComposantPlateau extends VBox {
    private List<Label> etiquettesRangées;

    public ComposantPlateau() {
        etiquettesRangées = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Label etiquette = new Label();
            this.getChildren().add(etiquette);
            etiquettesRangées.add(etiquette);
        }
    }

    public void mettreAJour(Jeu jeu) {
        List<List<Carte>> rangées = jeu.getPlateau().getRangées();
        for (int i = 0; i < rangées.size(); i++) {
            List<Carte> rangée = rangées.get(i);
            StringBuilder sb = new StringBuilder("Rangée " + (i + 1) + ": ");
            for (Carte carte : rangée) {
                sb.append(carte.getNombre()).append(" ").append("(").append(carte.getPoints()).append(" Points)").append(" ");
            }
            etiquettesRangées.get(i).setText(sb.toString());
        }
    }
}
