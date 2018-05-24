
package main;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class DbConnection 
{
    ResultSet rs;
    PreparedStatement pstmt;
    Connection conn;
    String SQLQuery;
    String GetDateToday;
    static String LoggedUserName, LoggedUserAdmin, LoggedUserCategory;
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("0.00");
    int GetMaxInvoice;
    public static String RegViewDb,RegInsertDb,RegDeleteDb,RegEditDb,RegPrintDb,
            ApptViewDb,ApptInsertDb,ApptEditDb,ApptDeleteDb,ApptPrintDb,
            DocViewDb,DocInsertDb,DocEditDb,DocDeleteDb,DocPrintDb,
            BillViewDb,BillProcessDb,BillPrintDb,BillInvoiceGenDb,
            InqPatientDb,InqApptDb,InqSalesDb,InqInvoiceDb,
            MasterGenDb,MasterUserDb,MasterSecurityDb,MasterDrugDb,MasterTreatmentDb,MasterICDDb,MasterInsuranceDb,
            ReportMedCertDb;
    public void GetNextInvoice(){
        try{
            pstmt = conn.prepareStatement("Select * from tblinvoicedetail order by id_number DESC LIMIT 1");
            rs = pstmt.executeQuery();
            if (rs.next()){
                GetMaxInvoice = rs.getInt("id_number");
                GetMaxInvoice++;
            }else{
                GetMaxInvoice = 1;
            }
            pstmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void DoConnect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://166.62.10.53:3306/DbEmrPh","jopjop","strongadmin123");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DbEmrPh","root","root");
    System.out.println("web connected");
        }catch(SQLException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void CheckUserAccess(){
        try{
            pstmt = conn.prepareStatement("Select * from tblusersecurity where us_name =?");
            pstmt.setString(1, LoggedUserName);
            rs = pstmt.executeQuery();
            if (rs.next()){
                RegViewDb = rs.getString("us_regview");
                RegInsertDb = rs.getString("us_reginsert");
                RegEditDb = rs.getString("us_regedit");
                RegDeleteDb = rs.getString("us_regdelete");
                RegPrintDb= rs.getString("us_regprint");
                
                ApptViewDb= rs.getString("us_apptview");
                ApptInsertDb = rs.getString("us_apptinsert");
                ApptEditDb = rs.getString("us_apptedit");
                ApptDeleteDb = rs.getString("us_apptdelete");
                ApptPrintDb = rs.getString("us_apptprint");
                
                DocViewDb = rs.getString("us_docview");
                DocInsertDb = rs.getString("us_docinsert");
                DocEditDb= rs.getString("us_docedit");
                DocDeleteDb = rs.getString("us_docdelete");
                DocPrintDb = rs.getString("us_docprint");
                
                BillViewDb = rs.getString("us_billview");
                BillProcessDb = rs.getString("us_billprocess");
                BillPrintDb = rs.getString("us_billprint");
                BillInvoiceGenDb = rs.getString("us_billinvoicegen");
                
                InqPatientDb = rs.getString("us_inqpatient");
                InqApptDb = rs.getString("us_inqappointment");
                InqSalesDb = rs.getString("us_inqsales");
                InqInvoiceDb = rs.getString("us_inqinvoice");
                
                MasterGenDb = rs.getString("us_mastergen");
                MasterUserDb = rs.getString("us_masteruser");
                MasterSecurityDb = rs.getString("us_mastersecurity");
                MasterDrugDb = rs.getString("us_masterdrug");
                MasterTreatmentDb = rs.getString("us_mastertreatment");
                MasterInsuranceDb = rs.getString("us_masterinsurance");
                MasterICDDb = rs.getString("us_mastericd");
                ReportMedCertDb = rs.getString("us_reportmedcert");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
   
        
    }