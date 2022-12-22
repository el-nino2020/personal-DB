package domain;

import com.google.common.base.Preconditions;
import utils.Utility;

import java.io.File;
import java.time.LocalDateTime;

public class FileInfo {
    private String filename;
    private String note;
    private Long filesize;

    private LocalDateTime lastmodified;
    private String passwd;
    private String md5value;

    private Integer id;
    private Integer dirid;


    /**
     * 比较this和file的md5值、文件大小等信息，并输出比较结果到控制台
     * 也会输出this的其他字段(不包括passwd)
     *
     * @param file rar压缩文件
     */
    public String getFileAbstract(File file) {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        StringBuilder ans = new StringBuilder();


        String md5 = Utility.getFileMD5(file);

        ans.append("================文件摘要===================\n");
        if (md5value.equals(md5)) {
            ans.append("1. 压缩文件MD5值与记录的一致\n");
        } else {
            ans.append("1. 压缩文件MD5值与记录的不一致，请注意!!!\n");
            ans.append(String.format("\t记录的MD5值: %s\n", md5value));
            ans.append(String.format("\t文件的MD5值: %s\n", md5));
        }


        Long length = file.length();//返回文件的字节数
        if (length == filesize) {
            ans.append("2. 压缩文件大小与记录的一致\n");
        } else {
            ans.append("2. 压缩文件MD5值与记录的不一致，请注意!!!\n");
            ans.append(String.format("\t记录的文件大小: %s bytes\n", md5value + ""));
            ans.append(String.format("\t当前的文件大小: %s bytes\n", length + ""));
        }

        ans.append(String.format("3. 文件的真实名称为: 『 %s 』\n", filename));
        ans.append(String.format("4. 最后一次修改的时间为: %s\n", Utility.getFormattedTime(lastmodified)));

        ans.append("5. 关于文件的注释：  ");
        ans.append(note);

        ans.append("\n================摘要结束===================\n");


        return ans.toString();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }


    public Integer getDirid() {
        return dirid;
    }

    public void setDirid(Integer dirid) {
        this.dirid = dirid;
    }
}
