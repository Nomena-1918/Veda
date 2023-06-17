package veda.reflect;
import veda.help.StringHelper;

public class Datas {
    public static String[] getNumericalTypes(){
        String[] list=new String[5];
        list[0]="Integer";
        list[1]="Double";
        list[2]="Float";
        list[3]="Long";
        list[4]="Short";
        return list;
    }
    public static String[] getNonNumericalTypes(){
        String[] list=new String[4];
        list[0]="String";
        list[1]="Character";
        list[2]="Boolean";
        list[3]="Byte";
        return list;
    }
    public static String[] getPrimitiveTypes(){
        String[] list=new String[10];
        list[0]="String";
        list[1]="Integer";
        list[2]="Double";
        list[3]="Boolean";
        list[4]="Character";
        list[5]="Long";
        list[6]="Float";
        list[7]="Byte";
        list[8]="Short";
        list[9]="DateGen";
        list[10]="DateOnly";
        list[11]="TimeOnly";
        return list;
    }
    public static String convertType(String type){
        if(type.equals("Integer")){
            return "int";
        }
        return StringHelper.minStart(type);
    }
    public static String revertType(String type){
        if(type.equals("int")){
            return "Integer";
        }else if(type.equals("char")){
            return "Character";
        }
        return StringHelper.majStart(type);
    }
}
