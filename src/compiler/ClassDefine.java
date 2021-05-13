package compiler;

public class ClassDefine {
    public String name;
    public String parent = "Object";

    public ClassDefine(String name) {
        this.name = name;
    }

    public ClassDefine(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Class: " + "(name: " + this.name + " ,parent: " + this.parent + ")";
    }
}
