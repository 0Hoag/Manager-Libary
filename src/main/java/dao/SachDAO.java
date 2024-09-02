/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Nxb;
import entity.Sach;
import entity.TacGia;
import entity.TheLoai;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import utils.Connect;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // tự động set private và final nếu không cần có thể xóa
public class SachDAO extends QLThuVienDAO<Sach, Integer> {

    TacGiaDAO tacGiaDAO;
    NXBDAO nxbDao;
    TheLoaiDAO theLoaiDAO;

    public SachDAO() {
        this.tacGiaDAO = new TacGiaDAO();
        this.nxbDao = new NXBDAO();
        this.theLoaiDAO = new TheLoaiDAO();
    }

    public ArrayList<Sach> load() {
        ArrayList<Sach> lists = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT * FROM Sach";
            con = Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int maTacGia = rs.getInt("tacGia");
                int maNXB = rs.getInt("nxb");
                int maTheLoai = rs.getInt("theloai");

                TacGia tacGia = getTacGiaById(maTacGia);
                Nxb nxb = getNXBgetById(maNXB);
                TheLoai theLoai = getMaTheLoaiById(maTheLoai);

                Sach s = new Sach(
                        rs.getInt("maSach"),
                        rs.getString("tenSach"),
                        tacGia,
                        nxb,
                        theLoai,
                        rs.getInt("giaSach"),
                        rs.getInt("tongBanSao"),
                        rs.getInt("soBanSaoHienCo"),
                        rs.getString("hinh")
                );
                lists.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Connect.close(rs, ps, con);
        }
        return lists;
    }

    @Override
    public void insert(Sach s) {
        String sql = "INSERT INTO Sach VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connect.executeUpdate(sql,
                    s.getTenSach(),
                    s.getTacGia().getId(),
                    s.getNxb().getId(),
                    s.getTheLoai().getId(),
                    s.getGiaSach(),
                    s.getTongBanSao(),
                    s.getSoBanSaoHienCo(),
                    s.getHinh());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Sach s) {
        String sql = "UPDATE Sach SET tenSach=?, tacGia=?, nxb=?, theLoai=?, giaSach=?, tongBanSao=?, soBanSaoHienCo=?, hinh=? WHERE maSach=?";
        try {
            Connect.executeUpdate(sql,
                    s.getTenSach(),
                    s.getTacGia().getId(),
                    s.getNxb().getId(),
                    s.getTheLoai().getId(),
                    s.getGiaSach(),
                    s.getTongBanSao(),
                    s.getSoBanSaoHienCo(),
                    s.getHinh(),
                    s.getMaSach());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer maSach) {
        String sql = "DELETE FROM Sach WHERE maSach=?";
        try {
            Connect.executeUpdate(sql, maSach);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Sach> selectAll() {
        String sql = "SELECT * FROM Sach";
        return this.selectBySql(sql);
    }

    @Override
    public Sach selectById(Integer maSach) {
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = this.selectBySql(sql, maSach);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Sach> selectBySql(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = Connect.executeQuery(sql, args);
            while (rs.next()) {
                int maTacGia = rs.getInt("tacGia");
                int maNXB = rs.getInt("nxb");
                int maTheLoai = rs.getInt("theLoai");

                TacGia tacGia = getTacGiaById(maTacGia);
                Nxb nxb = getNXBgetById(maNXB);
                TheLoai theLoai = getMaTheLoaiById(maTheLoai);

                Sach s = new Sach(
                        rs.getInt("maSach"),
                        rs.getString("tenSach"),
                        tacGia,
                        nxb,
                        theLoai,
                        rs.getInt("giaSach"),
                        rs.getInt("tongBanSao"),
                        rs.getInt("soBanSaoHienCo"),
                        rs.getString("hinh")
                );
                list.add(s);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public TacGia getTacGiaById(int maTacGia) {
        return tacGiaDAO.selectById(maTacGia);
    }

    public Nxb getNXBgetById(int maNXB) {
        return nxbDao.selectById(maNXB);
    }

    public TheLoai getMaTheLoaiById(int theLoai) {
        return theLoaiDAO.selectById(theLoai);
    }

    public List<Sach> searchByTen(String tenSach) {
        String sql = "SELECT * FROM Sach WHERE tenSach LIKE ?";
        return this.selectBySql(sql, "%" + tenSach + "%");
    }

    public List<Sach> searchByTenOrTheLoai(String tenSach, String theLoai) {
        String sql = "SELECT * FROM Sach WHERE tenSach LIKE ? OR theLoai LIKE ?";
        return this.selectBySql(sql, "%" + tenSach + "%", "%" + theLoai + "%");
    }

    public Sach findByName(String tenSach) {
        String sql = "SELECT * FROM Sach WHERE tenSach=?";
        List<Sach> list = this.selectBySql(sql, tenSach);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<Sach> searchSach(String key) {
        String sql = """
                    SELECT * 
                     FROM sach s 
                     JOIN THELOAI tl ON tl.ID = s.theLoai
                     JOIN TACGIA tg ON tg.ID = s.tacGia
                     WHERE 
                         s.tenSach LIKE ?
                         OR tg.TEN LIKE ?
                         OR tl.TEN LIKE ?""";
        return selectBySql(sql, "%" + key + "%", "%" + key + "%", "%" + key + "%");

    }
}
