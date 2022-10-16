//package test;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestCMDEncoding {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("中文");//中文注释
            System.out.println("日本語");//コメント
        } else if (args.length == 1 && args[0].equals("rar")) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("请输入文件路径：");
                File file = new File(scanner.next());
                System.out.println(compress(file, "r1"));
            }
        }

    }


    public static List<String> runSystemCommand(String directory, String... commands) {
        ArrayList<String> ans = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        if (directory != null) processBuilder.directory(new File(directory));


        try {
            Process process = processBuilder.start();
            //等待process代表的进程结束
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            // windows命令行使用gbk编码，需要转换
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));

            String line;
            while ((line = reader.readLine()) != null) {
                ans.add(line);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return ans;
    }

    public static String compress(File file, String targetName) {
        String password = "123";

        runSystemCommand(file.getParent(),
                "winrar", "a", "-ep1", "-rr10p",
                "-hp" + "\"" + password + "\"",
                targetName,
                file.getName());
        return password;
    }
}
