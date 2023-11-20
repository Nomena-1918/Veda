package veda.godao.utils;

public class StringUtils {
    public static String majStart(String s){
        return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toUpperCase());
    }
}
