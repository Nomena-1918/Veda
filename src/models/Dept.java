package models;

import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

@Table("dept")
public class Dept {
    @PrimaryKey
    @Column("id")
    Long id;
    @Column("nom")
    String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}