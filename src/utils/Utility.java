package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private Utility() {
    }


    /**
     * 开启一个新的进程来运行command，将输出以行为单位，存入List中返回
     *
     * @param command 一条MS-DOS命令
     * @return 列表包含命令的每行输出
     */
    public static List<String> runSystemCommand(String command) {
        ArrayList<String> ans = new ArrayList<>();

        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
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
