import java.util.Scanner;

public class View {
    public void menu() {
        boolean loop = true;
        String choice;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("=========================================");
            System.out.println("========== Personal DB =============");
            System.out.println("\t\t1.  压缩、记录文件(夹)");
            System.out.println("\t\t2.  解压文件");
            System.out.println("\t\tq.  退出系统");
            System.out.println("=========================================");
            System.out.print("输入你的选择: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    break;
                case "2":
                    break;
                case "q":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请再次输入");

            }
        }
        System.out.println("退出系统");
    }


    public static void main(String[] args) {
        new View().menu();
    }
}
