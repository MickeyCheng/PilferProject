
package main;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.proteanit.sql.DbUtils;
import org.joda.time.LocalTime;

public class frmAppointment extends javax.swing.JFrame {
boolean addRegister,editRegister;
public static int getMaxPID, getMaxApptID,getApptNumber,getApptPID;
SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
DateTimeFormatter jodaTime = DateTimeFormat.forPattern("hh:mm a");
DbConnection DbConn = new DbConnection();
    public frmAppointment() {
        initComponents();
        DbConn.DoConnect();
        fillComboTime();
        fillTable();
        disableTexts();
        fillComboDoctor();
        setDefaultCloseOperation(frmAppointment.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {listenSearchPatient();}

            @Override
            public void removeUpdate(DocumentEvent e) {listenSearchPatient();}

            @Override
            public void changedUpdate(DocumentEvent e) {listenSearchPatient();}
        });
        loadDateBooked();
    }
    private void loadDateBooked(){
        dateBooked.setDate(new java.util.Date());
        btnApptShowActionPerformed(null);
    }
    private void listenSearchPatient(){
        try{
            DbConn.SQLQuery = "Select pd_pid,pd_name,pd_mobile from tblPatientdetails where pd_name like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1,"%" + txtSearch.getText()+ "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblPatient.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblPatient.getColumnModel().getColumn(1).setHeaderValue("NAME");
            tblPatient.getColumnModel().getColumn(2).setHeaderValue("MOBILE");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void fillComboDoctor(){
        cmbDoctor.removeAllItems();
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select * from tblusermaster where um_category =?");
            DbConn.pstmt.setString(1,"Doctor");
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbDoctor.addItem(DbConn.rs.getString("um_name"));
                cmbDoctor1.addItem(DbConn.rs.getString("um_name"));
                cmbDoctor2.addItem(DbConn.rs.getString("um_name"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetHeaderDoctorTable(){
        
        tblDoctor2.getColumnModel().getColumn(0).setHeaderValue("ID");
        tblDoctor2.getColumnModel().getColumn(1).setHeaderValue("TIME");
        tblDoctor2.getColumnModel().getColumn(2).setHeaderValue("NAME");
        tblDoctor2.getColumnModel().getColumn(3).setHeaderValue("CPR");
        tblDoctor2.getColumnModel().getColumn(4).setHeaderValue("MOBILE");
        tblDoctor2.getColumnModel().getColumn(5).setHeaderValue("APPT NUMBER");
    }
    private void fillTableSchedule(){
        String fillTableScheduleQuery ="Select ap_pid,ap_appttime,ap_name,ap_cpr,ap_mobile,ap_apptnumber from tblappointment "
                + "where ap_doctor=? and ap_apptdate=? order by ap_appttime";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fillTableScheduleQuery);
            DbConn.pstmt.setString(1,cmbDoctor1.getSelectedItem().toString());
            DbConn.pstmt.setString(2,sdfDate.format(dateBooked.getDate()));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblDoctor1.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblDoctor1.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblDoctor1.getColumnModel().getColumn(1).setHeaderValue("TIME");
            tblDoctor1.getColumnModel().getColumn(2).setHeaderValue("NAME");
            tblDoctor1.getColumnModel().getColumn(3).setHeaderValue("CPR");
            tblDoctor1.getColumnModel().getColumn(4).setHeaderValue("MOBILE");
            tblDoctor1.getColumnModel().getColumn(5).setHeaderValue("APPT NUMBER");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }   
    }
     private void fillTableScheduleSecond(){
        String fillTableScheduleQuery ="Select ap_pid, ap_appttime,ap_name,ap_cpr,ap_mobile,ap_apptnumber from tblappointment "
                + "where ap_doctor=? and ap_apptdate=? order by ap_appttime";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fillTableScheduleQuery);
            DbConn.pstmt.setString(1,cmbDoctor2.getSelectedItem().toString());
            DbConn.pstmt.setString(2,sdfDate.format(dateBooked.getDate()));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblDoctor2.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblDoctor2.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblDoctor2.getColumnModel().getColumn(1).setHeaderValue("TIME");
            tblDoctor2.getColumnModel().getColumn(2).setHeaderValue("NAME");
            tblDoctor2.getColumnModel().getColumn(3).setHeaderValue("CPR");
            tblDoctor2.getColumnModel().getColumn(4).setHeaderValue("MOBILE");
            tblDoctor2.getColumnModel().getColumn(5).setHeaderValue("APPT NUMBER");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }   
    }
    private void enableTexts(){
        txtCpr.setEditable(true);
        txtMobile.setEditable(true);
        txtName.setEditable(true);
        dateDob.setEnabled(true);
        txtRemarks.setEditable(true);
    }
    private void disableTexts(){
        txtCpr.setEditable(false);
        txtMobile.setEditable(false);
        txtName.setEditable(false);
        dateDob.setEnabled(false);
        txtRemarks.setEditable(false);
    }
    public void doConnect(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbZoneClinic","root","root");
    }catch(SQLException | ClassNotFoundException e){
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    }
    private void fillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("SELECT pd_pid,pd_name,pd_mobile from tblpatientdetails order by pd_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblPatient.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblPatient.getColumnModel().getColumn(1).setHeaderValue("NAME");
            tblPatient.getColumnModel().getColumn(2).setHeaderValue("MOBILE");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void fillComboTime(){       
    LocalTime startTime = new LocalTime("0");    
    DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
    cmbTime.addItem(String.valueOf(fmt.print(startTime)));
    for (int i =0; i<95;i++){
        startTime = startTime.plusMinutes(15);
        cmbTime.addItem(String.valueOf(fmt.print(startTime)));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPatient = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cmbTime = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dateAppointment = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        cmbDoctor = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtCpr = new javax.swing.JTextField();
        txtMobile = new javax.swing.JTextField();
        lblPID = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        dateDob = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDoctor2 = new javax.swing.JTable();
        cmbDoctor2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoctor1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cmbDoctor1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        dateBooked = new com.toedter.calendar.JDateChooser();
        btnApptShow = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnVitalSigns = new javax.swing.JButton();
        btnXray = new javax.swing.JButton();
        btnChiefComplaint = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPatientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPatient);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 410, 140));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("MOBILE:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 50, 20));

        jLabel3.setText("NAME:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, 20));

        jLabel5.setText("CPR:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, 20));

        jLabel7.setText("BIRTHDATE:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 90, 20));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.add(cmbTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 230, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("TIME:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("DATE:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, 30));

        dateAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateAppointmentMouseReleased(evt);
            }
        });
        jPanel5.add(dateAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 230, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("DOCTOR:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 80, 30));

        jPanel5.add(cmbDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 230, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SET APPOINTMENT");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

        jButton1.setText("SAVE APPOINTMENT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 230, -1));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 190));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 370, 210));
        jPanel3.add(txtCpr, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 250, -1));
        jPanel3.add(txtMobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 130, -1));

        lblPID.setText("pid");
        jPanel3.add(lblPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 50, -1));

        jLabel13.setText("PID:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 20));
        jPanel3.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 250, -1));

        dateDob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateDobMouseReleased(evt);
            }
        });
        jPanel3.add(dateDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 250, 30));

        jLabel14.setText("REMARKS:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 90, 20));
        jPanel3.add(txtRemarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 250, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 420));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 410, 440));
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 30));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDoctor2.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDoctor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoctor2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDoctor2);

        jPanel8.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 400, 180));

        jPanel8.add(cmbDoctor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 230, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("DOCTOR:");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 30));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 420, 240));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDoctor1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDoctor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoctor1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDoctor1);

        jPanel9.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 400, 180));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("DOCTOR:");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, 30));

        jPanel9.add(cmbDoctor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 230, 30));

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 420, 240));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("DATE:");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 80, 30));

        dateBooked.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateBookedMouseReleased(evt);
            }
        });
        jPanel7.add(dateBooked, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 230, 30));

        btnApptShow.setText("Show");
        btnApptShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApptShowActionPerformed(evt);
            }
        });
        jPanel7.add(btnApptShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, -1, -1));

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVitalSigns.setText("VITAL SIGNS ENTRY");
        btnVitalSigns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVitalSignsActionPerformed(evt);
            }
        });
        jPanel10.add(btnVitalSigns, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, -1));

        btnXray.setText("XRAY");
        btnXray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXrayActionPerformed(evt);
            }
        });
        jPanel10.add(btnXray, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 190, -1));

        btnChiefComplaint.setText("CHIEF COMPLAINT");
        btnChiefComplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiefComplaintActionPerformed(evt);
            }
        });
        jPanel10.add(btnChiefComplaint, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 420, 260));
        jPanel7.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 420, 260));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 870, 610));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 890, 630));

        jButton2.setText("UNREGISTERED");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1340, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dateAppointmentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateAppointmentMouseReleased

    }//GEN-LAST:event_dateAppointmentMouseReleased

    private void dateBookedMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateBookedMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateBookedMouseReleased

    private void dateDobMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateDobMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateDobMouseReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        getNextPID();
        addRegister = true;
        txtCpr.setText("");
        txtMobile.setText("");
        txtName.setText("");
        txtSearch.setText("");
        txtRemarks.setText("");
        lblPID.setText(String.valueOf(getMaxPID));
        enableTexts();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPatientMouseClicked
        int row = tblPatient.getSelectedRow();
        int ba = tblPatient.convertRowIndexToModel(row);
        String tblClick =tblPatient.getModel().getValueAt(ba, 0).toString();
        String fetchData = "Select * from tblpatientdetails where pd_pid=?";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fetchData);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtMobile.setText(DbConn.rs.getString("pd_mobile"));
                txtName.setText(DbConn.rs.getString("pd_name"));
                lblPID.setText(DbConn.rs.getString("pd_pid"));
                txtRemarks.setText(DbConn.rs.getString("pd_remarks"));
                dateDob.setDate(DbConn.rs.getDate("pd_dob"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }//GEN-LAST:event_tblPatientMouseClicked
    private void getNextAPPTid(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("select * from tblappointment order by ap_apptNumber DESC LIMIT 1");
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                getMaxApptID = DbConn.rs.getInt("ap_apptNumber");
                getMaxApptID++;
            }else{
                getMaxApptID =1;
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            e.getMessage();
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getNextAPPTid();
        if (addRegister == true){
            saveUnregisteredPatient();
        }
        try{
            String insertApptQuery = "INSERT INTO tblappointment (ap_pid,ap_name,ap_cpr,ap_mobile,ap_dob,ap_doctor,"
                    + "ap_apptdate,ap_appttime,ap_remarks,ap_apptNumber) values (?,?,?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(insertApptQuery);
            DbConn.pstmt.setInt(1,Integer.valueOf(lblPID.getText()));
            DbConn.pstmt.setString(2, txtName.getText());
            if (txtCpr.getText().equals("")){
                    DbConn.pstmt.setInt(3, 0);
                }else{
                    DbConn.pstmt.setInt(3, Integer.valueOf(txtCpr.getText()));
                }
            if (txtMobile.getText().equals("")){
                DbConn.pstmt.setInt(4, 0);
            }else{
                DbConn.pstmt.setInt(4, Integer.valueOf(txtMobile.getText()));
            }
            if (dateDob.getDate() == null){
                DbConn.pstmt.setString(5, "1900-01-00");
            }else{
                DbConn.pstmt.setString(5, sdfDate.format(dateDob.getDate()));
            }
            
            DbConn.pstmt.setString(6, cmbDoctor.getSelectedItem().toString());
            DbConn.pstmt.setString(7, sdfDate.format(dateAppointment.getDate()));
            DbConn.pstmt.setString(8,cmbTime.getSelectedItem().toString());
            DbConn.pstmt.setString(9,txtRemarks.getText());
            DbConn.pstmt.setInt(10, getMaxApptID);
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
            JOptionPane.showMessageDialog(this, "APPOINTMENT SUCCESSFULLY BOOKED");
                
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnApptShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApptShowActionPerformed
        fillTableSchedule();
        fillTableScheduleSecond();
    }//GEN-LAST:event_btnApptShowActionPerformed

    private void btnVitalSignsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVitalSignsActionPerformed
        frmVitalSigns obs = new frmVitalSigns();
        obs.setVisible(true);
    }//GEN-LAST:event_btnVitalSignsActionPerformed

    private void tblDoctor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoctor1MouseClicked
        int row = tblDoctor1.getSelectedRow();
        int ba = tblDoctor1.convertRowIndexToModel(row);
        String tblClick = tblDoctor1.getModel().getValueAt(ba, 0).toString();
        String fetchData = "Select * from tblappointment where ap_pid=?";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fetchData);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                getApptNumber = Integer.parseInt(tblDoctor1.getValueAt(ba, 5).toString());
                getApptPID = DbConn.rs.getInt("ap_pid");
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblDoctor1MouseClicked

    private void btnXrayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXrayActionPerformed
        frmXrayTest obj = new frmXrayTest();
        obj.setVisible(true);
    }//GEN-LAST:event_btnXrayActionPerformed

    private void btnChiefComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiefComplaintActionPerformed
        frmChiefComplain obj = new frmChiefComplain();
        obj.setVisible(true);
    }//GEN-LAST:event_btnChiefComplaintActionPerformed

    private void tblDoctor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoctor2MouseClicked
        int row = tblDoctor2.getSelectedRow();
        int ba = tblDoctor2.convertRowIndexToModel(row);
        String tblClick = tblDoctor2.getModel().getValueAt(ba, 0).toString();
        String fetchData = "Select * from tblappointment where ap_pid=?";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fetchData);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                getApptNumber = Integer.parseInt(tblDoctor2.getValueAt(ba, 5).toString());
                getApptPID = DbConn.rs.getInt("ap_pid");
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblDoctor2MouseClicked
    
    private void saveUnregisteredPatient(){
            try{
                String insertPDQuery = "INSERT INTO tblpatientdetails (pd_pid,pd_name,pd_dob,pd_mobile,pd_remarks) "
                        + "values(?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(insertPDQuery);
                DbConn.pstmt.setInt(1, getMaxPID);
                DbConn.pstmt.setString(2, txtName.getText());
               
                if (dateDob.getDate() == null){
                    DbConn.pstmt.setString(3, "1900-01-00");
                }else{
                    DbConn.pstmt.setString(3, sdfDate.format(dateDob.getDate()));
                }
                
                if (txtMobile.getText().equals("")){
                    DbConn.pstmt.setInt(4, 0);
                }else{
                    DbConn.pstmt.setInt(4, Integer.valueOf(txtMobile.getText()));
                }
                DbConn.pstmt.setString(5,txtRemarks.getText());
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                fillTable();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }    
        
    }
    private void getNextPID(){
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblpatientdetails order by pd_pid DESC LIMIT 1");
                DbConn.rs = DbConn.pstmt.executeQuery();
                if (DbConn.rs.next()){
                    getMaxPID = DbConn.rs.getInt(1);
                    getMaxPID++;
                }else{
                    getMaxPID = 1;
                }
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
            java.util.logging.Logger.getLogger(frmAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAppointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApptShow;
    private javax.swing.JButton btnChiefComplaint;
    private javax.swing.JButton btnVitalSigns;
    private javax.swing.JButton btnXray;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbDoctor1;
    private javax.swing.JComboBox<String> cmbDoctor2;
    private javax.swing.JComboBox<String> cmbTime;
    private com.toedter.calendar.JDateChooser dateAppointment;
    private com.toedter.calendar.JDateChooser dateBooked;
    private com.toedter.calendar.JDateChooser dateDob;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPID;
    private javax.swing.JTable tblDoctor1;
    private javax.swing.JTable tblDoctor2;
    private javax.swing.JTable tblPatient;
    private javax.swing.JTextField txtCpr;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
