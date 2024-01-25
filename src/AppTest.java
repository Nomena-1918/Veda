import models.Dept;
import models.Emp;
import org.junit.Test;

import veda.godao.DAO;
import veda.godao.utils.Constantes;

public class AppTest {
    private static final DAO dao;
    static {
        dao=new DAO(
                "test_veda",
                "localhost",
                "5432",
                "nomena",
                "root",
                false,
                Constantes.PSQL_ID);
    }

    @Test
    public void insertDept() throws Exception{
        Dept dept=new Dept();
        dept.setNom("Info");
        dao.insertWithoutPrimaryKey(null, dept);
    }
    @Test
    public void insertEmp() throws Exception{
        Dept dept=new Dept();
        dept.setIddept(2L);

        Emp e=new Emp();
        e.setNom("Antema");
        e.setDept(dept);

        dao.insertWithoutPrimaryKey(null, e);
    }
    @Test
    public void selectEmps() throws Exception{
        Emp[] emps=dao.select(null, Emp.class);
        for(Emp e:emps){
            System.out.println(e.getNom()+" "+e.getDept().getNom());
        }
    }

    @Test
    public void selectEmpsWhere() throws Exception{
        Emp where = new Emp();
        where.setId(3L);
        Emp[] emps=dao.select(null, Emp.class, where);
        for(Emp e:emps){
            System.out.println(e.getNom()+" "+e.getDept().getNom());
        }
    }


    @Test
    public void updateLook() throws Exception{
        Emp where=new Emp();
        where.setNom("TEST EMP !!!");

        Emp change=new Emp();
        change.setNom("test tsotra");

        dao.update(null, change, where);
    }
}
