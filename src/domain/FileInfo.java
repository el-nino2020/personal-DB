package domain;

import com.google.common.base.Preconditions;
import utils.Utility;

import java.io.File;
import java.time.LocalDateTime;

public class FileInfo {
    private String filename;
    private String note;
    private long filesize;

    private LocalDateTime lastmodified;
    private String passwd;
    private String md5value;

    private int id;
    private int dirid;


    /**
     * 比较this和file的md5值、文件大小等信息，并输出比较结果到控制台
     * 也会输出this的其他字段(不包括passwd)
     *
     * @param file rar压缩文件
     */
    public void checkAndInform(File file) {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        String string = Utility.getFileMD5(file);

        System.out.println("================文件摘要===================");
        if (md5value.equals(string)) {
            System.out.println("1. 压缩文件MD5值与记录的一致");
        } else {
            System.out.println("1. 压缩文件MD5值与记录的不一致，请注意!!!");
            System.out.format("\t记录的MD5值: %s\n", md5value);
            System.out.format("\t文件的MD5值: %s\n", string);
        }


        long length = file.length();//返回文件的字节数
        if (length == filesize) {
            System.out.println("2. 压缩文件大小与记录的一致");
        } else {
            System.out.println("2. 压缩文件MD5值与记录的不一致，请注意!!!");
            System.out.format("\t记录的文件大小: %s bytes\n", md5value + "");
            System.out.format("\t当前的文件大小: %s bytes\n", length + "");
        }

        System.out.format("3. 文件的真实名称为: 『 %s 』\n", filename);
        System.out.format("4. 最后一次修改的时间为: %s\n", Utility.getFormattedTime(lastmodified));

        System.out.print("5. 关于文件的注释：  ");
        System.out.println(note);

        System.out.println("================摘要结束===================");

    }


    public FileInfo() {
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "filename='" + filename + '\'' +
                ", note='" + note + '\'' +
                ", filesize=" + filesize +
                ", lastmodified=" + lastmodified +
                ", passwd='" + passwd + '\'' +
                ", md5value='" + md5value + '\'' +
                ", id=" + id +
                ", dirid=" + dirid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(LocalDateTime lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMd5value() {
        return md5value;
    }

    public void setMd5value(String md5value) {
        this.md5value = md5value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }


    public int getDirid() {
        return dirid;
    }

    public void setDirid(int dirid) {
        this.dirid = dirid;
    }
}
