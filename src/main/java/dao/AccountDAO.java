/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Connect;

/**
 *
 * @author Tien
 */
public class AccountDAO {

    private static int currentId;
    String INSERT = "{call sp_insertAccount(?,?,?,?,?)}";
    String UPDATE_PASS = "UPDATE ACCOUNT SET PASS =? WHERE username = ?";
    String SELECT_BY_ID = "select * from ACCOUNT where username = ?";
    String SELECT_BY_EMAIL = "SELECT AC.* FROM ACCOUNT AC JOIN DOCGIA DG ON AC.ID = DG.ID WHERE EMAIL =? ";

    public void update(Account e) {
        try {
            Connect.executeUpdate(UPDATE_PASS, e.getPass(), e.getUsername());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Account selectById(String key) {
        try {
            ResultSet rs = Connect.executeQuery(SELECT_BY_ID, key);
            if (rs.next()) {
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String role = rs.getString(3);
                int id = rs.getInt(4);
                currentId = id;
                return new Account(username, pass, role, id);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public Account selectByEmail(String email) {
        try {
            ResultSet rs = Connect.executeQuery(SELECT_BY_EMAIL, email);
            if (rs.next()) {
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String role = rs.getString(3);
                int id = rs.getInt(4);
                return new Account(username, pass, role, id);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public void createAccount(String username, String pass, String hoTen, String sdt, String email) {
        try {
            Connect.executeUpdate(INSERT, username, pass, hoTen, sdt, email);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
