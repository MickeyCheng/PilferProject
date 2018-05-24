package main;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class frmMain extends javax.swing.JFrame {
    DbConnection DbConn = new DbConnection();
    public frmMain() {
        initComponents();
        setExtendedState(frmMain.MAXIMIZED_BOTH);
        GrantAccess();
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
//    private void CheckAccessLevel(){
//        if (!DbConn.LoggedUserCategory.equals("Doctor") && !DbConn.LoggedUserAdmin.equals("Y")){
//            mnuMedCert.setEnabled(false);
//            btnDoctor.setEnabled(false);
//        }
//        if (!DbConn.LoggedUserAdmin.equals("Y")){
//            mnuSalesInq.setEnabled(false);
//            mnuGenMaster.setEnabled(false);
//            mnuUserMaster.setEnabled(false);
//            mnuUserSecMaster.setEnabled(false);
//        }
//    }
    private void GrantAccess(){
        //registration
        if (DbConn.RegViewDb.equals("N")){
            btnRegistration.setEnabled(false);
        }
        
        //doctor
        if (DbConn.DocViewDb.equals("N")){
            btnDoctor.setEnabled(false);
        }
        //appointment
        if (DbConn.ApptViewDb.equals("N")){
            btnAppointment.setEnabled(false);
        }
        
        //bill
        if (DbConn.BillInvoiceGenDb.equals("N")){
            mnuInvoiceReceipt.setEnabled(false);
        }
        //inquiry
        if (DbConn.InqApptDb.equals("N")){
            mnuApptInquiry.setEnabled(false);
        }
        if (DbConn.InqInvoiceDb.equals("N")){
            mnuInvoiceInq.setEnabled(false);
        }
        if (DbConn.InqPatientDb.equals("N")){
            mnuPatientInquiry.setEnabled(false);
        }
        if (DbConn.InqSalesDb.equals("N")){
            mnuSalesInq.setEnabled(false);
        }
        //master
        if (DbConn.MasterGenDb.equals("N")){
            mnuGenMaster.setEnabled(false);
        }
        if(DbConn.MasterUserDb.equals("N")){
            mnuUserMaster.setEnabled(false);
        }
        if (DbConn.MasterSecurityDb.equals("N")){
            mnuUserSecMaster.setEnabled(false);
        }
        if (DbConn.MasterDrugDb.equals("N")){
            mnuDrugMaster.setEnabled(false);
        }
        if(DbConn.MasterTreatmentDb.equals("N")){
            mnuTreatmentMaster.setEnabled(false);
        }
        if (DbConn.MasterInsuranceDb.equals("N")){
            mnuInsuranceMaster.setEnabled(false);
        }
        if (DbConn.MasterICDDb.equals("N")){
            mnuICDMaster.setEnabled(false);
        }
        //reports
        if (DbConn.ReportMedCertDb.equals("N")){
            mnuMedCert.setEnabled(false);
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
        btnRegistration = new javax.swing.JButton();
        btnAppointment = new javax.swing.JButton();
        btnDoctor = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        mnuInvoiceReceipt = new javax.swing.JMenuItem();
        mnuPatientInquiry = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        mnuApptInquiry = new javax.swing.JMenuItem();
        mnuSalesInq = new javax.swing.JMenuItem();
        mnuInvoiceInq = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mnuMedCert = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mnuGenMaster = new javax.swing.JMenuItem();
        mnuUserMaster = new javax.swing.JMenuItem();
        mnuUserSecMaster = new javax.swing.JMenuItem();
        mnuDrugMaster = new javax.swing.JMenuItem();
        mnuTreatmentMaster = new javax.swing.JMenuItem();
        mnuInsuranceMaster = new javax.swing.JMenuItem();
        mnuICDMaster = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRegistration.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/registrationButtonMain.png"))); // NOI18N
        btnRegistration.setText("Registration");
        btnRegistration.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistration.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRegistration.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrationActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistration, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 220, 240));

        btnAppointment.setBackground(new java.awt.Color(255, 255, 255));
        btnAppointment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ApptButtonMain.png"))); // NOI18N
        btnAppointment.setText("Appointments");
        btnAppointment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAppointment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentActionPerformed(evt);
            }
        });
        jPanel1.add(btnAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 220, 240));

        btnDoctor.setBackground(new java.awt.Color(255, 255, 255));
        btnDoctor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DoctorButtonMain.png"))); // NOI18N
        btnDoctor.setText("Doctor");
        btnDoctor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDoctor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoctorActionPerformed(evt);
            }
        });
        jPanel1.add(btnDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 220, 240));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Log Out");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Billing");

        mnuInvoiceReceipt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInvoiceReceipt.setText("Invoice Generate");
        mnuInvoiceReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInvoiceReceiptActionPerformed(evt);
            }
        });
        jMenu3.add(mnuInvoiceReceipt);

        jMenuBar1.add(jMenu3);

        mnuPatientInquiry.setText("Inquiry");

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Patient Inquiry");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mnuPatientInquiry.add(jMenuItem7);

        mnuApptInquiry.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuApptInquiry.setText("Appointment Inquiry");
        mnuApptInquiry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuApptInquiryActionPerformed(evt);
            }
        });
        mnuPatientInquiry.add(mnuApptInquiry);

        mnuSalesInq.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSalesInq.setText("Sales Inquiry");
        mnuSalesInq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalesInqActionPerformed(evt);
            }
        });
        mnuPatientInquiry.add(mnuSalesInq);

        mnuInvoiceInq.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInvoiceInq.setText("Invoice Inquiry");
        mnuInvoiceInq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInvoiceInqActionPerformed(evt);
            }
        });
        mnuPatientInquiry.add(mnuInvoiceInq);

        jMenuBar1.add(mnuPatientInquiry);

        jMenu4.setText("Reports");

        mnuMedCert.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuMedCert.setText("Medical Certificate");
        mnuMedCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMedCertActionPerformed(evt);
            }
        });
        jMenu4.add(mnuMedCert);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Master");

        mnuGenMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuGenMaster.setText("General Master");
        mnuGenMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGenMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuGenMaster);

        mnuUserMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuUserMaster.setText("User Master");
        mnuUserMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUserMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuUserMaster);

        mnuUserSecMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuUserSecMaster.setText("User Security");
        mnuUserSecMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUserSecMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuUserSecMaster);

        mnuDrugMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuDrugMaster.setText("Drug Master");
        mnuDrugMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDrugMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuDrugMaster);

        mnuTreatmentMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuTreatmentMaster.setText("Treatment Master");
        mnuTreatmentMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTreatmentMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuTreatmentMaster);

        mnuInsuranceMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsuranceMaster.setText("Insurance Master");
        mnuInsuranceMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsuranceMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuInsuranceMaster);

        mnuICDMaster.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuICDMaster.setText("ICD 10 Master");
        mnuICDMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuICDMasterActionPerformed(evt);
            }
        });
        jMenu5.add(mnuICDMaster);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1357, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrationActionPerformed
        frmRegistration obj = new frmRegistration();
            obj.setVisible(true);
    }//GEN-LAST:event_btnRegistrationActionPerformed

    private void btnAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentActionPerformed

        frmAppointment obj = new frmAppointment();
            obj.setVisible(true);
    }//GEN-LAST:event_btnAppointmentActionPerformed

    private void btnDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoctorActionPerformed

        frmDoctor obj = new frmDoctor();
            obj.setVisible(true);
    }//GEN-LAST:event_btnDoctorActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        frmPatientInquiry obj = new frmPatientInquiry();
        obj.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void mnuApptInquiryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuApptInquiryActionPerformed
        frmApptInquiry obj = new frmApptInquiry();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuApptInquiryActionPerformed

    private void mnuSalesInqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalesInqActionPerformed
        frmSalesInquiry obj = new frmSalesInquiry();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuSalesInqActionPerformed

    private void mnuInvoiceInqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInvoiceInqActionPerformed
        frmInvoiceInquiry obj = new frmInvoiceInquiry();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuInvoiceInqActionPerformed

    private void mnuGenMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGenMasterActionPerformed
        frmGeneralMaster obj = new frmGeneralMaster();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuGenMasterActionPerformed

    private void mnuUserMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUserMasterActionPerformed
        frmUserMaster obj = new frmUserMaster();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuUserMasterActionPerformed

    private void mnuDrugMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDrugMasterActionPerformed
        frmMedicineMaster obj = new frmMedicineMaster();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuDrugMasterActionPerformed

    private void mnuTreatmentMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTreatmentMasterActionPerformed
        frmTreatmentMaster obj = new frmTreatmentMaster();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuTreatmentMasterActionPerformed

    private void mnuICDMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuICDMasterActionPerformed
        frmICDMaster obj = new frmICDMaster();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuICDMasterActionPerformed

    private void mnuInsuranceMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInsuranceMasterActionPerformed
        frmInsurance obj = new frmInsurance();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuInsuranceMasterActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        
        Window win[] = Window.getWindows();
        for (int i=0;i<win.length;i++){
            win[i].dispose();
        }
        frmLogin obj = new frmLogin();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int selectExit = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?","Exit",JOptionPane.YES_NO_OPTION);
        if (selectExit == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnuMedCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMedCertActionPerformed
        frmMedicalCertificate obj = new frmMedicalCertificate();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuMedCertActionPerformed

    private void mnuInvoiceReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInvoiceReceiptActionPerformed
        frmBilling obj = new frmBilling();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuInvoiceReceiptActionPerformed

    private void mnuUserSecMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUserSecMasterActionPerformed
        frmUserSecurity obj = new frmUserSecurity();
        obj.setVisible(true);
    }//GEN-LAST:event_mnuUserSecMasterActionPerformed

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppointment;
    private javax.swing.JButton btnDoctor;
    private javax.swing.JButton btnRegistration;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem mnuApptInquiry;
    private javax.swing.JMenuItem mnuDrugMaster;
    private javax.swing.JMenuItem mnuGenMaster;
    private javax.swing.JMenuItem mnuICDMaster;
    private javax.swing.JMenuItem mnuInsuranceMaster;
    private javax.swing.JMenuItem mnuInvoiceInq;
    private javax.swing.JMenuItem mnuInvoiceReceipt;
    private javax.swing.JMenuItem mnuMedCert;
    private javax.swing.JMenu mnuPatientInquiry;
    private javax.swing.JMenuItem mnuSalesInq;
    private javax.swing.JMenuItem mnuTreatmentMaster;
    private javax.swing.JMenuItem mnuUserMaster;
    private javax.swing.JMenuItem mnuUserSecMaster;
    // End of variables declaration//GEN-END:variables
}
