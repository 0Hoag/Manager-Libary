/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DocGia;
import entity.PhieuMuon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Date;
import java.util.List;
import utils.Connect;

/**
 *
 * @author PC
 */
public class PhieuMuonDAO extends QLThuVienDAO<PhieuMuon, Integer> {

    DocGiaDAO docGiaDAO = new DocGiaDAO();
    @Override
    public void insert(PhieuMuon s) {
        String sql = "INSERT INTO PhieuMuon VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connect.executeUpdate(sql,
                    s.getMaPhieu(),
                    s.getMaDocGia(),
                    s.getSoNgayMuon(),
                    s.getSoLuongMuon(),
                    s.getNgayMuon(),
                    s.getNgayTraDuTinh(),
                    s.getTrangThaiTra());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    
    public void update(PhieuMuon s) {
        String sql = "UPDATE PhieuMuon SET maDocGia=?, soNgayMuon=?, SoLuongMuon=?, ngayMuon=?, ngayTraDuTinh=?, trangThaiTra=? WHERE maPhieu=?";
        try {
            Connect.executeUpdate(sql,
                    s.getMaDocGia(),
                    s.getSoNgayMuon(),
                    s.getSoLuongMuon(),
                    s.getNgayMuon(),
                    s.getNgayTraDuTinh(),
                    s.getTrangThaiTra(),
                    s.getMaPhieu());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer maPhieu) {
        String sql = "DELETE FROM PhieuMuon WHERE maPhieu=?";
        try {
            Connect.executeUpdate(sql, maPhieu);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<PhieuMuon> selectAll() {
        
        String sql = "SELECT * FROM PhieuMuon";
        List<PhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql);
            while (rs.next()) {
                int maPhieu = rs.getInt(1);
                int maDocGia = rs.getInt(2); // Get maDocGia 

                DocGia docGia = docGiaDAO.selectById(maDocGia);

                int soNgayMuon = rs.getInt(3);
                int soLuongMuon = rs.getInt(4);
                Date ngayMuon = rs.getDate(5);
                Date ngayTraDuTinh = rs.getDate(6);
                String trangThaiTra = rs.getString(7);

                PhieuMuon pm = new PhieuMuon(maPhieu, docGia, soNgayMuon, soLuongMuon, ngayMuon, ngayTraDuTinh, trangThaiTra);
                list.add(pm);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PhieuMuon selectById(Integer key) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPhieu=?";
        List<PhieuMuon> list = this.selectBySql(sql, key);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<PhieuMuon> selectBySql(String sql, Object... args) {
        List<PhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql, args);
            while (rs.next()) {
                int docGia = rs.getInt("maDocGia");
                DocGia docGia1 = docGiaDAO.selectById(docGia);

                PhieuMuon s = new PhieuMuon(
                        rs.getInt("maPhieu"),
                        docGia1,
                        rs.getInt("soNgayMuon"),
                        rs.getInt("SoLuongMuon"),
                        rs.getDate("ngayMuon"),
                        rs.getDate("ngayTraDuTinh"),
                        rs.getString("trangThaiTra"));
                list.add(s);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public void borrowBooks(int maDocGia, int soNgayMuon, String trangThaiTra, String sachMuon) throws SQLException {
            String sql = "{CALL sp_muonSach(?,?,?,?)}";
            Connect.executeUpdate(sql, maDocGia, soNgayMuon, trangThaiTra, sachMuon);

    }
    public void updateBorrowBooks(int maPhieuMuon,int maDocGia, int soNgayMuon, String trangThaiTra, String sachMuon) {
        try {
            String sql = "{CALL sp_updateMuonSach(?,?,?,?,?)}";
            Connect.executeUpdate(sql, maPhieuMuon,maDocGia, soNgayMuon, trangThaiTra, sachMuon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<PhieuMuon> searchPhieuMuon(String key) {
        String sql = "select * from phieumuon where maphieu like ?";
        return selectBySql(sql, "%" + key + "%");
    }
    
}

//    public ArrayList<PhieuMuon> SearchMaSVMaPhieu(String ma)
//    {
//        ArrayList<PhieuMuon> lists = new ArrayList<PhieuMuon>();
//        try
//        {
//            String sql="SELECT * FROM PhieuMuon WHERE MaSV like '%" + ma +  "%'  or maPhieu like '%" + ma + "%'" ; //loi
//            Statement statement= con.createStatement();
//            ResultSet rs =statement.executeQuery(sql);
//          while(rs.next())
//          {
//            PhieuMuon pm = new PhieuMuon();
//            pm.setMaPhieuMuon(rs.getString(1));
//            pm.setMaSV(rs.getString(2));
//            pm.setNgayMuon(rs.getString(3));
//            pm.setNgayHenTra(rs.getString(4));
//            lists.add(pm);          
//          }
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return lists;
//    }
//    public void inphieumuon(String maPhieu){
//        try {
//            Hashtable map = new Hashtable();
//            JasperReport report = JasperCompileManager.compileReport("src/bkapt/com/print/XuatPhieuMuon.jrxml");
//            map.put("MaPhieuMuon", maPhieu);
//            JasperPrint p = JasperFillManager.fillReport(report,  map, con );
//            JasperViewer.viewReport(p, false);
////            JasperExportManager.exportReportToPdfFile(p, "test.pdf");
//        } catch (Exception ex) {
//            ex.getMessage();
//        }
//    }
//    public void indsphieumuon() throws JRException{
//    try {
//
//        JasperDesign jd = JRXmlLoader.load("src/bkapt/com/print/XuatDSPhieuMuon.jrxml");
//        JasperReport jr = JasperCompileManager.compileReport("src/bkapt/com/print/XuatDSPhieuMuon.jrxml");
//        JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), con);
//        JasperViewer.viewReport(jp);
////        JasperExportManager.exportReportToPdfFile(jp, "test1.pdf");
//        } catch (JRException e) {
//        
//        }
//    }

