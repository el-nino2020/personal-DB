package utils;


import com.google.common.base.Preconditions;
import common.Param;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");


    private Utility() {

    }

    //写这两个方法是因为assert并不是默认打开的，不可靠，因此不能使用。也可以使用JUnit5中的Assert包，不过
    //Junit5好像没有jar文件，我就懒得配置了

    /**
     * 检查o是否为null，如果是，抛出运行异常，内容为message。
     */
    public static void ifNullThrow(Object o, String message) {
        if (o == null) throw new RuntimeException(message);
    }


    /**
     * 检查b是否为false，如果是，抛出运行异常，内容为message。
     */
    public static void assertion(Boolean b, String message) {
        if (!b) throw new RuntimeException(message);
    }

    /**
     * 开启一个新的进程来运行command，将输出以行为单位存入List中返回。需要注意，当前Java进程会阻塞，
     * 直到新进程终止
     *
     * @param commands  一条MS-DOS命令的各个组成部分，拼接在一起形成一条完整的DOS命令
     * @param directory DOS命令运行在哪个目录下；如果为NULL，则为当前Java程序的运行目录
     * @return 列表包含命令的每行输出
     */
    public static List<String> runSystemCommand(String directory, String... commands) {
        ArrayList<String> ans = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        if (directory != null) processBuilder.directory(new File(directory));
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            //等待process代表的进程结束
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            // windows命令行使用gbk编码，需要转换
            //TODO: 思考： 如果在cmd中运行java程序，真的需要转换字符编码吗
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
                Param.CERTUTIL_PATH,
                "-hashfile",
                file.getAbsolutePath(),
                "md5");
        if ((!results.get(0).contains("MD5")) || results.size() != 3) {
            throw new RuntimeException("求MD5过程出错");
        }
        return results.get(1);
    }

    public static String getFormattedTime(LocalDateTime time) {
        return TIME_FORMATTER.format(time);
    }


    /**
     * 合法的表名：只包含小写英文字符、数字和下划线，且必须以英文字母开头
     */
    public static boolean checkTableNameValidity(String name) {
        if (name == null) return false;
        int n = name.length();
        if (n == 0) return false;
        //JDBC好像不允许在表名中使用大写字母
        return name.matches("^[a-z][a-z0-9_]*$");
    }

}









