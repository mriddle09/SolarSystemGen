package org.example.celestial;

public enum PlanetType {
    GASGIANT, UNINHABITABLE, BARREN, HABITABLE;

    String typeToString() {
        switch (this) {
            case HABITABLE: return "Habitable";
            case GASGIANT: return "Gas Giant";
            case BARREN: return "Barren";
            case UNINHABITABLE: return "Uninhabitable";
        }
        return "";
    }
}


