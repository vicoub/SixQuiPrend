package org.example;

public class Carte {
    private final int nombre;
    private final int points;

    public Carte(int nombre, int points) {
        this.nombre = nombre;
        this.points = points;
    }

    // Retourne le nombre de la carte
    public int getNombre() {
        return nombre;
    }

    // Retourne les points de la carte
    public int getPoints() {
        return points;
    }
}

