package compiler;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class SymbolTable {
    public Hashtable<String,Object> items = new Hashtable<String, Object>();
    public int scopeNumber;
    public String name;
    SymbolTable(int scopeNumber,String name){
        this.name = name;
        this.scopeNumber = scopeNumber;
    }
    public String toString() {
        return "-------------  " + name + " : " + scopeNumber + "  -------------\n" +
                printItems() +
                "-----------------------------------------\n";
    }
    public String printItems(){
        String itemsStr = "";
        for (Map.Entry<String,Object> entry : items.entrySet()) {
            itemsStr += "Key = " + entry.getKey() + "  | Value = " + entry.getValue().toString() + "\n";
        }
        return itemsStr;
    }
    public void insert(String idefname ,Object value){
        items.put(idefname,value);
    }
    public boolean lookUp (String idefName){
        return items.containsKey(idefName);
    }

}
