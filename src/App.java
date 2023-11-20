import java.sql.Connection;

import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;
import veda.godao.utils.DAOConnexion;
import veda.godao.utils.QueryUtils;

public class App {
    @Table("dept")
    public static class Dept{
        @PrimaryKey
        @Column("id_dept")
        Integer iddept;
        @Column("nom_dept")
        String nom="Finance";
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
        @Column("id_emp")
        Integer id;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        @Column("nom_emp")
        String nom;
        @Column("age_emp")
        Double age;
        @ForeignKey
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
        public Double getAge() {
            return age;
        }
        public void setAge(Double age) {
            this.age = age;
        }
        
    }
    static void addFive(Double d){
        d=5.;
    }
    public static void main(String[] args) throws Exception {
        Connection connect=DAOConnexion.getConnexionPostgreSql("vedatest", "eriq", "root", "localhost");
        Emp e=new Emp();
        e.nom="Ferry";
        Emp e2=new Emp();
        e2.id=1;
        try{
            DAO.update(connect, e, e2);
            connect.commit();
            // Object[] emps=DAO.select(connect, Emp.class);
            // for(Object emp:emps){
            //     System.out.println(((Emp)emp).nom+"; Dept: "+((Emp)emp).dept.nom);
            // }
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            connect.close();
        }
    }
}
