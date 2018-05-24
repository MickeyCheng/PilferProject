
package main;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.proteanit.sql.DbUtils;

public class frmTreatmentMaster extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
boolean add,edit;
    
    public frmTreatmentMaster() {
        initComponents();
        DbConn.DoConnect();
        FillTable();
        radYes.setSelected(true);
        setDefaultCloseOperation(frmTreatmentMaster.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void SetHeader(){
       tblTreatment.getColumnModel().getColumn(0).setHeaderValue("ID");
       tblTreatment.getColumnModel().getColumn(1).setHeaderValue("Code");
       tblTreatment.getColumnModel().getColumn(2).setHeaderValue("Group");
       tblTreatment.getColumnModel().getColumn(3).setHeaderValue("Description");
       tblTreatment.getColumnModel().getColumn(4).setHeaderValue("Price");
       tblTreatment.getColumnModel().getColumn(5).setHeaderValue("Status");
       tblTreatment.getColumnModel().getColumn(6).setHeaderValue("User Added");
       tblTreatment.getColumnModel().getColumn(7).setHeaderValue("Date");
    }
    private void FillTable(){
        try{
            DbConn.SQLQuery = "SELEct * from tbltreatmentmaster order by tm_description";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblTreatment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupActive = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTreatment = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        radYes = new javax.swing.JRadioButton();
        radNo = new javax.swing.JRadioButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Treatment Master");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(678, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 890, -1));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, -3, 50, 430));

        tblTreatment.setBackground(new java.awt.Color(214, 214, 194));
        tblTreatment.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTreatment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTreatmentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTreatment);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 520, 410));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Code:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        jPanel4.add(txtCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 220, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Description:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));
        jPanel4.add(txtDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 220, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Price:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));
        jPanel4.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 220, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Active:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        groupActive.add(radYes);
        radYes.setForeground(new java.awt.Color(0, 0, 0));
        radYes.setText("Yes");
        jPanel4.add(radYes, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 60, -1));

        groupActive.add(radNo);
        radNo.setForeground(new java.awt.Color(0, 0, 0));
        radNo.setText("No");
        jPanel4.add(radNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 60, -1));

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NewIcon.png"))); // NOI18N
        btnAdd.setText("NEW");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel4.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 120, 110));

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/EditIcon.png"))); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel4.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 120, 110));

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SaveIcon.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel4.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 120, 110));

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DeleteIcon.png"))); // NOI18N
        btnDelete.setText("DELETE");
        jPanel4.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 120, 110));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 890, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 909, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add = true;
        edit = false;
        clearTexts();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    edit = true;
    add= false;
    txtCode.requestFocus();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int row = tblTreatment.getSelectedRow();
        if (add==true && edit==false){
            try{
             DbConn.SQLQuery = "insert into tbltreatmentmaster (tm_code, tm_description,tm_price,tm_active) values (?,?,?,?)";
             DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
             DbConn.pstmt.setString(1, txtCode.getText());
             DbConn.pstmt.setString(2, txtDescription.getText());
             DbConn.pstmt.setString(3, txtPrice.getText());
             if (radYes.isSelected()){
                DbConn.pstmt.setString(4, "Y");
             }else{
                 DbConn.pstmt.setString(4, "N");
             }
             DbConn.pstmt.execute();
             JOptionPane.showMessageDialog(this, "Treatment Saved");
             clearTexts();
             FillTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }else if (edit==true && add==false){
            try{
             DbConn.SQLQuery = "update tbltreatmentmaster set tm_code=?, tm_description=?,tm_price=?,tm_active=? where tm_code=?";
             DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
             DbConn.pstmt.setString(1, txtCode.getText());
             DbConn.pstmt.setString(2, txtDescription.getText());
             DbConn.pstmt.setString(3, txtPrice.getText());
             if (radYes.isSelected()){
                DbConn.pstmt.setString(4, "Y");
             }else{
                 DbConn.pstmt.setString(4, "N");
             }
             DbConn.pstmt.setString(5, tblTreatment.getValueAt(row, 1).toString());
             DbConn.pstmt.executeUpdate();
             JOptionPane.showMessageDialog(this, "Treatment Updated");
             clearTexts();
             FillTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblTreatmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTreatmentMouseClicked
        int row = tblTreatment.getSelectedRow();
        int ba = tblTreatment.convertRowIndexToModel(row);
        txtCode.setText(tblTreatment.getValueAt(ba, 1).toString());
        txtDescription.setText(tblTreatment.getValueAt(ba, 3).toString());
        txtPrice.setText(tblTreatment.getValueAt(ba, 4).toString());
        String GetStatus = tblTreatment.getValueAt(ba, 5).toString();
        if (GetStatus == "Y"){
            radYes.isSelected();
        }else{
            radNo.isSelected();
        }
    }//GEN-LAST:event_tblTreatmentMouseClicked
    private void clearTexts(){
        txtCode.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
    }
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
            java.util.logging.Logger.getLogger(frmTreatmentMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTreatmentMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTreatmentMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTreatmentMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTreatmentMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup groupActive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radNo;
    private javax.swing.JRadioButton radYes;
    private javax.swing.JTable tblTreatment;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
