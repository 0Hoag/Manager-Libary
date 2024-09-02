package dao;

import entity.Sach;
import entity.SachMuon;
import utils.Connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for SachMuon
 */
public class SachMuonDao extends QLThuVienDAO<SachMuon, Integer> {

    SachDAO sachDAO = new SachDAO();

    @Override
    public void insert(SachMuon s) {
        String sql = "INSERT INTO SachMuon VALUES (?, ?, ?)";
        try {
            Connect.executeUpdate(sql, s.getMaPhieuMuon(), s.getSach().getMaSach(), s.getSoLuongBiMuon());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(SachMuon s) {
        String sql = "UPDATE SachMuon SET maSach=?, soLuongBiMuon=? WHERE maPhieuMuon=?";
        try {
            Connect.executeUpdate(sql,
                    s.getSach().getMaSach(),
                    s.getSoLuongBiMuon(),
                    s.getMaPhieuMuon());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM SachMuon WHERE maPhieuMuon=?";
        try {
            Connect.executeUpdate(sql, key);
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public List<SachMuon> selectAll() {
        List<SachMuon> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHMUON";
        try {
            list = selectBySql(sql);
        } catch (Exception e) {
        }
        return list;
    }

    
    public SachMuon selectById(Integer key) {
        String sql = "SELECT * FROM SachMuon WHERE maPhieuMuon=?";
        List<SachMuon> list = this.selectBySql(sql, key);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<SachMuon> selectBySql(String sql, Object... args) {
        List<SachMuon> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql, args);
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                Sach s = sachDAO.selectById(maSach);

                SachMuon sachMuon = new SachMuon(
                        rs.getInt("maPhieuMuon"),
                        s,
                        rs.getInt("soLuongBiMuon")
                );
                list.add(sachMuon);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }


}
