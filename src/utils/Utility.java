package utils;


import com.google.common.base.Preconditions;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    private Utility() {

    }


    /**
     * 检查o是否为null，如果是，抛出运行异常，内容为message。写这个方法是因为
     * assert并不是默认打开的，因此无法使用。也可以使用JUnit5中的Assert包，不过
     * Junit5好像没有jar文件，我就懒得配置了
     */
    public static void ifNullThrow(Object o, String message) {
        if (o == null) throw new RuntimeException(message);
    }


    /**
     * 开启一个新的进程来运行command，将输出以行为单位存入List中返回
     *
     * @param commands  一条MS-DOS命令的各个组成部分，拼接在一起形成一条完整的DOS命令
     * @param directory DOS命令运行在哪个目录下；如果为NULL，则为当前Java程序的运行目录
     * @return 列表包含命令的每行输出
     */
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

    public static String getFileMD5(File file) {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists());
        Preconditions.checkArgument(file.isFile());

        List<String> results = runSystemCommand(null,
                "C:\\Windows\\System32\\certutil.exe",
                "-hashfile",
                file.getAbsolutePath(),
                "md5");
        if ((!results.get(0).contains("MD5")) || results.size() != 3) {
            throw new RuntimeException("求MD5过程出错");
        }
        return results.get(1);
    }

    public static String getFormattedTime(LocalDateTime time) {
        return TIME_FORMATTER.format(time).toString();
    }

}
