/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import dao.DocGiaDAO;
import dao.PhieuMuonDAO;
import dao.SachDAO;
import dao.SachMuonDao;
import entity.DocGia;
import entity.PhieuMuon;
import entity.SachMuon;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utils.TaskManager;
import utils.XDate;
import utils.XDialog;
import utils.XValidate;

/**
 *
 * @author Tien
 */
public class QLDocGia extends javax.swing.JPanel {

    /**
     * Creates new form DocGia
     */
    DefaultTableModel phieuMuonTableModel;
    DefaultTableModel sachMuonPhieuMuon;

    SachMuonDao sachMuonDao;

    DefaultTableModel tableDocGiaModelDocGia;
    DocGiaDAO docGiaDAO; // thang nay chi moi la khai bao thoi
    PhieuMuonDAO phieuMuonDao;

    List<PhieuMuon> listPhieuMuon = new ArrayList<>();
    List<SachMuon> bookData = new ArrayList<>();

    int selectedMaPhieuMuon = -1;
    int column = 0;

    public QLDocGia() {
        initComponents();
        tableDocGiaModelDocGia = (DefaultTableModel) tblDocGia.getModel();
        phieuMuonTableModel = (DefaultTableModel) tblPhieuMuon.getModel();
        
        txtNgayTao.setText(XDate.toString(XDate.now()));
        load();
//        loadPhieuMuon();
    }

    public void load() {
        tableDocGiaModelDocGia.setRowCount(0);
        docGiaDAO = new DocGiaDAO();
        List<Object[]> list = docGiaDAO.selectByView();
        int index = 0;
        for (Object[] row : list) {
            Object[] newRow = new Object[tblDocGia.getColumnCount()];
            newRow[0] = ++index;
            System.arraycopy(row, 0, newRow, 1, row.length);
            tableDocGiaModelDocGia.addRow(newRow);
        }
    }

    public void insert() {
        docGiaDAO = new DocGiaDAO(); /// khoi tao dao ne
        if (!XValidate.checkNullText(txtMaDocGia, txtEmail, txtHoTen, txtSDT)) {
            return;
        }

        String hoten = txtHoTen.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        DocGia docGia = new DocGia();
        docGia.setHoten(hoten);
        docGia.setSdt(sdt);
        docGia.setEmail(email);

        try {
            docGiaDAO.insert(docGia);
            JOptionPane.showMessageDialog(this, "Thêm độc giả thành công");
            load();
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm độc giả: " + ex.getMessage());
        }

    }

    public void update() {
        docGiaDAO = new DocGiaDAO();
        try {
        int DocGiaId = Integer.parseInt(txtMaDocGia.getText());
        String hoten = txtHoTen.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        Date ngayTao = XDate.toDate(txtNgayTao.getText(), "dd-MM-yyyy");
        DocGia docGia = new DocGia();
        docGia.setDocGiaId(DocGiaId);
        docGia.setHoten(hoten);
        docGia.setSdt(sdt);
        docGia.setEmail(email);
        docGia.setNgayTao(ngayTao);

        
            docGiaDAO.update(docGia);
            XDialog.alert(this, "Cập nhật thành công");
            load();
        } catch (Exception ex) {
            XDialog.alert(this, "Cập nhật thất bại: " + ex.getMessage());
        }
    }

    public void detete() {
        docGiaDAO = new DocGiaDAO();
        int DocGiaId = Integer.parseInt(txtMaDocGia.getText());
        try {
            docGiaDAO.delete(DocGiaId);
            JOptionPane.showMessageDialog(this, "Xóa độc giả thành công");
            load();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa độc giả: " + ex.getMessage());
        }
    }

    public void clearForm() {
        txtMaDocGia.setText("");
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSDT.setText("");
        txtNgayTao.setText(XDate.toString(XDate.now()));
        txtTimKiem.setText("");
        load();
    }

    public void searchDocGia() {
        String key = txtTimKiem.getText().trim();
        tableDocGiaModelDocGia.setRowCount(0);
        List<Object[]> list = docGiaDAO.searchByView(key);
        int index = 0;
        for (Object[] row : list) {
            Object[] newRow = new Object[tblDocGia.getColumnCount()];
            newRow[0] = ++index;
            System.arraycopy(row, 0, newRow, 1, row.length);
            tableDocGiaModelDocGia.addRow(newRow);
        }
    }

    private void setFormPhieuMuon(PhieuMuon pm) {
        txtMaPhieuMuon.setText(pm.getMaPhieu() + "");
        txtMaDocGiaPhieuMuon.setText(pm.getMaDocGia().getDocGiaId() + "");
        txtSoNgayMuon.setText(pm.getSoNgayMuon() + "");
        txtNgayTra.setText(pm.getNgayTraDuTinh() + "");
        txtTrangThai.setText(pm.getTrangThaiTra() + "");
        loadTableBorrowBooks();
    }

