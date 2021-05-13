package compiler;

public class Scope {
    public Scope parentScope;
    public int depth;
    public int number;
    public String name;
    public SymbolTable SymbolTable = new SymbolTable(number, name);

    Scope(String name, int number) {
        this.number = number;
        this.name = name;
        SymbolTable.scopeNumber = number;
        SymbolTable.name = name;
    }
}
