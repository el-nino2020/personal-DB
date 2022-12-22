package utils;

import common.GUIParam;
import common.Param;
import view.MainMenu;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/**
 * utility methods for GUI development
 */
public class GUIUtils {
    private static final Font GUI_FONT = new Font(GUIParam.FONT_NAME,
            GUIParam.FONT_STYLE, GUIParam.FONT_SIZE);

    private static JFileChooser chooser;


    static {
        initializeJFileChooser();
    }

    private GUIUtils() {
    }

    private static MainMenu getMainMenu(Component thisComponent) {
        return (MainMenu) SwingUtilities.getRoot(thisComponent);
    }

    //不同的message dialog是什么样子的，可以看：https://mkyong.com/swing/java-swing-how-to-make-a-simple-dialog/
    public static void showErrorMessage(Component thisComponent, String message, String title) {
        JOptionPane.showMessageDialog(getMainMenu(thisComponent),
                message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(Component thisComponent, String message, String title) {
        JOptionPane.showMessageDialog(getMainMenu(thisComponent),
                message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showPlainMessage(Component thisComponent, String message, String title) {
        JOptionPane.showMessageDialog(getMainMenu(thisComponent),
                message, title, JOptionPane.PLAIN_MESSAGE);
    }




    /**
     * @return JOptionPane.YES_OPTION = 0 if YES is chosen; otherwise NO is chosen
     */
    public static int showConfirmDialog(Component thisComponent, String message, String title) {
        return JOptionPane.showConfirmDialog(getMainMenu(thisComponent),
                message, title, JOptionPane.YES_NO_OPTION);
    }

    public static void setFont(Component component) {
        component.setFont(GUI_FONT);
    }


    /**
     * 如果component是Container类型，则对其中所有的组件设置字体——递归调用。
     * 否则，对该component调用setFont方法
     *
     * @param component
     */
    public static void setFontRecursively(Component component) {
        setFont(component);
        if (component instanceof Container) {
            Component[] components = ((Container) component).getComponents();
            for (Component comp : components) {
                setFontRecursively(comp);
            }
        }
    }

    /**
     * 以文件窗口的形式选择文件
     *
     * @param directoryPermitted 是否允许选择文件夹
     * @return 选择的文件对应的File对象，如果用户取消选择，则返回null
     */
    public static File chooseFile(boolean directoryPermitted) {
        chooser.setFileSelectionMode(directoryPermitted ?
                JFileChooser.FILES_AND_DIRECTORIES :
                JFileChooser.FILES_ONLY);

        chooser.showDialog(null, null);

        return chooser.getSelectedFile();
    }

    private static void initializeJFileChooser() {

        chooser = new JFileChooser(FileSystemView.getFileSystemView());
        chooser.setMultiSelectionEnabled(false); //只能选择一个文件

        chooser.setDialogTitle("选择一个文件(夹)");
        chooser.setPreferredSize(new Dimension(1800, 1000));
        chooser.setCurrentDirectory(new File(Param.DESKTOP_PATH));
        GUIUtils.setFontRecursively(chooser);

        //以details，而不是list模式展示文件，参考：https://stackoverflow.com/a/16292761
//        chooser.getActionMap().get("viewTypeDetails").actionPerformed(null);
    }
}
