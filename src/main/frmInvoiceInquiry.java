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
Date TodayDate = new Date();
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
        tblInvoice.setDefaultEditor(Object.class, null);
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
            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblinvoiceheader "
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
//            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblinvoiceheader "
//                    + "where ih_date between ? and ? and ih_doctor =? and ih_pid like ? or ih_patientname like?";
 DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblinvoiceheader "
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
            DbConn.SQLQuery = "Select ih_number,ih_date,ih_pid,ih_patientname,ih_subtotal,ih_netamount,ih_doctor from tblinvoiceheader where ih_date between ? and ? order by ih_number ASC";
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
            tblInvoice.getColumnModel().getColumn(i).setPreferredWidth(100);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        btnViewInvoice = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        txtCard = new javax.swing.JTextField();
        txtCheck = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        txtInsurance = new javax.swing.JTextField();
        lblAmountDue = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblNet = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invoice Inquiry");

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
        jPanel2.add(fromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 140, 30));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("From:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, 30));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("To:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, 30));

        toDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                toDateMouseReleased(evt);
            }
        });
        jPanel2.add(toDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 140, 30));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Attending Doctor:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, -1, -1));
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 290, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Name or PID:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cmbDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 180, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 850, 110));

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
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoice);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 500, 330));

        btnViewInvoice.setBackground(new java.awt.Color(255, 255, 255));
        btnViewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ReportsIcon.png"))); // NOI18N
        btnViewInvoice.setText("View Invoice");
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });
        jPanel1.add(btnViewInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 470, -1, 50));

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Net");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Cash");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Card");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Check");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Discount");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Insurance");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Amount Due");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Subtotal");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

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
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 330, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Process.png"))); // NOI18N
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 140, 60));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 80, 110));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 330, 330));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemrph","root","root");
//            DbConn.conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/dbemrbeta","betapilfer","123456789");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportInvoice.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewInvoiceActionPerformed
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
        CalculateAmount();
        int selectItem = JOptionPane.showConfirmDialog(this, "Are you sure you want to edit this invoice?","Invoice Editing",JOptionPane.YES_NO_OPTION);
        if (selectItem == JOptionPane.YES_OPTION){
            SaveInvoiceHeader();
            SaveInvoiceDetail();
            JOptionPane.showMessageDialog(this, "INVOICE EDITED");
        }
        txtCard.setText("0.000");
        txtCash.setText("0.000");
        txtCheck.setText("0.000");
        txtDiscount.setText("0.000");
        txtInsurance.setText("0.000");
        lblAmountDue.setText("0.000");
        lblNet.setText("0.000");
        lblSubtotal.setText("0.000");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked
        int row = tblInvoice.getSelectedRow();
        int ba = tblInvoice.convertRowIndexToModel(row);
        String tblClick = tblInvoice.getValueAt(ba, 0).toString();
        lblSubtotal.setText(tblInvoice.getValueAt(ba, 4).toString());
        try{
            DbConn.SQLQuery = "select * from tblinvoiceheader where ih_number =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtCard.setText(DbConn.rs.getString("ih_card"));
                txtCash.setText(DbConn.rs.getString("ih_cash"));
                txtCheck.setText(DbConn.rs.getString("ih_cheque"));
                txtDiscount.setText(DbConn.rs.getString("ih_discount"));
                txtInsurance.setText(DbConn.rs.getString("ih_insurance"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblInvoiceMouseClicked
    private void SaveInvoiceHeader(){
        int row = tblInvoice.getSelectedRow();
        int ba = tblInvoice.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery = "update tblinvoiceheader set ih_insurance =?,ih_card=?,ih_cash=?,ih_cheque=?,"
                    + "ih_subtotal=?,ih_discount=?,ih_insuranceamount=?,"
                    + "ih_netamount=?,ih_moduser=?,ih_moddate=? where ih_number=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, txtInsurance.getText());
            DbConn.pstmt.setString(2, txtCard.getText());
            DbConn.pstmt.setString(3,txtCash.getText());
            DbConn.pstmt.setString(4, txtCheck.getText());
            DbConn.pstmt.setString(5,lblSubtotal.getText());
            DbConn.pstmt.setString(6,txtDiscount.getText());
            DbConn.pstmt.setString(7,txtInsurance.getText());
            DbConn.pstmt.setString(8,lblNet.getText());
            DbConn.pstmt.setString(9,DbConn.LoggedUserName);
            DbConn.pstmt.setString(10,DbConn.sdfDate.format(TodayDate));
            DbConn.pstmt.setString(11,tblInvoice.getValueAt(ba, 0).toString());
            DbConn.pstmt.executeUpdate();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SaveInvoiceDetail(){
        int row = tblInvoice.getSelectedRow();
        int ba = tblInvoice.convertRowIndexToModel(row);    
        try{
                DbConn.SQLQuery = "update tblinvoicedetail set id_discount=?,id_insurance=?,id_netamount=?,"
                        + "id_moduser=?,id_moddate=? where id_number=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtDiscount.getText());
                DbConn.pstmt.setString(2, txtInsurance.getText());
                DbConn.pstmt.setString(3, lblNet.getText());
                DbConn.pstmt.setString(4, DbConn.LoggedUserName);
                DbConn.pstmt.setString(5, DbConn.sdfDate.format(TodayDate));
                DbConn.pstmt.setString(6, tblInvoice.getValueAt(ba, 0).toString());
                DbConn.pstmt.executeUpdate();
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAmountDue;
    private javax.swing.JLabel lblNet;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JTable tblInvoice;
    private com.toedter.calendar.JDateChooser toDate;
    private javax.swing.JTextField txtCard;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtCheck;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtInsurance;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
