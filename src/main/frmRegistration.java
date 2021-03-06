
package main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.smartcardio.*;
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

public class frmRegistration extends javax.swing.JFrame {
boolean add,edit;
int getMaxPID;
DbConnection DbConn = new DbConnection();
    public frmRegistration() {
        initComponents();
        DbConn.DoConnect();
        fillTable();
        FillNationality();
        DisableTexts();
        setDefaultCloseOperation(frmRegistration.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        tblPatient.setAutoCreateRowSorter(true);
        tblPatient.setDefaultEditor(Object.class, null);
        addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    DbConn.RegForm = false;
                }
                @Override
                public void windowClosing(WindowEvent e){
                    DbConn.RegForm = false;
                }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {SearchPatient();}

            @Override
            public void removeUpdate(DocumentEvent e) {SearchPatient();}

            @Override
            public void changedUpdate(DocumentEvent e) {SearchPatient();}
        });
        try {
        setIconImage(ImageIO.read(new File("src\\images\\ESSolutionsLogo.jpg")));
    } catch (IOException ex) {
        Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    private void FillNationality(){
        cmbNationality.removeAllItems();
        try{
            DbConn.SQLQuery = "select * from tblnationalitymaster order by nm_description";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.rs = DbConn.pstmt.executeQuery();
            while (DbConn.rs.next()){
                cmbNationality.addItem(DbConn.rs.getString("nm_description"));
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void DisableTexts(){
        txtAddress.setEnabled(false);
        txtDepartment.setEnabled(false);
        txtMobileNumber.setEnabled(false);
        txtName.setEnabled(false);
        txtOccupation.setEnabled(false);
        txtPosition.setEnabled(false);
        txtRemarks.setEnabled(false);
        dateBirth.setEnabled(false);
        rbMale.setEnabled(false);
        rbFemale.setEnabled(false);
        cmbNationality.setEnabled(false);
    }
    private void EnableTexts(){
        txtAddress.setEnabled(true);
        txtDepartment.setEnabled(true);
        txtMobileNumber.setEnabled(true);
        txtName.setEnabled(true);
        txtOccupation.setEnabled(true);
        txtPosition.setEnabled(true);
        txtRemarks.setEnabled(true);
        dateBirth.setEnabled(true);
        rbMale.setEnabled(true);
        rbFemale.setEnabled(true);
        cmbNationality.setEnabled(true);
    }
    private void SearchPatient(){
        try{
            DbConn.SQLQuery = "Select pd_pid,pd_name from tblpatientdetails where pd_name like ?";
            DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
            DbConn.pstmt.setString(1, "%" + txtSearch.getText()+ "%");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
           DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void fillTable(){
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement("SELECT pd_pid,pd_name from tblpatientdetails order by pd_name");
            DbConn.rs = DbConn.pstmt.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(DbConn.rs));
            DbConn.pstmt.close();
            tblPatient.getColumnModel().getColumn(0).setHeaderValue("ID");
            tblPatient.getColumnModel().getColumn(1).setHeaderValue("NAME");
        }catch(SQLException e){
            e.getMessage();
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

        btnGroupGender = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtOccupation = new javax.swing.JTextField();
        txtDepartment = new javax.swing.JTextField();
        txtPosition = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblPid = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtMobileNumber = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        rbFemale = new javax.swing.JRadioButton();
        rbMale = new javax.swing.JRadioButton();
        btnAdd = new javax.swing.JButton();
        dateBirth = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        cmbNationality = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPatient = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registration");

        jPanel1.setBackground(new java.awt.Color(214, 214, 194));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(214, 214, 194));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("REGISTRATION");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(679, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 830, 40));

        jPanel3.setBackground(new java.awt.Color(214, 214, 194));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("DEPARTMENT:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 285, 110, 25));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("SYSTEM ID:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 25));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("OCCUPATION:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 242, 110, 25));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("POSITION:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 328, 110, 25));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("REMARKS:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 110, 25));
        jPanel3.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 44, 390, -1));
        jPanel3.add(txtOccupation, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 244, 390, -1));

        txtDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepartmentActionPerformed(evt);
            }
        });
        jPanel3.add(txtDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 287, 390, -1));
        jPanel3.add(txtPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 330, 390, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("NAME:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, 80, 25));

        lblPid.setForeground(new java.awt.Color(0, 0, 0));
        lblPid.setText("PID");
        jPanel3.add(lblPid, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 5, 90, -1));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("DOB:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 88, 25));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("NATIONALITY:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 80, 30));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("MOBILE NUMBER:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 145, 140, 34));

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 145, 220, 86));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("ADDRESS:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, 25));
        jPanel3.add(txtMobileNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 185, 140, -1));

        txtRemarks.setColumns(20);
        txtRemarks.setRows(5);
        jScrollPane2.setViewportView(txtRemarks);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 364, 390, 115));

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("GENDER:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 70, 20));

        btnGroupGender.add(rbFemale);
        rbFemale.setForeground(new java.awt.Color(0, 0, 0));
        rbFemale.setText("Female");
        jPanel3.add(rbFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

        btnGroupGender.add(rbMale);
        rbMale.setForeground(new java.awt.Color(0, 0, 0));
        rbMale.setText("Male");
        jPanel3.add(rbMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, -1, -1));

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NewIcon.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 110, 40));
        jPanel3.add(dateBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 150, 30));

        jLabel7.setForeground(new java.awt.Color(153, 0, 0));
        jLabel7.setText("*");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 10, 10));

        jLabel9.setForeground(new java.awt.Color(153, 0, 0));
        jLabel9.setText("*");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 10, 10));

        jLabel17.setForeground(new java.awt.Color(153, 0, 0));
        jLabel17.setText("*");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 10, 10));

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/EditIcon.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel3.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 110, 40));

        cmbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 140, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 530, 490));

        jPanel4.setBackground(new java.awt.Color(214, 214, 194));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SaveIcon.png"))); // NOI18N
        jButton2.setText("SAVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 120, 60));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("PICTURE");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 100));

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 290, 200));

        jPanel5.setBackground(new java.awt.Color(214, 214, 194));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane3.setViewportView(tblPatient);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 270, 140));
        jPanel5.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, -1));

        jButton1.setText("RESET");
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, -1));

        btnPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printericon.png"))); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel5.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, -1, 50));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 290, 280));

        jLabel16.setFont(new java.awt.Font("Dialog", 2, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 0, 0));
        jLabel16.setText("All mandatory fields are marked with *");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 220, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
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
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Date TodayDate = new Date();
        if (txtName.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter a patient name","Name",JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return;
        }
        if (dateBirth.getDate() == null){
            JOptionPane.showMessageDialog(this, "Please enter birthdate","Birthdate",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!rbMale.isSelected() && !rbFemale.isSelected()){
            JOptionPane.showMessageDialog(this, "Please select gender","Gender",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (add == true || edit == false){
            getNextPID();
            try{
                String insertPDQuery = "INSERT INTO tblpatientdetails (pd_pid,pd_name,pd_gender,pd_dob,pd_address,pd_mobile,"
                        + "pd_occupation,pd_department,pd_remarks,pd_nationality,pd_position,pd_adduser,pd_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                DbConn.pstmt = DbConn.conn.prepareStatement(insertPDQuery);
                DbConn.pstmt.setInt(1, getMaxPID);
                DbConn.pstmt.setString(2, txtName.getText());
                if (rbMale.isSelected()){
                    DbConn.pstmt.setString(3, "MALE");
                }else if (rbFemale.isSelected()){
                    DbConn.pstmt.setString(3, "FEMALE");
                }
                DbConn.pstmt.setString(4, DbConn.sdfDate.format(dateBirth.getDate()));
                DbConn.pstmt.setString(5,txtAddress.getText());
                DbConn.pstmt.setString(6, txtMobileNumber.getText());
                DbConn.pstmt.setString(7, txtOccupation.getText());
                DbConn.pstmt.setString(8, txtDepartment.getText());
                DbConn.pstmt.setString(9,txtRemarks.getText());
                DbConn.pstmt.setString(10,cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(11,txtPosition.getText());
                DbConn.pstmt.setString(12,DbConn.LoggedUserName);
                DbConn.pstmt.setString(13,DbConn.sdfDate.format(TodayDate));
                DbConn.pstmt.execute();
                DbConn.pstmt.close();
                JOptionPane.showMessageDialog(this, "REGISTRATION SUCCESFUL");
                clearTexts();
                fillTable();
                DisableTexts();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }    
        }else if (add == false && edit == true){
            try{
                DbConn.SQLQuery = "Update tblpatientdetails set pd_name=?,pd_gender=?,pd_dob=?,pd_address=?,pd_mobile=?,"
                        + "pd_occupation=?,pd_department=?,pd_remarks=?,pd_nationality=?, pd_position=?,pd_moduser=?,pd_date=? "
                        + "where pd_pid=?";
                DbConn.pstmt = DbConn.conn.prepareStatement(DbConn.SQLQuery);
                DbConn.pstmt.setString(1, txtName.getText());
                if (rbMale.isSelected()){
                    DbConn.pstmt.setString(2, "MALE");
                }else if (rbFemale.isSelected()){
                    DbConn.pstmt.setString(2, "FEMALE");
                }
                DbConn.pstmt.setString(3, DbConn.sdfDate.format(dateBirth.getDate()));
                DbConn.pstmt.setString(4,txtAddress.getText());
                DbConn.pstmt.setString(5, txtMobileNumber.getText());
                DbConn.pstmt.setString(6, txtOccupation.getText());
                DbConn.pstmt.setString(7, txtDepartment.getText());
                DbConn.pstmt.setString(8,txtRemarks.getText());
                DbConn.pstmt.setString(9,cmbNationality.getSelectedItem().toString());
                DbConn.pstmt.setString(10, txtPosition.getText());
                DbConn.pstmt.setString(11, DbConn.LoggedUserName);
                DbConn.pstmt.setString(12, DbConn.sdfDate.format(TodayDate));
                DbConn.pstmt.setString(13, lblPid.getText());
                DbConn.pstmt.executeUpdate();
                DbConn.pstmt.close();
                JOptionPane.showMessageDialog(this, "Record Edited");
                clearTexts();
                fillTable();
                DisableTexts();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }    
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed
    private void clearTexts(){
        txtAddress.setText("");
        txtDepartment.setText("");
        txtMobileNumber.setText("");
        txtName.setText("");
        txtOccupation.setText("");
        txtPosition.setText("");
        txtRemarks.setText("");
        dateBirth.setDate(null);
        lblPid.setText("");
        cmbNationality.setSelectedIndex(-1);
    }
    private void txtDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepartmentActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        getNextPID();
        lblPid.setText(String.valueOf(getMaxPID));
        CleartTexts();
        EnableTexts();
        txtName.requestFocus();
        add = true;
        edit=false;
    }//GEN-LAST:event_btnAddActionPerformed
    private void CleartTexts(){
        txtAddress.setText("");
        txtDepartment.setText("");
        txtMobileNumber.setText("");
        txtName.setText("");
        txtOccupation.setText("");
        txtPosition.setText("");
        txtRemarks.setText("");
        txtSearch.setText("");
    }
    private void dateBirthMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFromMouseReleased

    }//GEN-LAST:event_dateFromMouseReleased
    private void LoadDetails(){
        int row = tblPatient.getSelectedRow();
        int ba = tblPatient.convertRowIndexToModel(row);
        String tblClick =tblPatient.getModel().getValueAt(ba, 0).toString();
        String fetchData = "Select * from tblpatientdetails where pd_pid=?";
        try{
            DbConn.pstmt = DbConn.conn.prepareStatement(fetchData);
            DbConn.pstmt.setString(1, tblClick);
            DbConn.rs = DbConn.pstmt.executeQuery();
            if (DbConn.rs.next()){
                txtAddress.setText(DbConn.rs.getString("pd_address"));
                txtDepartment.setText(DbConn.rs.getString("pd_department"));
                txtMobileNumber.setText(DbConn.rs.getString("pd_mobile"));
                txtName.setText(DbConn.rs.getString("pd_name"));
                txtOccupation.setText(DbConn.rs.getString("pd_occupation"));
                txtPosition.setText(DbConn.rs.getString("pd_position"));
                txtRemarks.setText(DbConn.rs.getString("pd_remarks"));
                cmbNationality.setSelectedItem(DbConn.rs.getString("pd_nationality"));
                lblPid.setText(DbConn.rs.getString("pd_pid"));
                dateBirth.setDate(DbConn.rs.getDate("pd_dob"));
                String GetGender = "";
                GetGender = DbConn.rs.getString("pd_gender");
                if(GetGender.equals("MALE")){
                    rbMale.setSelected(true);
                }else if (GetGender.equals("FEMALE")){
                    rbFemale.setSelected(true);
                }else{
                    rbMale.setSelected(false);
                    rbFemale.setSelected(false);
                }
            }
            DbConn.pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage(),"Registration Load Patient",JOptionPane.WARNING_MESSAGE);
        }
    }
    private void tblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPatientMouseClicked
        LoadDetails();
    }//GEN-LAST:event_tblPatientMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit = true;
        add = false;
        EnableTexts();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
         Map param = new HashMap();
        int row = tblPatient.getSelectedRow();
        int ba = tblPatient.convertRowIndexToModel(row);
        param.put("PID", tblPatient.getValueAt(ba, 0).toString());
//        param.put("dateTo", sdfDate.format(getToDate));
//        param.put("showStatus", cmbStatus.getSelectedItem().toString());
//        param.put("showClient", cmbClient.getSelectedItem().toString());
        try{
            DbConn.conn.close();
            Class.forName("com.mysql.jdbc.Driver");
            DbConn.conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/dbemrbeta","betapilfer","123456789");
            JasperDesign jd = JRXmlLoader.load(new File("src\\reports\\reportPatient.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param,DbConn.conn);
            JasperViewer.viewReport(jp,false);

        }catch(ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cmbNationality;
    private com.toedter.calendar.JDateChooser dateBirth;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPid;
    private javax.swing.JRadioButton rbFemale;
    private javax.swing.JRadioButton rbMale;
    private javax.swing.JTable tblPatient;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtMobileNumber;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtOccupation;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
