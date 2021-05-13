package compiler;


public class Property {
    public String name;
    public String type;

    @Override
    public String toString() {
        return "proprety: (name: " + this.name + ") (type: " + this.type + ")";
    }
}
