package domain;

import com.google.common.base.Preconditions;
import utils.Utility;

import java.io.File;
import java.time.LocalDateTime;

public class FileInfo {
    private String name;

    /**
     * file note/comment
     */
    private String note;

    /**
     * file size in bytes
     */
    private Long size;

    /**
     * last modified time
     */
    private LocalDateTime time;


    /**
     * archive password
     */
    private String password;

    /**
     * md5(archive)
     */
    private String md5Value;

    /**
     * file_id
     */
    private Integer id;

    /**
     * foreign key on DirInfo.id
     */
    private Integer dirId;


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
        if (md5Value.equals(md5)) {
            ans.append("1. 压缩文件MD5值与记录的一致\n");
        } else {
            ans.append("1. 压缩文件MD5值与记录的不一致，请注意!!!\n");
            ans.append(String.format("\t记录的MD5值: %s\n", md5Value));
            ans.append(String.format("\t文件的MD5值: %s\n", md5));
        }


        Long length = file.length();//返回文件的字节数
        if (length == size) {
            ans.append("2. 压缩文件大小与记录的一致\n");
        } else {
            ans.append("2. 压缩文件MD5值与记录的不一致，请注意!!!\n");
            ans.append(String.format("\t记录的文件大小: %s bytes\n", md5Value + ""));
            ans.append(String.format("\t当前的文件大小: %s bytes\n", length + ""));
        }

        ans.append(String.format("3. 文件的真实名称为: 『 %s 』\n", name));
        ans.append(String.format("4. 最后一次修改的时间为: %s\n", Utility.getFormattedTime(time)));

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
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", size=" + size +
                ", time=" + time +
                ", password='" + password + '\'' +
                ", md5value='" + md5Value + '\'' +
                ", id=" + id +
                ", dirId=" + dirId +
                '}';
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDirId() {
        return dirId;
    }

    public void setDirId(Integer dirId) {
        this.dirId = dirId;
    }
}
