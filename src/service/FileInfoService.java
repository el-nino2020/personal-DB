package service;

import com.google.common.base.Preconditions;
import dao.FileInfoDAO;
import domain.FileInfo;
import utils.Utility;

import java.io.File;

/**
 * 该service负责: <br>
 * 1. 与DBMS交互，处理记录的所有字段 <br>
 * 2. 生成FileInfo对象
 */
public class FileInfoService {
    AccountService accountService;
    FileInfoDAO fileInfoDAO = new FileInfoDAO();

    public FileInfoService(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 向tableName表中插入一条记录，该记录的字段值存储在info中
     */
    public void insertFileInfo(FileInfo info) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(info);

        //如果在sql中使用 ? 代替表名，则实际生成的表名周围带有单引号，不是合法的SQL语句
        String sql = "INSERT INTO files" +
                " (filename, lastmodified, passwd, md5value, note, filesize, dirid) " +
                "VALUES (?, now(), ?, ?, ?, ?, ?);";

        fileInfoDAO.update(accountService.getConnection(), sql,
                info.getFilename(),
                info.getPasswd(),
                info.getMd5value(),
                info.getNote(),
                info.getFilesize(),
                info.getDirid());
    }

    /**
     * 给定一个压缩文件，从files表中读取对应的FileInfo
     *
     * @param file 过去压缩并记录的文件
     * @return FileInfo对象
     */
    public FileInfo getFileInfo(File file) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        String id = splitFileName(file.getName());


        //如果在sql中使用 ? 代替表名，则实际生成的表名周围带有单引号，不是合法的SQL语句
        String sql = "select * from files where id = ?;";
        FileInfo ans = fileInfoDAO.querySingleRow(accountService.getConnection(),
                sql,
                FileInfo.class,
                id);
        return ans;
    }

    /**
     * 根据rar压缩包的文件信息, 生成对应的FileInfo
     *
     * @param file 一个rar压缩包
     * @return
     */
    public FileInfo makeFileInfo(File file, String originalName, String archivePassword,
                                 String note, int directoryID) {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        FileInfo ans = new FileInfo();

        ans.setFilename(originalName);
        ans.setFilesize(file.length());
        ans.setMd5value(Utility.getFileMD5(file));
        ans.setPasswd(archivePassword);
        ans.setNote(note);
        ans.setDirid(directoryID);

        return ans;
    }


    /**
     * name应该代表一个名字形如 files_id.rar 的文件，如果不是，抛出异常
     *
     * @param name 文件名
     * @return id
     */
    private String splitFileName(String name) {
        String ans;
        try {
            Utility.assertion(name.endsWith(".rar"), "");

            int index = name.lastIndexOf("_");
            Utility.assertion(name.substring(0, index).startsWith("files"), "");

            ans = name.substring(index + 1, name.length() - 4);
            Utility.assertion(Integer.parseInt(ans) > 0, "");
        } catch (Exception e) {
            throw new RuntimeException("要解压的文件名非法");
        }
        return ans;
    }


}
