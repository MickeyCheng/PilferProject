
package main;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class frmUserMaster extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
boolean add,edit;
    public frmUserMaster() {
        initComponents();
        DbConn.DoConnect();
        SetRadioDefault();
        FillTable();
        FillCombo();
        ClearTexts();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmUserMaster.DISPOSE_ON_CLOSE);
    }
    private void ClearTexts(){
        txtAccountName.setText("");
        txtPassword.setText("");
        txtUserId.setText("");
    }
    private void SetRadioDefault(){
        radActiveYes.setSelected(true);
        radAdminYes.setSelected(true);
    }
    private void FillCombo(){
        cmbCategory.removeAllItems();
        cmbCategory.addItem("Nurse");
        cmbCategory.addItem("Receptionist");
        cmbCategory.addItem("Cashier");
        cmbCategory.addItem("Accountant");
        cmbCategory.addItem("Doctor");
        cmbCategory.addItem("Other");
        cmbCategory.setSelectedItem(-1);
    }
    private void FillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblusermaster");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblUserMaster.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AdminGroup = new javax.swing.ButtonGroup();
        ActiveGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserMaster = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAccountName = new javax.swing.JTextField();
        txtUserId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        radAdminYes = new javax.swing.JRadioButton();
        radAdminNo = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        radActiveYes = new javax.swing.JRadioButton();
        radActiveNo = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblUserMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUserMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUserMaster);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 660, 210));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ACCOUNT NAME:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 20));
        jPanel3.add(txtAccountName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 240, -1));
        jPanel3.add(txtUserId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 240, -1));

        jLabel2.setText("USER ID:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, 20));
        jPanel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 240, -1));

        jLabel3.setText("ACTIVE:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 50, 20));

        jLabel4.setText("PASSWORD:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 120, 20));

        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 240, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 20, 210));

        jButton1.setText("NEW");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 100, 60));

        jButton2.setText("EDIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 100, 60));

        jButton3.setText("DELETE");
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 100, 60));

        jButton4.setText("SAVE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 100, 60));

        jLabel5.setText("CATEGORY");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 120, 20));

        AdminGroup.add(radAdminYes);
        radAdminYes.setText("Yes");
        jPanel3.add(radAdminYes, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 60, -1));

        AdminGroup.add(radAdminNo);
        radAdminNo.setText("No");
        jPanel3.add(radAdminNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 50, -1));

        jLabel6.setText("ADMIN:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 50, 20));

        ActiveGroup.add(radActiveYes);
        radActiveYes.setText("Yes");
        jPanel3.add(radActiveYes, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 60, -1));

        ActiveGroup.add(radActiveNo);
        radActiveNo.setText("No");
        jPanel3.add(radActiveNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 50, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 660, 210));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ClearTexts();
        add = true;
        edit = false;
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        edit = true;
        add = false;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            if (add==true && edit==false){
                DbConn.SQLQuery = "INSERT into tblusermaster (um_id,um_name,um_password,um_status,um_category,um_administrator) "
                        + "values (?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtUserId.getText());
                DbConn.pstmt.setString(2, txtAccountName.getText());
                DbConn.pstmt.setString(3, txtPassword.getText());
                if (radActiveNo.isSelected()){                
                    DbConn.pstmt.setString(4, "N");
                }else{
                    DbConn.pstmt.setString(4, "Y");
                }
                DbConn.pstmt.setString(5, cmbCategory.getSelectedItem().toString());
                if (radAdminYes.isSelected()){                    
                    DbConn.pstmt.setString(6, "Y");
                }else{
                    DbConn.pstmt.setString(6, "N");
                }
                
                DbConn.pstmt.execute();
                JOptionPane.showMessageDialog(this, "USER ADDED");
                FillTable();
                ClearTexts();
            }else if (add==false && edit==true){
                DbConn.SQLQuery = "update tblusermaster set um_name=?,um_password=?,um_status=?,um_category=?,um_administrator=? where um_id=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtAccountName.getText());
                DbConn.pstmt.setString(2, txtPassword.getText());
                if (radActiveYes.isSelected()){                
                    DbConn.pstmt.setString(3, "Y");
                }else{
                    DbConn.pstmt.setString(3, "N");
                }
                DbConn.pstmt.setString(4, cmbCategory.getSelectedItem().toString());
                if (radAdminYes.isSelected()){                    
                    DbConn.pstmt.setString(5, "Y");
                }else{
                    DbConn.pstmt.setString(5, "N");
                }
                DbConn.pstmt.setString(6, txtUserId.getText());

                DbConn.pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "USER DETAILS UPDATED");
                FillTable();
                ClearTexts();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblUserMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMasterMouseClicked
        int row = tblUserMaster.getSelectedRow();
        int ba = tblUserMaster.convertColumnIndexToModel(row);
        String tblClick = tblUserMaster.getValueAt(ba, 0).toString();
        try{
            DbConn.SQLQuery = "Select * from tblusermaster where um_id =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtAccountName.setText(DbConn.rs.getString("um_name"));
                txtUserId.setText(DbConn.rs.getString("um_id"));
                txtPassword.setText(DbConn.rs.getString("um_password"));
                cmbCategory.setSelectedItem(DbConn.rs.getString("um_category"));
                String getActive, getAdmin;
                getActive = DbConn.rs.getString("um_status");
                getAdmin = DbConn.rs.getString("um_administrator");
                if (getActive.equals("Y")){
                    radActiveYes.setSelected(true);
                }else{
                    radActiveNo.setSelected(true);
                }
                if (getAdmin.equals("Y")){
                    radAdminYes.setSelected(true);
                }else{
                    radAdminNo.setSelected(true);
                }
                DbConn.pstmt.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblUserMasterMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmUserMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmUserMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmUserMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmUserMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmUserMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup ActiveGroup;
    private javax.swing.ButtonGroup AdminGroup;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radActiveNo;
    private javax.swing.JRadioButton radActiveYes;
    private javax.swing.JRadioButton radAdminNo;
    private javax.swing.JRadioButton radAdminYes;
    private javax.swing.JTable tblUserMaster;
    private javax.swing.JTextField txtAccountName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
