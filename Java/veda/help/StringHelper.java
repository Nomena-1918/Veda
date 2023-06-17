package veda.help;

import veda.block.Vector;

public class StringHelper {
    public static String minStart(String s){
        String first=String.valueOf(s.charAt(0));
        return s.replaceFirst(first, first.toLowerCase());
    }
    public static String majStart(String s){
        String first=String.valueOf(s.charAt(0));
        return s.replaceFirst(first, first.toUpperCase());
    }
    public static String[] stringify(Vector<Object> list){
        String[] strings=new String[list.size()];
        for(int i=0; i<strings.length; i++){
            strings[i]=list.get(i).toString();
        }
        return strings;
    }
    public static String listParenthesis(String[] list){
        String response="("+list[0];
        for(int i=1; i<list.length; i++){
            response+=", "+list[i];
        }
        response+=")";
        return response;
    }
}
