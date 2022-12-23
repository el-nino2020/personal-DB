package domain;

public class DirInfo {
    private String name;
    private String note;
    private Integer id;


    public DirInfo() {
    }

    public DirInfo(String name, String note, Integer id) {
        this.name = name;
        this.note = note;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "DirInfo{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", id=" + id +
                '}';
    }
}
