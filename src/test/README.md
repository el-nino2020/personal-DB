# 1. windows命令行编码

- 参考该文档https://stackoverflow.com/questions/17999371/javac-doesnt-compile-japanese-in-english-pc

- https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html：官网关于`javac`参数的介绍，看其中`encoding`的部分

- 中文Windows命令行默认是gbk编码，但IDE默认是用UTF-8编写代码，这会导致无法在命令行中编译和运行Java程序

- 使用`TestCMDEncoding.java`来测试运行以下命令：

- ```shell
    javac -encoding "UTF-8" TestCMDEncoding.java
    java TestCMDEncoding
    ```

- ```java
    public class TestCMDEncoding {
        public static void main(String[] args) {
            System.out.println("中文");//中文注释
            System.out.println("日本語");//コメント
        }
    }
    ```

- :warning:使用`chcp`命令并不是一个正确的解决方案





# 2. RAR命令行

- 需要先将`D:\winRAR`添加到环境变量

- 添加压缩文件：压缩`Test.java`为`r1.rar`，相对路径和全路径；压缩**文件夹**同理

    - ```sh
        winrar a r1 Test.java
        ```

    - ```sh
        winrar a -ep1 D:\CODES(daima)\personal-DB\src\test\r3 D:\CODES(daima)\personal-DB\src\test\Test.java
        ```

        - 使用绝对路径时参考：https://stackoverflow.com/questions/9684705/rar-a-folder-without-persisting-the-full-path，`-ep1`参数是必须的

- 添加恢复记录：`10p`表示10%

    - ```sh
        winrar a -rr10p r1 Test.java
        ```

- 加密文件`-hp密码`：加密数据和file header（无法在不输入密码的情况下看到压缩文件内的文件名），这里密码是`abc`

    - ```sh
        winrar a -hp"abc" r1 Test.java
        ```

    - 见这个帖子：https://stackoverflow.com/questions/48143265/how-to-use-rar-or-winrar-for-creating-an-encrypted-archive-with-a-password-start，密码应该被双引号`""`包围，但这导致密码中无法包括引号`"`字符，在生成ASCII密码时应该将`"`替换为别的字符

    - > RAR 压缩文件密码的最大长度是 127 个字符。超长的密码被裁切为此长度。

- 测试压缩文件`t`，如果测试没有问题，命令行**什么都不会显示**

    - ```rar
        winrar t r1.rar
        ```

- 解压：解压`r1.rar`到已知文件夹下。

    - ```sh
        winrar x r1.rar D:\CODES(daima)\personal-DB\src\test\folder\
        ```

    - 解压目的地的路径的最后一个`\`不能省略

    - 如果解压目的地不存在，会自动创建，但需要很高的权限才能删除，故有点问题，建议使用时设置为压缩文件当前所在的路径
    
    - 如果压缩文件有密码，使用`-hp`参数