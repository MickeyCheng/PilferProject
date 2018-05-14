
package main;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
public class frmGeneralMaster extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
String CheckRecord;
Date todayDate = new Date();
    public frmGeneralMaster() {
        initComponents();
        DbConn.DoConnect();
        LoadData();
        setDefaultCloseOperation(frmGeneralMaster.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private void LoadData(){
        try{
            DbConn.SQLQuery = "select * from tblgeneralmaster";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                CheckRecord="update";
                txtName.setText(DbConn.rs.getString("gm_name"));
                txtAreaAddress.setText(DbConn.rs.getString("gm_address"));
                txtEmail.setText(DbConn.rs.getString("gm_email"));
                txtFax.setText(DbConn.rs.getString("gm_fax"));
                txtMobile.setText(DbConn.rs.getString("gm_mob"));
                txtPhone.setText(DbConn.rs.getString("gm_phone"));
                txtWebsite.setText(DbConn.rs.getString("gm_website"));
            }else{
                CheckRecord="insert";
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMobile = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtWebsite = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaAddress = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Name:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 250, -1));

        jLabel2.setText("Phone:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 250, -1));

        jLabel3.setText("Mobile:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        jPanel1.add(txtMobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 250, -1));

        jLabel4.setText("Fax:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));
        jPanel1.add(txtFax, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 250, -1));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 160, -1));

        jLabel5.setText("Email:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, -1, -1));

        jLabel6.setText("Website:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, -1, -1));
        jPanel1.add(txtWebsite, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 160, -1));

        jLabel7.setText("Address:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setText("jLabel8");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, 80));

        txtAreaAddress.setColumns(20);
        txtAreaAddress.setRows(5);
        jScrollPane1.setViewportView(txtAreaAddress);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, 50));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, -1, -1));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (CheckRecord=="insert"){
        try{
            DbConn.SQLQuery = "insert into tblgeneralmaster (gm_name,gm_phone,gm_mob,gm_fax,gm_adduser,gm_adddate,"
                    + "gm_email,gm_address,gm_website)"
                    + " values (?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, txtName.getText());
            DbConn.pstmt.setString(2, txtPhone.getText());
            DbConn.pstmt.setString(3, txtMobile.getText());
            DbConn.pstmt.setString(4, txtFax.getText());
            DbConn.pstmt.setString(5, "Joefrey");
            DbConn.pstmt.setString(6, DbConn.sdfDate.format(todayDate));
            DbConn.pstmt.setString(7, txtEmail.getText());
            DbConn.pstmt.setString(8, txtAreaAddress.getText());
            DbConn.pstmt.setString(9, txtWebsite.getText());
            DbConn.pstmt.execute();
            JOptionPane.showMessageDialog(this, "Details Saved");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }else if (CheckRecord=="update"){
        try{
            DbConn.SQLQuery = "update tblgeneralmaster set gm_name=?,gm_phone=?,gm_mob=?,gm_fax=?,gm_moduser=?,gm_moddate=?,"
                    + "gm_email=?,gm_address=?,gm_website=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, txtName.getText());
            DbConn.pstmt.setString(2, txtPhone.getText());
            DbConn.pstmt.setString(3, txtMobile.getText());
            DbConn.pstmt.setString(4, txtFax.getText());
            DbConn.pstmt.setString(5, "Joefrey");
            DbConn.pstmt.setString(6, DbConn.sdfDate.format(todayDate));
            DbConn.pstmt.setString(7, txtEmail.getText());
            DbConn.pstmt.setString(8, txtAreaAddress.getText());
            DbConn.pstmt.setString(9, txtWebsite.getText());
            DbConn.pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Details Updated");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(frmGeneralMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmGeneralMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmGeneralMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmGeneralMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmGeneralMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtWebsite;
    // End of variables declaration//GEN-END:variables
}
