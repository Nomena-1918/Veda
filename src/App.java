import java.sql.Connection;
import java.util.HashMap;
import org.junit.Test;

import veda.godao.entity.EntityTable;
import veda.godao.DAO;
import veda.godao.DAOInterface;
import veda.godao.Look;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;
import veda.godao.utils.Constantes;
import veda.godao.utils.DAOConnexion;

public class App {
    @Table("dept")
    public static class Dept{
        @PrimaryKey
        @Column("id")
        Integer iddept;
        @Column("label")
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
    @Table("emp")
    public static class Emp{
        @PrimaryKey
        @Column("idemp")
        Integer id;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        @Column("label")
        String nom;
        // @Column("age")
        // Double age;
        @ForeignKey(recursive = false)
        @Column("iddeptemp")
        Dept dept;
        public Dept getDept() {
            return dept;
        }
        public void setDept(Dept dept) {
            this.dept = dept;
        }
        public String getNom() {
            return nom;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }
        // public Double getAge() {
        //     return age;
        // }
        // public void setAge(Double age) {
        //     this.age = age;
        // }
    }
    @Table("film")
    public static class Film{
        @PrimaryKey
        @Column("id")
        private Integer id;
        @Column("nom")
        private String nom;
        @Column("duree")
        private Double duree;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getNom() {
            return nom;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }
        public Double getDuree() {
            return duree;
        }
        public void setDuree(Double duree) {
            this.duree = duree;
        }
        
    }
    public static void main(String[] args) throws Exception {
        // Emp where=new Emp();
        // where.setId(1);
        // Dept dept=new Dept();
        // dept.setIddept(1);
        // Emp e=new Emp();
        // e.nom="Ferry";
        // e.setDept(dept);
        // DAO dao=new DAO("scott", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        // dao.update(null, e, where);
        // Dept dept1=new Dept();
        // Dept dept2=new Dept();
        // System.out.println(dept1);
        // System.out.println(dept2);
        DAO dao=new DAO("org.postgresql.Driver", "postgresql", "cinepax", "localhost", "5432", "eriq", "root", false, true, 2);
        try(Connection connect=DAOConnexion.getConnexion(dao)){
            EntityTable table=new EntityTable();
            table.setNom("projection_donnee");
            table.setColonnes(new HashMap<>(){{
                put("id", "serial primary key");
                put("numseance", "int not null");
                put("film", "varchar not null");
                put("categorie", "varchar not null");
                put("salle", "varchar not null");
                put("date", "date not null");
                put("heure", "timestamp not null");
            }});
            dao.createTable(connect, table, true);
        }
    }
    @Test
    public void insertEmp() throws Exception{
        Dept dept=new Dept();
        dept.setIddept(1);
        Emp e=new Emp();
        e.nom="Ferry";
        e.setDept(dept);
        DAOInterface daoInterface =new DAO("scott", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        daoInterface.insertWithoutPrimaryKey(null, e);
    }
    @Test
    public void updateEmp() throws Exception{
        Emp where=new Emp();
        where.setId(2);
        Dept dept=new Dept();
        dept.setIddept(2);
        Emp e=new Emp();
        e.nom="Ferry2";
        e.setDept(dept);
        DAOInterface daoInterface =new DAO("scott", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        daoInterface.update(null, e, where);
    }
    @Test
    public void insertDept() throws Exception{
        Dept dept=new Dept();
        dept.setNom("Finances");
        DAOInterface daoInterface =new DAO("vedatest", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        daoInterface.insertWithoutPrimaryKey(null, dept);
    }
    @Test
    public void selectEmps() throws Exception{
        DAOInterface daoInterface =new DAO("vedatest", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        Emp[] emps= daoInterface.select(null, Emp.class);
        for(Emp e:emps){
            System.out.println(e.getNom()+" "+e.getDept());
        }
    }
    @Test
    public void updateLook() throws Exception{
        DAOInterface daoInterface =new DAO("poketra", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        Look change=new Look();
        change.setId(1);
        change.setNom("debraille");
        Look where=new Look();
        where.setId(1);
        daoInterface.update(null, change, where);
    }
}
