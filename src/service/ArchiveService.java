package service;


import java.io.File;

/**
 * 负责压缩包相关的服务
 */
public class ArchiveService {

    /**
     * 使用随机密码压缩一个文件/文件夹，返回该随机密码
     *
     * @param file       要压缩的文件/文件夹
     * @param targetName 生成的rar压缩包的名字
     * @return 压缩时使用的随机密码
     */
    public String compress(File file, String targetName) {
        //TODO
        return null;
    }


    /**
     * 尝试将压缩包解压至同一目录下，这里假设提供的密码是正确的，即压缩包的信息存储在数据库中
     *
     * @param file     要解压的rar压缩包
     * @param password 数据库中记录的该压缩包的密码
     */
    public void decompress(File file, String password) {
        //TODO
    }

    /**
     * 测试某一压缩文件，“为了验证指定的文件，这个命令会运行虚拟文件解压，但不会写入输出数据流”——引用自WinRAR中文帮助文件
     *
     * @param file     rar压缩包
     * @param password 压缩包的密码
     * @return 压缩文件是否验证成功
     */
    public boolean testRar(File file, String password) {
        //TODO
        return false;
    }
}
