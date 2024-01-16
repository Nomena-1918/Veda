import org.junit.Test;

import veda.godao.DAO;
import veda.godao.Look;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;
import veda.godao.utils.Constantes;

public class App {
    @Table("dept")
    public static class Dept{
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
    @Table("emp")
    public static class Emp{
        @PrimaryKey
        @Column("id")
        Integer id;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        @Column("nom")
        String nom;
        // @Column("age")
        // Double age;
        @ForeignKey(recursive = false)
        @Column("iddept")
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
    public static void main(String[] args) throws Exception {
        DAO dao=new DAO("poketra", "localhost", "5432", "eriq", "root", false, 2);
        Look[] objects=dao.select(null, Look.class);
        for(Look l:objects){
            System.out.println(l.getNom());
        }
    }
    @Test
    public void insertEmp() throws Exception{
        Dept dept=new Dept();
        dept.setIddept(1);
        Emp e=new Emp();
        e.nom="Ferry";
        e.setDept(dept);
        DAO dao=new DAO("vedatest", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        dao.insertWithoutPrimaryKey(null, e);
    }
    @Test
    public void insertDept() throws Exception{
        Dept dept=new Dept();
        dept.setNom("Finances");
        DAO dao=new DAO("vedatest", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        dao.insertWithoutPrimaryKey(null, dept);
    }
    @Test
    public void selectEmps() throws Exception{
        DAO dao=new DAO("vedatest", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        Emp[] emps=dao.select(null, Emp.class);
        for(Emp e:emps){
            System.out.println(e.getNom()+" "+e.getDept());
        }
    }
    @Test
    public void updateLook() throws Exception{
        DAO dao=new DAO("poketra", "localhost", "5432", "eriq", "root", false, Constantes.PSQL_ID);
        Look change=new Look();
        change.setId(1);
        change.setNom("debraille");
        Look where=new Look();
        where.setId(1);
        dao.update(null, change, where);
    }
}
