package dao;

import entity.TacGia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connect;
import static utils.Connect.executeQuery;
import static utils.Connect.executeUpdate;

public class TacGiaDAO extends QLThuVienDAO<TacGia, Integer> { //not existed all

    @Override
    public void insert(TacGia tg) {
        String sql = "INSERT INTO TacGia (ten) VALUES (?)";
        try {
            executeUpdate(sql, tg.getTen());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(TacGia tg) {
        String sql = "UPDATE TacGia SET ten = ? WHERE id = ?";
        try {
            executeUpdate(sql, tg.getTen(), tg.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer tacGiaId) {
        String sql = "DELETE FROM TacGia WHERE id = ?";
        try {
            executeUpdate(sql, tacGiaId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<TacGia> selectAll() {
        String sql = "SELECT * FROM TacGia";
        return selectBySql(sql);
    }

    @Override
    public TacGia selectById(Integer id) {
        String sql = "SELECT * FROM TacGia WHERE id = ?";
        List<TacGia> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<TacGia> selectBySql(String sql, Object... args) {
        List<TacGia> list = new ArrayList<>();
        try (ResultSet rs = executeQuery(sql, args)) {
            while (rs.next()) {
                TacGia tg = new TacGia(
                        rs.getInt("id"),
                        rs.getString("ten")
                );
                list.add(tg);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public TacGia selectByTen (String ten) {
        String sql = "SELECT * FROM TacGia WHERE Ten LIKE ?";
        List<TacGia> list = selectBySql(sql, "%" + ten + "%");
        return !list.isEmpty() ? list.get(0) : null ;
    }

    
    public List<TacGia> searchTacGia(String key) {
        String sql = "select * from tacgia where id like ? or ten like ?";
        return selectBySql(sql, "%" + key + "%", "%" + key + "%");
    }

}
