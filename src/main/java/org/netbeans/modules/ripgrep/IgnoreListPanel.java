/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.ripgrep;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import org.netbeans.api.search.RegexpUtil;
import org.netbeans.api.search.SearchScopeOptions;
import org.netbeans.modules.ripgrep.ui.UiUtils;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 *
 * @author jhavlin
 */
public final class IgnoreListPanel extends javax.swing.JPanel {

    private IgnoredListModel ignoreListModel;
    private JFileChooser jFileChooser;

    /**
     * Creates new form IgnoreListPanel
     */
    public IgnoreListPanel() {
        ignoreListModel = new IgnoredListModel();
        initComponents();
        setMnemonics();
        updateEnabledButtons();
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> updateEnabledButtons());
        // double click invokes edit action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnEdit.doClick();
                }
            }
        });
        //select first item
        selectAndScrollToRow(table, 0);
    }

    /**
     * Selects the row in a table. It will be assured that the row is visible.
     *
     * @param aTable
     * @param row
     */
    private void selectAndScrollToRow(JTable aTable, int row) {
        int rowcount = aTable.getModel().getRowCount();
        boolean rowsAvailable = rowcount > 0;
        boolean isValidRow = row < rowcount;
        if (rowsAvailable && isValidRow) {
            //select and scroll to item
            aTable.getSelectionModel().setSelectionInterval(row, row);
            aTable.scrollRectToVisible(aTable.getCellRect(row, 0, false));
        }
    }

    private void updateEnabledButtons() {
        int cnt = table.getSelectedRows().length;
        btnDelete.setEnabled(cnt > 0);

        boolean editable = false;
        int index = table.getSelectedRow();
        if (cnt == 1 && index >= 0) {
            IgnoreListItem ili = ignoreListModel.list.get(index);
            editable = ili.type == ItemType.PATTERN
                    || ili.type == ItemType.REGEXP;
        }
        btnEdit.setEnabled(editable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBrowse = new javax.swing.JButton();
        btnPattern = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        btnBrowse.setText("Add Folder...");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnPattern.setText("Add Path Pattern....");
        btnPattern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatternActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        table.setModel(ignoreListModel);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPattern, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBrowse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBrowse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPattern)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int firstSelectedRow = table.getSelectedRow();
        List<IgnoreListItem> itemsToDelete = new ArrayList<>();
        //get all selected objects
        //NOTE: remove the item from model later, so that the selected index
        //always points to the correct object
        for (int i : table.getSelectedRows()) {
            itemsToDelete.add(ignoreListModel.list.get(i));
        }
        //remove the selected objects
        for (IgnoreListItem item : itemsToDelete) {
            ignoreListModel.remove(item);
        }
        //select the next available row
        int row = Math.min(Math.max(0, firstSelectedRow),
                table.getModel().getRowCount() - 1);
        selectAndScrollToRow(table, row);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Window w = (Window) SwingUtilities.getAncestorOfClass(
                Window.class, this);
        if (w != null) {
            w.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        if (jFileChooser == null) {
            jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(
                    JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setMultiSelectionEnabled(true);
        }
        int showOpenDialog = jFileChooser.showOpenDialog(table);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File[] selected = jFileChooser.getSelectedFiles();
            if (selected != null) {
                for (File f : selected) {
                    ignoreListModel.addFile(f);
                }
            }
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnPatternActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatternActionPerformed

        PatternSandbox.openDialog(new PatternSandbox.PathPatternComposer(
                "", false) {                                            //NOI18N

            @Override
            protected void onApply(String pattern, boolean regexp) {
                ignoreListModel.addPattern(pattern, regexp);
            }
        }, this);
    }//GEN-LAST:event_btnPatternActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        final IgnoreListItem ili =
                ignoreListModel.list.get(table.getSelectedRow());
        boolean regex = ili.type == ItemType.REGEXP;
        PatternSandbox.openDialog(new PatternSandbox.PathPatternComposer(
                ili.value, regex) {
            @Override
            protected void onApply(String pattern, boolean regexp) {
                ignoreListModel.remove(ili);
                ignoreListModel.addPattern(pattern, regexp);
            }
        }, btnEdit);
    }//GEN-LAST:event_btnEditActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPattern;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        IgnoreListPanel ilp = new IgnoreListPanel();
        jf.add(ilp);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    private void setMnemonics() {
        setMnem(btnBrowse, "IgnoreListPanel.btnBrowse.text");           //NOI18N
        setMnem(btnDelete, "IgnoreListPanel.btnDelete.text");           //NOI18N
        setMnem(btnEdit, "IgnoreListPanel.btnEdit.text");               //NOI18N
        setMnem(btnClose, "IgnoreListPanel.btnClose.text");             //NOI18N
        setMnem(btnPattern, "IgnoreListPanel.btnPattern.text");         //NOI18N
    }

    private static void setMnem(AbstractButton button, String key) {
        Mnemonics.setLocalizedText(button, getText(key));
    }

    private static String getText(String key) {
        return NbBundle.getMessage(IgnoreListPanel.class, key);
    }

    enum ItemType {

        FILE("f: "), FOLDER("f: "), REGEXP("x: "),
        PATTERN("s: "), INVALID("i: ");
        private String typePrefix;

        private ItemType(String typePrefix) {
            this.typePrefix = typePrefix;
        }

        public String getTypePrefix() {
            return typePrefix;
        }

        public boolean isTypeOf(String s) {
            return s.startsWith(typePrefix);
        }
    }

    static class IgnoreListItem {

        private ItemType type;
        private String value;

        private IgnoreListItem(String string) {
            if (ItemType.PATTERN.isTypeOf(string)) {
                this.type = ItemType.PATTERN;
                this.value = string.substring(3);
            } else if (ItemType.REGEXP.isTypeOf(string)) {
                this.type = ItemType.REGEXP;
                this.value = string.substring(3);
            } else if (ItemType.FILE.isTypeOf(string)) {
                String path = string.substring(3);
                File f = new File(path);
                if (!f.exists()) {
                    this.type = ItemType.INVALID;
                    return;
                } else if (f.isDirectory()) {
                    this.type = ItemType.FOLDER;
                } else {
                    this.type = ItemType.FILE;
                }
                this.value = path;
            } else {
                type = ItemType.INVALID;
                return;
            }
        }

        private IgnoreListItem(ItemType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return type.getTypePrefix() + value;
        }

        static IgnoreListItem fromString(String string) {
            return new IgnoreListItem(string);
        }

        static IgnoreListItem forFile(File f) {
            ItemType type;
            if (!f.exists()) {
                type = ItemType.INVALID;
            } else if (f.isDirectory()) {
                type = ItemType.FOLDER;
            } else {
                type = ItemType.FILE;
            }
            return new IgnoreListItem(type, f.getAbsolutePath());
        }

        static IgnoreListItem forRegexp(String regexp) {
            return new IgnoreListItem(ItemType.REGEXP, regexp);
        }

        static IgnoreListItem forPattern(String pattern) {
            return new IgnoreListItem(ItemType.PATTERN, pattern);
        }
    }

    class IgnoredListModel extends AbstractTableModel {

        List<IgnoreListItem> list;

        public void remove(Object o) {
            int index = list.indexOf(o);
            list.remove(index);
            fireTableRowsDeleted(index, index);
            persist();
        }

        public void addFile(File f) {
            list.add(IgnoreListItem.forFile(f));
            fireTableRowsInserted(list.size() - 1, list.size());
            persist();
        }

        public void addPattern(String p, boolean regexp) {
            if (regexp) {
                addRegularExpression(p);
            } else {
                addSimplePatter(p);
            }
        }

        public void addSimplePatter(String p) {
            list.add(IgnoreListItem.forPattern(p));
            fireTableRowsInserted(list.size() - 1, list.size());
            persist();
        }

        public void addRegularExpression(String x) {
            list.add(IgnoreListItem.forRegexp(x));
            fireTableRowsInserted(list.size() - 1, list.size());
            persist();
        }

        public IgnoredListModel() {
            List<String> orig = FindDialogMemory.getDefault().getIgnoreList();
            list = new ArrayList<>(orig.size());
            for (String s : orig) {
                IgnoreListItem ili = IgnoreListItem.fromString(s);
                if (ili.type != ItemType.INVALID) {
                    list.add(ili);
                }
            }
        }

        public void persist() {
            List<String> copy = new ArrayList<>(list.size());
            for (IgnoreListItem ili : list) {
                copy.add(ili.toString());
            }
            FindDialogMemory.getDefault().setIgnoreList(copy);
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            IgnoreListItem ili = list.get(rowIndex);
            if (columnIndex == 0) {
                String msg;
                switch (ili.type) {
                    case FILE:
                        msg = "IgnoreListPanel.type.file";              //NOI18N
                        break;
                    case FOLDER:
                        msg = "IgnoreListPanel.type.folder";            //NOI18N
                        break;
                    case PATTERN:
                        msg = "IgnoreListPanel.type.pattern";           //NOI18N
                        break;
                    case REGEXP:
                        msg = "IgnoreListPanel.type.regexp";            //NOI18N
                        break;
                    default:
                        msg = "IgnoreListPanel.type.invalid";           //NOI18N
                }
                return UiUtils.getText(msg);
            } else {
                return ili.value;
            }
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return UiUtils.getText("IgnoreListPanel.item.type");    //NOI18N
            } else {
                return UiUtils.getText("IgnoreListPanel.item.value");   //NOI18N
            }
        }
    }

    public static void openDialog(JComponent baseComponent) {

        JDialog jd = new JDialog(
                (JDialog) SwingUtilities.getAncestorOfClass(
                JDialog.class, baseComponent));

        final IgnoreListPanel ilp = new IgnoreListPanel();
        jd.add(ilp);
        jd.setModal(true);
        jd.setLocationRelativeTo(baseComponent);
        jd.getRootPane().setDefaultButton(ilp.btnClose);
        registerCloseKey(jd, ilp);
        registerDeleteKey(jd, ilp);
        jd.pack();
        jd.setTitle(getText("IgnoreListPanel.title"));                  //NOI18N
        jd.setVisible(true);
    }

    /**
     * Register ESC key to close the dialog.
     */
    private static void registerCloseKey(JDialog jd,
            final IgnoreListPanel ilp) {

        Object actionKey = "cancel";                                   // NOI18N
        jd.getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), actionKey);

        Action cancelAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                ilp.btnClose.doClick();
            }
        };
        jd.getRootPane().getActionMap().put(actionKey, cancelAction);
    }

    /**
     * Register DEL key to remove the current selected items.
     */
    private static void registerDeleteKey(JDialog jd,
            final IgnoreListPanel ilp) {

        Object actionKey = "delete";                                   // NOI18N
        jd.getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), actionKey);

        Action deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                ilp.btnDelete.doClick();
            }
        };
        jd.getRootPane().getActionMap().put(actionKey, deleteAction);
    }

    static class IgnoreListManager {

        List<IgnoredItemDefinition> items;

        public IgnoreListManager(List<String> ignoreList) {
            items = new LinkedList<>();
            for (String s : ignoreList) {
                IgnoreListItem ili = IgnoreListItem.fromString(s);
                switch (ili.type) {
                    case PATTERN:
                        items.add(new IgnoredPatternDefinition(ili.value));
                        break;
                    case REGEXP:
                        items.add(new IgnoredRegexpDefinition(ili.value));
                        break;
                    case FOLDER:
                    case FILE:
                        items.add(new IgnoredDirDefinition(ili.value));
                        break;
                }
            }
        }

        boolean isIgnored(FileObject fo) {
            for (IgnoredItemDefinition iid : items) {
                if (iid.isIgnored(fo)) {
                    return true;
                }
            }
            return false;
        }

        private abstract class IgnoredItemDefinition {

            abstract boolean isIgnored(FileObject obj);
        }

        private class IgnoredPatternDefinition extends IgnoredItemDefinition {

            private Pattern p;

            public IgnoredPatternDefinition(String pattern) {
                p = RegexpUtil.makeFileNamePattern(
                        SearchScopeOptions.create(pattern, false));
            }

            @Override
            boolean isIgnored(FileObject obj) {
                return p.matcher(obj.getNameExt()).matches();
            }
        }

        private class IgnoredRegexpDefinition extends IgnoredItemDefinition {

            private Pattern p;

            public IgnoredRegexpDefinition(String pattern) {
                this.p = Pattern.compile(pattern);
            }

            @Override
            boolean isIgnored(FileObject obj) {
                File file = FileUtil.toFile(obj);
                if (file != null) {
                    return p.matcher(file.getPath()).find();
                } else {
                    return p.matcher(obj.getPath()).find();
                }
            }
        }

        private class IgnoredDirDefinition extends IgnoredItemDefinition {

            FileObject dir;

            public IgnoredDirDefinition(String path) {
                dir = FileUtil.toFileObject(new File(path));
            }

            @Override
            boolean isIgnored(FileObject obj) {
                return FileUtil.isParentOf(dir, obj) || obj.equals(dir);
            }
        }
    }
}