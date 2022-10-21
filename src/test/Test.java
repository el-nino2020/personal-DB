package test;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomStringUtils;
import service.ArchiveService;
import utils.Utility;
import view.View;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("请输入表名: ");
            System.out.println(Utility.checkTableNameValidity(scanner.next()));
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

    @org.junit.Test
    public void testRarPasswdGen() {
        int n = 10000;
        for (int i = 0; i < n; i++) {
            ArchiveService.makePassword();
        }
    }

    @org.junit.Test
    public void getAUTOINCREMENTValue() {
        //参考这个：https://stackoverflow.com/questions/15821532/get-current-auto-increment-value-for-any-table

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_backup"
                    , "backupadmin", "123");
            preparedStatement = connection.prepareStatement("SHOW TABLE STATUS FROM cloud_backup WHERE `name` LIKE 't1' ");
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    //注意到: resultSet中字段的顺序并不是固定的，应该根据列名选出AUTO_INCREMENT的值
                    System.out.println("column count :" + (i + 1));
                    System.out.print(metaData.getColumnName(i + 1) + ": ");
                    System.out.println(resultSet.getString(i + 1));
                    System.out.println();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    //TODO 需求：生成可以随文字自动变更大小的表格，还有一列可以勾选，程序可以获得勾选信息
    public void useTables() {
        JFrame frame = new JFrame();

        // Frame Title
        frame.setTitle("所有表");

        // Data to be displayed in the JTable
        String[][] data = {
                {"1", "GAME", "各种游戏，测试用表"},
                {"2", "learning", "别名：学习。主要是大学的各种资料"},
                {"3", "测试表t2", "随机文字：合法开始电话费咖啡拉风景阿飞了法律纠纷大师来分解斯拉夫萨罗夫书法家拉手福建省法拉省发啊算法"}
        };

        // Column Names
        String[] columnNames = {"序号", "表名", "注释"};

        // Initializing the JTable
        JTable table = new JTable(data, columnNames);
        table.setBounds(0, 0, 1000, 1000);
        table.setFont(new Font("黑体", Font.BOLD, 20));
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.doLayout();


        // adding it to JScrollPane
        JScrollPane panel = new JScrollPane(table);

        frame.add(panel);
        // Frame Size
        frame.setSize(1000, 1000);
        // Frame Visible = true
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
