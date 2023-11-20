package veda.godao.exceptions;

import veda.godao.utils.Constantes;

public class ConnectionException extends Exception{
    private int code;
    
    public ConnectionException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        String message="";
        switch(getCode()){
            case Constantes.NOCONNECTION_CODE:
                message="No connection was defined (exit code: "+Constantes.NOCONNECTION_CODE+")";
                break;
        }
        return message;
    }
    
}
