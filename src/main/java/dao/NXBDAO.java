package dao;

import entity.Nxb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static utils.Connect.executeQuery;
import static utils.Connect.executeUpdate;

public class NXBDAO extends QLThuVienDAO<Nxb, Integer> {

    @Override
    public void insert(Nxb nxb) {
        String sql = "INSERT INTO NXB (ten) VALUES ( ?)";
        try {
            executeUpdate(sql, nxb.getTen());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Nxb nxb) {
        String sql = "UPDATE NXB SET ten = ? WHERE id = ?";
        try {
            executeUpdate(sql, nxb.getTen(), nxb.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM NXB WHERE id = ?";
        try {
            executeUpdate(sql, id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Nxb> searchTen(String key) {
        String sql = "SELECT * FROM NXB WHERE id LIKE ? OR ten LIKE ?";
        return selectBySql(sql, "%" + key + "%", "%" + key + "%");
    }

    @Override
    public List<Nxb> selectAll() {
        String sql = "SELECT * FROM NXB";
        return selectBySql(sql);
    }

    @Override
    public Nxb selectById(Integer id) {
        String sql = "SELECT * FROM NXB WHERE id = ?";
        List<Nxb> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Nxb> selectBySql(String sql, Object... args) {
        List<Nxb> list = new ArrayList<>();
        try (ResultSet rs = executeQuery(sql, args)) {
            while (rs.next()) {
                Nxb nxb = new Nxb(
                        rs.getInt("id"),
                        rs.getString("ten")
                );
                list.add(nxb);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public List<Nxb> searchTacGia(String key) {
        String sql = "select * from NXB where id like ? or ten like ?";
        return selectBySql(sql, "%" + key + "%", "%" + key + "%");
    }
    
    public Nxb selectByTen (String ten) {
        String sql = "SELECT * FROM NXB WHERE Ten LIKE ?";
        List<Nxb> list = selectBySql(sql, "%" + ten + "%");
        return !list.isEmpty() ? list.get(0) : null ;
    }
}
