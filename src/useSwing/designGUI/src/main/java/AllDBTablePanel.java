/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Morgan
 */
public class AllDBTablePanel extends javax.swing.JPanel {

    /**
     * Creates new form AllDBTablePanel
     */
    public AllDBTablePanel() {
        initComponents();
        initTableSetting();
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
        tablePanel = new javax.swing.JScrollPane();
        table = trueTable;

        setToolTipText("");

        titleLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 22)); // NOI18N
        titleLabel.setText("所有表");

        table.setModel(allDBTableModel);
        table.setToolTipText("");
        tablePanel.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(534, 534, 534)
                        .addComponent(titleLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(568, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables


    private JTable trueTable = new JTable() {
        @Override
        public String getToolTipText(MouseEvent event) {
            Point point = event.getPoint();

            int rowIndex = convertRowIndexToModel(rowAtPoint(point));
            int columnIndex = convertColumnIndexToModel(columnAtPoint(point));


            TableModel model = getModel();
            if (0 <= rowIndex && rowIndex < model.getRowCount() &&
                    0 <= columnIndex && columnIndex < model.getColumnCount())
                return model.getValueAt(rowIndex, columnIndex).toString();
            return null;
        }
    };


    private String[][] getAllDBTableInfo() {
        return new String[][]{
                {"1", "abc", "无"},
                {"3", "ade", "测试软件用，返回控制技术该函数立刻感觉返回时鼓里撒擂个阿斯顿该函数离开该丰富代理商公司管理是德国首都绿化格拉斯哥士丹利是"},
                {"4", "pdf", "存放PDF"}
        };
    }


    private AllDBTableModel allDBTableModel = new AllDBTableModel(getAllDBTableInfo());

    private class AllDBTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "DIR_NAME", "NOTE"};
        private String[][] data;


        public AllDBTableModel(String[][] data) {
            this.data = data;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
//            return getValueAt(0, columnIndex).getClass();
            return String.class;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int index) {
            return columnNames[index];
        }
    }


    private void initTableSetting() {
        table.setFillsViewportHeight(true);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(300);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setRowHeight(30);
        table.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21)); // NOI18N

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 21));
    }
}
