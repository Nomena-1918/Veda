package models;

import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

@Table("dept")
public class Dept {
    @PrimaryKey
    @Column("id")
    Long iddept;
    @Column("nom")
    String nom;

    public Long getIddept() {
        return iddept;
    }

    public void setIddept(Long iddept) {
        this.iddept = iddept;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}