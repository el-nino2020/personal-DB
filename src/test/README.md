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

- 