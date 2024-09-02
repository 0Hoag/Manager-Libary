/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import dao.*;

import entity.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import utils.*;

/**
 *
 * @author Tien
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QLMuonTra extends javax.swing.JPanel {

    /**
     * Creates new form Test
     */
    DefaultTableModel sachTableModel;
    DefaultTableModel phieuMuonTableModel;
    DefaultTableModel sachMuonPhieuMuon;
    DefaultTableModel phieuTraModel;
    DefaultTableModel sachTraModel;

    PhieuMuonDAO phieuMuonDao;
    SachMuonDao sachMuonDao;
    SachDAO sachDAO;
    PhieuTraDAO phieuTraDAO;

    int selectedMaSach = -1;
    int selectedMaPhieuMuon = -1;
//    int soSachMuon = 0;

    List<Object[]> bookHardData = new ArrayList<>();
    List<PhieuMuon> listPhieuMuon = new ArrayList<>();
    List<SachMuon> bookData = new ArrayList<>();
    List<SachMuon> listGiveBackBook = new ArrayList<>();

    public QLMuonTra() {
        initComponents();
        txtNgayTaoPhieuTra.setText(XDate.toString(XDate.now()));
        txtNgayTra.setText(XDate.addDay(Integer.parseInt(txtSoNgayMuon.getText())));
        sachTableModel = (DefaultTableModel) tblSachPhieuMuon.getModel();
        sachMuonPhieuMuon = (DefaultTableModel) tblSachMuonPhieuMuon.getModel();
        phieuMuonTableModel = (DefaultTableModel) tblPhieuMuon.getModel();
        phieuTraModel = (DefaultTableModel) tblPhieuTra.getModel();
        sachTraModel = (DefaultTableModel) tblSachTra.getModel();

//        loadPhieuMuon();
//        loadTableSach();
    }

//    public PhieuMuon createPhieuMuonFromUI() {
//        DocGiaDAO dgDao = new DocGiaDAO();
//        int maDocGia = Integer.valueOf(txtMaDocGiaPhieuMuon.getText());
//        DocGia docGia = dgDao.selectById(maDocGia); // Lấy thông tin độc giả từ DAO
//
//        Date ngayMuon = new Date();
//        List<Sach> sachMuon = tblSachMuonPhieuMuon.getSachMuon(); // Lấy danh sách sách mượn từ tableModel hoặc thành phần tương tự
//
//        // Tạo đối tượng PhieuMuon từ thông tin thu thập được
//        PhieuMuon phieuMuon = new PhieuMuon();
//        //Thiếu So Ngay muon và số ngày trả?
//        phieuMuon.setMaDocGia(docGia);
//        phieuMuon.setNgayMuon(ngayMuon);
//        phieuMuon.setSachMuon(sachMuon);
//
//        return phieuMuon;
//    }
    public void loadTableSach() {
        sachDAO = new SachDAO();
        List<Sach> lists = sachDAO.load();
        sachTableModel.setRowCount(0);

//        lists.forEach(el -> System.out.println(el));
        int index = 0;
        for (Sach sach : lists) {
            Object[] rowData = {
                ++index,
                sach.getTenSach(),
                sach.getTacGia().getTen(),
                sach.getTheLoai().getTen(),
                sach.getSoBanSaoHienCo()
            };
            sachTableModel.addRow(rowData);
//            System.out.println(Arrays.toString(rowData)); // Để in ra dữ liệu dễ đọc hơn
        }
    }

    private void clearForm() {
        txtMaPhieuMuon.setText("");
        txtMaDocGiaPhieuMuon.setText("");
        bookHardData.clear();
        bookData.clear();
        sachMuonPhieuMuon.setRowCount(0);
        selectedMaPhieuMuon = -1;
    }
