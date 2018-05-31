
package main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class frmPrescription extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();

    public frmPrescription() {
        initComponents();
        DbConn.DoConnect();
         FillTable();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmMedicalCertificate.DISPOSE_ON_CLOSE);
        tblPrescription.setAutoscrolls(true);
        tblPrescription.setDefaultEditor(Object.class, null);
        tblPrescription.setAutoResizeMode(tblPrescription.AUTO_RESIZE_OFF);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                DbConn.PrescribeForm = false;
            }
        });
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
 private void SetTableHeader(){
        tblPrescription.getColumnModel().getColumn(0).setHeaderValue("NAME");
        tblPrescription.getColumnModel().getColumn(1).setHeaderValue("PID");
        tblPrescription.getColumnModel().getColumn(2).setHeaderValue("APPT ID");
        tblPrescription.getColumnModel().getColumn(3).setHeaderValue("APPT DATE");
        tblPrescription.getColumnModel().getColumn(4).setHeaderValue("DOCTOR");
        tblPrescription.getColumnModel().getColumn(5).setHeaderValue("DESCRIPTION");
        
        int i=0;
        while (i<tblPrescription.getColumnCount()){
            tblPrescription.getColumnModel().getColumn(i).setPreferredWidth(100);
            i++;
        }
    }
    private void ListenSearch(){
        try{
            DbConn.SQLQuery = "select pm_name,pm_pid,pm_apptid,pm_adddate,pm_doctor,pm_description from tblpatientmedications where pm_name like ? or pm_pid like ? ";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" + txtSearch.getText() + "%");
            DbConn.pstmt.setString(2, "%" + txtSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPrescription.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetTableHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillTable(){
        try{
            DbConn.SQLQuery = "select pm_name,pm_pid,pm_apptid,pm_adddate,pm_doctor,pm_description from tblpatientmedications order by pm_name";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPrescription.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetTableHeader();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrescription = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPrescription.setBackground(new java.awt.Color(214, 214, 194));
        tblPrescription.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPrescription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPrescriptionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPrescription);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 610, 167));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 630, 190));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printericon.png"))); // NOI18N
        jButton1.setText("PRINT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Enter Name or PID:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, -1));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 440, -1));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPrescriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPrescriptionMouseClicked
        
    }//GEN-LAST:event_tblPrescriptionMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = tblPrescription.getSelectedRow();
        int ba = tblPrescription.convertRowIndexToModel(row);
        Map param = new HashMap();
        param.put("apptNumber", tblPrescription.getValueAt(ba, 2));
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemrph","root","root");
//            DbConn.conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/dbemrbeta","betapilfer","123456789");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportPrescription.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(frmPrescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrescription().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPrescription;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
