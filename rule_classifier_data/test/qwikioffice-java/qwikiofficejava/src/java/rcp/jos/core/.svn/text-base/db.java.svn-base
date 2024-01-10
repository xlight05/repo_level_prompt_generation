/*
 * db.java
 *
 * Created on 6 de febrero de 2008, 17:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rcp.jos.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author mrwlogistica
 */
public class db {

    public static String poolName ="jdbc/TestDB";

    /** Creates a new instance of db */
    public db() {
    }
    
    
    
    /** Creates a Connection to a Access Database */
    public static java.sql.Connection lookupConnection(String pool) throws Exception,SQLException {
        
        Connection cnx=null;
                
        // Obtain our environment naming context
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");

        // Look up our data source
        DataSource ds = (DataSource)
        envCtx.lookup(pool);

        cnx = ds.getConnection();
        
        return cnx;
        
    }
    public static void killConnection(Connection cnx) throws Exception,SQLException {
       cnx.close(); 
    }
    public static void commitTransaction(Connection cnx) throws Exception,SQLException {cnx.commit();}
    public static void rollbackTransaction(Connection cnx) throws Exception,SQLException {cnx.rollback();}
}

    
