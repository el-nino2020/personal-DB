package service;

import dao.FileInfoDAO;
import domain.FileInfo;

import java.io.File;
import java.lang.reflect.Field;

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


    public boolean insertFileInfo(FileInfo info,String tableName /*TODO:还需要知道插入哪张表*/) {
        loginAssert();
    }

    public FileInfo getFileInfo(File file) {
        loginAssert();
        String name = file.getName();

        //TODO:将name分解为表名+id的形式，用于查询

        String sql = "select * from ? where id = ?;";
        FileInfo ans = fileInfoDAO.querySingleRow(accountService.getConnection(),
                sql,
                FileInfo.class,
                null);
        return ans;
    }


    private void loginAssert() {
        //The most important thing to remember about assertions is that
        // they can be disabled, so never assume they'll be executed.
        //可以用第三方库来开启assert，但我直接选择写成一个方法了
//        assert accountService.getLoginStatus() : "数据库账户未登录"
        if (!accountService.getLoginStatus()) {
            throw new RuntimeException("数据库账户未登录");
        }
    }


    /**
     * 根据rar压缩包的文件信息
     * @param file 一个rar压缩包
     * @return
     */
    public FileInfo makeFileInfo(File file) {
        //TODO: 还需要知道压缩包的密码
        return new FileInfo();
    }


}
