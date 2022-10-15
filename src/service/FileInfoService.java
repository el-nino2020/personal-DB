package service;

import com.google.common.base.Preconditions;
import dao.FileInfoDAO;
import domain.FileInfo;

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


    //TODO:不会使用info.lastmodified，直接使用MySQL的NOW()函数
    public boolean insertFileInfo(FileInfo info, String tableName /*TODO:还需要知道插入哪张表*/) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(info);
        Preconditions.checkNotNull(tableName);

        if (!new DBService(accountService).tableExists(tableName)) {
            return false;
        }





        return true;
    }

    /**
     * 给定一个压缩文件，从数据库中读取对应的FileInfo
     *
     * @param file 过去压缩并记录的文件
     * @return FileInfo对象
     */
    public FileInfo getFileInfo(File file) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        String[] strings = splitFileName(file.getName());
        String tableName = strings[0];
        String id = strings[1];

        Preconditions.checkState(new DBService(accountService).tableExists(tableName), "文件所属的表不存在");

        String sql = "select * from ? where id = ?;";
        FileInfo ans = fileInfoDAO.querySingleRow(accountService.getConnection(),
                sql,
                FileInfo.class,
                tableName, id);
        return ans;
    }

    /**
     * 根据rar压缩包的文件信息, 生成对应的FileInfo
     *
     * @param file 一个rar压缩包
     * @return
     */
    public FileInfo makeFileInfo(File file) {
        return null;
    }


    /**
     * name应该代表一个名字形如 表名_id.rar 的文件，如果不是，抛出异常
     *
     * @param name 文件名
     * @return [表名, id]
     */
    private String[] splitFileName(String name) {
        String[] ans = new String[2];
        try {
            Preconditions.checkArgument(name.endsWith(".rar"));

            int index = name.lastIndexOf("_");

            ans[0] = name.substring(0, index);
            ans[1] = name.substring(index + 1, name.length() - 4);

            Preconditions.checkArgument(Integer.parseInt(ans[1]) > 0);
        } catch (Exception e) {
            throw new RuntimeException("invaild file name");
        }

        return ans;
    }





}
