package common;

/**
 * 项目使用的各种参数
 */
public class Param {
    private Param() {
    }

    /**
     * 当前主机桌面路径，具体值视主机而定
     */
    public static final String DESKTOP_PATH = "C:\\Users\\Morgan\\Desktop\\";

    /**
     * 要将数据库备份到哪个目录下，推荐设置为某一云盘的自动备份目录，具体值视主机而定
     */
    public static final String DUMP_DESTINATION = "C:\\Users\\Morgan\\Desktop\\mega同步\\";

    /**
     * 当前主机cmd(command prompt)程序的绝对路径，具体值视主机而定
     */
    public static final String CMD_PATH = "C:\\windows\\System32\\cmd.exe";


    /**
     * 当前主机mysqldump程序的绝对路径，具体值视主机而定
     */
    public static final String MYSQLDUMP_PATH = "D:\\mysql5.7.19\\bin\\mysqldump.exe";


    ////////////////////////////////////////////////////////////////////////////////////////////
    //                                      以下参数一般不需要改变                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 负责记录备份信息的数据库名，按照initialize.sql创建，一般不需要改变
     */
    public static final String DATABASE_NAME = "cloud_backup";

    /**
     * 对DATABASE_NAME有完全操作权限的用户，按照initialize.sql创建，一般不需要改变
     */
    public static final String DBMS_USER = "backupadmin";


    /**
     * MYSQL服务器的IP，具体值视主机而定，一般不需要改变
     */
    public static final String MYSQL_IP = "localhost";
    /**
     * MYSQL服务器的端口，具体值视主机而定，一般不需要改变
     */
    public static final String MYSQL_PORT = "3306";


    /**
     * 尝试登录数据库（输入密码）的次数，用在view.View中
     */
    public static final int LOGIN_TRY_TIMES = 5;


    /**
     * 生成压缩包时随机创建的密码长度，用在service.ArchiveService中
     */
    public static final int PASSWORD_LENGTH = 50;

}
