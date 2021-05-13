package compiler;

import java.util.ArrayList;

public class Method {
    public String name;
    public ArrayList<String> parametersTypes = new ArrayList<String>();
    public String returnType;

    public String toString(){
        String method = "Method: " +  "(name: " + name + ") (returnType: " + returnType + ")";
        if (parametersTypes.size() == 0){
            return method;
        }
        String types = " (parametersType:";
        for (int i=0; i<parametersTypes.size(); i++){
            types += " [" +parametersTypes.get(i) + " , index: " + (i+1) + "] ";
        }

        return  method + types + ")";
    }
    public Method(String name){
        this.name = name;
    }
}
