/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Nxb;
import entity.TacGia;
import entity.TheLoai;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import static utils.Connect.executeQuery;
import static utils.Connect.executeUpdate;

/**
 *
 * @author PC
 */
public class TheLoaiDAO extends QLThuVienDAO<TheLoai, Integer>{

    @Override
    public void insert(TheLoai e) {
        String sql = "INSERT INTO TheLoai (ten) VALUES ( ?)";
        try {
            executeUpdate(sql, e.getTen());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(TheLoai e) {
        String sql = "UPDATE TheLoai SET ten = ? WHERE id = ?";
        try {
            executeUpdate(sql, e.getTen(), e.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM TheLoai WHERE id = ?";
        try {
            executeUpdate(sql, key);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<TheLoai> selectAll() {
        String sql = "SELECT * FROM TheLoai";
        return selectBySql(sql);
    }

    @Override
    public TheLoai selectById(Integer key) {
        String sql = "SELECT * FROM THELOAI WHERE id = ?";
        List<TheLoai> list = selectBySql(sql, key);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<TheLoai> selectBySql(String sql, Object... args) {
        List<TheLoai> list = new ArrayList<>();
        try (ResultSet rs = executeQuery(sql, args)) {
            while (rs.next()) {
                TheLoai tl = new TheLoai(
                        rs.getInt("id"),
                        rs.getString("ten")
                );
                list.add(tl);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public List<TheLoai> searchTacGia(String key) {
        String sql = "select * from theLoai where id like ? or ten like ?";
        return selectBySql(sql, "%" + key + "%", "%" + key + "%");
    }
    
    public TheLoai selectByTen (String ten) {
        String sql = "SELECT * FROM TheLoai WHERE Ten LIKE ?";
        List<TheLoai> list = selectBySql(sql, "%" + ten + "%");
        return !list.isEmpty() ? list.get(0) : null ;
    }
}
