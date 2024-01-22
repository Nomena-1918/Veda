package models;

import veda.godao.annotations.Column;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

@Table("dept")
public class Dept {
    @PrimaryKey
    @Column("id")
    Integer iddept;
    @Column("nom")
    String nom;
    public Integer getIddept() {
        return iddept;
    }
    public void setIddept(Integer iddept) {
        this.iddept = iddept;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

}