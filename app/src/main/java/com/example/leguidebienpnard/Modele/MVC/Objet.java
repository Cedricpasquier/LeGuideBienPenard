package com.example.leguidebienpnard.Modele.MVC;

public class Objet {
    private String name;
    private int own;
    private String Description;
    private String Importance;
    private String Localisation;

    public Objet(String name, int own) {
        this.name = name;
        this.own = own;
    }

    public String getName() {
        return name;
    }

    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
    }

    public String getDescription() {
        return Description;
    }

    public String getImportance() {
        return Importance;
    }

    public String getLocalisation() {
        return Localisation;
    }
}
