package domain;

public class TableInfo {
    private int id;
    private String tablename;
    private String note;

    public TableInfo() {
    }

    public TableInfo(String tablename, String note) {
        this.tablename = tablename;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return id + "\t\t" + tablename + "\t\t\t" + note + "\n";
    }
}
