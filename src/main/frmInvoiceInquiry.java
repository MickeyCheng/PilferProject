package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
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
public class frmInvoiceInquiry extends javax.swing.JFrame {
DbConnection DbConn= new DbConnection();
Date TodayDate;
String GetFromDate,GetToDate;
    public frmInvoiceInquiry() {
        initComponents();
        setDefaultCloseOperation(frmInvoiceInquiry.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        DbConn.DoConnect();
        FillComboDoctor();
        TodayDate = new Date();
        SetTodayDate();
        FillTodayTable();
        tblInvoice.setAutoCreateRowSorter(true);
        tblInvoice.setAutoResizeMode(tblInvoice.AUTO_RESIZE_OFF);
        tblInvoice.setAutoscrolls(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                DbConn.InqInvoiceForm = false;
            }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenSearchTextBox();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenSearchTextBox();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenSearchTextBox();}
        });
        cmbDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {ListenSearch();}
        });
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void FillComboDoctor(){
        cmbDoctor.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblusermaster where um_category =? order by um_name");
            DbConn.pstmt.setString(1, "Doctor");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDoctor.addItem(DbConn.rs.getString("um_name"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
     private void ListenSearchTextBox(){
        GetDate();
        try{
            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblInvoiceheader "
                    + "where ih_pid like ? or ih_patientname like?";

            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1,"%"+ txtSearch.getText() + "%");
            DbConn.pstmt.setString(2,"%"+ txtSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblInvoice.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void ListenSearch(){
        GetDate();
        try{
//            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblInvoiceheader "
//                    + "where ih_date between ? and ? and ih_doctor =? and ih_pid like ? or ih_patientname like?";
 DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblInvoiceheader "
                    + "where ih_date between ? and ? and ih_doctor =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, GetFromDate);
            DbConn.pstmt.setString(2, GetToDate);
            DbConn.pstmt.setString(3,cmbDoctor.getSelectedItem().toString());
//            DbConn.pstmt.setString(4,"%"+ txtSearch.getText() + "%");
//            DbConn.pstmt.setString(5,"%"+ txtSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblInvoice.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetTodayDate(){
        fromDate.setDate(TodayDate);
        toDate.setDate(TodayDate);
    }
    private void GetDate (){
        GetFromDate = DbConn.sdfDate.format(fromDate.getDate());
        GetToDate = DbConn.sdfDate.format(toDate.getDate());
    }
    private void FillTodayTable(){
        GetDate();
        try{
            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblInvoiceheader where ih_date between ? and ? order by ih_number ASC";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, GetFromDate);
            DbConn.pstmt.setString(2, GetToDate);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblInvoice.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetHeader(){
        tblInvoice.getColumnModel().getColumn(0).setHeaderValue("INVOICE NUMBER");
        tblInvoice.getColumnModel().getColumn(1).setHeaderValue("DATE");
        tblInvoice.getColumnModel().getColumn(2).setHeaderValue("PATIENT ID");
        tblInvoice.getColumnModel().getColumn(3).setHeaderValue("PATIENT NAME");
        tblInvoice.getColumnModel().getColumn(4).setHeaderValue("SUBTOTAL");
        tblInvoice.getColumnModel().getColumn(5).setHeaderValue("NET AMOUNT");
        tblInvoice.getColumnModel().getColumn(6).setHeaderValue("DOCTOR");
        
        int i = 0;
        while (i <tblInvoice.getColumnCount()){
            tblInvoice.getColumnModel().getColumn(i).setPreferredWidth(150);
            i++;
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
        fromDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        toDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbDoctor = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        btnViewInvoice = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Invoice Inquiry");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, -1));

        fromDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fromDateMouseReleased(evt);
            }
        });
        jPanel2.add(fromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 140, 30));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("From:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, 30));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("To:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, -1, 30));

        toDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                toDateMouseReleased(evt);
            }
        });
        jPanel2.add(toDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 140, 30));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Attending Doctor:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, -1, -1));
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 290, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Name or PID:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 280, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 80, 110));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 900, 110));

        tblInvoice.setBackground(new java.awt.Color(214, 214, 194));
        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblInvoice);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 900, 340));

        btnViewInvoice.setBackground(new java.awt.Color(255, 255, 255));
        btnViewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ReportsIcon.png"))); // NOI18N
        btnViewInvoice.setText("View Invoice");
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });
        jPanel1.add(btnViewInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, -1, 50));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fromDateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromDateMouseReleased
        ListenSearch();
    }//GEN-LAST:event_fromDateMouseReleased

    private void toDateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toDateMouseReleased
        ListenSearch();
    }//GEN-LAST:event_toDateMouseReleased

    private void btnViewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewInvoiceActionPerformed
        Map param = new HashMap();
        int row = tblInvoice.getSelectedRow();
        int ba = tblInvoice.convertRowIndexToModel(row);
        param.put("invoiceNumber", tblInvoice.getValueAt(row, 0).toString());
//        param.put("dateTo", sdfDate.format(getToDate));
//        param.put("showStatus", cmbStatus.getSelectedItem().toString());
//        param.put("showClient", cmbClient.getSelectedItem().toString());
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
            //            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbticketing","root","root");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemrph","root","root");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportInvoice.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewInvoiceActionPerformed

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
            java.util.logging.Logger.getLogger(frmInvoiceInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInvoiceInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInvoiceInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInvoiceInquiry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInvoiceInquiry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnViewInvoice;
    private javax.swing.JComboBox<String> cmbDoctor;
    private com.toedter.calendar.JDateChooser fromDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblInvoice;
    private com.toedter.calendar.JDateChooser toDate;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
