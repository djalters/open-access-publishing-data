/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAFS_main;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author walte
 */
public class Connection_MySQL
{
    public Connection_MySQL()
    {
        
    }
    public Connection getConnection(String db_name,String user_name,String password)
    {
        Connection con=null;
        try
        {
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost/"+db_name+"?user="+user_name+"&password="+password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;        
    }
}

