package view;

import service.AccountService;
import service.FileInfoService;

import java.util.Scanner;

public class View {

    private static final int LOGIN_TRY_TIMES = 5;

    private AccountService accountService = new AccountService();
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
        if (!loginDBMS(LOGIN_TRY_TIMES)) return;
    }

    private void decompressArchive() {
        if (!loginDBMS(LOGIN_TRY_TIMES)) return;

    }

    private void quitSystem() {
        accountService.quitAccount();
    }

    public static void main(String[] args) {
        new View().menu();
    }
}
