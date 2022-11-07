package utils;

import common.GUIParam;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/**
 * utility methods for GUI development
 */
public class GUIUtility {
    private static final Font GUI_FONT = new Font(GUIParam.FONT_NAME,
            GUIParam.FONT_STYLE, GUIParam.FONT_SIZE);

    private static JFileChooser chooser;


    static {
        initializeJFileChooser();
    }

    private GUIUtility() {
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
        chooser.setPreferredSize(new Dimension(1200, 800));

        GUIUtility.setFontRecursively(chooser);
    }
}
