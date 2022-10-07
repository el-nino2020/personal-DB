import java.io.*;

public class Test {
    public static void main(String[] args) {

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


}
