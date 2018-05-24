
package main;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.proteanit.sql.DbUtils;

public class frmPatientInquiry extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();

    public frmPatientInquiry() {
        initComponents();
        DbConn.DoConnect();
        FillTable();
        SetTableWidth();
        setDefaultCloseOperation(frmPatientInquiry.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        tblPatient.setAutoscrolls(true);
        tblPatient.setAutoResizeMode(tblPatient.AUTO_RESIZE_OFF);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenSearch();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenSearch();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenSearch();}
        });
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void SetTableWidth(){
        tblPatient.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblPatient.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblPatient.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblPatient.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblPatient.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblPatient.getColumnModel().getColumn(5).setPreferredWidth(75);
        
    }
    private void FillTable(){
        try{
            DbConn.SQLQuery = "select pd_pid,pd_name,pd_gender,pd_dob,pd_address,pd_mobile from tblpatientdetails order by pd_pid";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();        
            tblPatient.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblPatient.getColumnModel().getColumn(1).setHeaderValue("Name");
            tblPatient.getColumnModel().getColumn(2).setHeaderValue("Gender");
            tblPatient.getColumnModel().getColumn(3).setHeaderValue("DOB");
            tblPatient.getColumnModel().getColumn(4).setHeaderValue("Address");
            tblPatient.getColumnModel().getColumn(5).setHeaderValue("Mobile");
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPatient = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Type Here:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, 20));
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 320, -1));

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SearchIcon.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 120, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 430, 110));

        tblPatient.setBackground(new java.awt.Color(214, 214, 194));
        tblPatient.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPatient);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 430, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       
    }//GEN-LAST:event_btnSearchActionPerformed
    private void ListenSearch(){
         try{
            DbConn.SQLQuery = "select pd_pid,pd_name,pd_gender,pd_dob,pd_address,pd_mobile from tblpatientdetails "
                    + "where pd_pid like ? or pd_name like ? or pd_mobile like ? order by pd_pid";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" +txtSearch.getText()+ "%");
            DbConn.pstmt.setString(2, "%" +txtSearch.getText()+ "%");
            DbConn.pstmt.setString(3, "%" +txtSearch.getText()+ "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            SetTableWidth();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
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
            java.util.logging.Logger.getLogger(frmPatientInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPatientInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPatientInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPatientInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPatientInquiry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPatient;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
