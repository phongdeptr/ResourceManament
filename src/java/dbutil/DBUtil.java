/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class DBUtil {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SE140633_SE010D_P0016";
            con = DriverManager.getConnection(url, "sa", "123456");
        } catch (ClassNotFoundException | SQLException e) {
        }
        return con;
    }

}
