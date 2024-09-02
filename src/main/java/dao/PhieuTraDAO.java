/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.DocGia;
import entity.PhieuMuon;
import entity.PhieuTra;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import utils.Connect;

/**
 *
 * @author Admin
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO extends QLThuVienDAO<PhieuTra, Integer> {

    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();

    public List<PhieuTra> load() {
        List<PhieuTra> lists = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PhieuTra";
            ResultSet rs = Connect.executeQuery(sql);

            while (rs.next()) {
                int phieuMuonId = rs.getInt("maPhieuMuon");
                PhieuMuon muon = phieuMuonDAO.selectById(phieuMuonId);

                PhieuTra s = new PhieuTra(
                        rs.getInt("maPhieuTra"),
                        muon,
                        rs.getInt("soLuongTra"),
                        rs.getDate("ngayTao"));
                lists.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        return lists;
    }

    private PhieuMuon fetchPhieuMuonById(int maPhieu) {
        return phieuMuonDAO.selectById(maPhieu);
    }

    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM PhieuTra WHERE maPhieuTra=?";
        try {
            Connect.executeUpdate(sql, key);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<PhieuTra> selectAll() {
        String sql = "SELECT * FROM PhieuTra";
        return this.selectBySql(sql);
    }

    @Override
    public PhieuTra selectById(Integer key) {
        String sql = "SELECT * FROM PhieuTra WHERE maPhieuTra=?";
        List<PhieuTra> list = this.selectBySql(sql, key);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<PhieuTra> selectBySql(String sql, Object... args) {
        List<PhieuTra> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql, args);
            while (rs.next()) {
                int phieuMuon = rs.getInt("maphieuMuon");
                PhieuMuon muon = fetchPhieuMuonById(phieuMuon);

                PhieuTra s = new PhieuTra(
                        rs.getInt("maPhieuTra"),
                        muon,
                        rs.getInt("soLuongtra"),
                        rs.getDate("ngayTao")
                );
                list.add(s);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(PhieuTra s) {
        String sql = "INSERT INTO PhieuTra VALUES (?, ?, ?, ?)";
        try {
            Connect.executeUpdate(sql,
                    s.getMaPhieuTra(),
                    s.getPhieuMuon(),
                    s.getSoLuong(),
                    s.getNgayTao());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(PhieuTra s) {
        String sql = "UPDATE PhieuTra SET phieuMuon=?, soLuong=?, ngayTao=? WHERE maPhieuTra=?";
        try {
            Connect.executeUpdate(sql,
                    s.getPhieuMuon(),
                    s.getSoLuong(),
                    s.getNgayTao(),
                    s.getMaPhieuTra());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<PhieuTra> searchPhieuTra(String key) {
        String sql = """
                     SELECT *
                     FROM PHIEUTRA 
                     where
                        MAPHIEUMUON like ?""";
        return selectBySql(sql, "%" + key + "%");
    }
    
    public void createPhieuTra(int maPhieuMuon, String sachTra){
        try {
            String sql = "{call sp_traSach(?,?)}";
            Connect.executeUpdate(sql, maPhieuMuon, sachTra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//    public ArrayList<PhieuTra> SearchMaSVMaPhieu(String ma)
//    {
//        ArrayList<PhieuTra> lists = new ArrayList<PhieuTra>();
//        try
//        {
//            String sql="SELECT * FROM PhieuTra WHERE MaSV like '%" + ma +  "%'  or maPhieuTra like '%" + ma + "%'" ;
//            Statement statement= con.createStatement();
//            ResultSet rs =statement.executeQuery(sql);
//          while(rs.next())
//          {
//            PhieuTra pm = new PhieuTra();
//            pm.setMaPhieuMuon(rs.getString(1));
//            pm.setMaPhieuTra(rs.getString(2));
//            pm.setMaSV(rs.getString(3));
//            pm.setSoLuongTra(rs.getInt(4));
//            pm.setNgayMuon(rs.getString(5));
//            pm.setNgayTra(rs.getString(6));
//
//            lists.add(pm);          
//          }
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return lists;
//    }
//    public void inphieuTra(String maPhieuTra){
//        try {
//            Hashtable map = new Hashtable();
//            JasperReport report = JasperCompileManager.compileReport("src/bkapt/com/print/XuatPhieuMuon.jrxml"); //? cai nay m setting lai nha t khong biet duong dan cua m lam gi
//            map.put("maPhieuTra", maPhieuTra);
//            JasperPrint p = JasperFillManager.fillReport(report,  map, con );
//            JasperViewer.viewReport(p, false);
////            JasperExportManager.exportReportToPdfFile(p, "test.pdf");
//        } catch (Exception ex) {
//            ex.getMessage();
//        }
//    }
//    
//    public void indsphieutra() throws JRException{
//    try {
//
//        JasperDesign jd = JRXmlLoader.load("src/bkapt/com/print/XuatDSPhieuMuon.jrxml"); //? cai nay m setting lai nha t khong biet duong dan cua m lam gi
//        JasperReport jr = JasperCompileManager.compileReport("src/bkapt/com/print/XuatDSPhieuMuon.jrxml"); //? cai nay m setting lai nha t khong biet duong dan cua m lam gi
//        JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), con);
//        JasperViewer.viewReport(jp);
////        JasperExportManager.exportReportToPdfFile(jp, "test1.pdf");
//        } catch (JRException e) {
//        
//        }
//    }
