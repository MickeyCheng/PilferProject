
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static main.DbConnection.LoggedUserName;
public class frmUserSecurity extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
String GetCategory,GetAdmin,GetUserId;
String checkExist = "insert";
String RegView,RegInsert,RegDelete,RegEdit,RegPrint,ApptView,ApptInsert,ApptEdit,ApptDelete,ApptPrint,DocView,DocInsert,
        DocEdit,DocDelete,DocPrint,BillView,BillProcess,BillPrint,BillInvoiceGen,InqPatient,InqAppt,InqSales,InqInvoice,
        MasterGen,MasterUser,MasterSecurity,MasterDrug,MasterTreatment,MasterICD,MasterInsurance,ReportMedCert;
    public frmUserSecurity() {
        initComponents();
        DbConn.DoConnect();
        FillComboUsers();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(frmUserSecurity.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                DbConn.MasterSecurityForm = false;
            }
        });
        UncheckAll();
        cmbUserName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckAccess();
                CheckDoctor();
                CheckAdmin();
            }
        });
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    private void UncheckAll(){
        chkRegAll.setSelected(false);
        chkApptAll.setSelected(false);
        chkBillingAll.setSelected(false);
        chkDoctorAll.setSelected(false);
        chkInqAll.setSelected(false);
        chkMastersAll.setSelected(false);
        chkReportsAll.setSelected(false);
    }
    public void CheckUserAccessLocal(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblusersecurity where us_name =?");
            DbConn.pstmt.setString(1, cmbUserName.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                RegView = DbConn.rs.getString("us_regview");
                RegInsert = DbConn.rs.getString("us_reginsert");
                RegEdit= DbConn.rs.getString("us_regedit");
                RegDelete = DbConn.rs.getString("us_regdelete");
                RegPrint= DbConn.rs.getString("us_regprint");
                
                ApptView= DbConn.rs.getString("us_apptview");
                ApptInsert = DbConn.rs.getString("us_apptinsert");
                ApptEdit = DbConn.rs.getString("us_apptedit");
                ApptDelete = DbConn.rs.getString("us_apptdelete");
                ApptPrint = DbConn.rs.getString("us_apptprint");
                
                DocView = DbConn.rs.getString("us_docview");
                DocInsert = DbConn.rs.getString("us_docinsert");
                DocEdit= DbConn.rs.getString("us_docedit");
                DocDelete = DbConn.rs.getString("us_docdelete");
                DocPrint = DbConn.rs.getString("us_docprint");
                
                BillView = DbConn.rs.getString("us_billview");
                BillProcess = DbConn.rs.getString("us_billprocess");
                BillPrint = DbConn.rs.getString("us_billprint");
                BillInvoiceGen = DbConn.rs.getString("us_billinvoicegen");
                
                InqPatient = DbConn.rs.getString("us_inqpatient");
                InqAppt = DbConn.rs.getString("us_inqappointment");
                InqSales = DbConn.rs.getString("us_inqsales");
                InqInvoice = DbConn.rs.getString("us_inqinvoice");
                
                MasterGen = DbConn.rs.getString("us_mastergen");
                MasterUser = DbConn.rs.getString("us_masteruser");
                MasterSecurity = DbConn.rs.getString("us_mastersecurity");
                MasterDrug = DbConn.rs.getString("us_masterdrug");
                MasterTreatment = DbConn.rs.getString("us_mastertreatment");
                MasterInsurance = DbConn.rs.getString("us_masterinsurance");
                MasterICD = DbConn.rs.getString("us_mastericd");
                ReportMedCert = DbConn.rs.getString("us_reportmedcert");
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void CheckAccess(){
        CheckUserAccessLocal();
        //registration
        if (RegView.equals("Y")){
            chkRegView.setSelected(true);
        }else{
            chkRegView.setSelected(false);
        }
        
        if (RegDelete.equals("Y")){
            chkRegDelete.setSelected(true);
        }else{
            chkRegDelete.setSelected(false);
        }
        if (RegInsert.equals("Y")){
            chkRegInsert.setSelected(true);
        }else{
            chkRegInsert.setSelected(false);        
        }
        if (RegPrint.equals("Y")){
            chkRegPrint.setSelected(true);
        }else{
            chkRegPrint.setSelected(false);
        
        }
        if (RegEdit.equals("Y")){
            chkRegEdit.setSelected(true);
        }else{
            chkRegEdit.setSelected(false);
        
        }
        
        //doctor
        if (DocView.equals("Y")){
            chkDoctorView.setSelected(true);
        }else{
            chkDoctorView.setSelected(false);
        
        }
        if (DocDelete.equals("Y")){
            chkDoctorDelete.setSelected(true);
        }else{
            chkDoctorDelete.setSelected(false);
        
        }
        if (DocEdit.equals("Y")){
            chkDoctorEdit.setSelected(true);
        }else{
            chkDoctorEdit.setSelected(false);
        
        }
        if (DocInsert.equals("Y")){
            chkDoctorInsert.setSelected(true);
        }else{
            chkDoctorInsert.setSelected(false);
        
        }
        if (DocPrint.equals("Y")){
            chkDoctorPrint.setSelected(true);
        }else{
            chkDoctorPrint.setSelected(false);
        
        }
        //appointment
        if (ApptPrint.equals("Y")){
            chkApptPrint.setSelected(true);
        }else{
            chkApptPrint.setSelected(false);
        
        }
        if (ApptDelete.equals("Y")){
            chkApptDelete.setSelected(true);
        }else{
            chkApptDelete.setSelected(false);
        
        }
        if (ApptEdit.equals("Y")){
            chkApptEdit.setSelected(true);
        }else{
            chkApptEdit.setSelected(false);
        
        }
        if (ApptInsert.equals("Y")){
            chkApptInsert.setSelected(true);
        }else{
            chkApptInsert.setSelected(false);
        
        }
        if (ApptView.equals("Y")){
            chkApptView.setSelected(true);
        }else{
            chkApptView.setSelected(false);
        
        }
        //bill
        if (BillInvoiceGen.equals("Y")){
            chkBillingInvoice.setSelected(true);
        }else{
            chkBillingInvoice.setSelected(false);
        
        }
        if (BillPrint.equals("Y")){
            chkBillingPrint.setSelected(true);
        }else{
            chkBillingPrint.setSelected(false);
        
        }
        if (BillProcess.equals("Y")){
            chkBillingProcess.setSelected(true);
        }else{
            chkBillingProcess.setSelected(false);
        
        }
        if (BillView.equals("Y")){
            chkBillingView.setSelected(true);
        }else{
            chkBillingView.setSelected(false);
        
        }
        //inquiry
        if (InqAppt.equals("Y")){
            chkInqAppointment.setSelected(true);
        }else{
            chkInqAppointment.setSelected(false);
        
        }
        if (InqInvoice.equals("Y")){
            chkInqInvoice.setSelected(true);
        }else{
            chkInqInvoice.setSelected(false);
        
        }
        if (InqPatient.equals("Y")){
            chkInqPatient.setSelected(true);
        }else{
            chkInqPatient.setSelected(false);
        
        }
        if (InqSales.equals("Y")){
            chkInqSales.setSelected(true);
        }else{
            chkInqSales.setSelected(false);
        
        }
        //master
        if (MasterDrug.equals("Y")){
            chkMastersDrug.setSelected(true);
        }else{
            chkMastersDrug.setSelected(false);
        
        }
        if (MasterGen.equals("Y")){
            chkMastersGeneral.setSelected(true);
        }else{
            chkMastersGeneral.setSelected(false);
        
        }
        if (MasterICD.equals("Y")){
            chkMastersICD.setSelected(true);
        }else{
            chkMastersICD.setSelected(false);
        
        }
        if (MasterInsurance.equals("Y")){
            chkMastersInsurance.setSelected(true);
        }else{
            chkMastersInsurance.setSelected(false);
        
        }
        if (MasterTreatment.equals("Y")){
            chkMastersTreatment.setSelected(true);
        }else{
            chkMastersTreatment.setSelected(false);
        
        }
        if (MasterUser.equals("Y")){
            chkMastersUser.setSelected(true);
        }else{
            chkMastersUser.setSelected(false);
        
        }
        if (MasterSecurity.equals("Y")){
            chkMastersUserSecurity.setSelected(true);
        }else{
            chkMastersUserSecurity.setSelected(false);
        
        }
        //reports
        if (ReportMedCert.equals("Y")){
            chkReportMedCert.setSelected(true);
        }else{
            chkReportMedCert.setSelected(false);
        
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
        cmbUserName = new javax.swing.JComboBox<>();
        chkRegAll = new javax.swing.JCheckBox();
        chkRegView = new javax.swing.JCheckBox();
        chkRegInsert = new javax.swing.JCheckBox();
        chkRegEdit = new javax.swing.JCheckBox();
        chkRegDelete = new javax.swing.JCheckBox();
        chkRegPrint = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        chkApptView = new javax.swing.JCheckBox();
        chkApptInsert = new javax.swing.JCheckBox();
        chkApptEdit = new javax.swing.JCheckBox();
        chkApptDelete = new javax.swing.JCheckBox();
        chkApptPrint = new javax.swing.JCheckBox();
        chkApptAll = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        chkDoctorView = new javax.swing.JCheckBox();
        chkDoctorInsert = new javax.swing.JCheckBox();
        chkDoctorEdit = new javax.swing.JCheckBox();
        chkDoctorDelete = new javax.swing.JCheckBox();
        chkDoctorPrint = new javax.swing.JCheckBox();
        chkDoctorAll = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        chkBillingView = new javax.swing.JCheckBox();
        chkBillingProcess = new javax.swing.JCheckBox();
        chkBillingInvoice = new javax.swing.JCheckBox();
        chkBillingAll = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        chkInqPatient = new javax.swing.JCheckBox();
        chkInqAppointment = new javax.swing.JCheckBox();
        chkInqSales = new javax.swing.JCheckBox();
        chkInqInvoice = new javax.swing.JCheckBox();
        chkInqAll = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        chkMastersGeneral = new javax.swing.JCheckBox();
        chkMastersUser = new javax.swing.JCheckBox();
        chkMastersDrug = new javax.swing.JCheckBox();
        chkMastersTreatment = new javax.swing.JCheckBox();
        chkMastersICD = new javax.swing.JCheckBox();
        chkMastersAll = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        chkMastersInsurance = new javax.swing.JCheckBox();
        chkBillingPrint = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        chkMastersUserSecurity = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        chkReportsAll = new javax.swing.JCheckBox();
        chkReportMedCert = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Security");

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbUserName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbUserName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cmbUserNameMouseReleased(evt);
            }
        });
        jPanel2.add(cmbUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

        chkRegAll.setForeground(new java.awt.Color(0, 0, 0));
        chkRegAll.setText("All");
        chkRegAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkRegAllMouseReleased(evt);
            }
        });
        jPanel2.add(chkRegAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        chkRegView.setForeground(new java.awt.Color(0, 0, 0));
        chkRegView.setText("View");
        jPanel2.add(chkRegView, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        chkRegInsert.setForeground(new java.awt.Color(0, 0, 0));
        chkRegInsert.setText("Insert");
        jPanel2.add(chkRegInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        chkRegEdit.setForeground(new java.awt.Color(0, 0, 0));
        chkRegEdit.setText("Edit");
        jPanel2.add(chkRegEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, -1));

        chkRegDelete.setForeground(new java.awt.Color(0, 0, 0));
        chkRegDelete.setText("Delete");
        jPanel2.add(chkRegDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));

        chkRegPrint.setForeground(new java.awt.Color(0, 0, 0));
        chkRegPrint.setText("Print");
        jPanel2.add(chkRegPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Registration");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 46, 160, 40));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 20, 190));

        chkApptView.setForeground(new java.awt.Color(0, 0, 0));
        chkApptView.setText("View");
        jPanel2.add(chkApptView, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));

        chkApptInsert.setForeground(new java.awt.Color(0, 0, 0));
        chkApptInsert.setText("Insert");
        jPanel2.add(chkApptInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, -1, -1));

        chkApptEdit.setForeground(new java.awt.Color(0, 0, 0));
        chkApptEdit.setText("Edit");
        jPanel2.add(chkApptEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        chkApptDelete.setForeground(new java.awt.Color(0, 0, 0));
        chkApptDelete.setText("Delete");
        jPanel2.add(chkApptDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, -1, -1));

        chkApptPrint.setForeground(new java.awt.Color(0, 0, 0));
        chkApptPrint.setText("Print");
        jPanel2.add(chkApptPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, -1, -1));

        chkApptAll.setForeground(new java.awt.Color(0, 0, 0));
        chkApptAll.setText("All");
        chkApptAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkApptAllMouseReleased(evt);
            }
        });
        jPanel2.add(chkApptAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Appointment");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 160, 40));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 20, 190));

        chkDoctorView.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorView.setText("View");
        jPanel2.add(chkDoctorView, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        chkDoctorInsert.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorInsert.setText("Insert");
        jPanel2.add(chkDoctorInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, -1, -1));

        chkDoctorEdit.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorEdit.setText("Edit");
        jPanel2.add(chkDoctorEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, -1, -1));

        chkDoctorDelete.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorDelete.setText("Delete");
        jPanel2.add(chkDoctorDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, -1, -1));

        chkDoctorPrint.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorPrint.setText("Print");
        jPanel2.add(chkDoctorPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, -1, -1));

        chkDoctorAll.setForeground(new java.awt.Color(0, 0, 0));
        chkDoctorAll.setText("All");
        chkDoctorAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkDoctorAllMouseReleased(evt);
            }
        });
        jPanel2.add(chkDoctorAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Doctor");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 160, 40));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 20, 190));

        chkBillingView.setForeground(new java.awt.Color(0, 0, 0));
        chkBillingView.setText("View");
        jPanel2.add(chkBillingView, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, -1, -1));

        chkBillingProcess.setForeground(new java.awt.Color(0, 0, 0));
        chkBillingProcess.setText("Process");
        jPanel2.add(chkBillingProcess, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, -1, -1));

        chkBillingInvoice.setForeground(new java.awt.Color(0, 0, 0));
        chkBillingInvoice.setText("Invoice Generation");
        jPanel2.add(chkBillingInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, -1, -1));

        chkBillingAll.setForeground(new java.awt.Color(0, 0, 0));
        chkBillingAll.setText("All");
        chkBillingAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkBillingAllMouseReleased(evt);
            }
        });
        jPanel2.add(chkBillingAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Billing");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 160, 40));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 60, 20, 190));

        chkInqPatient.setForeground(new java.awt.Color(0, 0, 0));
        chkInqPatient.setText("Patient Inquiry");
        jPanel2.add(chkInqPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, -1));

        chkInqAppointment.setForeground(new java.awt.Color(0, 0, 0));
        chkInqAppointment.setText("Appointment Inquiry");
        jPanel2.add(chkInqAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, -1, -1));

        chkInqSales.setForeground(new java.awt.Color(0, 0, 0));
        chkInqSales.setText("Sales Inquiry");
        jPanel2.add(chkInqSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, -1, -1));

        chkInqInvoice.setForeground(new java.awt.Color(0, 0, 0));
        chkInqInvoice.setText("Invoice");
        jPanel2.add(chkInqInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, -1, -1));

        chkInqAll.setForeground(new java.awt.Color(0, 0, 0));
        chkInqAll.setText("All");
        chkInqAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkInqAllMouseReleased(evt);
            }
        });
        jPanel2.add(chkInqAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Inquiry");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 160, 40));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 20, 230));

        chkMastersGeneral.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersGeneral.setText("General Master");
        jPanel2.add(chkMastersGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

        chkMastersUser.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersUser.setText("User Master");
        jPanel2.add(chkMastersUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        chkMastersDrug.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersDrug.setText("Drug Master");
        jPanel2.add(chkMastersDrug, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, -1, -1));

        chkMastersTreatment.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersTreatment.setText("Treatment Master");
        jPanel2.add(chkMastersTreatment, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 460, -1, -1));

        chkMastersICD.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersICD.setText("ICD10 Master");
        jPanel2.add(chkMastersICD, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 520, -1, -1));

        chkMastersAll.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersAll.setText("All");
        chkMastersAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkMastersAllMouseReleased(evt);
            }
        });
        chkMastersAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMastersAllActionPerformed(evt);
            }
        });
        jPanel2.add(chkMastersAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Masters");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 160, 40));

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, 20, 230));

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 310, 20, 230));

        chkMastersInsurance.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersInsurance.setText("Insurance Master");
        jPanel2.add(chkMastersInsurance, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, -1, -1));

        chkBillingPrint.setForeground(new java.awt.Color(0, 0, 0));
        chkBillingPrint.setText("Print");
        jPanel2.add(chkBillingPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SaveIcon.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 450, -1, 70));

        chkMastersUserSecurity.setForeground(new java.awt.Color(0, 0, 0));
        chkMastersUserSecurity.setText("User Security");
        jPanel2.add(chkMastersUserSecurity, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Reports");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 160, 40));

        chkReportsAll.setForeground(new java.awt.Color(0, 0, 0));
        chkReportsAll.setText("All");
        chkReportsAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkReportsAllMouseReleased(evt);
            }
        });
        chkReportsAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkReportsAllActionPerformed(evt);
            }
        });
        jPanel2.add(chkReportsAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, -1, -1));

        chkReportMedCert.setForeground(new java.awt.Color(0, 0, 0));
        chkReportMedCert.setText("Medical Certificate");
        jPanel2.add(chkReportMedCert, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 770, 590));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("User Security");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addContainerGap(673, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 770, 60));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkMastersAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMastersAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMastersAllActionPerformed
    private void CheckAdmin(){
        CheckCredentials();
        if (GetAdmin.equals("Y")){
            chkApptAll.setSelected(true);
            chkDoctorAll.setSelected(true);
            chkRegAll.setSelected(true);
            chkBillingAll.setSelected(true);
            chkInqAll.setSelected(true);
            chkMastersAll.setSelected(true);
        }else{
            chkApptAll.setSelected(false);
            chkDoctorAll.setSelected(false);
            chkRegAll.setSelected(false);
            chkBillingAll.setSelected(false);
            chkInqAll.setSelected(false);
            chkMastersAll.setSelected(false);
        
        }
    }
    private void CheckCredentials(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblusermaster where um_name=?");
            DbConn.pstmt.setString(1, cmbUserName.getSelectedItem().toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                GetCategory = DbConn.rs.getString("um_category");
                GetAdmin = DbConn.rs.getString("um_administrator");
                GetUserId = DbConn.rs.getString("um_id");
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void CheckDoctor(){
       CheckCredentials();
        if (!GetCategory.equals("Doctor")){
            chkDoctorAll.setEnabled(false);
            chkDoctorDelete.setEnabled(false);
            chkDoctorEdit.setEnabled(false);
            chkDoctorInsert.setEnabled(false);
            chkDoctorPrint.setEnabled(false);
            chkDoctorView.setEnabled(false);
            
            chkDoctorAll.setSelected(false);
            chkDoctorDelete.setSelected(false);
            chkDoctorEdit.setSelected(false);
            chkDoctorInsert.setSelected(false);
            chkDoctorPrint.setSelected(false);
            chkDoctorView.setSelected(false);
        }else{
            chkDoctorAll.setEnabled(true);
            chkDoctorDelete.setEnabled(true);
            chkDoctorEdit.setEnabled(true);
            chkDoctorInsert.setEnabled(true);
            chkDoctorPrint.setEnabled(true);
            chkDoctorView.setEnabled(true);
            
        }
    }
    private void FillComboUsers(){
        cmbUserName.removeAllItems();
        try{
            DbConn.SQLQuery = "Select * from tblusermaster order by um_name";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs =DbConn.pstmt.executeQuery();
            while(DbConn.rs.next()){
                cmbUserName.addItem(DbConn.rs.getString("um_name"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void chkRegAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkRegAllMouseReleased
        if (chkRegAll.isSelected()){
            chkRegDelete.setSelected(true);
            chkRegEdit.setSelected(true);
            chkRegInsert.setSelected(true);
            chkRegPrint.setSelected(true);
            chkRegView.setSelected(true);
        }else{
            chkRegDelete.setSelected(false);
            chkRegEdit.setSelected(false);
            chkRegInsert.setSelected(false);
            chkRegPrint.setSelected(false);
            chkRegView.setSelected(false);
        }
    }//GEN-LAST:event_chkRegAllMouseReleased

    private void chkApptAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkApptAllMouseReleased
        if (chkApptAll.isSelected()){
            chkApptDelete.setSelected(true);
            chkApptEdit.setSelected(true);
            chkApptInsert.setSelected(true);
            chkApptPrint.setSelected(true);
            chkApptView.setSelected(true);
        }else{
            chkApptDelete.setSelected(false);
            chkApptEdit.setSelected(false);
            chkApptInsert.setSelected(false);
            chkApptPrint.setSelected(false);
            chkApptView.setSelected(false);
        }
    }//GEN-LAST:event_chkApptAllMouseReleased

    private void chkDoctorAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkDoctorAllMouseReleased
        if (chkDoctorAll.isSelected()){
            chkDoctorDelete.setSelected(true);
            chkDoctorEdit.setSelected(true);
            chkDoctorInsert.setSelected(true);
            chkDoctorPrint.setSelected(true);
            chkDoctorView.setSelected(true);
        }else{
            chkDoctorDelete.setSelected(false);
            chkDoctorEdit.setSelected(false);
            chkDoctorInsert.setSelected(false);
            chkDoctorPrint.setSelected(false);
            chkDoctorView.setSelected(false);
        }
    }//GEN-LAST:event_chkDoctorAllMouseReleased

    private void chkBillingAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkBillingAllMouseReleased
        if (chkBillingAll.isSelected()){
            chkBillingInvoice.setSelected(true);
            chkBillingPrint.setSelected(true);
            chkBillingProcess.setSelected(true);
            chkBillingView.setSelected(true);
        }else{
            chkBillingInvoice.setSelected(false);
            chkBillingPrint.setSelected(false);
            chkBillingProcess.setSelected(false);
            chkBillingView.setSelected(false);
        
        }
    }//GEN-LAST:event_chkBillingAllMouseReleased

    private void chkInqAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkInqAllMouseReleased
        if(chkInqAll.isSelected()){
            chkInqAppointment.setSelected(true);
            chkInqInvoice.setSelected(true);
            chkInqPatient.setSelected(true);
            chkInqSales.setSelected(true);
        }else{
            chkInqAppointment.setSelected(false);
            chkInqInvoice.setSelected(false);
            chkInqPatient.setSelected(false);
            chkInqSales.setSelected(false);
        
        }
    }//GEN-LAST:event_chkInqAllMouseReleased

    private void chkMastersAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMastersAllMouseReleased
        if (chkMastersAll.isSelected()){
            chkMastersDrug.setSelected(true);
            chkMastersGeneral.setSelected(true);
            chkMastersICD.setSelected(true);
            chkMastersInsurance.setSelected(true);
            chkMastersTreatment.setSelected(true);
            chkMastersUser.setSelected(true);
            chkMastersUserSecurity.setSelected(true);
        }else{
            chkMastersDrug.setSelected(false);
            chkMastersGeneral.setSelected(false);
            chkMastersICD.setSelected(false);
            chkMastersInsurance.setSelected(false);
            chkMastersTreatment.setSelected(false);
            chkMastersUser.setSelected(false);
            chkMastersUserSecurity.setSelected(false);
        }
    }//GEN-LAST:event_chkMastersAllMouseReleased

    private void cmbUserNameMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbUserNameMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUserNameMouseReleased
    private void RegCheckBox(){
         if (chkRegAll.isSelected()){
            RegDelete = "Y";
            RegEdit = "Y";
            RegInsert= "Y";
            RegPrint= "Y";
            RegView= "Y";
        }
        else{
            if (chkRegDelete.isSelected()){
                RegDelete = "Y";
            }else{
                RegDelete = "N";
            }
            if (chkRegEdit.isSelected()){
                RegEdit = "Y";
            }else{
                RegEdit = "N";
            }
            if (chkRegInsert.isSelected()){
                RegInsert = "Y";
            }else{
                RegInsert = "N";
            }
            if (chkRegPrint.isSelected()){
                RegPrint = "Y";
            }else{
                RegPrint = "N";
            }
            if (chkRegView.isSelected()){
                RegView = "Y";
            }else{
                RegView = "N";
            }
        }
    }
    private void ApptCheckBox(){
        if (chkApptAll.isSelected()){
            ApptDelete = "Y";
            ApptEdit = "Y";
            ApptInsert= "Y";
            ApptPrint= "Y";
            ApptView= "Y";
        }
        else{
            if (chkApptDelete.isSelected()){
                ApptDelete = "Y";
            }else{
                ApptDelete = "N";
            }
            if (chkApptEdit.isSelected()){
                ApptEdit = "Y";
            }else{
                ApptEdit = "N";
            }
            if (chkApptInsert.isSelected()){
                ApptInsert = "Y";
            }else{
                ApptInsert = "N";
            }
            if (chkApptPrint.isSelected()){
                ApptPrint = "Y";
            }else{
                ApptPrint = "N";
            }
            if (chkApptView.isSelected()){
                ApptView = "Y";
            }else{
                ApptView = "N";
            }
        }        
    }
    private void DocCheckBox(){
         if (chkDoctorAll.isSelected()){
            DocDelete = "Y";
            DocEdit = "Y";
            DocInsert= "Y";
            DocPrint= "Y";
            DocView= "Y";
        }
        else{
            if (chkDoctorDelete.isSelected()){
                DocDelete = "Y";
            }else{
                DocDelete = "N";
            }
            if (chkDoctorEdit.isSelected()){
                DocEdit = "Y";
            }else{
                DocEdit = "N";
            }
            if (chkDoctorInsert.isSelected()){
                DocInsert = "Y";
            }else{
                DocInsert = "N";
            }
            if (chkDoctorPrint.isSelected()){
                DocPrint = "Y";
            }else{
                DocPrint = "N";
            }
            if (chkDoctorView.isSelected()){
                DocView = "Y";
            }else{
                DocView = "N";
            }
        }
    }
    private void BillCheckBox(){
        if (chkBillingAll.isSelected()){
            BillView = "Y";
            BillPrint = "Y";
            BillProcess= "Y";
            BillInvoiceGen= "Y";
        }
        else{
            if (chkBillingInvoice.isSelected()){
                BillInvoiceGen = "Y";
            }else{
                BillInvoiceGen = "N";
            }
            if (chkBillingPrint.isSelected()){
                BillPrint = "Y";
            }else{
                BillPrint = "N";
            }
            if (chkBillingProcess.isSelected()){
                BillProcess = "Y";
            }else{
                BillProcess = "N";
            }
            if (chkBillingView.isSelected()){
                BillView = "Y";
            }else{
                BillView = "N";
            }
            
        }
    }
    private void InquiryCheckBox(){
        if (chkInqAll.isSelected()){
            InqAppt = "Y";
            InqInvoice = "Y";
            InqPatient= "Y";
            InqSales= "Y";
        }
        else{
            if (chkInqAppointment.isSelected()){
                InqAppt = "Y";
            }else{
                InqAppt = "N";
            }
            if (chkInqInvoice.isSelected()){
                InqInvoice = "Y";
            }else{
                InqInvoice = "N";
            }
            if (chkInqPatient.isSelected()){
                InqPatient = "Y";
            }else{
                InqPatient = "N";
            }
            if (chkInqSales.isSelected()){
                InqSales = "Y";
            }else{
                InqSales = "N";
            }
            
        }
    }
    private void MasterCheckBox(){
         if (chkMastersAll.isSelected()){
            MasterDrug = "Y";
            MasterGen = "Y";
            MasterICD= "Y";
            MasterInsurance= "Y";
            MasterSecurity= "Y";
            MasterTreatment= "Y";
            MasterUser= "Y";
        }
        else{
            if (chkMastersDrug.isSelected()){
                MasterDrug = "Y";
            }else{
                MasterDrug = "N";
            }
            if (chkMastersGeneral.isSelected()){
                MasterGen = "Y";
            }else{
                MasterGen = "N";
            }
            if (chkMastersICD.isSelected()){
                MasterICD = "Y";
            }else{
                MasterICD = "N";
            }
            if (chkMastersInsurance.isSelected()){
                MasterInsurance = "Y";
            }else{
                MasterInsurance = "N";
            }
            if (chkMastersUserSecurity.isSelected()){
                MasterSecurity = "Y";
            }else{
                MasterSecurity = "N";
            }
            if (chkMastersTreatment.isSelected()){
                MasterTreatment = "Y";
            }else{
                MasterTreatment = "N";
            }
            if (chkMastersUser.isSelected()){
                MasterUser = "Y";
            }else{
                MasterUser = "N";
            }
        }
    }
    private void CheckReports(){
        if (chkReportsAll.isSelected()){
            ReportMedCert = "Y";
        }
        else{
            if (chkReportMedCert.isSelected()){
                ReportMedCert = "Y";
            }else{
                ReportMedCert = "N";
            }
        }
    }
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        ApptCheckBox();
        RegCheckBox();
        DocCheckBox();
        BillCheckBox();
        InquiryCheckBox();
        MasterCheckBox();
        CheckReports();
            try{
                DbConn.SQLQuery = "update tblusersecurity set us_regview=?,us_reginsert=?,us_regedit=?,us_regdelete=?,us_regprint=?,"
                        + "us_apptview=?,us_apptinsert=?,us_apptedit=?,us_apptdelete=?,us_apptprint=?,"
                        + "us_docview=?,us_docinsert=?,us_docedit=?,us_docdelete=?,us_docprint=?,"
                        + "us_billview=?,us_billprocess=?,us_billprint=?,us_billinvoicegen=?,"
                        + "us_inqpatient=?,us_inqappointment=?,us_inqsales=?,us_inqinvoice=?,"
                        + "us_mastergen=?,us_masteruser=?,us_mastersecurity=?,us_masterdrug=?,us_mastertreatment=?,us_masterinsurance=?,us_mastericd=?,"
                        + "us_reportmedcert=? where us_name= ?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, RegView);
                DbConn.pstmt.setString(2, RegInsert);
                DbConn.pstmt.setString(3, RegEdit);
                DbConn.pstmt.setString(4, RegDelete);
                DbConn.pstmt.setString(5, RegPrint);
                DbConn.pstmt.setString(6, ApptView);
                DbConn.pstmt.setString(7, ApptInsert);
                DbConn.pstmt.setString(8, ApptEdit);
                DbConn.pstmt.setString(9, ApptDelete);
                DbConn.pstmt.setString(10, ApptPrint);
                DbConn.pstmt.setString(11,DocView );
                DbConn.pstmt.setString(12, DocInsert);
                DbConn.pstmt.setString(13, DocEdit);
                DbConn.pstmt.setString(14, DocDelete);
                DbConn.pstmt.setString(15, DocPrint);
                DbConn.pstmt.setString(16, BillView);
                DbConn.pstmt.setString(17, BillProcess);
                DbConn.pstmt.setString(18, BillPrint);
                DbConn.pstmt.setString(19, BillInvoiceGen);
                DbConn.pstmt.setString(20, InqPatient);
                DbConn.pstmt.setString(21, InqAppt);
                DbConn.pstmt.setString(22, InqSales);
                DbConn.pstmt.setString(23, InqInvoice);
                DbConn.pstmt.setString(24, MasterGen);
                DbConn.pstmt.setString(25, MasterUser);
                DbConn.pstmt.setString(26, MasterSecurity);
                DbConn.pstmt.setString(27, MasterDrug);
                DbConn.pstmt.setString(28, MasterTreatment);
                DbConn.pstmt.setString(29, MasterInsurance);
                DbConn.pstmt.setString(30, MasterICD);
                DbConn.pstmt.setString(31,ReportMedCert);
                DbConn.pstmt.setString(32, cmbUserName.getSelectedItem().toString());
                DbConn.pstmt.execute();
                JOptionPane.showMessageDialog(this, "Acccess Level Saved");
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chkReportsAllMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkReportsAllMouseReleased
        if (chkReportsAll.isSelected()){
            chkReportMedCert.setSelected(true);
        }else{
            chkReportMedCert.setSelected(false);
        }
    }//GEN-LAST:event_chkReportsAllMouseReleased

    private void chkReportsAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkReportsAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkReportsAllActionPerformed

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
            java.util.logging.Logger.getLogger(frmUserSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmUserSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmUserSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmUserSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmUserSecurity().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkApptAll;
    private javax.swing.JCheckBox chkApptDelete;
    private javax.swing.JCheckBox chkApptEdit;
    private javax.swing.JCheckBox chkApptInsert;
    private javax.swing.JCheckBox chkApptPrint;
    private javax.swing.JCheckBox chkApptView;
    private javax.swing.JCheckBox chkBillingAll;
    private javax.swing.JCheckBox chkBillingInvoice;
    private javax.swing.JCheckBox chkBillingPrint;
    private javax.swing.JCheckBox chkBillingProcess;
    private javax.swing.JCheckBox chkBillingView;
    private javax.swing.JCheckBox chkDoctorAll;
    private javax.swing.JCheckBox chkDoctorDelete;
    private javax.swing.JCheckBox chkDoctorEdit;
    private javax.swing.JCheckBox chkDoctorInsert;
    private javax.swing.JCheckBox chkDoctorPrint;
    private javax.swing.JCheckBox chkDoctorView;
    private javax.swing.JCheckBox chkInqAll;
    private javax.swing.JCheckBox chkInqAppointment;
    private javax.swing.JCheckBox chkInqInvoice;
    private javax.swing.JCheckBox chkInqPatient;
    private javax.swing.JCheckBox chkInqSales;
    private javax.swing.JCheckBox chkMastersAll;
    private javax.swing.JCheckBox chkMastersDrug;
    private javax.swing.JCheckBox chkMastersGeneral;
    private javax.swing.JCheckBox chkMastersICD;
    private javax.swing.JCheckBox chkMastersInsurance;
    private javax.swing.JCheckBox chkMastersTreatment;
    private javax.swing.JCheckBox chkMastersUser;
    private javax.swing.JCheckBox chkMastersUserSecurity;
    private javax.swing.JCheckBox chkRegAll;
    private javax.swing.JCheckBox chkRegDelete;
    private javax.swing.JCheckBox chkRegEdit;
    private javax.swing.JCheckBox chkRegInsert;
    private javax.swing.JCheckBox chkRegPrint;
    private javax.swing.JCheckBox chkRegView;
    private javax.swing.JCheckBox chkReportMedCert;
    private javax.swing.JCheckBox chkReportsAll;
    private javax.swing.JComboBox<String> cmbUserName;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    // End of variables declaration//GEN-END:variables
}
