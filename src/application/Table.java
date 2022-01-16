package application;

public class Table {
    private int id;
    private String name;
    private double marks;
    private String section;

    public Table() {
        this.id = 0;
        this.name = "";
        this.marks = 0.0;
        this.section = "";
    }

    public Table(int id, String name, double marks, String section) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.section = section;
    }

    // Getters & setters - Alt + insert
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public double getMarks() {
        return marks;
    }
    
    public String toString() {
    	return id + " " + name + " " + marks + " " + section;
    }
}
