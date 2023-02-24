package veda;

import org.checkerframework.checker.units.qual.Acceleration;

import veda.chrono.DateGen;
import veda.reflect.annotation.IsRelation;

@IsRelation(table="504")
@Deprecated
@Acceleration
public class User {
    String nom;
    int age;
    DateGen dateNaiss;
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public DateGen getDateNaiss() {
        return dateNaiss;
    }
    public void setDateNaiss(DateGen dateNaiss) {
        this.dateNaiss = dateNaiss;
    }
}