//---------------------------------
    //Sach muon

    private String getTableSachMuon() {
        sachDAO = new SachDAO();
        int rowCount = tblSachMuonPhieuMuon.getRowCount();
        String str = "";
        for (int i = 0; i < rowCount; i++) {
            int maSach = sachDAO.findByName((sachMuonPhieuMuon.getValueAt(i, 1).toString())).getMaSach();
            str += String.format("%s,%s", maSach,
                    sachMuonPhieuMuon.getValueAt(i, 2));
            if (i < rowCount - 1) {
                str += ";";
            }
        }
        return str;
    }

    private void createPhieuMuon() {
//        TaskManager.getInstance().submitTask(() -> {
        try {
            phieuMuonDao = new PhieuMuonDAO();
            phieuMuonDao.borrowBooks(Integer.parseInt(txtMaDocGiaPhieuMuon.getText()),
                    Integer.parseInt(txtSoNgayMuon.getText()),
                    txtTrangThai.getText(), getTableSachMuon());
            clearForm();
            XDialog.alert(this, "Lập phiếu thành công");
            loadPhieuMuon();
            loadTableSach();

        } catch (Exception e) {
            XDialog.alert(this, "Lập phiếu không thành công");
        }
//        });
    }

    private void updatePhieuMuon() {
//        TaskManager.getInstance().submitTask(() -> {
        try {
            phieuMuonDao = new PhieuMuonDAO();
            phieuMuonDao.updateBorrowBooks(Integer.parseInt(txtMaPhieuMuon.getText()), Integer.parseInt(txtMaDocGiaPhieuMuon.getText()),
                    Integer.parseInt(txtSoNgayMuon.getText()),
                    txtTrangThai.getText(), getTableSachMuon());
            clearForm();
            loadPhieuMuon();
            XDialog.alert(this, "Lập phiếu thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Lập phiếu không thành công");
        }
//        });
    }

    private int checkContain(List listData, Object check) {
        int index = -1;
        for (int i = 0; i < listData.size(); i++) {
            Object[] array = (Object[]) listData.get(i);
            List<Object> list = Arrays.asList(array); // Chuyển mảng thành danh sách

            if (list.contains(check)) {
                index = i; // Lưu chỉ số của mảng chứa phần tử
                break; // Ngừng vòng lặp khi tìm thấy phần tử
            }
        }
        return index;
    }

    private void updateQuantity(int index) {
        int quantity = Integer.parseInt(sachMuonPhieuMuon.getValueAt(index, 2).toString());
        quantity++;

        sachMuonPhieuMuon.setValueAt(quantity, index, 2);

        Object[] setQuantity = bookHardData.get(index);
        setQuantity[2] = quantity;
    }

    private void loadHardDataBorrowedBooks() {
        TaskManager.getInstance().submitTask(() -> {
            sachMuonPhieuMuon.setRowCount(0);
            int index = 0;
            for (Object[] b : bookHardData) {
                b[0] = ++index;
                sachMuonPhieuMuon.addRow(b);
            }
        });
    }

    private int checkSoSachMuon() {
        int soSachMuon = 0;
        for (Object[] sach : bookHardData) {
            soSachMuon += (int) sach[2];
        }
        return soSachMuon;
    }

    private void addBooks() {

        int soSachMuon = checkSoSachMuon();
        int row = tblSachPhieuMuon.getSelectedRow();
        String tenSach = (String) sachTableModel.getValueAt(row, 1);
        int index = checkContain(bookHardData, tenSach);
        int soLuongHienCo = (int) tblSachPhieuMuon.getValueAt(row, 4);
        System.out.println(soSachMuon);
        if (soSachMuon < 5) {
            if (soLuongHienCo < 1) {
                XDialog.alert(this, "Không đủ sách để mượn");
            } else {
                if (index == - 1) {
                    Object[] book = new Object[]{"", tenSach, 1};
                    bookHardData.add(book);
                } else {
                    int soLuong = (int) bookHardData.get(index)[2];
                    if (soLuong > soLuongHienCo) {
                        XDialog.alert(this, "Không đủ sách để mượn");
                    } else if (soLuong >= 2) {
                        XDialog.alert(this, "Chỉ được mượn tối đa 2 cuốn sách");
                    } else {
                        updateQuantity(index);
                    }
//
                }
            }

        } else {
            XDialog.alert(this, "Chỉ được mượn tối đa 5 cuốn sách");
        }

//            tblSachPhieuMuon.clearSelection();
//            loadTableBorrowBooks();
//            System.out.println(getTableSachMuon());
        TaskManager.getInstance().submitTask(() -> {
            loadTableSach();

        });
        TaskManager.getInstance().submitTask(() -> {
            loadHardDataBorrowedBooks();
        });
    }

    private void removeBook() {
        int row = tblSachMuonPhieuMuon.getSelectedRow();

//        if (selectedMaPhieuMuon < 0) {
//        soSachMuon -= (int) bookHardData.get(row)[2];
        bookHardData.remove(row);
//        }

        loadHardDataBorrowedBooks();

    }

    //---------------------------------
    //---------------------------------
    //phieu muon 
    private void loadTableBorrowBooks() {
        TaskManager.getInstance().submitTask(() -> {
            sachMuonPhieuMuon = (DefaultTableModel) tblSachMuonPhieuMuon.getModel();
            sachMuonDao = new SachMuonDao();
            sachDAO = new SachDAO();
            selectedMaSach = tblSachPhieuMuon.getSelectedRow();
            int maPhieu = Integer.parseInt(txtMaPhieuMuon.getText());

            String sql = "SELECT * FROM SachMuon WHERE maPhieuMuon=?";

            bookData = sachMuonDao.selectBySql(sql, maPhieu);
            sachMuonPhieuMuon.setRowCount(0);
            int index = 0;

            for (SachMuon s : bookData) {
                Object[] rows = new Object[]{
                    ++index,
                    s.getSach().getTenSach(),
                    s.getSoLuongBiMuon()

                };
                sachMuonPhieuMuon.addRow(rows);
            }

            tblSachMuonPhieuMuon.clearSelection();
        });

    }

    private void setFormPhieuMuon(PhieuMuon pm) {
        txtMaPhieuMuon.setText(pm.getMaPhieu() + "");
        txtMaDocGiaPhieuMuon.setText(pm.getMaDocGia().getDocGiaId() + "");
        txtSoNgayMuon.setText(pm.getSoNgayMuon() + "");
        txtNgayTra.setText(pm.getNgayTraDuTinh() + "");
        txtTrangThai.setText(pm.getTrangThaiTra() + "");
        loadTableBorrowBooks();
    }

    public void loadPhieuMuon() {

        phieuMuonTableModel.setRowCount(0);
        phieuMuonDao = new PhieuMuonDAO();
        listPhieuMuon = phieuMuonDao.selectAll();
        int index = 0;
        for (PhieuMuon pm : listPhieuMuon) {
            Object[] rows = new Object[]{
                ++index,
                pm.getMaPhieu(),
                pm.getMaDocGia().getDocGiaId(),
                pm.getSoLuongMuon(),
                pm.getNgayMuon(),
                pm.getNgayTraDuTinh(),
                pm.getTrangThaiTra()
            };
            phieuMuonTableModel.addRow(rows);
        }
        int tab = tabPanel.getSelectedIndex();
        if (tab == 0) {
            tblPhieuMuon.setModel(phieuMuonTableModel);
        } else {
            tblPhieuMuonPhieuTra.setModel(phieuMuonTableModel);
        }

    }

    //------------search-------------------
    private void searchPhieuMuon(String key) {
        List<PhieuMuon> list = phieuMuonDao.searchPhieuMuon(key);
        phieuMuonTableModel.setRowCount(0);
        int index = 0;
        for (PhieuMuon pm : list) {
            Object[] rows = new Object[]{
                ++index,
                pm.getMaPhieu(),
                pm.getMaDocGia().getDocGiaId(),
                pm.getSoLuongMuon(),
                pm.getNgayMuon(),
                pm.getNgayTraDuTinh(),
                pm.getTrangThaiTra()
            };
            phieuMuonTableModel.addRow(rows);
        }
    }

    private void searchSach(String key) {
        List<Sach> list = sachDAO.searchSach(key);
        sachTableModel.setRowCount(0);
        int index = 0;
        for (Sach sach : list) {
            Object[] rows = new Object[]{
                ++index,
                sach.getTenSach(),
                sach.getTacGia().getTen(),
                sach.getTheLoai().getTen(),
                sach.getSoBanSaoHienCo()
            };
            sachTableModel.addRow(rows);
        }
    }

    private void searchPhieuTra(String key) {
        List<PhieuTra> list = phieuTraDAO.searchPhieuTra(key);
        phieuTraModel.setRowCount(0);
        int index = 0;
        for (PhieuTra pt : list) {
            Object[] rows = new Object[]{
                ++index,
                pt.getMaPhieuTra(),
                pt.getPhieuMuon().getMaPhieu(),
                pt.getSoLuong(),
                pt.getNgayTao()
            };
            phieuTraModel.addRow(rows);
        }
    }

    //--------------PhieuTra----------
    private void loadPhieuTra() {
        phieuTraDAO = new PhieuTraDAO();
        List<PhieuTra> list = phieuTraDAO.load();
        phieuTraModel.setRowCount(0);
        int index = 0;
        for (PhieuTra pt : list) {
            Object[] rows = new Object[]{
                ++index,
                pt.getMaPhieuTra(),
                pt.getPhieuMuon().getMaPhieu(),
                pt.getSoLuong(),
                pt.getNgayTao()
            };
            phieuTraModel.addRow(rows);
        }
    }

    private void setFormPhieuMuonPhieuTra(PhieuMuon pm) {
        txtMaPhieuMuonPhieuTra.setText(pm.getMaPhieu() + "");
        txtMaDocGiaTraSach.setText(pm.getMaDocGia().getDocGiaId() + "");
        txtNgayTaoPhieuTra.setText(pm.getNgayTraDuTinh() + "");
        loadTableGiveBookBack();
    }

    private void loadTableGiveBookBack() {
        sachMuonDao = new SachMuonDao();
        sachDAO = new SachDAO();
        selectedMaSach = tblPhieuMuonPhieuTra.getSelectedRow();
        int maPhieu = Integer.parseInt(txtMaPhieuMuonPhieuTra.getText());
        sachMuonPhieuMuon = (DefaultTableModel) tblSachMuonPhieuTra.getModel();
        String sql = "SELECT * FROM SachMuon WHERE maPhieuMuon=?";

        bookData = sachMuonDao.selectBySql(sql, maPhieu);
        sachMuonPhieuMuon.setRowCount(0);
        int index = 0;
        for (SachMuon s : bookData) {
            Object[] rows = new Object[]{
                ++index,
                s.getSach().getTenSach(),
                s.getSoLuongBiMuon()

            };
            sachMuonPhieuMuon.addRow(rows);
        }

        tblSachMuonPhieuTra.clearSelection();
    }

    private void loadGiveBackBook() {
        sachTraModel.setRowCount(0);
        int index = 0;
        for (SachMuon s : listGiveBackBook) {
            Object[] rows = new Object[]{
                ++index,
                s.getSach().getTenSach(),
                s.getSoLuongBiMuon()

            };
            sachTraModel.addRow(rows);
        }

    }

    private void giveBackHardBook() {
        int row = tblSachMuonPhieuTra.getSelectedRow();
        listGiveBackBook.add(bookData.get(row));
        loadGiveBackBook();
    }

    private String getStringTableSachTra() {
        int rowCount = tblSachTra.getRowCount();
        String str = "";
        for (int i = 0; i < rowCount; i++) {
            int maSach = sachDAO.findByName((sachTraModel.getValueAt(i, 1).toString())).getMaSach();
            str += String.format("%s,%s", maSach,
                    sachTraModel.getValueAt(i, 2));
            if (i < rowCount - 1) {
                str += ";";
            }
        }
        return str;
    }

    private void giveBackBook() {

        try {
            int maPhieuMuon = Integer.parseInt(txtMaPhieuMuonPhieuTra.getText());
            phieuTraDAO.createPhieuTra(maPhieuMuon, getStringTableSachTra());
            XDialog.alert(this, "Trả sách thành công");
            clearFormPhieuTra();
        } catch (Exception e) {
            XDialog.alert(this, "Trả sách không thành công");
        }

    }

    private void clearFormPhieuTra() {
        txtMaDocGiaTraSach.setText("");
        txtMaPhieuMuonPhieuTra.setText("");
        txtNgayTaoPhieuTra.setText(XDate.toString(XDate.now()));
        listGiveBackBook.clear();
        bookData.clear();
        sachTraModel.setRowCount(0);
        sachMuonPhieuMuon.setRowCount(0);

    }

    //---------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabPanel = new javax.swing.JTabbedPane();
        pnlMuonSach = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSachMuonPhieuMuon = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSachPhieuMuon = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        btnThemSach = new javax.swing.JButton();
        btnXoaSach = new javax.swing.JButton();
        btnThemPhieuMuon = new javax.swing.JButton();
        btnCapNhatPhieuMuon = new javax.swing.JButton();
        btnLamMoiPhieuMuon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaDocGiaPhieuMuon = new javax.swing.JTextField();
        txtTimKiemSachPhieuMuon = new javax.swing.JTextField();
        txtSoNgayMuon = new javax.swing.JTextField();
        txtNgayTra = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();
        txtTimKiemPhieuMuon = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();
        pnlTraSach = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSachMuonPhieuTra = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieuMuonPhieuTra = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        btnTraSach = new javax.swing.JButton();
        btnThemPhieuTra = new javax.swing.JButton();
        btnCapNhatPhieuTra = new javax.swing.JButton();
        btnLamMoiPhieuTra = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaPhieuMuonPhieuTra = new javax.swing.JTextField();
        txtTimKiemPhieuMuonPhieuTra = new javax.swing.JTextField();
        txtNgayTaoPhieuTra = new javax.swing.JTextField();
        txtMaDocGiaTraSach = new javax.swing.JTextField();
        txtTimKiemPhieuTra = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSachTra = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1024, 720));
        setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(234, 239, 239));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 720));

        tabPanel.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel.setPreferredSize(new java.awt.Dimension(1024, 700));
        tabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPanelMouseClicked(evt);
            }
        });

        pnlMuonSach.setBackground(new java.awt.Color(234, 239, 239));
        pnlMuonSach.setPreferredSize(new java.awt.Dimension(1000, 700));
        pnlMuonSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMuonSachMouseClicked(evt);
            }
        });

        tblSachMuonPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sách", "Số lượng"
            }
        ));
        tblSachMuonPhieuMuon.setShowGrid(true);
        jScrollPane4.setViewportView(tblSachMuonPhieuMuon);

        tblSachPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên sách", "Tác giả", "Thể loại", "Sách hiện có"
            }
        ));
        tblSachPhieuMuon.setShowGrid(true);
        tblSachPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblSachPhieuMuonMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblSachPhieuMuon);

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu", "Mã độc giả", "Số lượng mượn", "Ngày mượn", "Ngày trả", "Trạng thái"
            }
        ));
        tblPhieuMuon.setShowGrid(true);
        tblPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblPhieuMuonMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblPhieuMuon);

        btnThemSach.setText("Thêm sách");
        btnThemSach.setFocusPainted(false);
        btnThemSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSachActionPerformed(evt);
            }
        });

        btnXoaSach.setText("Xóa sách");
        btnXoaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSachActionPerformed(evt);
            }
        });

        btnThemPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemPhieuMuon.setText("Thêm");
        btnThemPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhieuMuonActionPerformed(evt);
            }
        });

        btnCapNhatPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhatPhieuMuon.setText("Cập nhật");
        btnCapNhatPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhieuMuonActionPerformed(evt);
            }
        });

        btnLamMoiPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiPhieuMuon.setText("Làm mới");
        btnLamMoiPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiPhieuMuonMouseClicked(evt);
            }
        });
        btnLamMoiPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiPhieuMuonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Sách");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Mã độc giả:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Sách mượn");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Số ngày mượn:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ngày trả:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Trạng thái:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Phiếu mượn");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tìm kiếm:");

        txtTimKiemSachPhieuMuon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSachPhieuMuonKeyReleased(evt);
            }
        });

        txtSoNgayMuon.setEditable(false);
        txtSoNgayMuon.setText("30");

        txtNgayTra.setEditable(false);
        txtNgayTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTraActionPerformed(evt);
            }
        });

        txtTrangThai.setEditable(false);
        txtTrangThai.setText("Đang mượn");

        txtTimKiemPhieuMuon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemPhieuMuonKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Mã phiếu mượn:");

        txtMaPhieuMuon.setEditable(false);

        javax.swing.GroupLayout pnlMuonSachLayout = new javax.swing.GroupLayout(pnlMuonSach);
        pnlMuonSach.setLayout(pnlMuonSachLayout);
        pnlMuonSachLayout.setHorizontalGroup(
            pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addComponent(btnThemPhieuMuon)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhatPhieuMuon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoiPhieuMuon))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(8, 8, 8)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoNgayMuon)
                            .addComponent(txtNgayTra)
                            .addComponent(txtTrangThai)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)))
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaDocGiaPhieuMuon, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(txtMaPhieuMuon))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSach)
                            .addComponent(btnXoaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemSachPhieuMuon))
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemPhieuMuon))))
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
        );
        pnlMuonSachLayout.setVerticalGroup(
            pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaDocGiaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel7)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtSoNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(150, 150, 150)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemPhieuMuon)
                            .addComponent(btnCapNhatPhieuMuon)
                            .addComponent(btnLamMoiPhieuMuon))
                        .addGap(73, 73, 73))
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel3))
                            .addComponent(txtTimKiemSachPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(btnThemSach)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoaSach)))
                        .addGap(36, 36, 36)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addComponent(txtTimKiemPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(pnlMuonSachLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        tabPanel.addTab("Mượn sách", pnlMuonSach);

        pnlTraSach.setBackground(new java.awt.Color(234, 239, 239));
        pnlTraSach.setPreferredSize(new java.awt.Dimension(1000, 700));

        tblSachMuonPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Tên sách", "Số lượng"
            }
        ));
        tblSachMuonPhieuTra.setShowGrid(true);
        jScrollPane7.setViewportView(tblSachMuonPhieuTra);

        tblPhieuMuonPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Mã độc giả", "Số ngày mượn", "Số lượng mượn", "Ngày mượn", "Ngày trả", "Trạng thái"
            }
        ));
        tblPhieuMuonPhieuTra.setShowGrid(true);
        tblPhieuMuonPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblPhieuMuonPhieuTraMouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tblPhieuMuonPhieuTra);

        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Mã phiếu mượn", "Số lượng trả", "Ngày tạo"
            }
        ));
        tblPhieuTra.setShowGrid(true);
        tblPhieuTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblPhieuTraKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tblPhieuTra);

        btnTraSach.setText("Trả sách");
        btnTraSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraSachActionPerformed(evt);
            }
        });

        btnThemPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemPhieuTra.setText("Thêm");
        btnThemPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhieuTraActionPerformed(evt);
            }
        });

        btnCapNhatPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhatPhieuTra.setText("Cập nhật");

        btnLamMoiPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiPhieuTra.setText("Mới");
        btnLamMoiPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiPhieuTraActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Tìm kiếm:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Phiếu mượn");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã phiếu mượn:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Sách mượn");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Ngày tạo:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Mã độc giả");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Phiếu trả");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Tìm kiếm:");

        txtTimKiemPhieuMuonPhieuTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemPhieuMuonPhieuTraKeyReleased(evt);
            }
        });

        txtTimKiemPhieuTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemPhieuTraKeyReleased(evt);
            }
        });

        tblSachTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Tên sách", "Số lượng"
            }
        ));
        tblSachTra.setShowGrid(true);
        jScrollPane10.setViewportView(tblSachTra);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Sách trả");

        javax.swing.GroupLayout pnlTraSachLayout = new javax.swing.GroupLayout(pnlTraSach);
        pnlTraSach.setLayout(pnlTraSachLayout);
        pnlTraSachLayout.setHorizontalGroup(
            pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTraSachLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                        .addComponent(btnTraSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(pnlTraSachLayout.createSequentialGroup()
                            .addComponent(btnThemPhieuTra)
                            .addGap(18, 18, 18)
                            .addComponent(btnCapNhatPhieuTra)
                            .addGap(30, 30, 30)
                            .addComponent(btnLamMoiPhieuTra)
                            .addGap(48, 48, 48))
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlTraSachLayout.createSequentialGroup()
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMaPhieuMuonPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlTraSachLayout.createSequentialGroup()
                            .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlTraSachLayout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(txtNgayTaoPhieuTra))
                                .addGroup(pnlTraSachLayout.createSequentialGroup()
                                    .addGap(44, 44, 44)
                                    .addComponent(txtMaDocGiaTraSach))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .addComponent(jScrollPane9)
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemPhieuMuonPhieuTra))
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemPhieuTra)))
                .addGap(44, 44, 44))
        );
        pnlTraSachLayout.setVerticalGroup(
            pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTraSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(txtMaPhieuMuonPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtMaDocGiaTraSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addComponent(txtTimKiemPhieuMuonPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(btnTraSach))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtNgayTaoPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemPhieuTra)
                            .addComponent(btnCapNhatPhieuTra)
                            .addComponent(btnLamMoiPhieuTra))
                        .addGap(109, 109, 109))
                    .addGroup(pnlTraSachLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTraSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlTraSachLayout.createSequentialGroup()
                                .addComponent(txtTimKiemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(pnlTraSachLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel20)
                                .addGap(16, 16, 16)))
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(60, Short.MAX_VALUE))))
        );

        tabPanel.addTab("Trả sách", pnlTraSach);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuTraActionPerformed
        try {
            if (getStringTableSachTra().isEmpty()) {

                return;
            }
            giveBackBook();
            TaskManager.getInstance().submitTask(() -> {
                loadPhieuMuon();
                loadPhieuTra();
            });
        } catch (Exception e) {
            XDialog.alert(this, "Thêm không thành công");
        }


    }//GEN-LAST:event_btnThemPhieuTraActionPerformed

    private void btnLamMoiPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiPhieuMuonActionPerformed

    }//GEN-LAST:event_btnLamMoiPhieuMuonActionPerformed

    private void btnLamMoiPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiPhieuMuonMouseClicked
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiPhieuMuonMouseClicked

    private void btnThemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuMuonActionPerformed

        phieuMuonDao = new PhieuMuonDAO();
        int soSachMuon = checkSoSachMuon();
        String sql = """
                    select SUM(sm.soLuongBiMuon) from SACHMUON  sm
                    join PHIEUMUON pm on sm.maPhieuMuon = pm.MAPHIEU
                    where MADOCGIA = ?""";
        try {
            ResultSet rs = Connect.executeQuery(sql, txtMaDocGiaPhieuMuon.getText());
            int soLuongToiDa = 0;
            if (rs.next()) {
                soLuongToiDa = rs.getInt(1) + soSachMuon;
            }

            System.out.println(soLuongToiDa);
            if (soLuongToiDa > 5) {
                XDialog.alert(this, "Bạn chỉ được mượn tối đa 5 cuốn. Vui lòng trả sách để có thể mượn tiếp");
            } else if (getTableSachMuon().isEmpty()) {
                XDialog.alert(this, "Thêm không thành công");
            } else {
                createPhieuMuon();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnThemPhieuMuonActionPerformed

    private void tblPhieuMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMouseReleased
        // TODO add your handling code here:
        selectedMaPhieuMuon = tblPhieuMuon.getSelectedRow();
        PhieuMuon pm = listPhieuMuon.get(selectedMaPhieuMuon);
        setFormPhieuMuon(pm);

//        if (selectedRow >= 0) {
//            selectedMaPhieuMuon = Integer.parseInt(phieuMuonTableModel.getValueAt(selectedRow, 0).toString());
//            System.out.println("Đã chọn phiếu mượn: " + selectedMaPhieuMuon);
//        }
    }//GEN-LAST:event_tblPhieuMuonMouseReleased

    private void tblSachPhieuMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachPhieuMuonMouseReleased
        // TODO add your handling code here:
//        selectedRow = tblSachPhieuMuon.getSelectedRow();
//        if (selectedRow >= 0) {
//            selectedMaPhieuMuon = Integer.parseInt(sachTableModel.getValueAt(selectedRow, 0).toString());
//            System.out.println("Đã chọn sách: " + selectedMaPhieuMuon);
//        }
    }//GEN-LAST:event_tblSachPhieuMuonMouseReleased

    private void btnThemSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSachActionPerformed
        addBooks();

    }//GEN-LAST:event_btnThemSachActionPerformed

    private void btnXoaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSachActionPerformed
        removeBook();
    }//GEN-LAST:event_btnXoaSachActionPerformed

    private void btnCapNhatPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhieuMuonActionPerformed
        updatePhieuMuon();
    }//GEN-LAST:event_btnCapNhatPhieuMuonActionPerformed

    private void txtNgayTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTraActionPerformed

    private void txtTimKiemPhieuMuonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemPhieuMuonKeyReleased
        TaskManager.getInstance().submitTask(() -> {
            searchPhieuMuon(txtTimKiemPhieuMuon.getText());
        });
    }//GEN-LAST:event_txtTimKiemPhieuMuonKeyReleased

    private void txtTimKiemSachPhieuMuonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSachPhieuMuonKeyReleased
        searchSach(txtTimKiemSachPhieuMuon.getText());
    }//GEN-LAST:event_txtTimKiemSachPhieuMuonKeyReleased

    private void tblPhieuMuonPhieuTraMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonPhieuTraMouseReleased
        selectedMaPhieuMuon = tblPhieuMuonPhieuTra.getSelectedRow();
        PhieuMuon pm = listPhieuMuon.get(selectedMaPhieuMuon);
        setFormPhieuMuonPhieuTra(pm);
    }//GEN-LAST:event_tblPhieuMuonPhieuTraMouseReleased

    private void btnTraSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraSachActionPerformed
        giveBackHardBook();
    }//GEN-LAST:event_btnTraSachActionPerformed

    private void tabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPanelMouseClicked
        int tabSelected = tabPanel.getSelectedIndex();
        if (tabSelected == 0) {
            TaskManager.getInstance().submitTask(() -> {
                loadPhieuMuon();
                loadTableSach();
            });

        } else {
            TaskManager.getInstance().submitTask(() -> {
                loadPhieuMuon();
                loadPhieuTra();
            });

        }

    }//GEN-LAST:event_tabPanelMouseClicked

    private void btnLamMoiPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiPhieuTraActionPerformed
        clearFormPhieuTra();
    }//GEN-LAST:event_btnLamMoiPhieuTraActionPerformed

    private void txtTimKiemPhieuMuonPhieuTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemPhieuMuonPhieuTraKeyReleased
        TaskManager.getInstance().submitTask(() -> {
            searchPhieuMuon(txtTimKiemPhieuMuonPhieuTra.getText());
        });
    }//GEN-LAST:event_txtTimKiemPhieuMuonPhieuTraKeyReleased

    private void tblPhieuTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPhieuTraKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPhieuTraKeyReleased

    private void txtTimKiemPhieuTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemPhieuTraKeyReleased
        TaskManager.getInstance().submitTask(() -> {
            searchPhieuTra(txtTimKiemPhieuTra.getText());
        });
    }//GEN-LAST:event_txtTimKiemPhieuTraKeyReleased

    private void pnlMuonSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMuonSachMouseClicked

    }//GEN-LAST:event_pnlMuonSachMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLMuonTra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatPhieuMuon;
    private javax.swing.JButton btnCapNhatPhieuTra;
    private javax.swing.JButton btnLamMoiPhieuMuon;
    private javax.swing.JButton btnLamMoiPhieuTra;
    private javax.swing.JButton btnThemPhieuMuon;
    private javax.swing.JButton btnThemPhieuTra;
    private javax.swing.JButton btnThemSach;
    private javax.swing.JButton btnTraSach;
    private javax.swing.JButton btnXoaSach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel pnlMuonSach;
    private javax.swing.JPanel pnlTraSach;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuMuonPhieuTra;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTable tblSachMuonPhieuMuon;
    private javax.swing.JTable tblSachMuonPhieuTra;
    private javax.swing.JTable tblSachPhieuMuon;
    private javax.swing.JTable tblSachTra;
    private javax.swing.JTextField txtMaDocGiaPhieuMuon;
    private javax.swing.JTextField txtMaDocGiaTraSach;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtMaPhieuMuonPhieuTra;
    private javax.swing.JTextField txtNgayTaoPhieuTra;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSoNgayMuon;
    private javax.swing.JTextField txtTimKiemPhieuMuon;
    private javax.swing.JTextField txtTimKiemPhieuMuonPhieuTra;
    private javax.swing.JTextField txtTimKiemPhieuTra;
    private javax.swing.JTextField txtTimKiemSachPhieuMuon;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
