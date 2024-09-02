/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Connect;
import utils.XDate;

/**
 *
 * @author Tien
 */
public class DocGiaDAO extends QLThuVienDAO<DocGia, Integer> {

    String INSERT = "{call sp_insertDocGia(?,?,?)}"; // o day ne, chi co 3 dau "?"
    String UPDATE = "UPDATE DOCGIA SET HOTEN = ?, SDT = ?, EMAIL = ? WHERE Id = ?";
    String DELETE = "DELETE FROM DOCGIA WHERE id = ?";
    String SELECT_ALL = "SELECT * FROM DOCGIA";
    String SELECT_BY_ID = "select * from DOCGIA where id = ?";
    String SELECT_FROM_VIEW = "SELECT * FROM v_DocGia";
    String SEARCH_BY_VIEW = "select * from v_DocGia where id like ? or hoten like ?";

    @Override
    public void insert(DocGia e) {
        try {
            Connect.executeUpdate(INSERT, e.getHoten(), e.getSdt(), e.getEmail()); //thang nay chi co 3 tham so thoi
        } catch (SQLException ex) {
            Logger.getLogger(DocGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(DocGia e) {
        try {
            Connect.executeUpdate(UPDATE, e.getHoten(), e.getSdt(), e.getEmail(), e.getDocGiaId());
        } catch (SQLException ex) {
            Logger.getLogger(DocGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Connect.executeUpdate(DELETE, id);
        } catch (SQLException ex) {
            Logger.getLogger(DocGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<DocGia> selectAll() {
        List<DocGia> docGiaList = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int DocGiaId = rs.getInt("Id");
                String hoten = rs.getString("HOTEN");
                String sdt = rs.getString("SDT");
                String email = rs.getString("EMAIL");
                Date ngayTao = rs.getDate("NGAYTAO");

                DocGia docGia = new DocGia(DocGiaId, hoten, sdt, email, ngayTao);
                docGiaList.add(docGia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return docGiaList;
    }

    @Override
    public DocGia selectById(Integer key) {
        try {
            ResultSet rs = Connect.executeQuery(SELECT_BY_ID, key);
            if (rs.next()) {
                int DocGiaId = rs.getInt(1);
                String hoTen = rs.getString(2);
                String sdt = rs.getString(3);
                String email = rs.getString(4);
                Date ngayTao = rs.getDate(5);

                return new DocGia(DocGiaId, hoTen, sdt, email, ngayTao);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<DocGia> selectBySql(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    

    public List<Object[]> selectByView() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(SELECT_FROM_VIEW);
            while (rs.next()) {
                Object[] rows = new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDate(5),
                    rs.getInt(6),
                    rs.getInt(7)
                };

                list.add(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Object[]> searchByView(String key) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(SEARCH_BY_VIEW, "%" + key + "%","%" + key + "%");
            while (rs.next()) {
                Object[] rows = new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDate(5),
                    rs.getInt(6),
                    rs.getInt(7)
                };

                list.add(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
