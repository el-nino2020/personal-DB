package utils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private Utility() {

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

}