    private void loadTableBorrowBooks() {
        TaskManager.getInstance().submitTask(() -> {
            sachMuonPhieuMuon = (DefaultTableModel) tblSachMuonPhieuMuon.getModel();
            sachMuonDao = new SachMuonDao();

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

    public void loadPhieuMuon() {
        int maPhieu = Integer.parseInt(txtMaDocGia.getText());
        phieuMuonTableModel.setRowCount(0);
        phieuMuonDao = new PhieuMuonDAO();
        String sql;
        if (column == 6) {
            sql = "SELECT * FRoM PHIEUMUON WHERE MADOCGIA = ? AND SOLUONGMUON > 0 AND TRANGTHAITRA = N'Đang mượn'";
            listPhieuMuon = phieuMuonDao.selectBySql(sql, maPhieu);
        } else {
            sql = "SELECT * FRoM PHIEUMUON WHERE MADOCGIA = ? AND SOLUONGMUON > 0 AND TRANGTHAITRA = N'Đang mượn' AND NGAYTRADUTINH < GETDATE()";
            listPhieuMuon = phieuMuonDao.selectBySql(sql, maPhieu);
        }
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

        tblPhieuMuon.setModel(phieuMuonTableModel);

    }

    private void clearFormPhieuTra() {
        txtMaPhieuMuon.setText("");
        txtMaDocGiaPhieuMuon.setText("");
        bookData.clear();
        sachMuonPhieuMuon.setRowCount(0);
        selectedMaPhieuMuon = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaDocGia = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocGia = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        pnlMuonSach = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSachMuonPhieuMuon = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtMaDocGiaPhieuMuon = new javax.swing.JTextField();
        txtSoNgayMuon = new javax.swing.JTextField();
        txtNgayTra = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1024, 720));

        jPanel1.setBackground(new java.awt.Color(234, 239, 239));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Họ và tên:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã độc giả:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Ngày tạo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("SDT");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Tìm kiếm:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("QUẢN LÝ ĐỘC GIẢ");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtMaDocGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        tblDocGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Họ và tên", "SDT", "Email", "Ngày tạo", "Số sách mượn", "Số sách quá hạn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDocGia.setShowGrid(true);
        tblDocGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocGia);

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(79, 79, 79)
                        .addComponent(btnCapNhat)
                        .addGap(51, 51, 51)
                        .addComponent(btnMoi)
                        .addGap(385, 385, 385))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                            .addComponent(txtHoTen)
                            .addComponent(txtMaDocGia)
                            .addComponent(txtNgayTao)
                            .addComponent(txtTimKiem))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(357, 357, 357))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnCapNhat)
                    .addComponent(btnMoi))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        pnlTab.addTab("Độc giả", jPanel1);

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Phiếu mượn:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mã độc giả:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Sách mượn");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Số ngày mượn:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Ngày trả:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Trạng thái:");

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
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12))
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
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)))
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaDocGiaPhieuMuon, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(txtMaPhieuMuon))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMuonSachLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(383, 383, 383))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(jLabel10)
                            .addComponent(txtMaDocGiaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel11)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtSoNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(250, 250, 250))
                    .addGroup(pnlMuonSachLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pnlTab.addTab("Mượn sách", pnlMuonSach);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTab, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTab, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblDocGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocGiaMouseClicked
        // TODO add your handling code here:
        int index = tblDocGia.getSelectedRow();
        if (index >= 0) {
            // Lấy dữ liệu từ bảng và hiển thị lên các trường nhập liệu
            String docGiaId = tblDocGia.getValueAt(index, 1).toString();
            String hoTen = tblDocGia.getValueAt(index, 2).toString();
            String sdt = tblDocGia.getValueAt(index, 3).toString();
            String email = tblDocGia.getValueAt(index, 4).toString();
            String ngayTao = tblDocGia.getValueAt(index, 5).toString();

            txtMaDocGia.setText(docGiaId);
            txtHoTen.setText(hoTen);
            txtSDT.setText(sdt);
            txtEmail.setText(email);
            txtNgayTao.setText(ngayTao);
        }

        column = tblDocGia.getSelectedColumn();
        if (column == 6 && evt.getClickCount() == 2) {
            pnlTab.setSelectedIndex(1);
            if (!txtMaDocGiaPhieuMuon.getText().equals("")) {
                clearFormPhieuTra();
            }

            loadPhieuMuon();
        }
        if (column == 7 && evt.getClickCount() == 2) {
            pnlTab.setSelectedIndex(1);
            clearFormPhieuTra();
            loadPhieuMuon();
        }

    }//GEN-LAST:event_tblDocGiaMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        searchDocGia();

    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblPhieuMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMouseReleased
        // TODO add your handling code here:
        selectedMaPhieuMuon = tblPhieuMuon.getSelectedRow();
        PhieuMuon pm = listPhieuMuon.get(selectedMaPhieuMuon);
        setFormPhieuMuon(pm);

    }//GEN-LAST:event_tblPhieuMuonMouseReleased

    private void txtNgayTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTraActionPerformed

    private void pnlMuonSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMuonSachMouseClicked

    }//GEN-LAST:event_pnlMuonSachMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLDocGia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel pnlMuonSach;
    private javax.swing.JTabbedPane pnlTab;
    private javax.swing.JTable tblDocGia;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblSachMuonPhieuMuon;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaDocGia;
    private javax.swing.JTextField txtMaDocGiaPhieuMuon;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoNgayMuon;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
