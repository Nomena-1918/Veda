package veda.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import veda.block.Vector;
import veda.help.StringHelper;

@SuppressWarnings("rawtypes")
public class Reflect {
    private Class c;
    public Class getC() {
        return c;
    }
    public void setC(Class c) {
        this.c = c;
    }
    public Reflect(Class c){
        setC(c);
    }
    public String getClassName(Object object){
        return getC().getSimpleName();
    }
    public Field[] getDeclaredFields(Object object){
        return getC().getDeclaredFields();
    }
    public String[] getFieldNames(){
        Field[] field=getC().getDeclaredFields();
        String[] fields=new String[field.length];
        for(int i=0; i<field.length; i++){
            fields[i]=field[i].getName();
        }
        return fields;
    }
    public String[] getFieldTypes(){
        Field[] field=getC().getDeclaredFields();
        String[] types=new String[field.length];
        for(int i=0; i<field.length; i++){
            types[i]=field[i].getType().getSimpleName();
        }
        return types;
    }
    public Vector<Field> getNotNullField(Object object) throws Exception{
        String[] fieldName=getFieldNames();
        Field[] fields=getC().getDeclaredFields();
        Vector<Field> list=new Vector<>();
        for(int i=0; i<fieldName.length; i++){
            Object get=get(object, fieldName[i]);
            if(get==null){
                continue;
            }
            list.add(fields[i]);
        }
        return list;
    }
    public String[] getNotNullFieldsName(Object object) throws Exception{
        Vector<Field> listFields=getNotNullField(object);
        String[] names=new String[listFields.size()];
        for(int i=0; i<names.length; i++){
            names[i]=listFields.get(i).getName();
        }
        return names;
    }
    public String[] getNotNullFieldsType(Object object) throws Exception{
        Vector<Field> listFields=getNotNullField(object);
        String[] types=new String[listFields.size()];
        for(int i=0; i<types.length; i++){
            types[i]=listFields.get(i).getType().getSimpleName();
        }
        return types;
    }
    public static Object get(Object caller, String att) throws Exception{
        return caller.getClass().getMethod("get"+StringHelper.majStart(att)).invoke(caller);
    }
    public static void set(Object caller, String att, String type, Object value) throws Exception{
        if(type.equals("int")){
            caller.getClass().getMethod("set"+StringHelper.majStart(att), int.class).invoke(caller, value);
        }else{
            caller.getClass().getMethod("set"+StringHelper.majStart(att), value.getClass()).invoke(caller, value);
        }
    }
    public Object construct(Object origin) throws Exception{
        return origin.getClass().getConstructor().newInstance();
    }
    public String typeOfField(Object object, String field){
        String[] fieldName=getFieldNames();
        String[] fieldType=getFieldTypes();
        for(int i=0; i<fieldType.length; i++){
            if(fieldName[i].equals(field)){
                return fieldType[i];
            }
        }
        return null;
    }
    public Annotation isAnnotedWith(String annotName){
		Annotation[] annotes=getC().getAnnotations();
		for(Annotation annot:annotes){
			if(annot.annotationType().getSimpleName().equals(annotName)){
				return annot;
			}
		}
		return null;
	}
	public static Annotation isAnnotedWith(String annotName, Field clas){
		Annotation[] annotes=clas.getAnnotations();
		for(Annotation annot:annotes){
			if(annot.annotationType().getSimpleName().equals(annotName)){
				return annot;
			}
		}
		return null;
	}
    public String[] getAnnotationNames(){
        Annotation[] annots=getC().getAnnotations();
        String[] names=new String[annots.length];
        for(int i=0; i<annots.length; i++){
            names[i]=annots[i].annotationType().getSimpleName();
        }
        return names;
    }
    public Vector<Field> getAnnotatedField(String annotName){
        Field[] listField=getC().getDeclaredFields();
        Vector<Field> newList=new Vector<>();
        for(Field f:listField){
            Annotation annot=isAnnotedWith(annotName, f);
            if(annot!=null){
                newList.add(f);
            }
        }
        return newList;
    }
    public static String[] getAnnotOfField(Field field){
        Annotation[] annotes=field.getAnnotations();
        String[] list=new String[annotes.length];
        for(int i=0; i<annotes.length; i++){
            list[i]=annotes[i].annotationType().getSimpleName();
        }
        return list;
    }
    public String getValueOfAnnot(String annot, String annotField) throws Exception{
        Annotation annote=isAnnotedWith(annot);
        String value=annote.annotationType().getMethod(annotField).invoke(annote).toString();
        return value;
    }
}
