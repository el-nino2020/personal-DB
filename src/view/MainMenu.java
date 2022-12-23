package view;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import com.formdev.flatlaf.FlatIntelliJLaf;
import domain.DirInfo;
import service.AccountService;
import service.DBService;
import service.FileInfoService;
import utils.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Morgan
 */
public class MainMenu extends javax.swing.JFrame {

    private AccountService accountService = new AccountService();
    private DBService dbService = new DBService(accountService);
    private FileInfoService fileInfoService = new FileInfoService(accountService);


    //将directories的记录缓存
    private List<DirInfo> dirInfos = new ArrayList<>();
    private String[] directoryNames;
    private HashMap<String, DirInfo> directoryInfoMap = new HashMap<>();//键:dirname；值：对应的DirectoryInfo对象

    private static String[] choices = {"压缩并记录文件(夹)", "解压文件", "查询现有表", "新建表"};
    private CompressPanel compressPanel = new CompressPanel(dbService, fileInfoService, accountService);
    private DecompressPanel decompressPanel = new DecompressPanel(fileInfoService);
    private AllDBTablePanel allDBTablePanel = new AllDBTablePanel();
    private CreateNewDBTablePanel createNewDBTablePanel = new CreateNewDBTablePanel(dbService);
    private JPanel currentShowingPanel = compressPanel;

    //将choices和function panels联系起来
    private HashMap<String, JPanel> panels = new HashMap<>();

    /**
     * 只有在(1)directories表被更新后(2)初始化时，才应该调用该方法
     */
    public void cacheDirectoryInfo() {
        dirInfos.clear();
        directoryInfoMap.clear();

        dirInfos = dbService.getAllDirectoryInfo();
        directoryNames = new String[dirInfos.size()];

        int i = 0;
        for (DirInfo info : dirInfos) {
            directoryInfoMap.put(info.getName(), info);
            directoryNames[i] = info.getName();
            i++;
        }


        compressPanel.setDirectoryInfoMap(directoryInfoMap);
        compressPanel.setDirectoryNames(directoryNames);


        allDBTablePanel.setDirectoryInfos(dirInfos);
        allDBTablePanel.notifyTableDataChanged();
    }

    private void loginDBMS() {
        if (accountService.getLoginStatus()) return;
        while (true) {
            String password = JOptionPane.showInputDialog(this,
                    "请输入账户" + AccountService.USER + "的密码");
            if (accountService.loginDBMS(password)) {
                System.out.println("数据库账户登录成功");
                break;
            }
        }
    }


    private void fillChoicesList() {
        DefaultListModel<String> model = (DefaultListModel<String>) choicesList.getModel();
        for (String choice : choices) {
            model.addElement(choice);
        }
    }

    private void initPanels() {
        panels.put(choices[0], compressPanel);
        panels.put(choices[1], decompressPanel);
        panels.put(choices[2], allDBTablePanel);
        panels.put(choices[3], createNewDBTablePanel);

        addPanelToBottomPanel(compressPanel);
        addPanelToBottomPanel(decompressPanel);
        addPanelToBottomPanel(allDBTablePanel);
        addPanelToBottomPanel(createNewDBTablePanel);

        compressPanel.setVisible(true);
    }


    private void addPanelToBottomPanel(JPanel panel) {
        bottomPanel.add(panel, JLayeredPane.DEFAULT_LAYER);
        panel.setBounds(0, 0, bottomPanel.getWidth(), bottomPanel.getHeight());
        panel.setVisible(false);
    }

    private void initFrameSetting() {
        setSize(1600, 1000);
    }


    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        fillChoicesList();
        initPanels();
        initFrameSetting();
        redirectOutAndErrStream();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        choicesLabel = new javax.swing.JLabel();
        listPanel = new javax.swing.JScrollPane();
        choicesList = new javax.swing.JList<>();
        textAreaPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        logLabel = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(200, 200));
        setSize(new java.awt.Dimension(1600, 1600));
        getContentPane().setLayout(null);

        titleLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 22)); // NOI18N
        titleLabel.setText("Personal DB");
        getContentPane().add(titleLabel);
        titleLabel.setBounds(611, 15, 134, 29);

        choicesLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        choicesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        choicesLabel.setText("选择功能");
        choicesLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        getContentPane().add(choicesLabel);
        choicesLabel.setBounds(21, 62, 203, 30);

        choicesList.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        choicesList.setModel(new DefaultListModel<>());
        choicesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        choicesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                choicesListValueChanged(evt);
            }
        });
        listPanel.setViewportView(choicesList);

        getContentPane().add(listPanel);
        listPanel.setBounds(21, 110, 203, 446);

        logTextArea.setColumns(20);
        logTextArea.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        logTextArea.setRows(5);
        textAreaPane.setViewportView(logTextArea);

        getContentPane().add(textAreaPane);
        textAreaPane.setBounds(21, 759, 1421, 158);

        logLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        logLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logLabel.setText("Log");
        logLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        getContentPane().add(logLabel);
        logLabel.setBounds(21, 723, 92, 30);

        bottomPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
                bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1194, Short.MAX_VALUE)
        );
        bottomPanelLayout.setVerticalGroup(
                bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 674, Short.MAX_VALUE)
        );

        getContentPane().add(bottomPanel);
        bottomPanel.setBounds(240, 60, 1200, 680);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choicesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_choicesListValueChanged
        int index = choicesList.getSelectedIndex();
        if (index < 0) return;

        String choice = choicesList.getModel().getElementAt(index);
        JPanel panel = panels.get(choice);

        currentShowingPanel.setVisible(false);
        panel.setVisible(true);
        currentShowingPanel = panel;

    }//GEN-LAST:event_choicesListValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        FlatIntelliJLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
                mainMenu.loginDBMS();
                mainMenu.cacheDirectoryInfo();

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JLabel choicesLabel;
    private javax.swing.JList<String> choicesList;
    private javax.swing.JScrollPane listPanel;
    private javax.swing.JLabel logLabel;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JScrollPane textAreaPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables


    private void redirectOutAndErrStream() {

        MessageConsole mc = new MessageConsole(logTextArea);
        mc.redirectOut();
        mc.redirectErr(Color.RED, null);
    }
}
