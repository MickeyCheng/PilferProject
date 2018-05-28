
package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.joda.time.LocalTime;
public class frmDoctor extends javax.swing.JFrame {
DbConnection DbConn = new DbConnection();
String HistoryCheck = "insert",GetApptDate;
JComboBox cmbRoute = new JComboBox();
Date todayDate = new Date();
    /**
     * Creates new form frmDoctor
     */
    public frmDoctor() {
        initComponents();
        DbConn.DoConnect();
        lblApptID.setVisible(false);
        setLocationRelativeTo(null);
        setExtendedState(frmDoctor.MAXIMIZED_BOTH);
        setDate();
        FillAppointment();
        FillTreatment();
        FillMedicine();
        FillICD();
        FillComboDays();
        LoadTableSummaryHeader();
        setDefaultCloseOperation(frmDoctor.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        SetTablesAutoScroll();
        lblDoctor.setText(DbConn.LoggedUserName);
        txtAreaNotesHistory.setEditable(false);
        txtAreaDiagnosisHistory.setEditable(false);
        cmbDoctor.setVisible(false);
        FillComboDoctor();
        lblCurrentDoctor.setText(cmbDoctor.getSelectedItem().toString());
        if (DbConnection.LoggedUserAdmin.equals("Y")){
            cmbDoctor.setVisible(true);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                DbConn.DoctorForm = false;
            }
        });
        tabbedDoctor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                LoadSummaryHistory();
                LoadSummaryMedicine();
                LoadSummaryTreatment();
            }
        });
        cmbDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {FillAdminDoctorChange();}
        });
        dateAppointment.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {FillApptChange();}
        });
        txtTreatment.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenTreatment();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenTreatment();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenTreatment();}
        });
        txtMedicineSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenMedicine();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenMedicine();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenMedicine();}
        });
        txtICDSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {ListenICD();}

            @Override
            public void removeUpdate(DocumentEvent e) {ListenICD();}

            @Override
            public void changedUpdate(DocumentEvent e) {ListenICD();}
        });
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void SetTablesAutoScroll(){
        tblMedicineTable.setAutoResizeMode(tblMedicineTable.AUTO_RESIZE_OFF);
        tblMedicineTable.setAutoscrolls(true);
        tblHistory.setAutoResizeMode(tblMedicineTable.AUTO_RESIZE_OFF);
        tblHistory.setAutoscrolls(true);
        tblTreatmentSummary.setAutoResizeMode(tblTreatmentSummary.AUTO_RESIZE_OFF);
        tblTreatmentSummary.setAutoscrolls(true);
        tblPrescriptionSummary.setAutoResizeMode(tblPrescriptionSummary.AUTO_RESIZE_OFF);
        tblPrescriptionSummary.setAutoscrolls(true);
        tblHistorySummary.setAutoResizeMode(tblHistorySummary.AUTO_RESIZE_OFF);
        tblHistorySummary.setAutoscrolls(true);
    }
    private void LoadTableSummaryHeader(){
        //treatment
        tblTreatmentSummary.getColumnModel().getColumn(0).setHeaderValue("CODE");
        tblTreatmentSummary.getColumnModel().getColumn(1).setHeaderValue("DESCRIPTION");
        tblTreatmentSummary.getColumnModel().getColumn(2).setHeaderValue("PRICE");
        tblTreatmentSummary.getColumnModel().getColumn(3).setHeaderValue("QTY");
        tblTreatmentSummary.getColumnModel().getColumn(4).setHeaderValue("VALUE");
        int treatRow=0;
        while (treatRow != tblTreatmentSummary.getColumnCount()){
            tblTreatmentSummary.getColumnModel().getColumn(treatRow).setPreferredWidth(100);
            treatRow++;
        }
        
        //medicine
        tblPrescriptionSummary.getColumnModel().getColumn(0).setHeaderValue("CODE");
        tblPrescriptionSummary.getColumnModel().getColumn(1).setHeaderValue("NAME");
        tblPrescriptionSummary.getColumnModel().getColumn(2).setHeaderValue("QTY");
        tblPrescriptionSummary.getColumnModel().getColumn(3).setHeaderValue("DAYS");
        tblPrescriptionSummary.getColumnModel().getColumn(4).setHeaderValue("ROUTE");
        tblPrescriptionSummary.getColumnModel().getColumn(5).setHeaderValue("SIGNA");
        tblPrescriptionSummary.getColumnModel().getColumn(6).setHeaderValue("DOSE");
        tblPrescriptionSummary.getColumnModel().getColumn(7).setHeaderValue("INSTRUCTIONS");
        tblPrescriptionSummary.getColumnModel().getColumn(8).setHeaderValue("COMMENTS");
        tblPrescriptionSummary.getColumnModel().getColumn(9).setHeaderValue("VALUE");
        int treatMed=0;
        while (treatMed != tblPrescriptionSummary.getColumnCount()){
            tblPrescriptionSummary.getColumnModel().getColumn(treatMed).setPreferredWidth(100);
            treatMed++;
        }
        //History
        tblHistorySummary.getColumnModel().getColumn(0).setHeaderValue("DIAGNOSIS");
        tblHistorySummary.getColumnModel().getColumn(1).setHeaderValue("SUBJECTIVE");
        tblHistorySummary.getColumnModel().getColumn(2).setHeaderValue("PRESENTATION");
        tblHistorySummary.getColumnModel().getColumn(3).setHeaderValue("P.HISTORY");
        tblHistorySummary.getColumnModel().getColumn(4).setHeaderValue("HABITS");
        tblHistorySummary.getColumnModel().getColumn(5).setHeaderValue("F.HISTORY");
        tblHistorySummary.getColumnModel().getColumn(6).setHeaderValue("ALLERGIES");
        tblHistorySummary.getColumnModel().getColumn(7).setHeaderValue("PLANNING");
        int treatHistory=0;
        while (treatHistory != tblHistorySummary.getColumnCount()){
            tblHistorySummary.getColumnModel().getColumn(treatHistory).setPreferredWidth(100);
            treatHistory++;
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
            cmbDoctor.setSelectedItem(lblDoctor.getText());
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetMedicineHeader(){
        tblMedicine.getColumnModel().getColumn(0).setHeaderValue("Code");
        tblMedicine.getColumnModel().getColumn(1).setHeaderValue("Name");
        tblMedicine.getColumnModel().getColumn(2).setHeaderValue("Description");
    }
    private void FillMedicine(){
        try{
            DbConn.SQLQuery= "Select mm_code,mm_name,mm_description from tblmedicinemaster order by mm_name";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblMedicine.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetMedicineHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetICDHeader(){
        tblICDSearch.getColumnModel().getColumn(0).setHeaderValue("Code");
        tblICDSearch.getColumnModel().getColumn(1).setHeaderValue("Description");
    }
    private void FillICD(){
        try{
            DbConn.SQLQuery = "Select ic_code,ic_description from tblicddetails order by ic_code";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblICDSearch.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetICDHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetTreatmentHeader(){
        tblTreatment.getColumnModel().getColumn(0).setHeaderValue("Code");
        tblTreatment.getColumnModel().getColumn(1).setHeaderValue("Description");
        tblTreatment.getColumnModel().getColumn(2).setHeaderValue("Price");
    }
    private void FillTreatment(){
        try{
            DbConn.SQLQuery = "select tm_code, tm_description,tm_price from tbltreatmentmaster order by tm_description";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblTreatment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetTreatmentHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillComboDays(){
        cmbMedCert.removeAllItems();
        int i =0;
        while (i <=101){
            cmbMedCert.addItem(String.valueOf(i));
            i++;
        }
    }
    private void fillRoute(){
        
        cmbRoute.removeAllItems();
        try{
            DbConn.SQLQuery = "Select * from tblroute order by rt_description";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbRoute.addItem(DbConn.rs.getString("rt_description"));
            }
            
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void ListenICD(){
        try{
            DbConn.SQLQuery = "Select ic_code,ic_description from tblicddetails where ic_code like ? or ic_description like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" + txtICDSearch.getText() + "%");
            DbConn.pstmt.setString(2, "%" + txtICDSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblICDSearch.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            SetICDHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void ListenMedicine(){
        try{
            DbConn.SQLQuery= "Select mm_code,mm_name,mm_description from tblmedicinemaster where mm_name like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%"+txtMedicineSearch.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblMedicine.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetMedicineHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void ListenTreatment(){
        try{
            DbConn.SQLQuery = "select tm_code, tm_description,tm_price from tbltreatmentmaster where tm_description like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" + txtTreatment.getText() + "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblTreatment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetTreatmentHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void setDate(){
        dateAppointment.setDate(new Date());   
        dateMedCertAppt.setDate(todayDate);
        dateMedCertDischarge.setDate(todayDate);
    }
    private void FillAdminDoctorChange(){
        lblCurrentDoctor.setText(cmbDoctor.getSelectedItem().toString());
        try{
            DbConn.SQLQuery = "Select ap_appttime,ap_name,ap_pid,ap_apptnumber from tblappointment where ap_doctor =? and ap_apptdate=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblCurrentDoctor.getText());
            DbConn.pstmt.setString(2, DbConn.sdfDate.format(dateAppointment.getDate()));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblAppointment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetApptHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillApptChange(){
        try{
            DbConn.SQLQuery = "Select ap_appttime,ap_name,ap_pid,ap_apptnumber from tblappointment where ap_doctor =? and ap_apptdate=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, DbConn.LoggedUserName);
            DbConn.pstmt.setString(2, DbConn.sdfDate.format(dateAppointment.getDate()));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblAppointment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            SetApptHeader();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void FillAppointment(){
        try{
            DbConn.SQLQuery = "Select ap_appttime,ap_name,ap_pid,ap_apptnumber from tblappointment where ap_doctor =? and ap_apptdate=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, DbConn.LoggedUserName);
            DbConn.pstmt.setString(2, DbConn.sdfDate.format(dateAppointment.getDate()));
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblAppointment.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            SetApptHeader();
            DbConn.pstmt.close();
            GetApptDate = DbConn.sdfDate.format(dateAppointment.getDate());
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SetApptHeader(){
        tblAppointment.getColumnModel().getColumn(0).setHeaderValue("Time");
        tblAppointment.getColumnModel().getColumn(1).setHeaderValue("Name");
        tblAppointment.getColumnModel().getColumn(2).setHeaderValue("PID");
        tblAppointment.getColumnModel().getColumn(3).setHeaderValue("Appt Number");
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
        lblAge = new javax.swing.JLabel();
        lblPatientName = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblPID = new javax.swing.JLabel();
        lblApptID = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAppointment = new javax.swing.JTable();
        dateAppointment = new com.toedter.calendar.JDateChooser();
        cmbDoctor = new javax.swing.JComboBox<>();
        tabbedDoctor = new javax.swing.JTabbedPane();
        TabTreatments = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTreatment = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblTreatmentService = new javax.swing.JTable();
        btnAddTreatment = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtTreatment = new javax.swing.JTextField();
        TabNotes = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaNotes = new javax.swing.JTextArea();
        TabHistory = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtAreaSubjective = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        txtAreaPresentation = new javax.swing.JTextArea();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtAreaPastHistory = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        txtAreaHabits = new javax.swing.JTextArea();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtAreaFamilyHistory = new javax.swing.JTextArea();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtAreaAllergies = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtAreaPlanning = new javax.swing.JTextArea();
        jScrollPane23 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        txtAreaDiagnosisHistory = new javax.swing.JTextArea();
        TabPrescription = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        dateMedCertAppt = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        dateMedCertDischarge = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        cmbMedCert = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAreaDiagnosis = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        txtMedicineSearch = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblMedicine = new javax.swing.JTable();
        btnAddMedicine = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblMedicineTable = new javax.swing.JTable();
        TabVitalAndCC = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaChiefComplaint = new javax.swing.JTextArea();
        txtVisionRight = new javax.swing.JTextField();
        txtVisionLeft = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtBloodSugar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTemperature = new javax.swing.JTextField();
        txtPulse = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBP1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBp2 = new javax.swing.JTextField();
        txtWeight = new javax.swing.JTextField();
        txtHeight = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtICDSearch = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblICDShow = new javax.swing.JTable();
        btnAddICD = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblICDSearch = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        panelSummary = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblTreatmentSummary = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblPrescriptionSummary = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHistorySummary = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        btnSaveAll = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtAreaNotesHistory = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblDoctor = new javax.swing.JLabel();
        lblCurrentDoctor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAge.setForeground(new java.awt.Color(0, 0, 0));
        lblAge.setText("Age");
        jPanel2.add(lblAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 210, -1));

        lblPatientName.setForeground(new java.awt.Color(0, 0, 0));
        lblPatientName.setText("Patient Name");
        jPanel2.add(lblPatientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 380, 20));

        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Name:");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblPID.setForeground(new java.awt.Color(0, 0, 0));
        lblPID.setText("Patient Id");
        jPanel2.add(lblPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 110, -1));

        lblApptID.setForeground(new java.awt.Color(0, 0, 0));
        lblApptID.setText("apptid");
        jPanel2.add(lblApptID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("PID:");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Age:");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Gender:");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, -1, -1));

        lblGender.setForeground(new java.awt.Color(0, 0, 0));
        lblGender.setText("Gender");
        jPanel2.add(lblGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 210, -1));

        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("DOB:");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, -1, -1));

        lblBirthday.setForeground(new java.awt.Color(0, 0, 0));
        lblBirthday.setText("Birthday");
        jPanel2.add(lblBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 210, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1040, 70));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setName(""); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 350, 10));

        tblAppointment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Time", "Name", "PID", "Appt Number"
            }
        ));
        tblAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAppointmentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAppointment);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 330, 480));

        dateAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateAppointmentMouseReleased(evt);
            }
        });
        jPanel3.add(dateAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 30));

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 330, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 350, 580));
        jPanel3.getAccessibleContext().setAccessibleName("");
        jPanel3.getAccessibleContext().setAccessibleDescription("");

        tabbedDoctor.setBackground(new java.awt.Color(214, 214, 194));
        tabbedDoctor.setForeground(new java.awt.Color(0, 0, 0));
        tabbedDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedDoctorMouseClicked(evt);
            }
        });

        TabTreatments.setBackground(new java.awt.Color(214, 214, 194));
        TabTreatments.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane4.setViewportView(tblTreatment);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 900, 120));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 920, 20));

        tblTreatmentService.setBackground(new java.awt.Color(214, 214, 194));
        tblTreatmentService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Description", "Price", "Quantity", "Sum"
            }
        ));
        tblTreatmentService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblTreatmentServiceKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblTreatmentService);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 900, 270));

        btnAddTreatment.setBackground(new java.awt.Color(255, 255, 255));
        btnAddTreatment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NewIcon.png"))); // NOI18N
        btnAddTreatment.setText("ADD");
        btnAddTreatment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTreatmentActionPerformed(evt);
            }
        });
        jPanel4.add(btnAddTreatment, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 120, 40));

        TabTreatments.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 920, 480));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Type Service Here:");
        TabTreatments.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 140, 20));
        TabTreatments.add(txtTreatment, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 750, -1));

        tabbedDoctor.addTab("Treatments", TabTreatments);

        TabNotes.setForeground(new java.awt.Color(0, 0, 0));

        jPanel7.setBackground(new java.awt.Color(214, 214, 194));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAreaNotes.setBackground(new java.awt.Color(214, 214, 194));
        txtAreaNotes.setColumns(20);
        txtAreaNotes.setRows(5);
        jScrollPane3.setViewportView(txtAreaNotes);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 910, 520));

        javax.swing.GroupLayout TabNotesLayout = new javax.swing.GroupLayout(TabNotes);
        TabNotes.setLayout(TabNotesLayout);
        TabNotesLayout.setHorizontalGroup(
            TabNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabNotesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        TabNotesLayout.setVerticalGroup(
            TabNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabNotesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedDoctor.addTab("Notes", TabNotes);

        TabHistory.setBackground(new java.awt.Color(214, 214, 194));
        TabHistory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(214, 214, 194));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Planning:");
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 370, -1, -1));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Subjective:");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Presentation:");
        jPanel6.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Past History:");
        jPanel6.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Habits:");
        jPanel6.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Family History:");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, -1, -1));

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Allergies:");
        jPanel6.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, -1, -1));

        txtAreaSubjective.setColumns(20);
        txtAreaSubjective.setRows(5);
        jScrollPane14.setViewportView(txtAreaSubjective);

        jPanel6.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 460, 60));

        txtAreaPresentation.setColumns(20);
        txtAreaPresentation.setRows(5);
        jScrollPane15.setViewportView(txtAreaPresentation);

        jPanel6.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 460, 60));

        txtAreaPastHistory.setColumns(20);
        txtAreaPastHistory.setRows(5);
        jScrollPane16.setViewportView(txtAreaPastHistory);

        jPanel6.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 380, 60));

        txtAreaHabits.setColumns(20);
        txtAreaHabits.setRows(5);
        jScrollPane17.setViewportView(txtAreaHabits);

        jPanel6.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 380, 60));

        txtAreaFamilyHistory.setColumns(20);
        txtAreaFamilyHistory.setRows(5);
        jScrollPane18.setViewportView(txtAreaFamilyHistory);

        jPanel6.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 380, 60));

        txtAreaAllergies.setColumns(20);
        txtAreaAllergies.setRows(5);
        jScrollPane19.setViewportView(txtAreaAllergies);

        jPanel6.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 380, 60));

        txtAreaPlanning.setColumns(20);
        txtAreaPlanning.setRows(5);
        jScrollPane20.setViewportView(txtAreaPlanning);

        jPanel6.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 390, 380, 60));

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoryMouseClicked(evt);
            }
        });
        jScrollPane23.setViewportView(tblHistory);

        jPanel6.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 460, 110));

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Diagnosis:");
        jPanel6.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        txtAreaDiagnosisHistory.setColumns(20);
        txtAreaDiagnosisHistory.setRows(5);
        jScrollPane22.setViewportView(txtAreaDiagnosisHistory);

        jPanel6.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 460, 110));

        TabHistory.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 530));

        tabbedDoctor.addTab("Patient History", TabHistory);

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Appointment Date:");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 110, -1));

        dateMedCertAppt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateMedCertApptMouseReleased(evt);
            }
        });
        jPanel5.add(dateMedCertAppt, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 170, 30));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Diagnosis:");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 110, -1));

        dateMedCertDischarge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dateMedCertDischargeMouseReleased(evt);
            }
        });
        jPanel5.add(dateMedCertDischarge, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 70, 170, 30));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Discharged Date:");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 110, -1));

        cmbMedCert.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cmbMedCert, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 170, -1));

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Days:");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 110, -1));

        txtAreaDiagnosis.setColumns(20);
        txtAreaDiagnosis.setRows(5);
        jScrollPane6.setViewportView(txtAreaDiagnosis);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, -1, 150));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel5.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 20, 540));

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Type Medicine Here:");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 20));
        jPanel5.add(txtMedicineSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 440, -1));

        tblMedicine.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMedicine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMedicineMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblMedicine);

        jPanel5.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 440, 110));

        btnAddMedicine.setBackground(new java.awt.Color(255, 255, 255));
        btnAddMedicine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NewIcon.png"))); // NOI18N
        btnAddMedicine.setText("ADD");
        btnAddMedicine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMedicineActionPerformed(evt);
            }
        });
        jPanel5.add(btnAddMedicine, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 120, 50));
        jPanel5.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 460, 20));

        tblMedicineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Quantity", "Days", "Route", "Signa", "Dose", "Instructions", "Comments", "Value"
            }
        ));
        jScrollPane8.setViewportView(tblMedicineTable);

        jPanel5.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 440, 270));

        javax.swing.GroupLayout TabPrescriptionLayout = new javax.swing.GroupLayout(TabPrescription);
        TabPrescription.setLayout(TabPrescriptionLayout);
        TabPrescriptionLayout.setHorizontalGroup(
            TabPrescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabPrescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        TabPrescriptionLayout.setVerticalGroup(
            TabPrescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TabPrescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedDoctor.addTab("Prescription and Medical Certificate", TabPrescription);

        TabVitalAndCC.setBackground(new java.awt.Color(214, 214, 194));
        TabVitalAndCC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(214, 214, 194));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Chief Complaint");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 100, 30));

        txtAreaChiefComplaint.setBackground(new java.awt.Color(214, 214, 194));
        txtAreaChiefComplaint.setColumns(20);
        txtAreaChiefComplaint.setRows(5);
        jScrollPane2.setViewportView(txtAreaChiefComplaint);

        jPanel8.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 580, 60));
        jPanel8.add(txtVisionRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 40, -1));
        jPanel8.add(txtVisionLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 40, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("LEFT");
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 40, -1));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RIGHT");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 40, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("VISION");
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 100, 20));

        txtBloodSugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBloodSugarActionPerformed(evt);
            }
        });
        jPanel8.add(txtBloodSugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 110, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("BLOOD SUGAR:");
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 100, 20));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("TEMPERATURE:");
        jPanel8.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 100, 20));
        jPanel8.add(txtTemperature, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 110, -1));
        jPanel8.add(txtPulse, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 110, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("PULSE:");
        jPanel8.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 100, 20));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("BP:");
        jPanel8.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 100, 20));
        jPanel8.add(txtBP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 50, -1));

        jLabel9.setText("/");
        jPanel8.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 10, 20));
        jPanel8.add(txtBp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 50, -1));
        jPanel8.add(txtWeight, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 110, -1));
        jPanel8.add(txtHeight, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 110, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("WEIGHT:");
        jPanel8.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 100, 20));
        jPanel8.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 260, 10));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("HEIGHT:");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 100, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VITAL SIGNS");
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 260, 30));

        jPanel15.setBackground(new java.awt.Color(214, 214, 194));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Type ICD Here:");
        jPanel15.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 110, 20));
        jPanel15.add(txtICDSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 340, -1));

        tblICDShow.setBackground(new java.awt.Color(214, 214, 194));
        tblICDShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Description"
            }
        ));
        jScrollPane12.setViewportView(tblICDShow);

        jPanel15.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 560, 110));

        btnAddICD.setBackground(new java.awt.Color(255, 255, 255));
        btnAddICD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NewIcon.png"))); // NOI18N
        btnAddICD.setText("ADD");
        btnAddICD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddICDActionPerformed(evt);
            }
        });
        jPanel15.add(btnAddICD, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 100, 50));
        jPanel15.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 560, 20));

        tblICDSearch.setBackground(new java.awt.Color(214, 214, 194));
        tblICDSearch.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(tblICDSearch);

        jPanel15.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 560, 120));

        jPanel8.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 580, 380));

        jButton3.setText("View Graph");
        jPanel8.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, -1, -1));

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel8.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 30, 530));

        TabVitalAndCC.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 530));

        tabbedDoctor.addTab("Vital Signs, Chief Complaint & ICD", TabVitalAndCC);

        panelSummary.setBackground(new java.awt.Color(214, 214, 194));
        panelSummary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelSummaryMouseClicked(evt);
            }
        });
        panelSummary.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(214, 214, 194));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(214, 214, 194));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTreatmentSummary.setBackground(new java.awt.Color(214, 214, 194));
        tblTreatmentSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane10.setViewportView(tblTreatmentSummary);

        jPanel12.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 11, 390, 140));

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 410, 160));

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Treatment");
        jPanel11.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Prescription");
        jPanel11.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jPanel13.setBackground(new java.awt.Color(214, 214, 194));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPrescriptionSummary.setBackground(new java.awt.Color(214, 214, 194));
        tblPrescriptionSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        jScrollPane11.setViewportView(tblPrescriptionSummary);

        jPanel13.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 140));

        jPanel11.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 410, 160));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printericon.png"))); // NOI18N
        jButton2.setText("PRINT PRESCRIPTION");
        jPanel11.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 300, 50));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printericon.png"))); // NOI18N
        jButton1.setText("PRINT MEDICAL CERTIFICATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 300, 50));

        panelSummary.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 430, 540));

        jPanel14.setBackground(new java.awt.Color(214, 214, 194));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHistorySummary.setBackground(new java.awt.Color(214, 214, 194));
        tblHistorySummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane9.setViewportView(tblHistorySummary);

        jPanel14.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 460, 150));

        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Patient History");
        jPanel14.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btnSaveAll.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SaveIcon.png"))); // NOI18N
        btnSaveAll.setText("SAVE");
        btnSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAllActionPerformed(evt);
            }
        });
        jPanel14.add(btnSaveAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, 120, 60));

        txtAreaNotesHistory.setColumns(20);
        txtAreaNotesHistory.setRows(5);
        jScrollPane21.setViewportView(txtAreaNotesHistory);

        jPanel14.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 460, -1));

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Notes:");
        jPanel14.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        panelSummary.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 480, 540));

        tabbedDoctor.addTab("Summary", panelSummary);

        jPanel1.add(tabbedDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 940, 580));
        tabbedDoctor.getAccessibleContext().setAccessibleName("");

        jPanel9.setBackground(new java.awt.Color(214, 214, 194));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Doctor:");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblDoctor.setForeground(new java.awt.Color(0, 0, 0));
        lblDoctor.setText("Doctor");
        jPanel9.add(lblDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 170, -1));

        lblCurrentDoctor.setForeground(new java.awt.Color(0, 0, 0));
        lblCurrentDoctor.setText("jLabel35");
        jPanel9.add(lblCurrentDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 210, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 10, 240, 70));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dateAppointmentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateAppointmentMouseReleased
        
    }//GEN-LAST:event_dateAppointmentMouseReleased
    private void fillVitalsAndCC(){
        int row = tblAppointment.getSelectedRow();
        int ba = tblAppointment.convertRowIndexToModel(row);
        String tblClick = tblAppointment.getValueAt(ba, 3).toString();
        try{
            DbConn.SQLQuery = "Select tblpatientvitalsign.*, tblchiefcomplaint.cc_description "
                    + "from tblpatientvitalsign left join tblchiefcomplaint on tblpatientvitalsign.pv_apptnumber = tblchiefcomplaint.cc_apptid where pv_apptnumber = ? or cc_apptid=?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.pstmt.setString(2, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtHeight.setText(DbConn.rs.getString("tblpatientvitalsign.pv_height"));
                txtWeight.setText(DbConn.rs.getString("tblpatientvitalsign.pv_weight"));
                txtBP1.setText(DbConn.rs.getString("tblpatientvitalsign.pv_bloodpressure1"));
                txtBp2.setText(DbConn.rs.getString("tblpatientvitalsign.pv_bloodpressure2"));
                txtPulse.setText(DbConn.rs.getString("tblpatientvitalsign.pv_pulse"));
                txtTemperature.setText(DbConn.rs.getString("tblpatientvitalsign.pv_temperature"));
                txtBloodSugar.setText(DbConn.rs.getString("tblpatientvitalsign.pv_bloodsugar"));
                txtVisionLeft.setText(DbConn.rs.getString("tblpatientvitalsign.pv_visionleft"));
                txtVisionRight.setText(DbConn.rs.getString("tblpatientvitalsign.pv_visionright"));
                txtAreaChiefComplaint.setText(DbConn.rs.getString("tblchiefcomplaint.cc_description"));
            }else{
                txtHeight.setText("No previous data");
                txtWeight.setText("No previous data");
                txtBP1.setText("No previous data");
                txtBp2.setText("No previous data");
                txtPulse.setText("No previous data");
                txtTemperature.setText("No previous data");
                txtBloodSugar.setText("No previous data");
                txtVisionLeft.setText("No previous data");
                txtVisionRight.setText("No previous data");
                txtAreaChiefComplaint.setText("No previous data");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void txtBloodSugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBloodSugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBloodSugarActionPerformed
    private void LoadPatientDetails(){
//        try{
//            DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblpatientdetails where pd_pid=?");
//            DbConn.pstmt.setString(1, tblAppointment.getValueAt(ERROR, NORMAL));
//        }catch(SQLException e){
//            JOptionPane.showMessageDialog(this, "Load Patient Details");
//        } 
    }
    private void tblAppointmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAppointmentMouseClicked
        try{
            int row = tblAppointment.getSelectedRow();
            int ba = tblAppointment.convertRowIndexToModel(row);
            try{
                DbConn.pstmt = DbConn.conn.prepareStatement("Select * from tblpatientdetails where pd_pid=?");
                DbConn.pstmt.setString(1, tblAppointment.getValueAt(ba, 2).toString());
                DbConn.rs = DbConn.pstmt.executeQuery();
                if (DbConn.rs.next()){
                    lblApptID.setText(tblAppointment.getValueAt(ba, 3).toString());
                    lblPID.setText(DbConn.rs.getString("pd_pid"));
                    lblPatientName.setText(DbConn.rs.getString("pd_name"));
                    lblBirthday.setText(DbConn.rs.getString("pd_dob"));
                    lblGender.setText(DbConn.rs.getString("pd_gender"));
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Load Patient Details");
            } 
            fillVitalsAndCC();
            SetApptHeader();
            FillHistory();
            LoadTreatment();
            LoadMedicine();
            LoadHistory();
            LoadSummaryHistory();
            LoadSummaryMedicine();
            LoadSummaryTreatment();
            LoadTableSummaryHeader();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Please check that you have chosen the correct patient.");
        }
    }//GEN-LAST:event_tblAppointmentMouseClicked
    private void ClearHistoryTexts(){
        txtAreaDiagnosisHistory.setText("");
        txtAreaSubjective.setText("");
        txtAreaPresentation.setText("");
        txtAreaPastHistory.setText("");
        txtAreaHabits.setText("");
        txtAreaFamilyHistory.setText("");
        txtAreaAllergies.setText("");
        txtAreaPlanning.setText("");
    }
    private void LoadHistory(){
        ClearHistoryTexts();
        try{
            DbConn.SQLQuery = "select * from tblpatienthistory where ph_apptnumber =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblApptID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtAreaDiagnosisHistory.setText(DbConn.rs.getString("ph_diagnosis"));
                txtAreaSubjective.setText(DbConn.rs.getString("ph_subjective"));
                txtAreaPresentation.setText(DbConn.rs.getString("ph_presentation"));
                txtAreaPastHistory.setText(DbConn.rs.getString("ph_pasthistory"));
                txtAreaHabits.setText(DbConn.rs.getString("ph_habits"));
                txtAreaFamilyHistory.setText(DbConn.rs.getString("ph_familyhistory"));
                txtAreaAllergies.setText(DbConn.rs.getString("ph_allergies"));
                txtAreaPlanning.setText(DbConn.rs.getString("ph_planning"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void LoadMedicine(){
        DefaultTableModel modelMedicine = (DefaultTableModel)tblMedicineTable.getModel(); 
        modelMedicine.setRowCount(0);
        try{
            DbConn.SQLQuery = "Select * from tblpatientmedications where pm_apptid =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblApptID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            //code,name,qty,days,route,signa,dose,instructions,comments,value
            while (DbConn.rs.next()){  
                Object[] AddMedicine = {DbConn.rs.getString("pm_code"),DbConn.rs.getString("pm_description"),DbConn.rs.getString("pm_qty"),DbConn.rs.getString("pm_days")
                        ,DbConn.rs.getString("pm_route"),DbConn.rs.getString("pm_signa"),DbConn.rs.getString("pm_dose"),DbConn.rs.getString("pm_specialinstruction")
                        ,DbConn.rs.getString("pm_comments")};
                modelMedicine.addRow(AddMedicine);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void LoadTreatment(){
        DefaultTableModel modelTreatment = (DefaultTableModel)tblTreatmentService.getModel(); 
        modelTreatment.setRowCount(0);
        try{
            DbConn.SQLQuery = "Select * from tblpatientservices where ps_apptid =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblApptID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){  
                Object[] addTreatment = {DbConn.rs.getString("ps_code"),DbConn.rs.getString("ps_description"),DbConn.rs.getString("ps_price"),DbConn.rs.getString("ps_qty"),DbConn.rs.getString("ps_value")};
                modelTreatment.addRow(addTreatment);
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void btnAddTreatmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTreatmentActionPerformed
        Double SetValue;
        DefaultTableModel modelTreatment = (DefaultTableModel)tblTreatmentService.getModel();
        int row = tblTreatment.getSelectedRow();    
        SetValue = Double.valueOf(tblTreatment.getValueAt(row, 2).toString()) * 1;
        Object[] addTreatment = {tblTreatment.getValueAt(row, 0),tblTreatment.getValueAt(row, 1),tblTreatment.getValueAt(row, 2),"1",SetValue};
        modelTreatment.addRow(addTreatment);
    }//GEN-LAST:event_btnAddTreatmentActionPerformed

    private void dateMedCertApptMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateMedCertApptMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateMedCertApptMouseReleased

    private void dateMedCertDischargeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateMedCertDischargeMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateMedCertDischargeMouseReleased

    private void btnAddMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMedicineActionPerformed
        DefaultTableModel modelMedicine = (DefaultTableModel)tblMedicineTable.getModel();
        int row = tblMedicine.getSelectedRow();
        Object[] addMedicine = {tblMedicine.getValueAt(row, 0),tblMedicine.getValueAt(row, 1),"1","1","na","na","na","na","na","na"};
        modelMedicine.addRow(addMedicine);
        fillRoute();
        tblMedicineTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(cmbRoute));
        
        int i=0;
        while (i != tblMedicineTable.getColumnCount()){
            tblMedicineTable.getColumnModel().getColumn(i).setPreferredWidth(100);
            i++;
        }
    }//GEN-LAST:event_btnAddMedicineActionPerformed

    private void btnAddICDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddICDActionPerformed
        DefaultTableModel modelICD = (DefaultTableModel)tblICDShow.getModel();
        int row = tblICDSearch.getSelectedRow();   
        Object[] addIcd = {tblICDSearch.getValueAt(row, 0),tblICDSearch.getValueAt(row, 1)};
        modelICD.addRow(addIcd);
    }//GEN-LAST:event_btnAddICDActionPerformed

    private void btnSaveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAllActionPerformed
        SaveTreatment();
        SaveHistory();
        SaveMedications();
        SaveMedicalCertificate();
        SaveICD();
        UpdateAppointment();
        JOptionPane.showMessageDialog(this, "Treatment Saved");
    }//GEN-LAST:event_btnSaveAllActionPerformed

    private void tblTreatmentServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblTreatmentServiceKeyReleased
        int RowCount =0;
        Double GetQty=0.0, GetPrice= 0.0;
        while (RowCount<tblTreatmentService.getRowCount()){
            GetPrice = Double.valueOf(tblTreatmentService.getValueAt(RowCount, 2).toString());
            GetQty = Double.valueOf(tblTreatmentService.getValueAt(RowCount,3).toString());
            tblTreatmentService.setValueAt(String.valueOf(GetPrice*GetQty), RowCount, 4);
            RowCount++;
        }
    }//GEN-LAST:event_tblTreatmentServiceKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Map param = new HashMap();
        param.put("ApptNumber", lblApptID.getText());
//        param.put("dateTo", sdfDate.format(getToDate));
//        param.put("showStatus", cmbStatus.getSelectedItem().toString());
//        param.put("showClient", cmbClient.getSelectedItem().toString());
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
            //            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbticketing","root","root");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemrph","root","root");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportMedCert.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoryMouseClicked
        int row = tblHistory.getSelectedRow();
        int ba = tblHistory.convertRowIndexToModel(row);
        try{
            DbConn.SQLQuery = "select * from tblpatienthistory where ph_apptnumber =?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, tblHistory.getValueAt(ba, 5).toString());
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtAreaDiagnosisHistory.setText(DbConn.rs.getString("ph_diagnosis"));
                txtAreaSubjective.setText(DbConn.rs.getString("ph_subjective"));
                txtAreaPresentation.setText(DbConn.rs.getString("ph_presentation"));
                txtAreaPastHistory.setText(DbConn.rs.getString("ph_pasthistory"));
                txtAreaHabits.setText(DbConn.rs.getString("ph_habits"));
                txtAreaFamilyHistory.setText(DbConn.rs.getString("ph_familyhistory"));
                txtAreaAllergies.setText(DbConn.rs.getString("ph_allergies"));
                txtAreaPlanning.setText(DbConn.rs.getString("ph_planning"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tblHistoryMouseClicked

    private void tblMedicineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMedicineMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMedicineMouseClicked

    private void tabbedDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedDoctorMouseClicked
        
    }//GEN-LAST:event_tabbedDoctorMouseClicked
    private void LoadSummaryHistory(){
    //history
        DefaultTableModel modelHistory = (DefaultTableModel)tblHistorySummary.getModel();
        modelHistory.setRowCount(0);
        Object[] addHistory= {txtAreaDiagnosisHistory.getText(),txtAreaSubjective.getText(),txtAreaPresentation.getText(),
        txtAreaPastHistory.getText(),txtAreaHabits.getText(),txtAreaFamilyHistory.getText(),txtAreaAllergies.getText(),
        txtAreaPlanning.getText()};
        modelHistory.addRow(addHistory);
 
    }
    private void LoadSummaryMedicine(){
        int rowMedicine=0;
        //medicine
        if (tblMedicineTable.getRowCount() <=0){
            return;
        }else{
            DefaultTableModel modelmedicine= (DefaultTableModel)tblPrescriptionSummary.getModel();
            modelmedicine.setRowCount(0);
            while (rowMedicine!=tblMedicineTable.getRowCount()){
                Object[] addMedicine= {tblMedicineTable.getValueAt(rowMedicine, 0).toString(),tblMedicineTable.getValueAt(rowMedicine, 1).toString(),
                tblMedicineTable.getValueAt(rowMedicine, 2).toString(),tblMedicineTable.getValueAt(rowMedicine, 3).toString(),
                tblMedicineTable.getValueAt(rowMedicine, 4).toString(),tblMedicineTable.getValueAt(rowMedicine, 5).toString(),tblMedicineTable.getValueAt(rowMedicine, 6).toString(),
                tblMedicineTable.getValueAt(rowMedicine, 7).toString()};
                modelmedicine.addRow(addMedicine);
                rowMedicine++;
            }
        }
    }
    private void LoadSummaryTreatment(){
        int rowTreatment =0;
        //treatment
        if (tblTreatmentService.getRowCount() <=0){
            return;
        }else{
            DefaultTableModel modelTreatment = (DefaultTableModel)tblTreatmentSummary.getModel();
            modelTreatment.setRowCount(0);
            while (rowTreatment!=tblTreatmentService.getRowCount()){
                Object[] addTreatmentSummary= {tblTreatmentService.getValueAt(rowTreatment, 0).toString(),tblTreatmentService.getValueAt(rowTreatment, 1).toString(),
                tblTreatmentService.getValueAt(rowTreatment, 2).toString(),tblTreatmentService.getValueAt(rowTreatment, 3).toString(),
                tblTreatmentService.getValueAt(rowTreatment, 4).toString()};
                modelTreatment.addRow(addTreatmentSummary);
                rowTreatment++;
            }
        }
    }
    private void panelSummaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSummaryMouseClicked

    }//GEN-LAST:event_panelSummaryMouseClicked
    private void UpdateAppointment(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("Update tblappointment set ap_billed=? where ap_apptnumber =?");
            DbConn.pstmt.setString(1, "YES");
            DbConn.pstmt.setString(2, lblApptID.getText());
            DbConn.pstmt.executeUpdate();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SaveICD(){
        if(tblICDShow.getRowCount()<=0){
            return;
        }else{
            int rowCount=0;
            while (rowCount != tblICDShow.getRowCount()){
                try{
//        check1 = tblICDShow.getValueAt(rowCount,0).toString();
//        check2 = tblICDShow.getValueAt(rowCount,1).toString();
//        check3 = tblICDSearch.getValueAt(rowCount,0).toString();
//        check4 = tblICDSearch.getValueAt(rowCount,0).toString();
                    DbConn.SQLQuery = "Insert into tblpatienticdcodes (pi_pid,pi_apptid,pi_patientname,pi_code,pi_description,pi_doctor) "
                            + "values (?,?,?,?,?,?)";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1, lblPID.getText());
                    DbConn.pstmt.setString(2,lblApptID.getText());
                    DbConn.pstmt.setString(3,lblPatientName.getText());
                    DbConn.pstmt.setString(4,tblICDShow.getValueAt(rowCount,0).toString());
                    DbConn.pstmt.setString(5,tblICDShow.getValueAt(rowCount,1).toString());
                    DbConn.pstmt.setString(6, lblDoctor.getText());
                    DbConn.pstmt.execute();
                    DbConn.pstmt.close();
                    rowCount++;
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }
    }
    private void SaveMedicalCertificate(){
        try{
            DbConn.SQLQuery = "insert into tblmedicalcertificate (mc_pid,mc_apptid,mc_patientname,mc_apptdate,"
                    + "mc_dischargedate,mc_days,mc_diagnosis,mc_doctor) "
                    + "values (?,?,?,?,?,?,?,?)";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblPID.getText());
            DbConn.pstmt.setString(2,lblApptID.getText());
            DbConn.pstmt.setString(3, lblPatientName.getText());
            DbConn.pstmt.setString(4, String.valueOf(DbConn.sdfDate.format(dateMedCertAppt.getDate())));
            DbConn.pstmt.setString(5,String.valueOf(DbConn.sdfDate.format(dateMedCertDischarge.getDate())));
            DbConn.pstmt.setString(6, cmbMedCert.getSelectedItem().toString());
            DbConn.pstmt.setString(7, txtAreaDiagnosis.getText());
            DbConn.pstmt.setString(8, lblDoctor.getText());
            DbConn.pstmt.execute();
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void SaveMedications(){
    if (tblMedicineTable.getRowCount() <=0){
        return;
    }else{
        int rowCount =0;
        while (rowCount != tblMedicineTable.getRowCount()){
            try{
                DbConn.SQLQuery = "insert into tblpatientmedications (pm_pid,pm_apptid,pm_code,pm_description,pm_days,pm_route,pm_signa,pm_dose,"
                        + "pm_specialinstruction,pm_doctor,pm_comments,pm_qty) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1,lblPID.getText());
                DbConn.pstmt.setString(2,lblApptID.getText());
                DbConn.pstmt.setString(3,tblMedicineTable.getValueAt(rowCount, 0).toString());
                DbConn.pstmt.setString(4,tblMedicineTable.getValueAt(rowCount, 1).toString());
                DbConn.pstmt.setString(5,tblMedicineTable.getValueAt(rowCount, 3).toString());
                DbConn.pstmt.setString(6,tblMedicineTable.getValueAt(rowCount, 4).toString());
                DbConn.pstmt.setString(7,tblMedicineTable.getValueAt(rowCount, 5).toString());
                DbConn.pstmt.setString(8,tblMedicineTable.getValueAt(rowCount, 6).toString());
                DbConn.pstmt.setString(9,tblMedicineTable.getValueAt(rowCount, 7).toString());
                DbConn.pstmt.setString(10,lblDoctor.getText());
                DbConn.pstmt.setString(11,tblMedicineTable.getValueAt(rowCount, 8).toString());
                DbConn.pstmt.setString(12,tblMedicineTable.getValueAt(rowCount, 2).toString());
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                rowCount++;
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Please fill up the medications");
            }
        }
    }
    }
    private void FillHistory(){
        try{
            DbConn.SQLQuery = "select ph_apptdate,ph_chiefcomplaint,ph_doctor,ph_diagnosis,ph_presentation,ph_apptnumber from tblpatienthistory where ph_pid = ? order by ph_apptdate";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, lblPID.getText());
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblHistory.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblHistory.getColumnModel().getColumn(0).setHeaderValue("APPT DATE");
            tblHistory.getColumnModel().getColumn(1).setHeaderValue("C.COMPLAINT");
            tblHistory.getColumnModel().getColumn(2).setHeaderValue("DOCTOR");
            tblHistory.getColumnModel().getColumn(3).setHeaderValue("DIAGNOSIS");
            tblHistory.getColumnModel().getColumn(4).setHeaderValue("PRESENTATION");
            tblHistory.getColumnModel().getColumn(5).setHeaderValue("APPT #");
            
            int i = 0;
            while (i<tblHistory.getColumnCount()){
                tblHistory.getColumnModel().getColumn(i).setPreferredWidth(150);
                i++;
            }
            tblHistory.getColumnModel().getColumn(0).setPreferredWidth(80);
            tblHistory.getColumnModel().getColumn(5).setPreferredWidth(80);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }
    private void SaveHistory(){
        if (HistoryCheck.equals("insert")){
            try{
                DbConn.SQLQuery = "insert into tblpatienthistory (ph_pid,ph_notes,ph_subjective,ph_presentation,ph_habits,ph_familyhistory,ph_allergies,"
                        + "ph_planning,ph_doctor,ph_diagnosis,ph_chiefcomplaint,ph_pasthistory,ph_apptdate,ph_apptnumber) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, lblPID.getText());
                DbConn.pstmt.setString(2, txtAreaNotes.getText());
                DbConn.pstmt.setString(3,txtAreaSubjective.getText());
                DbConn.pstmt.setString(4,txtAreaPresentation.getText());
                DbConn.pstmt.setString(5,txtAreaAllergies.getText());
                DbConn.pstmt.setString(6,txtAreaFamilyHistory.getText());
                DbConn.pstmt.setString(7,txtAreaAllergies.getText());
                DbConn.pstmt.setString(8,txtAreaPlanning.getText());
                DbConn.pstmt.setString(9, lblDoctor.getText());
                DbConn.pstmt.setString(10,txtAreaDiagnosis.getText());
                DbConn.pstmt.setString(11,txtAreaChiefComplaint.getText());
                DbConn.pstmt.setString(12,txtAreaPastHistory.getText());
                DbConn.pstmt.setString(13,GetApptDate);
                DbConn.pstmt.setString(14,lblApptID.getText());
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }else if (HistoryCheck.equals("update")){
            try{
                DbConn.SQLQuery = "update tblpatienthistory set ph_notes=?,ph_subjective=?,ph_presentation=?,ph_habits=?,ph_familyhistory=?,"
                        + "ph_allergies=?,ph_planning=?,ph_doctor=?,ph_diagnosis=?,ph_chiefcomplaint=? where ph_pid=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtAreaNotes.getText());
                DbConn.pstmt.setString(2,txtAreaSubjective.getText());
                DbConn.pstmt.setString(3,txtAreaPresentation.getText());
                DbConn.pstmt.setString(4,txtAreaAllergies.getText());
                DbConn.pstmt.setString(5,txtAreaFamilyHistory.getText());
                DbConn.pstmt.setString(6,txtAreaAllergies.getText());
                DbConn.pstmt.setString(7,txtAreaPlanning.getText());
                DbConn.pstmt.setString(8, lblDoctor.getText());
                DbConn.pstmt.setString(9,txtAreaDiagnosis.getText());
                DbConn.pstmt.setString(10,txtAreaChiefComplaint.getText());
                DbConn.pstmt.setString(11, lblPID.getText());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    private void SaveTreatment(){
        int rowCount =0;
        if (tblTreatmentService.getRowCount()<=0){
                return;
        }else{
            while (rowCount != tblTreatmentService.getRowCount()){
                try{
                    DbConn.SQLQuery = "insert into tblpatientservices (ps_pid,ps_apptid,ps_code,ps_description,ps_price,ps_doctor,ps_qty,ps_value) "
                            + "values (?,?,?,?,?,?,?,?)";
                    DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                    DbConn.pstmt.setString(1, lblPID.getText());
                    DbConn.pstmt.setString(2, lblApptID.getText());
                    DbConn.pstmt.setString(3,tblTreatmentService.getValueAt(rowCount, 0).toString());
                    DbConn.pstmt.setString(4,tblTreatmentService.getValueAt(rowCount, 1).toString());
                    DbConn.pstmt.setString(5,tblTreatmentService.getValueAt(rowCount, 2).toString());
                    DbConn.pstmt.setString(6,lblDoctor.getText());
                    DbConn.pstmt.setString(7, tblTreatmentService.getValueAt(rowCount, 3).toString());
                    DbConn.pstmt.setString(8, tblTreatmentService.getValueAt(rowCount, 4).toString());
                    DbConn.pstmt.execute();
                    DbConn.pstmt.close();
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
                rowCount++;
            }
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
            java.util.logging.Logger.getLogger(frmDoctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDoctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDoctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDoctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDoctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TabHistory;
    private javax.swing.JPanel TabNotes;
    private javax.swing.JPanel TabPrescription;
    private javax.swing.JPanel TabTreatments;
    private javax.swing.JPanel TabVitalAndCC;
    private javax.swing.JButton btnAddICD;
    private javax.swing.JButton btnAddMedicine;
    private javax.swing.JButton btnAddTreatment;
    private javax.swing.JButton btnSaveAll;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbMedCert;
    private com.toedter.calendar.JDateChooser dateAppointment;
    private com.toedter.calendar.JDateChooser dateMedCertAppt;
    private com.toedter.calendar.JDateChooser dateMedCertDischarge;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lblAge;
    private javax.swing.JLabel lblApptID;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblCurrentDoctor;
    private javax.swing.JLabel lblDoctor;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblPID;
    private javax.swing.JLabel lblPatientName;
    private javax.swing.JPanel panelSummary;
    private javax.swing.JTabbedPane tabbedDoctor;
    private javax.swing.JTable tblAppointment;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTable tblHistorySummary;
    private javax.swing.JTable tblICDSearch;
    private javax.swing.JTable tblICDShow;
    private javax.swing.JTable tblMedicine;
    private javax.swing.JTable tblMedicineTable;
    private javax.swing.JTable tblPrescriptionSummary;
    private javax.swing.JTable tblTreatment;
    private javax.swing.JTable tblTreatmentService;
    private javax.swing.JTable tblTreatmentSummary;
    private javax.swing.JTextArea txtAreaAllergies;
    private javax.swing.JTextArea txtAreaChiefComplaint;
    private javax.swing.JTextArea txtAreaDiagnosis;
    private javax.swing.JTextArea txtAreaDiagnosisHistory;
    private javax.swing.JTextArea txtAreaFamilyHistory;
    private javax.swing.JTextArea txtAreaHabits;
    private javax.swing.JTextArea txtAreaNotes;
    private javax.swing.JTextArea txtAreaNotesHistory;
    private javax.swing.JTextArea txtAreaPastHistory;
    private javax.swing.JTextArea txtAreaPlanning;
    private javax.swing.JTextArea txtAreaPresentation;
    private javax.swing.JTextArea txtAreaSubjective;
    private javax.swing.JTextField txtBP1;
    private javax.swing.JTextField txtBloodSugar;
    private javax.swing.JTextField txtBp2;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextField txtICDSearch;
    private javax.swing.JTextField txtMedicineSearch;
    private javax.swing.JTextField txtPulse;
    private javax.swing.JTextField txtTemperature;
    private javax.swing.JTextField txtTreatment;
    private javax.swing.JTextField txtVisionLeft;
    private javax.swing.JTextField txtVisionRight;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration//GEN-END:variables
}
