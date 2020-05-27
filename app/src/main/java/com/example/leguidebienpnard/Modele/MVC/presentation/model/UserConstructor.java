package com.example.leguidebienpnard.Modele.MVC.presentation.model;

import java.util.ArrayList;
import java.util.List;

public class UserConstructor {



    public UserConstructor() {

    }

    public User preFabUser(String userName) {
        List<Objet> objets = new ArrayList<>();
        Objet batte = new Objet("Batte de baseball",0);
        Objet livre = new Objet("Livre",0);
        Objet canape = new Objet("Canapé",0);
        Objet casque = new Objet("Casque VR",0);
        Objet fondVert = new Objet("Fond vert",0);
        Objet lit = new Objet("Lit",0);
        Objet ordi = new Objet("Ordinateur",0);
        Objet frigo = new Objet("Réfrigérateur",0);
        Objet tapisYoga = new Objet("Tapis de yoga",0);
        Objet tele = new Objet("Télévision",0);
        objets.add(batte);
        objets.add(livre);
        objets.add(canape);
        objets.add(casque);
        objets.add(fondVert);
        objets.add(lit);
        objets.add(ordi);
        objets.add(frigo);
        objets.add(tapisYoga);
        objets.add(tele);
        User user = new User(objets);
        return user;
    }
}
