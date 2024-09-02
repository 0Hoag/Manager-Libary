package dao;

import entity.DocGia;
import entity.PhieuPhat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Connect;

public class ViPhamDAO extends QLThuVienDAO<PhieuPhat, Integer> {
    DocGiaDAO dgDao = new DocGiaDAO();
    @Override
    public void insert(PhieuPhat e) {
       String sql = "{call TaoPhieuPhat(?,?,?,?)}";
        try {
            Connect.executeUpdate(sql,e.getMaDocGia().getDocGiaId(),e.getNoiDungPhat(),e.getPhiphat(),e.getNgayTao());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(PhieuPhat e) {
        String sql = "update phieuphat set madocgia=?, noidungphat=?, phiphat=? where id =?";
        try {
            Connect.executeUpdate(sql,e.getMaDocGia().getDocGiaId(),e.getNoiDungPhat(),e.getPhiphat(),e.getPhieuPhatId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer key) {
        
    }

    @Override
    public List<PhieuPhat> selectAll() {
      String sql = "select * from phieuphat";
      return selectBySql(sql);
    }

    @Override
    public PhieuPhat selectById(Integer key) {
        String sql = "Select * from phieuphat where id = ?";
        List<PhieuPhat> list = selectBySql(sql, key);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public List<PhieuPhat> selectBySql(String sql, Object... args)   {
        List<PhieuPhat> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql, args);
            while (rs.next()) {
                int maPhieuTra = rs.getInt(1);
                int madocGia = rs.getInt(2);
                DocGia docGia = dgDao.selectById(madocGia);
                String noiDungPhat = rs.getString(3);
                int phiPhat = rs.getInt(4);
                Date ngayTao = rs.getDate(5);

                PhieuPhat p = new PhieuPhat(maPhieuTra, docGia, noiDungPhat, phiPhat, ngayTao);
                list.add(p);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    
}
