package veda.help;

public class Check {
    public static boolean checkIfContains(Object[] list, Object element){
        for(Object obj:list){
            if(obj.equals(element)){
                return true;
            }
        }
        return false;
    }
}
