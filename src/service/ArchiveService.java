package service;


import common.Param;
import org.apache.commons.lang3.RandomStringUtils;
import utils.Utility;

import java.io.File;
import java.util.ArrayList;

/**
 * 负责压缩包相关的服务
 */
public class ArchiveService {
    private static final int PASSWORD_LENGTH = Param.PASSWORD_LENGTH;

    /**
     * 压缩一个文件/文件夹，生成的压缩包与原始文件在同一目录下
     *
     * @param file       要压缩的文件/文件夹
     * @param targetName 生成的rar压缩包的名字，生成的压缩包的名字形如 targetName.rar
     * @param password   压缩包的密码，如果为null，压缩包没有密码
     */
    public void compress(File file, String targetName, String password) {
        ArrayList<String> command = new ArrayList<>();
        command.add("winrar");
        command.add("a");
        command.add("-ep1");
        command.add("-rr10p");
        if (password != null)
            command.add("-hp" + "\"" + password + "\"");
        command.add(targetName);
        command.add(file.getName());

        Utility.runSystemCommand(file.getParent(), command.toArray(new String[0]));
    }

    /**
     * 使用随机密码压缩一个文件/文件夹，返回该随机密码，
     * 生成的压缩包与原始文件在同一目录下
     *
     * @param file       要压缩的文件/文件夹
     * @param targetName 生成的rar压缩包的名字，生成的压缩包的名字形如 targetName.rar
     * @return 压缩时使用的随机密码
     */
    public String compressWithRandomPassword(File file, String targetName) {
        String password = makePassword();

        compress(file, targetName, password);

        return password;
    }

    /**
     * 生成长度为PASSWORD_LENGTH的ASCII字符串，该字符串中不包含双引号"，
     * 以便于在命令行输入
     *
     * @return
     */
    public String makePassword() {
        StringBuilder ans = new StringBuilder(RandomStringUtils.randomAscii(PASSWORD_LENGTH));
        //需要将ans中的双引号替换为其他字符
        String alternative = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        for (int i = 0, j = 0; i < PASSWORD_LENGTH; i++) {
            char c = ans.charAt(i);
            if (c == '"') {
                ans.setCharAt(i, alternative.charAt(j));
                j++;
            }
        }

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            if (ans.charAt(i) == '"') {
                throw new RuntimeException("生成密码的方法有问题");
            }
        }
        System.out.println("随机密码生成成功");
        return ans.toString();
    }


    /**
     * 尝试将压缩包解压至同一目录下，这里假设提供的密码是正确的，即压缩包的信息存储在数据库中
     *
     * @param file     要解压的rar压缩包
     * @param password 数据库中记录的该压缩包的密码
     */
    public void decompress(File file, String password) {
        Utility.runSystemCommand(file.getParent(),
                "winrar", "x",
                "-hp" + "\"" + password + "\"",
                file.getName());
    }

    /**
     * 测试某一压缩文件，“为了验证指定的文件，这个命令会运行虚拟文件解压，但不会写入输出数据流”——引用自WinRAR中文帮助文件
     *
     * @param file     rar压缩包
     * @param password 压缩包的密码
     * @return 压缩文件是否验证成功
     */
    public void testRar(File file, String password) {
        Utility.runSystemCommand(file.getParent(),
                "winrar", "t",
                "-hp" + "\"" + password + "\"",
                file.getName());
    }
}
