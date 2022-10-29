package domain;

public class DirectoryInfo {
    private String dirname;
    private String note;
    private int id;


    public DirectoryInfo() {
    }

    public DirectoryInfo(String dirname, String note) {
        this.dirname = dirname;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return id + "\t\t" + dirname + "\t\t\t" + note + "\n";
    }
}
