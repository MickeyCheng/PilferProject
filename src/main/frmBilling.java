
package main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

public class frmBilling extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
Date todayDate = new Date();
String GetApptID,GetBillStatus,GetPID,GetDoctor,GetPatientName;
    public frmBilling() {
        initComponents();
        loadToday();
        DbConn.DoConnect();
        FillCurrentDay();
        setDefaultCloseOperation(frmBilling.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        lblBillChecker.setText("<html><center>Bill<br> Processed</center></html>");
        lblBillChecker.setVisible(false);
        tblBilling.setAutoResizeMode(tblBilling.AUTO_RESIZE_OFF);
        tblBilling.setAutoscrolls(true);
        tblBilling.setAutoCreateRowSorter(true);
        tblBilling.setDefaultEditor(Object.class, null);
        tblPayable.setDefaultEditor(Object.class, null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                DbConn.InvGenForm = false;
            }
        });
        dateAppointment.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {FillDateChange();}
        });
//        txtCash.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {CalculateAmount();}
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {CalculateAmount();}
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {CalculateAmount();}
//        });
try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void CalculateAmount(){
        Double SetAmountDue=0.000, GetCash=0.000, GetCard=00.000,GetCheck=00.000,
                GetTotalPayment=00.000, GetDiscount =0.0,GetSubTotal=0.0;
        GetCash = Double.valueOf(txtCash.getText());
        GetCard = Double.valueOf(txtCard.getText());
        GetCheck = Double.valueOf(txtCheck.getText());
        GetSubTotal = Double.valueOf(lblSubtotal.getText());
        GetTotalPayment = GetCard + GetCash+GetCheck;
        GetDiscount = Double.valueOf(txtDiscount.getText());
        SetAmountDue = Double.valueOf(lblSubtotal.getText()) - GetTotalPayment - GetDiscount;
        lblAmountDue.setText(String.valueOf(DbConn.df.format(SetAmountDue)));
        lblNet.setText(String.valueOf(DbConn.df.format(GetTotalPayment)));
    }
    private void loadToday(){
       dateAppointment.setDate(todayDate);
    }
    private void FillDateChange(){
        try{
            DbConn.SQLQuery = "select ap_apptnumber,ap_name,ap_appttime,ap_doctor,ap_pid from tblappointment where ap_apptdate =? and ap_billed =? ";
//            DbConn.SQLQuery = "select ap_apptnumber,ap_name,ap_appttime,ap_doctor,ap_pid from tblappointment where ap_apptdate =? and ap_billed =? or ap_billed =? ";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, String.valueOf(DbConn.sdfDate.format(dateAppointment.getDate())));
            DbConn.pstmt.setString(2, "YES");
//            DbConn.pstmt.setString(3, "PROCESSED");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblBilling.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetBillingHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetBillingHeader(){
            tblBilling.getColumnModel().getColumn(0).setHeaderValue("APPT NUMBER");
            tblBilling.getColumnModel().getColumn(1).setHeaderValue("NAME");
            tblBilling.getColumnModel().getColumn(2).setHeaderValue("TIME");
            tblBilling.getColumnModel().getColumn(3).setHeaderValue("DOCTOR");
            tblBilling.getColumnModel().getColumn(4).setHeaderValue("ID");
    }
    private void FillCurrentDay(){
        try{
            DbConn.SQLQuery = "select ap_apptnumber,ap_name,ap_appttime,ap_doctor,ap_pid from tblappointment where ap_apptdate =? and ap_billed =? ";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, String.valueOf(DbConn.sdfDate.format(todayDate)));
            DbConn.pstmt.setString(2, "YES");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblBilling.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetBillingHeader();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPayable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        txtCard = new javax.swing.JTextField();
        txtCheck = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        txtInsurance = new javax.swing.JTextField();
        lblAmountDue = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblNet = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblBillChecker = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBilling = new javax.swing.JTable();
        dateAppointment = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtInvoice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPayable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Description", "Quantity", "Price", "Value"
            }
        ));
        jScrollPane2.setViewportView(tblPayable);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 630, 390));

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Net");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Cash");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Card");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Check");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Discount");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Insurance");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Amount Due");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Subtotal");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        txtCash.setText("0.000");
        txtCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCashKeyReleased(evt);
            }
        });
        jPanel4.add(txtCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 110, -1));

        txtCard.setText("0.000");
        txtCard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCardKeyReleased(evt);
            }
        });
        jPanel4.add(txtCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 110, -1));

        txtCheck.setText("0.000");
        txtCheck.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCheckKeyReleased(evt);
            }
        });
        jPanel4.add(txtCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 110, -1));

        txtDiscount.setText("0.000");
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
        });
        jPanel4.add(txtDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 110, -1));

        txtInsurance.setText("0.000");
        txtInsurance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInsuranceKeyReleased(evt);
            }
        });
        jPanel4.add(txtInsurance, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 110, -1));

        lblAmountDue.setForeground(new java.awt.Color(0, 0, 0));
        lblAmountDue.setText("0.0");
        jPanel4.add(lblAmountDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, -1, -1));

        lblSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        lblSubtotal.setText("0.0");
        jPanel4.add(lblSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        lblNet.setForeground(new java.awt.Color(0, 0, 0));
        lblNet.setText("0.0");
        jPanel4.add(lblNet, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 330, 30));

        lblBillChecker.setBackground(new java.awt.Color(153, 0, 0));
        lblBillChecker.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblBillChecker.setForeground(new java.awt.Color(255, 255, 255));
        lblBillChecker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBillChecker.setOpaque(true);
        jPanel4.add(lblBillChecker, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 90, 150));

        jPanel6.setBackground(new java.awt.Color(214, 214, 194));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Process.png"))); // NOI18N
        jButton1.setText("PROCESS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 140, 60));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printericon.png"))); // NOI18N
        jButton2.setText("PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 110, 60));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 298, 78));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 330, 390));

        lblStatus.setForeground(new java.awt.Color(0, 0, 0));
        lblStatus.setText("jLabel9");
        jPanel2.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 1020, 460));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblBilling.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBilling);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 240, 390));

        dateAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateAppointmentMouseReleased(evt);
            }
        });
        jPanel3.add(dateAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 240, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 260, 460));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Invoice Number:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        txtInvoice.setText("1");
        jPanel1.add(txtInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 170, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1303, Short.MAX_VALUE)
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

    private void dateAppointmentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateAppointmentMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateAppointmentMouseReleased

    private void tblBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillingMouseClicked
        int row = tblBilling.getSelectedRow();
        int ba = tblBilling.convertRowIndexToModel(row);
        GetApptID = tblBilling.getValueAt(ba, 0).toString();
        try{
            DbConn.SQLQuery = "select tblpatientservices.ps_description,tblpatientservices.ps_qty, tblpatientservices.ps_price,tblpatientservices.ps_value from tblpatientservices where ps_apptid =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, GetApptID);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPayable.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            GetPID = tblBilling.getValueAt(ba, 4).toString();
            GetDoctor = tblBilling.getValueAt(ba, 3).toString();
            GetPatientName = tblBilling.getValueAt(ba, 1).toString();
            DbConn.pstmt.close();
            GetSubTotal();
            tblPayable.getColumnModel().getColumn(0).setHeaderValue("Description");
            tblPayable.getColumnModel().getColumn(1).setHeaderValue("Quantity");
            tblPayable.getColumnModel().getColumn(2).setHeaderValue("Price");
            tblPayable.getColumnModel().getColumn(3).setHeaderValue("Value");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        //Get bill status
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblappointment where ap_apptnumber=?");
            DbConn.pstmt.setString(1, GetApptID);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                GetBillStatus = DbConn.rs.getString("ap_billed");
                lblStatus.setText(GetBillStatus);
                DbConn.pstmt.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblBillingMouseClicked

    private void txtCashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashKeyReleased
        CalculateAmount();
    }//GEN-LAST:event_txtCashKeyReleased

    private void txtCardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCardKeyReleased
        CalculateAmount();
    }//GEN-LAST:event_txtCardKeyReleased

    private void txtCheckKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCheckKeyReleased
        CalculateAmount();
    }//GEN-LAST:event_txtCheckKeyReleased

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased
        CalculateAmount();
    }//GEN-LAST:event_txtDiscountKeyReleased

    private void txtInsuranceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInsuranceKeyReleased
        CalculateAmount();
    }//GEN-LAST:event_txtInsuranceKeyReleased
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DbConn.GetNextInvoice();
        txtInvoice.setText(String.valueOf(DbConn.GetMaxInvoice));
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Update tblappointment set ap_billed=? where ap_apptnumber =?");
            DbConn.pstmt.setString(1, "PROCESSED");
            DbConn.pstmt.setString(2, GetApptID);
            DbConn.pstmt.executeUpdate();
            SaveInvoiceDetail();
            SaveInvoiceHeader();
            JOptionPane.showMessageDialog(this, "BILL SUCCESSFULLY PROCESSED");
            txtInvoice.setText(String.valueOf(DbConn.GetMaxInvoice));
            PrintInvoiceDetail();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PrintInvoiceDetail();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void SaveInvoiceHeader(){
        try{
            DbConn.SQLQuery = "insert into tblinvoiceheader (ih_number,ih_date,ih_pid,ih_apptid,"
                    + "ih_insurance,ih_card,ih_cash,ih_cheque,ih_subtotal,ih_discount,ih_insuranceamount,"
                    + "ih_netamount,ih_doctor,ih_patientname) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, txtInvoice.getText());
            DbConn.pstmt.setString(2, DbConn.sdfDate.format(todayDate));
            DbConn.pstmt.setString(3,GetPID);
            DbConn.pstmt.setString(4,GetApptID);
            DbConn.pstmt.setString(5,txtInsurance.getText());
            DbConn.pstmt.setString(6,txtCard.getText());
            DbConn.pstmt.setString(7,txtCash.getText());
            DbConn.pstmt.setString(8,txtCheck.getText());
            DbConn.pstmt.setString(9,lblSubtotal.getText());
            DbConn.pstmt.setString(10,txtDiscount.getText());
            DbConn.pstmt.setString(11,txtInsurance.getText());
            DbConn.pstmt.setString(12,lblNet.getText());
            DbConn.pstmt.setString(13,GetDoctor);
            DbConn.pstmt.setString(14,GetPatientName);
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void PrintInvoiceDetail(){
//        getDate();
        Map param = new HashMap();
        param.put("invoiceNumber", txtInvoice.getText());
//        param.put("dateTo", sdfDate.format(getToDate));
//        param.put("showStatus", cmbStatus.getSelectedItem().toString());
//        param.put("showClient", cmbClient.getSelectedItem().toString());
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
////            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemrph","root","root");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/dbemrbeta","betapilfer","123456789");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportInvoice.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SaveInvoiceDetail(){
        int rowCount=0;
        while (rowCount != tblPayable.getRowCount()){
            try{
                DbConn.SQLQuery = "insert into tblinvoicedetail (id_number,id_description,id_qty,id_unitprice,id_subtotal,id_discount,id_insurance,"
                        + "id_netamount) values (?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtInvoice.getText());
                DbConn.pstmt.setString(2, tblPayable.getValueAt(rowCount, 0).toString());
                DbConn.pstmt.setString(3, tblPayable.getValueAt(rowCount, 1).toString());
                DbConn.pstmt.setString(4, tblPayable.getValueAt(rowCount, 2).toString());
                DbConn.pstmt.setString(5, tblPayable.getValueAt(rowCount, 3).toString());
                DbConn.pstmt.setString(6, txtDiscount.getText());
                DbConn.pstmt.setString(7, txtInsurance.getText());
                DbConn.pstmt.setString(8,lblNet.getText());
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                rowCount++;
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    private void GetSubTotal(){
        int rowCount =0;
        double GetST = 0.0;
        while (rowCount < tblPayable.getRowCount()){
            GetST += Double.valueOf(tblPayable.getValueAt(rowCount, 3).toString());
            rowCount++;
        }
        lblSubtotal.setText(String.valueOf(GetST));
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
            java.util.logging.Logger.getLogger(frmBilling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmBilling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmBilling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmBilling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmBilling().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dateAppointment;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAmountDue;
    private javax.swing.JLabel lblBillChecker;
    private javax.swing.JLabel lblNet;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JTable tblBilling;
    private javax.swing.JTable tblPayable;
    private javax.swing.JTextField txtCard;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtCheck;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtInsurance;
    private javax.swing.JTextField txtInvoice;
    // End of variables declaration//GEN-END:variables
}
