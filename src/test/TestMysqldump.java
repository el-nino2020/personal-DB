
public class TestMysqldump {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\windows\\System32\\cmd.exe", "/c",
                    "D:\\mysql5.7.19\\bin\\mysqldump.exe -u backupadmin -p123 cloud_backup > C:\\Users\\Morgan\\Desktop\\mega同步\\7890.sql");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("Fin1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
