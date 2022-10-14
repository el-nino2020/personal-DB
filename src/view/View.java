package view;

import domain.FileInfo;
import service.AccountService;
import service.DBService;
import service.FileInfoService;

import java.io.File;
import java.util.Scanner;

public class View {

    private static final int LOGIN_TRY_TIMES = 5;

    private AccountService accountService = new AccountService();
    private DBService dbService = new DBService(accountService);

    private FileInfoService fileInfoService = new FileInfoService(accountService);


    private Scanner scanner = new Scanner(System.in);
    boolean menuLoop = true;


    public void menu() {
        String choice;
        while (menuLoop) {
            System.out.println("=========================================");
            System.out.println("========== Personal DB =============");
            System.out.println("\t\t1.  压缩并记录文件(夹)");
            System.out.println("\t\t2.  解压文件");
            System.out.println("\t\t3.  新建表");
            System.out.println("\t\t4.  查询数据库");
            System.out.println("\t\tq.  退出系统");
            System.out.println("=========================================");
            System.out.print("输入你的选择: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    makeArchiveAndRecord();
                    break;
                case "2":
                    decompressArchive();
                    break;
                //TODO 新加入的选项3和4
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


    private void makeArchiveAndRecord() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return;
        }

        //TODO 要压缩的文件/文件夹的绝对路径
        //TODO ArchiveService要生成随机的字符串作为密码，并在内存中保存该密码
        //TODO 压缩文件
        //TODO 根据压缩文件的信息和保存的密码，生成FileInfo对象
        //TODO 将FileInfo对象写入数据库
        //TODO 数据库备份——没错，每次调用该方法，都要备份一次数据库
        //TODO 用保存的密码测试压缩文件。如果测试失败，有点尴尬，上面哪一步肯定出现问题了，考虑重做整个过程
    }

    private void decompressArchive() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) {
            System.out.println("该操作失败，需要先登录数据库账户");
            return;
        }
        System.out.print("请输入要解压的压缩包的绝对路径: ");
        File file = new File(scanner.next());

        FileInfo fileInfo = fileInfoService.getFileInfo(file);
        String fileDirectory = file.getParent();

        //TODO 核对md5、文件大小、修改时间等信息
        //TODO 完成ArchiveService中的解压功能，在对应目录下解压

    }

    private void quitSystem() {
        accountService.quitAccount();
    }

    public static void main(String[] args) {
        new View().menu();
    }
}
