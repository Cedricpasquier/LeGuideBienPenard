package com.example.leguidebienpnard.Modele.MVC.presentation.model;

import java.util.List;

public class User {

    public List<Objet> listeObjets;

    public List<Objet> getListeObjets() {
        return listeObjets;
    }

    public void addObject(Objet objet){
        listeObjets.add(objet);
    }

    public User(List<Objet> listeObjets) {
        this.listeObjets = listeObjets;
    }
}
