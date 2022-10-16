package service;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utility;

import java.io.File;

/**
 * 负责压缩包相关的服务
 */
public class ArchiveService {
    private static final int PASSWORD_LENGTH = 50;

    /**
     * 使用随机密码压缩一个文件/文件夹，返回该随机密码
     *
     * @param file       要压缩的文件/文件夹
     * @param targetName 生成的rar压缩包的名字
     * @return 压缩时使用的随机密码
     */
    public String compress(File file, String targetName) {
        String password = makePassword();

        Utility.runSystemCommand(file.getParent(),
                "winrar", "a", "-ep1", "-rr10p",
                "-hp" + "\"" + password + "\"",
                targetName,
                file.getName());
        return password;
    }

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
        System.out.println("随机密码生成");
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
                "winrar", "x", "-hp" + "\"" + password + "\"", file.getName());
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
                "winrar", "t", "-hp" + "\"" + password + "\"", file.getName());
    }
}
