/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
/**
 *
 * @author Nixforest
 */
public class SQLConnection {
    Connection connect = null;
    ResultSet result = null;
    Statement statement = null;
    String Host = "";
    String Port = "";
    String database = "";
    String username = "";
    String password = "";
    public SQLConnection(){
        Host = "WJND_FIELD-PC\\MYSERVER";
        Port = "1433";
        database = "DiaOC";
        username = "sa";
        password = "123456";
    }
    public Connection GetConnect() throws Exception{
        if(this.connect == null)
        {
            try
            {
                //Data Source=LAMPHAM-PC\SQLEXPRESS;Initial Catalog=DiaOC;User ID=sa;Password=***********
                // Kiểm tra Driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Tạo chuỗi kết nối
//                String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=DiaOC;user=sa;password=123456;";
//                String url = "jdbc:sqlserver://NIXFOREST-PC:1433;" + "databaseName=DiaOC;user=sa;password=123456;";
                String url = "jdbc:sqlserver://" + this.Host + ":" + this.Port +
                        ";databaseName=" + this.database + ";user=" + this.username +
                        ";password=" + this.password + ";";
                this.connect = DriverManager.getConnection(url);
            }catch(java.lang.ClassNotFoundException e){
                throw new Exception("SQL JDBC not found ...");
            }catch(java.sql.SQLException e){
                throw new Exception("can't connect to database server ... " + e.getMessage());
            }
        }
        // Trả về Connect
        return this.connect;
    }
    public Statement getStatement()throws SQLException,Exception{
        if(this.statement == null || this.statement.isClosed())
        {
            try{
                // Tạo một statement
                this.statement = this.GetConnect().createStatement();
            }catch(Exception e){
                throw new Exception("error " + e.getMessage());
            }
        }
        return this.statement;
    }
    public ResultSet executeQuery(String query)throws Exception{
        try
        {
            // Thực thi câu lệnh SQL
            this.result = getStatement().executeQuery(query);
        } catch(Exception e){
            throw new Exception("error "+e.getMessage());
        }
        // Thực thi xong trả statement về null
        this.statement = null;
        return this.result;
    }
    public int executeUpdate(String query)throws Exception{
        int num = Integer.MIN_VALUE;
        try{
            // Thực thi câu lệnh SQL, kết quả trả về là 1 số nguyên
            num = getStatement().executeUpdate(query);
        }catch(Exception e){
            throw new Exception("error "+e.getMessage());
        }finally{
            // Đóng kết nối
            this.Close();
        }
        return num;
    }
    public void Close() throws SQLException{
        if(this.result != null){
            this.result.close();
            this.result = null;
        }
        if(this.statement != null){
            this.statement.close();
            this.statement = null;
        }
        if(this.connect != null)
        {
            this.connect.close();
            this.connect = null;
        }
    }
    // Trả về một chuỗi, đầu vào là {?=call ...} ... là tên function
    public String SCallFunction(String sql) throws Exception{
        Connection con = GetConnect();
        CallableStatement csmt = con.prepareCall(sql);
        csmt.registerOutParameter(1,java.sql.Types.NCHAR);
        csmt.execute();
        String s = csmt.getString(1);
        con.close();
        return s;
    }
    // Trả về một số nguyên, đầu vào là {?=call ...} ... là tên function
    public int ICallFunction(String sql) throws Exception{
        Connection con = GetConnect();
        CallableStatement csmt = con.prepareCall(sql);
        csmt.registerOutParameter(1,java.sql.Types.INTEGER);
        csmt.execute();
        int s = csmt.getInt(1);
        con.close();
        return s;
    }
}
