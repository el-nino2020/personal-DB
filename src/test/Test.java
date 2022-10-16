package test;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomStringUtils;
import service.ArchiveService;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {


        ArchiveService archiveService = new ArchiveService();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入文件路径：");
            File file = new File(scanner.next());

            System.out.println(archiveService.compress(file, "r1"));
        }

    }

    @org.junit.Test
    public void callCommandLine() {
        Runtime runtime = Runtime.getRuntime();

        try {
            //查询文件的md5码
            String command = "certutil -hashfile D:\\CODES(daima)\\personal-DB\\src\\createTables.sql md5";
            Process process = runtime.exec(command);
            InputStream inputStream = process.getInputStream();
            // windows命令行使用gbk编码，需要转换
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @org.junit.Test
    public void waitForCommandLine() {
        Runtime runtime = Runtime.getRuntime();

        try {
            String command = "echo hello, world";
            Process process = runtime.exec(command);

            //等待process代表的进程结束
            process.waitFor();

            System.out.println("command line process terminated");

            System.out.println();

            InputStream inputStream = process.getInputStream();
            // windows命令行使用gbk编码，需要转换
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));

            System.out.println(reader.readLine());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void useProcessBuilder() {
        String filePath = "D:\\CODES(daima)\\personal-DB\\src\\createTables.sql";
        String[] command = {"certutil",
                "-hashfile",
                filePath,
                "md5"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        //将新进程的输出导向当前java程序的输出，但是编码不同仍旧是问题
//        processBuilder.inheritIO();

        try {

            Process process = processBuilder.start();
            process.waitFor();
            InputStream inputStream = process.getInputStream();

            // windows命令行使用gbk编码，需要转换
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (inputStream, "gbk"));

            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void changeCMDCharset() {
        //TODO: 启动了两个cmd窗口（进程），只有第一个进程的编码被修改为UTF8
        try {
            Process process = Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\chcp.com 65001");
            process.waitFor();
            //change cmd character set/encoding to UTF 8;

            String filePath = "D:\\CODES(daima)\\personal-DB\\src\\createTables.sql";
            String[] command = {"certutil",
                    "-hashfile",
                    filePath,
                    "md5"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.inheritIO();
            process = processBuilder.start();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void randomStringGenerate() {
        int n = 20;
        for (int i = 0; i < n; i++) {
            System.out.println(RandomStringUtils.randomAscii(100));
        }
    }


    /**
     * 测试Windows的选择文件框
     */
    @org.junit.Test
    public void fileChooser() {

        //这个Java自带的选择文件框有点难用,而且难看
        // www.geeksforgeeks.org/java-swing-jfilechooser/#:~:text=JFileChooser%20is%20a%20part%20of,%2C%20panels%2C%20dialogs%2C%20etc%20.
//        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
//        jFileChooser.showSaveDialog(null);


        //这个命令无效
//        try {
//            Runtime.getRuntime().exec("explorer.exe /select,");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //这个有用，但是没法选择文件夹，而压缩时通常是压缩文件夹
        //https://stackoverflow.com/questions/40255039/how-to-choose-file-in-java
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setMultipleMode(true);
        dialog.setVisible(true);
        //单个文件
        //        String file = dialog.getFile();
//        System.out.println(file + " chosen.");

        //多个文件
        File[] files = dialog.getFiles();
        System.out.println(Arrays.toString(files));


    }

    @org.junit.Test
    public void chooseFolder() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        jFileChooser.setMultiSelectionEnabled(true);
        jFileChooser.setCurrentDirectory(new File("C:\\Users\\Morgan\\Desktop"));
//        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只显示文件夹
        jFileChooser.setDialogTitle("choose direcotry(ies)");
        jFileChooser.setPreferredSize(new Dimension(1200, 800));

        //设置字体似乎没用
        jFileChooser.setFont(new Font("Arial", Font.PLAIN, 15));


        jFileChooser.showDialog(null, null);
        System.out.println(Arrays.toString(jFileChooser.getSelectedFiles()));

    }


    @org.junit.Test
    public void useFile() {
        Scanner scanner = new Scanner(System.in);
        File file = new File("D:\\GAME\\Watamari - A Match Made in Heaven\\Watamari_dx11_x86.exe");
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println("=========================");
        file = new File("D:\\GAME\\フタマタ恋愛\\全CG存档");
        System.out.println(file.getName());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
    }

    @org.junit.Test
    public void useGuava() {
        int a = -1;
        Preconditions.checkArgument(a < 0, String.format("a = %d", a));

        try {
            a = 0;
            Preconditions.checkArgument(a < 0, String.format("a = %d", a));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * this method fail when opening cmd
     */
    public void processIO() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe");
            Scanner scanner = new Scanner(System.in);

            Process process = processBuilder.start();


            InputStream inputStream = process.getInputStream();
            OutputStream outputStream = process.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "gbk"));
            String line;

            while (true) {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                writer.write(scanner.next());
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
