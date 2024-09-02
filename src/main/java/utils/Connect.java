/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Connect {

    private final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String url = "jdbc:sqlserver://26.68.248.135\\DAFPL:1433;databaseName=Ql_ThuVien;encrypt = false;";
    private final static String user = "sa";
    private final static String pass = "123456";

    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to get a connection to the database
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * Method to execute an update (INSERT, UPDATE, DELETE)
     */
    public static void executeUpdate(String sql, Object... args) throws SQLException {

        Connection conn = getConnection();
        PreparedStatement pstmt = sql.trim().startsWith("{") ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        setParameters(pstmt, args);
        pstmt.executeUpdate();

    }

    /**
     * Method to execute a query
     */
    public static ResultSet executeQuery(String sql, Object... args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, args);
        return pstmt.executeQuery();
    }

    /**
     * Method to set parameters for preparedSatement
     */
    private static void setParameters(PreparedStatement pstmt, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
    }

    /**
     * Method to close resources
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        Connection con;
        try {
            con = getConnection();
            if (con != null) {
                System.out.println("thanh cong");
            } else {
                System.out.println("that bai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
