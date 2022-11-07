package view.function_panel;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import utils.GUIUtility;
import utils.ListDialog;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/**
 * @author Morgan
 */
public class CompressPanel extends javax.swing.JPanel {

    /**
     * Creates new form CompressPanel
     */
    public CompressPanel() {
        initComponents();
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
        chooseFileButton = new javax.swing.JButton();
        chosedFileTextField = new javax.swing.JTextField();
        chosedFileLabel = new javax.swing.JLabel();
        chooseTableButton = new javax.swing.JButton();
        chosedTableLabel = new javax.swing.JLabel();
        chosedTableTextField = new javax.swing.JTextField();
        noteLabel = new javax.swing.JLabel();
        noteScrollPane = new javax.swing.JScrollPane();
        noteTextArea = new javax.swing.JTextArea();
        archiveNameLabel = new javax.swing.JLabel();
        archiveDirectoryLabel = new javax.swing.JLabel();
        archiveNameTextField = new javax.swing.JTextField();
        archiveDirectoryTextField = new javax.swing.JTextField();
        noticeLabel = new javax.swing.JLabel();
        finalDecisionButton = new javax.swing.JButton();
        finalResultLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setEnabled(false);

        titleLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 22)); // NOI18N
        titleLabel.setText("压缩并记录");

        chooseFileButton.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chooseFileButton.setText("选择文件(夹)");
        chooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileButtonActionPerformed(evt);
            }
        });

        chosedFileTextField.setEditable(false);
        chosedFileTextField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chosedFileTextField.setText("请选择一个要压缩的文件(夹)");
        chosedFileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chosedFileTextFieldActionPerformed(evt);
            }
        });

        chosedFileLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chosedFileLabel.setText("要压缩的文件(夹)：");
        chosedFileLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chooseTableButton.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chooseTableButton.setText("选择表");
        chooseTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseTableButtonActionPerformed(evt);
            }
        });

        chosedTableLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chosedTableLabel.setText("压缩包要存入的表：");
        chosedTableLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chosedTableTextField.setEditable(false);
        chosedTableTextField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        chosedTableTextField.setText("请选择压缩包要存入哪张表");
        chosedTableTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chosedTableTextFieldActionPerformed(evt);
            }
        });

        noteLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        noteLabel.setText("输入该文件的说明：");
        noteLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        noteTextArea.setColumns(20);
        noteTextArea.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        noteTextArea.setLineWrap(true);
        noteTextArea.setRows(5);
        noteScrollPane.setViewportView(noteTextArea);

        archiveNameLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        archiveNameLabel.setText("生成的压缩包名：");
        archiveNameLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        archiveDirectoryLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        archiveDirectoryLabel.setText("生成的压缩包的目录：");
        archiveDirectoryLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        archiveNameTextField.setEditable(false);
        archiveNameTextField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        archiveNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveNameTextFieldActionPerformed(evt);
            }
        });

        archiveDirectoryTextField.setEditable(false);
        archiveDirectoryTextField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        archiveDirectoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveDirectoryTextFieldActionPerformed(evt);
            }
        });

        noticeLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        noticeLabel.setText("提示：可以压缩 / 目录下存在名为%s的文件，与将要生成的压缩包同名");
        noticeLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        finalDecisionButton.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        finalDecisionButton.setText("压缩并记录");
        finalDecisionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalDecisionButtonActionPerformed(evt);
            }
        });

        finalResultLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N
        finalResultLabel.setText("结果：");
        finalResultLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(433, 433, 433)
                                                .addComponent(titleLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(chooseTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(chooseFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGap(29, 29, 29)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(chosedFileLabel)
                                                                        .addComponent(chosedTableLabel))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(chosedFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(chosedTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(archiveDirectoryLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(archiveDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(noteLabel)
                                                                        .addComponent(archiveNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(27, 27, 27)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(archiveNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(noteScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(noticeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1049, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(421, 421, 421)
                                                .addComponent(finalDecisionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(84, 84, 84)
                                                .addComponent(finalResultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(titleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chooseFileButton)
                                        .addComponent(chosedFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(chosedFileLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chooseTableButton)
                                        .addComponent(chosedTableLabel)
                                        .addComponent(chosedTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(noteLabel)
                                        .addComponent(noteScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(archiveNameLabel)
                                        .addComponent(archiveNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(archiveDirectoryLabel)
                                        .addComponent(archiveDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(noticeLabel)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(finalDecisionButton)
                                        .addComponent(finalResultLabel))
                                .addContainerGap(208, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseFileButtonActionPerformed
        File selectedFile = GUIUtility.chooseFile(true);
        if (selectedFile == null) return;
        chosedFileTextField.setText(selectedFile.getAbsolutePath());
    }//GEN-LAST:event_chooseFileButtonActionPerformed

    private void chosedFileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chosedFileTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chosedFileTextFieldActionPerformed

    private void chooseTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseTableButtonActionPerformed
        String selectedTableName = ListDialog.showDialog(this,
                chooseTableButton,
                "所有的表名",
                "选择一张表",
                tableNames,
                tableNames[0],
                "ABCDEFG");
        chosedTableTextField.setText(selectedTableName);

    }//GEN-LAST:event_chooseTableButtonActionPerformed

    private void chosedTableTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chosedTableTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chosedTableTextFieldActionPerformed

    private void archiveNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_archiveNameTextFieldActionPerformed

    private void archiveDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveDirectoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_archiveDirectoryTextFieldActionPerformed

    private void finalDecisionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalDecisionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finalDecisionButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel archiveDirectoryLabel;
    private javax.swing.JTextField archiveDirectoryTextField;
    private javax.swing.JLabel archiveNameLabel;
    private javax.swing.JTextField archiveNameTextField;
    private javax.swing.JButton chooseFileButton;
    private javax.swing.JButton chooseTableButton;
    private javax.swing.JLabel chosedFileLabel;
    private javax.swing.JTextField chosedFileTextField;
    private javax.swing.JLabel chosedTableLabel;
    private javax.swing.JTextField chosedTableTextField;
    private javax.swing.JButton finalDecisionButton;
    private javax.swing.JLabel finalResultLabel;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JScrollPane noteScrollPane;
    private javax.swing.JTextArea noteTextArea;
    private javax.swing.JLabel noticeLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables


    private static String[] tableNames = {"abc", "ade", "pdf", "game", "H"};
}
