package view;

import common.Param;
import domain.FileInfo;
import domain.DirInfo;
import service.AccountService;
import service.ArchiveService;
import service.DBService;
import service.FileInfoService;
import utils.Utility;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class View {
    private static final int LOGIN_TRY_TIMES = Param.LOGIN_TRY_TIMES;

    private AccountService accountService = new AccountService();
    private DBService dbService = new DBService(accountService);
    private FileInfoService fileInfoService = new FileInfoService(accountService);

    private Scanner scanner = new Scanner(System.in);
    private boolean menuLoop = true;
    private JFileChooser chooser;


    private View() {
        initializeJFileChooser();
    }


    public void menu() {
        System.out.println("======================================================================");

        String choice;
        while (menuLoop) {
            System.out.println("=========================================");
            System.out.println("========== Personal DB =============");
            System.out.println("\t1.  压缩并记录文件(夹)");
            System.out.println("\t2.  解压文件");
            System.out.println("\t3.  查询现有表");
            System.out.println("\t4.  新建表");
            System.out.println("\t5.  备份数据库(仅在必要时选择该项，别没事备份状态相同的数据库)");
            System.out.println("\tq.  退出系统");
            System.out.println("=========================================");
            System.out.print("输入你的选择: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    if (makeArchiveAndRecord()) System.out.println("总结: 操作成功");
                    else System.out.println("总结: 操作失败");
                    break;
                case "2":
                    if (decompressArchive()) System.out.println("总结: 操作成功");
                    else System.out.println("总结: 操作失败");
                    break;
                case "3":
                    showAllTables();
                    break;
                case "4":
                    if (createNewDBTable()) System.out.println("总结: 操作成功");
                    else System.out.println("总结: 操作失败");
                    break;
                //TODO: 也许要写一个checkDBIntegrity()的函数，检查数据库状态，以便我作为DBA，手动修复不正确的数据库
                case "5":
                    if (DBBackup()) System.out.println("总结: 操作成功");
                    else System.out.println("总结: 操作失败");
                    break;
                case "q":
                    menuLoop = false;
                    break;
                default:
                    System.out.println("输入有误，请再次输入");
            }
        }
        quitSystem();
        System.out.println("退出系统");
    }


    /**
     * 要求用户输入AccountService.USER这一账户的密码，最多尝试tryTimes次.
     * 用户可以输入"q"放弃登录.
     * "q"这个长度为1的字符串肯定不是密码.
     *
     * @param tryTimes 尝试登录的次数
     * @return 是否成功登录
     */
    private boolean loginDBMS(int tryTimes) {
        if (accountService.getLoginStatus()) {
            System.out.println("数据库账户登录成功");
            return true;
        }

        String password;
        for (int i = 0; i < tryTimes; i++) {
            System.out.print("请输入账户" + AccountService.USER + "的密码(输入\"q\"放弃登录):");
            password = scanner.next();

            if ("q".equals(password)) {
                System.out.println("放弃登录，请稍后重试，或退出系统");
                return false;
            }
            if (accountService.loginDBMS(password)) {
                System.out.println("数据库账户登录成功");
                return true;
            }
        }
        System.out.println("数据库账户登录失败，请稍后重试，或退出系统");

        return false;
    }


    private boolean makeArchiveAndRecord() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return false;
        }

        try {
            //选择要压缩的文件/文件夹的绝对路径
            File file = chooseFileInGUI(true);
            Utility.ifNullThrow(file, "选择的文件不存在");//这个异常基本不可能发生

            //TODO 展示现有的表（在一个新的窗口展示，在现有cmd中展示显得有些拥挤），选择要存放的表，考虑能否在Jtable中打勾来选择
            //TODO 如果不使用Jtable，也许可以通过选择序号的方式
            List<DirInfo> list = showAllTables();

            System.out.print("请输入文件所属的表名(文件夹名): ");
            String tableName = scanner.next();
            Utility.assertion(dbService.directoryExists(tableName), "该表不存在");

            //根据选择的表，找到其AUTO_INCREMENT的值，用于生成压缩文件的名字：表名_ID.rar，ID即为表中的ID字段
            String id = dbService.getFilesAutoIncrementValue();
            Utility.ifNullThrow(id,
                    String.format("查找不到表%s的AUTO_INCREMENT值", tableName));//这个异常基本不可能发生

            String archiveName = "files_" + id + ".rar";
            File archiveFile = new File(file.getParent() + "\\" + archiveName);

            // 查询同目录下是否存在与压缩包相同的文件名，如果有，提示
            Utility.assertion(!archiveFile.exists(),
                    String.format("%s目录下存在名为%s的文件，与将要生成的压缩包同名，请移动该文件，或者将要压缩的文件移动到别的文件夹",
                            file.getParent(), archiveName));

            // ArchiveService要生成随机的字符串作为密码，并在内存中保存该密码
            // 压缩文件
            String archivePassword = ArchiveService.compressWithRandomPassword(file, archiveName);
            Utility.ifNullThrow(archivePassword, "生成的密码有误");//这个异常基本不可能发生

            // 要求用户输入关于源文件的一些说明
            System.out.print("请输入该文件的说明(没有的话请输入『无』): ");
            String note = scanner.next();

            /////////////////////////////////////////////////////////////////////////
            //这一段应该整合成一个方法的
            int dirID = -1;

            for (DirInfo dirInfo : list) {
                if (dirInfo.getName().equals(tableName)) {
                    dirID = dirInfo.getId();
                    break;
                }
            }

            Utility.assertion(dirID != -1, "内存损坏"); //这个异常基本不可能发生，除非内存有问题
            /////////////////////////////////////////////////////////////////////////

            //根据压缩文件的信息和保存的密码，生成FileInfo对象
            FileInfo fileInfo = fileInfoService.makeFileInfo(archiveFile,
                    file.getName(),
                    archivePassword,
                    note, dirID);

            //将FileInfo对象写入数据库
            fileInfoService.insertFileInfo(fileInfo);

            //数据库备份——没错，每次调用makeArchiveAndRecord，都要备份一次数据库
            dbService.databaseDump();

            //TODO 用保存的密码测试压缩文件。如果测试失败，有点尴尬，上面哪一步肯定出现问题了，考虑重做整个过程
            //TODO 思考：如何重做数据库? 事实上，插入一个实际上没有意义的记录是安全的，但日后(通过比对文件大小或md5)查找时会有点麻烦

            System.out.println("最终测试：再次测试压缩包的密码");
            ArchiveService.testRar(archiveFile, archivePassword);
            System.out.println("生成的压缩包为" + archiveFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("========== 以下是异常信息 ===============");
            e.printStackTrace();
            System.out.println("========== 异常信息结束 ===============");
            return false;
        }
        return true;
    }


    private boolean decompressArchive() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return false;
        }
        try {
            //选择要解压的压缩包
            File file = chooseFileInGUI(false);
            Utility.ifNullThrow(file, "选择的文件不存在");//这个异常基本不可能发生

            //从数据库中读取对应的记录
            FileInfo fileInfo = fileInfoService.getFileInfo(file);
            Utility.ifNullThrow(fileInfo, "数据库不存在该文件的记录");

            //对比数据库中的记录与该文件的实际信息
            System.out.println(fileInfo.getFileAbstract(file));

            //检验密码是否正确
            System.out.println("测试压缩包");
            ArchiveService.testRar(file, fileInfo.getPassword());

            //压缩到同一目录下
            System.out.println("开始解压");
            ArchiveService.decompress(file, fileInfo.getPassword());

            System.out.println("解压完成");
        } catch (Exception e) {
            System.out.println("========== 以下是异常信息 ===============");
            e.printStackTrace();
            System.out.println("========== 异常信息结束 ===============");
            return false;
        }
        return true;
    }


    //TODO: 考虑使用Jtable实现

    public List<DirInfo> showAllTables() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
        }

        List<DirInfo> ans = dbService.getAllDirectoryInfo();

        if (ans.isEmpty()) {
            System.out.println("===================================================");
            System.out.println("不存在任何表，请创建一张");
            System.out.println("===================================================");
            return ans;
        }

        System.out.println("====================展示所有表=========================\n");
        for (DirInfo dirInfo : ans) {
            System.out.format("序号: %d ||*|| 表名: %s ||*|| 注释: %s \n",
                    dirInfo.getId(), dirInfo.getName(), dirInfo.getNote());
        }
        System.out.println("\n====================展示完毕=========================");
        return ans;
    }


    private boolean createNewDBTable() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return false;
        }

        try {
            System.out.println("合法的表名：只包含小写英文字符、数字和下划线，且必须以英文字母开头。");
            System.out.print("请输入要新创建的表名: ");
            String tableName = scanner.next();

            if (dbService.directoryExists(tableName)) {
                System.out.println("该表已存在，请重试");
                return false;
            }
            if (!Utility.checkTableNameValidity(tableName)) {
                System.out.println("表名不合法，请重试");
                return false;
            }

            System.out.print("请输入关于这张表的说明: ");
            String note = scanner.next();

            dbService.createNewDirectory(new DirInfo(tableName, note, null));

        } catch (Exception e) {
            System.out.println("========== 以下是异常信息 ===============");
            e.printStackTrace();
            System.out.println("========== 异常信息结束 ===============");
            return false;
        }
        return true;
    }


    private boolean DBBackup() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return false;
        }
        try {
            dbService.databaseDump();
        } catch (Exception e) {
            System.out.println("========== 以下是异常信息 ===============");
            e.printStackTrace();
            System.out.println("========== 异常信息结束 ===============");
            return false;
        }
        return true;
    }


    //TODO: 参考https://stackoverflow.com/questions/13798163/jfilechooser-appearance里面的回答，也许有除了
    //JFileChooser以外更好的实现，但现阶段这个实现够用了

    /**
     * 以文件窗口的形式(GUI)选择文件
     *
     * @param directoryPermitted 是否允许选择文件夹
     * @return 选择的文件对应的File对象
     */
    public File chooseFileInGUI(boolean directoryPermitted) {
        System.out.println("为了方便使用，请输入要压缩的文件(夹)所在的目录. 输入desktop以快速定位桌面");

        String line;
        File parentDirectory;

        while (true) {
            System.out.print("你的输入：");
            line = scanner.next();

            if ("desktop".equals(line)) {
                parentDirectory = new File(Param.DESKTOP_PATH);
                break;
            }
            parentDirectory = new File(line);

            if (parentDirectory.exists() && parentDirectory.isDirectory()) {
                break;//输入的目录验证成功
            }
            System.out.println("输入有误，请重新输入");
        }

        System.out.println("请注意，只能选择一个文件(夹)");
        chooser.setCurrentDirectory(parentDirectory);

        chooser.setFileSelectionMode(directoryPermitted ?
                JFileChooser.FILES_AND_DIRECTORIES :
                JFileChooser.FILES_ONLY);

        chooser.showDialog(null, null);

        return chooser.getSelectedFile();
    }


    /**
     * 对chooser进行设置，使得打开文件窗口人性化——原版的JFileChooser根本没法看
     */
    private void initializeJFileChooser() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        chooser = new JFileChooser(FileSystemView.getFileSystemView());
        chooser.setMultiSelectionEnabled(false); //只能选择一个文件

        chooser.setDialogTitle("选择一个要压缩的文件(夹)");
        chooser.setPreferredSize(new Dimension(1200, 800));

        //TODO 设置字体似乎没用
//        chooser.setFont(new Font("Arial", Font.PLAIN, 15));
//        setFileChooserFont(chooser.getComponents(), new Font("Arial", Font.BOLD, 15));
//TODO: 参考以下两个链接：
        //https://coderanch.com/t/342116/java/set-font-JFileChooser
        //https://stackoverflow.com/questions/45791492/java-how-can-i-increase-the-font-of-the-folder-names-in-jfilechooser
    }

    private void quitSystem() {
        accountService.quitAccount();
    }

    public static void main(String[] args) {
        new View().menu();
    }
}
