package org.example;
import java.util.ArrayList;
import java.util.List;

public class Plateau {
    private List<List<Carte>> rangées;

    public Plateau() {
        rangées = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rangées.add(new ArrayList<>());
        }
    }

    private List<Carte> choisirRangée(Carte carte) {
        int indexRangéeChoisie = 4;
        int minOffset = 0;

        for (List<Carte> rangée : rangées) {
            if (carte.getNombre() > rangée.get(rangée.size() - 1).getNombre()) {
                int offset = carte.getNombre() - rangée.get(rangée.size() - 1).getNombre();
                if (minOffset == 0 || offset < minOffset) {
                    minOffset = offset;
                    indexRangéeChoisie = rangées.indexOf(rangée);
                }
            }
        }

        return rangées.get(indexRangéeChoisie);
    }

    public int ajouterCarte(Carte carte) {
        int pointsPris = 0;
        try {
            List<Carte> rangéeChoisie = choisirRangée(carte);
            if (rangéeChoisie.size() < 5) {
                rangéeChoisie.add(carte);
            } else {
                // Si la rangée choisie possède 5 cartes, le joueur récupère les points de la rangée
                // et cette rangée est vidée
                pointsPris = getTotalPoints(rangéeChoisie);
                rangéeChoisie.clear();
                rangéeChoisie.add(carte);
            }

        } catch(Exception e) {
            // Si aucune rangée ne peut recevoir la carte, elle est placée dans la rangée avec le total de points le plus bas
            // et cette rangée est vidée
            List<Carte> rangéeÀRemplacer = obtenirRangéeMoinsDePoints();
            pointsPris = getTotalPoints(rangéeÀRemplacer);
            rangéeÀRemplacer.clear();
            rangéeÀRemplacer.add(carte);
        }

        return pointsPris;
    }

    public List<List<Carte>> getRangées() {
        return rangées;
    }

    private int getTotalPoints(List<Carte> rangée) {
        int total = 0;
        for (Carte carte : rangée) {
            total += carte.getPoints();
        }
        return total;
    }

    private List<Carte> obtenirRangéeMoinsDePoints() {
        int indexRangéeRetournée = 0;
        int minPoints = 0;

        for (List<Carte> rangée : rangées) {
            int pointsRangée = getTotalPoints(rangée);
            if (minPoints == 0 || pointsRangée < minPoints) {
                minPoints = pointsRangée;
                indexRangéeRetournée = rangées.indexOf(rangée);
            }
        }

        return rangées.get(indexRangéeRetournée);
    }
}
