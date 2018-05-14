
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
    }
