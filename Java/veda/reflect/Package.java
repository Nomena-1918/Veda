package veda.reflect;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import veda.block.Vector;

@SuppressWarnings("rawtypes")
public class Package{
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Package(){}
    public Package(String name){
        setName(name);
    }
	public Vector<Class> getClasses() throws Exception {
        Vector<Class> classes = new Vector<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = getName().replace('.', '/');
		URL resource = classLoader.getResource(path);
		File directory = new File(resource.getFile());
        if (directory.exists()) {
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".class")) {
                    classes.add(Class.forName(getName() + '.' + files[i].substring(0, files[i].length() - 6)));
                }
            }
        }
        return classes;
    }
    public Vector<Class> getClassesAnnoted(String annotName) throws Exception{
        Vector<Class> listClass=getClasses();
        Vector<Class> newList=new Vector<>();
        for(Class c:listClass){
            Reflect reflect=new Reflect(c);
            Annotation annot=reflect.isAnnotedWith(annotName);
            if(annot!=null){
                newList.add(c);
            }
        }
        return newList;
    }
}
