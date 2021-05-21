package compiler;


public class Property {
    public String name;
    public String type;
    public int row;
    public int col;

    @Override
    public String toString() {
        return "proprety: (name: " + this.name + ") (type: " + this.type + ") (line: " +this.row + ") ( col: "
                + this.col +")";
    }
}
