package veda.godao.entity;

import java.util.HashMap;

public class EntityTable {
    private String nom;
    private HashMap<String, String> colonnes;
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public HashMap<String, String> getColonnes() {
        return colonnes;
    }
    public void setColonnes(HashMap<String, String> colonnes) {
        this.colonnes = colonnes;
    }
    
}
