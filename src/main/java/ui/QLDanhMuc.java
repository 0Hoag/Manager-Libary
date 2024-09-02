/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import utils.*;
import java.util.*;
import dao.*;
import entity.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tien
 */
public class QLDanhMuc extends javax.swing.JPanel {

    /**
     * Creates new form DanhMuc
     */
    DefaultTableModel modelTacGia;
    DefaultTableModel modelNxb;
    DefaultTableModel modelTheLoai;

    List<Nxb> listNxb = new ArrayList<>();
    List<TacGia> listTacGia = new ArrayList<>();
    List<TheLoai> listTheLoai = new ArrayList<>();

    TheLoaiDAO tlDao = new TheLoaiDAO();
    TacGiaDAO tgDao = new TacGiaDAO();
    NXBDAO nxbDao = new NXBDAO();

    public QLDanhMuc() {
        initComponents();
        modelTacGia = (DefaultTableModel) tblTacGia.getModel();
        modelNxb = (DefaultTableModel) tblNXB.getModel();
        modelTheLoai = (DefaultTableModel) tblTL.getModel();
        loadTableNxb();
        loadTableTacGia();
        loadTableTheLoai();
    }

    public void loadTableNxb() {
        listNxb = nxbDao.selectAll();
        modelNxb.setRowCount(0);
        int index = 0;
        for (Nxb nxb : listNxb) {
            Object[] rows = new Object[]{
                ++index,
                nxb.getId(),
                nxb.getTen()
            };
            modelNxb.addRow(rows);
        }

    }

    public void loadTableTacGia() {
        listTacGia = tgDao.selectAll();
        modelTacGia.setRowCount(0);
        int index = 0;
        for (TacGia tg : listTacGia) {
            ++index;
            Object[] rows = new Object[]{
                index,
                tg.getId(),
                tg.getTen()
            };
            modelTacGia.addRow(rows);
        }
    }

    public void loadTableTheLoai() {
        listTheLoai = tlDao.selectAll();
        modelTheLoai.setRowCount(0);
        int index = 0;
        for (TheLoai tl : listTheLoai) {
            ++index;
            Object[] rows = new Object[]{
                index,
                tl.getId(),
                tl.getTen()
            };
            modelTheLoai.addRow(rows);
        }

    }

    private void insert(Object entity) {
        try {
            if (entity instanceof TacGia tacGia) {
                if(!XValidate.checkNullText(txtTenTacGia)){
                    return;
                }
                tgDao.insert(tacGia);
            } else if (entity instanceof TheLoai theLoai) {
                if(!XValidate.checkNullText(txtTenTL)){
                    return;
                }
                tlDao.insert(theLoai);
            } else if (entity instanceof Nxb nxb) {
                if(!XValidate.checkNullText(txtTenNxb)){
                    return;
                }
                nxbDao.insert(nxb);
            } else {
                throw new RuntimeException("Đối tượng không xác định");
            }
            XDialog.alert(this, "Thêm thành công");
        } catch (Exception e) {
            throw new RuntimeException("Đối tượng không xác ");
        }

    }

    public void update() {
        int index = tabPanel.getSelectedIndex();
        try {
            
        switch (index) {
            case 0 -> {
                int id = Integer.parseInt(txtIdTacGia.getText());
                TacGia tg = new TacGia();
                tg.setId(id);
                tgDao.update(tg);
            }
            case 1 -> {
                int id = Integer.parseInt(txtIdTheLoai.getText());
                TheLoai tl = new TheLoai();
                tl.setId(id);
                tlDao.update(tl);
            }
            case 2 -> {
                int id = Integer.parseInt(txtIdNxb.getText());
                Nxb nxb = new Nxb();
                nxb.setId(id);
                nxbDao.update(nxb);
            }
            default -> {
                throw new AssertionError("Lỗi index bảng");
            }

        }
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật không thành công");
        }
    }

    public void clearForm() {
        int index = tabPanel.getSelectedIndex();
        switch (index) {
            case 0 -> {
                txtIdTacGia.setText("");
                txtTenTacGia.setText("");
            }
            case 1 -> {
                txtIdTheLoai.setText("");
                txtTenTL.setText("");
            }
            case 2 -> {
                txtIdNxb.setText("");
                txtTenNxb.setText("");
            }
            default -> {
                throw new AssertionError("Lỗi index bảng");
            }

        }
    }

    public void setForm(int selectedRow) {
        int index = tabPanel.getSelectedIndex();
        switch (index) {
            case 0 -> {
                TacGia tg = listTacGia.get(selectedRow);
                txtIdTacGia.setText(tg.getId() + "");
                txtTenTacGia.setText(tg.getTen());
            }
            case 1 -> {
                TheLoai tl = listTheLoai.get(selectedRow);
                txtIdTheLoai.setText(tl.getId() + "");
                txtTenTL.setText(tl.getTen());
            }
            case 2 -> {
                Nxb nxb = listNxb.get(selectedRow);
                txtIdNxb.setText(nxb.getId() + "");
                txtTenNxb.setText(nxb.getTen());
            }
            default -> {
                throw new AssertionError("Lỗi index bảng");
            }

        }
    }

    private void searchTacGia() {
        String key = txtTimKiemTacGia.getText();
        List<TacGia> list = tgDao.searchTacGia(key);
        modelTacGia.setRowCount(0);
        int index = 0;
        for (TacGia tg : list) {
            Object[] rows = new Object[]{
                ++index,
                tg.getId(),
                tg.getTen()
            };
            modelTacGia.addRow(rows);
        }
    }

    private void searchTheLoai() {
        String key = txtTimKiemTheLoai.getText();
        List<TheLoai> list = tlDao.searchTacGia(key);
        modelTheLoai.setRowCount(0);
        int index = 0;
        for (TheLoai tl : list) {

            Object[] rows = new Object[]{
                ++index,
                tl.getId(),
                tl.getTen()
            };
            modelTheLoai.addRow(rows);
        }
    }

    private void searchNxb() {
        String key = txtTimKiemNXB.getText();
        List<Nxb> list = nxbDao.searchTacGia(key);
        modelNxb.setRowCount(0);
        int index = 0;
        for (Nxb nxb : list) {
            Object[] rows = new Object[]{
                ++index,
                nxb.getId(),
                nxb.getTen()
            };
            modelNxb.addRow(rows);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        tabPanel = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenTacGia = new javax.swing.JTextField();
        btnThemTG = new javax.swing.JButton();
        btnCapNhatTG = new javax.swing.JButton();
        btnXoaTG = new javax.swing.JButton();
        btnMoiTG = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTacGia = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiemTacGia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtIdTacGia = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenTL = new javax.swing.JTextField();
        btnThemTL = new javax.swing.JButton();
        btnCapNhatTL = new javax.swing.JButton();
        btnXoaTL = new javax.swing.JButton();
        btnMoiTL = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTL = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiemTheLoai = new javax.swing.JTextField();
        txtIdTheLoai = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTenNxb = new javax.swing.JTextField();
        btnThemNXB = new javax.swing.JButton();
        btnCapNhatNXB = new javax.swing.JButton();
        btnXoaNXB = new javax.swing.JButton();
        btnMoiNXB = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNXB = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiemNXB = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIdNxb = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1024, 720));

        jPanel5.setBackground(new java.awt.Color(234, 239, 239));
        jPanel5.setPreferredSize(new java.awt.Dimension(1024, 720));

        tabPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(234, 239, 239));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ TÁC GIẢ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Họ và tên:");

        txtTenTacGia.setToolTipText("Tên tác giả");

        btnThemTG.setText("Thêm");
        btnThemTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTGActionPerformed(evt);
            }
        });

        btnCapNhatTG.setText("Cập nhật");
        btnCapNhatTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatTGActionPerformed(evt);
            }
        });

        btnXoaTG.setText("Xóa");

        btnMoiTG.setText("Mới");
        btnMoiTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiTGActionPerformed(evt);
            }
        });

        tblTacGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên"
            }
        ));
        tblTacGia.setShowGrid(true);
        tblTacGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblTacGiaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblTacGia);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm:");

        txtTimKiemTacGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTacGiaKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("ID:");

        txtIdTacGia.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(386, 386, 386)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtTimKiemTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtIdTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(197, 197, 197)
                                    .addComponent(btnThemTG)
                                    .addGap(86, 86, 86)
                                    .addComponent(btnCapNhatTG)
                                    .addGap(64, 64, 64)
                                    .addComponent(btnXoaTG)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnMoiTG)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtTenTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtIdTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemTG)
                    .addComponent(btnCapNhatTG)
                    .addComponent(btnXoaTG)
                    .addComponent(btnMoiTG))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimKiemTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        tabPanel.addTab("Tác giả", jPanel2);

        jPanel3.setBackground(new java.awt.Color(234, 239, 239));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("QUẢN LÝ THỂ LOẠI");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Tên:");

        txtTenTL.setToolTipText("Tên thể loại");

        btnThemTL.setText("Thêm");
        btnThemTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTLActionPerformed(evt);
            }
        });

        btnCapNhatTL.setText("Cập nhật");
        btnCapNhatTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatTLActionPerformed(evt);
            }
        });

        btnXoaTL.setText("Xóa");

        btnMoiTL.setText("Mới");
        btnMoiTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiTLActionPerformed(evt);
            }
        });

        tblTL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên"
            }
        ));
        tblTL.setShowGrid(true);
        tblTL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblTLMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblTL);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Tìm kiếm:");

        txtTimKiemTheLoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTheLoaiKeyReleased(evt);
            }
        });

        txtIdTheLoai.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("ID:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(386, 386, 386)
                .addComponent(jLabel4)
                .addContainerGap(408, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtTenTL, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(btnThemTL)
                                    .addGap(86, 86, 86)
                                    .addComponent(btnCapNhatTL)
                                    .addGap(64, 64, 64)
                                    .addComponent(btnXoaTL)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnMoiTL)
                                    .addGap(119, 119, 119)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtTimKiemTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtIdTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(70, 70, 70))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtIdTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemTL)
                    .addComponent(btnCapNhatTL)
                    .addComponent(btnXoaTL)
                    .addComponent(btnMoiTL))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiemTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        tabPanel.addTab("Thể loại", jPanel3);

        jPanel4.setBackground(new java.awt.Color(234, 239, 239));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("QUẢN LÝ NHÀ XUẤT BẢN");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Tên:");

        txtTenNxb.setToolTipText("Tên nhà xuất bản");

        btnThemNXB.setText("Thêm");
        btnThemNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNXBActionPerformed(evt);
            }
        });

        btnCapNhatNXB.setText("Cập nhật");
        btnCapNhatNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatNXBActionPerformed(evt);
            }
        });

        btnXoaNXB.setText("Xóa");

        btnMoiNXB.setText("Mới");
        btnMoiNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiNXBActionPerformed(evt);
            }
        });

        tblNXB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên"
            }
        ));
        tblNXB.setShowGrid(true);
        tblNXB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblNXBMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblNXB);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tìm kiếm:");

        txtTimKiemNXB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemNXBKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("ID:");

        txtIdNxb.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(386, 386, 386)
                .addComponent(jLabel7)
                .addContainerGap(332, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtTenNxb, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(btnThemNXB)
                                    .addGap(86, 86, 86)
                                    .addComponent(btnCapNhatNXB)
                                    .addGap(64, 64, 64)
                                    .addComponent(btnXoaNXB)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnMoiNXB)
                                    .addGap(99, 99, 99))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtIdNxb, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtTimKiemNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtIdNxb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenNxb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNXB)
                    .addComponent(btnCapNhatNXB)
                    .addComponent(btnXoaNXB)
                    .addComponent(btnMoiNXB))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTimKiemNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        tabPanel.addTab("NXB", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabPanel)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabPanel)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNXBActionPerformed
        Nxb nxb = new Nxb();
        nxb.setTen(txtTenNxb.getText());
        insert(nxb);
        loadTableNxb();
    }//GEN-LAST:event_btnThemNXBActionPerformed

    private void btnThemTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTGActionPerformed
        TacGia tg = new TacGia();
        tg.setTen(txtTenTacGia.getText());
        insert(tg);
        loadTableTacGia();
    }//GEN-LAST:event_btnThemTGActionPerformed

    private void btnThemTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTLActionPerformed
        TheLoai tl = new TheLoai();
        tl.setTen(txtTenTL.getText());
        insert(tl);
        loadTableTheLoai();
    }//GEN-LAST:event_btnThemTLActionPerformed

    private void btnMoiTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiTGActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiTGActionPerformed

    private void btnMoiTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiTLActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiTLActionPerformed

    private void btnMoiNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiNXBActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiNXBActionPerformed

    private void btnCapNhatNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatNXBActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatNXBActionPerformed

    private void btnCapNhatTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatTLActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatTLActionPerformed

    private void btnCapNhatTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatTGActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatTGActionPerformed

    private void tblTacGiaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTacGiaMouseReleased
        int row = tblTacGia.getSelectedRow();
        setForm(row);

    }//GEN-LAST:event_tblTacGiaMouseReleased

    private void tblTLMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTLMouseReleased
        int row = tblTL.getSelectedRow();
        setForm(row);
    }//GEN-LAST:event_tblTLMouseReleased

    private void tblNXBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNXBMouseReleased
        int row = tblNXB.getSelectedRow();
        setForm(row);

    }//GEN-LAST:event_tblNXBMouseReleased

    private void txtTimKiemTheLoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTheLoaiKeyReleased
        searchTheLoai();
    }//GEN-LAST:event_txtTimKiemTheLoaiKeyReleased

    private void txtTimKiemNXBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemNXBKeyReleased
        searchNxb();
    }//GEN-LAST:event_txtTimKiemNXBKeyReleased

    private void txtTimKiemTacGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTacGiaKeyReleased
        searchTacGia();
    }//GEN-LAST:event_txtTimKiemTacGiaKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new QLDanhMuc().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatNXB;
    private javax.swing.JButton btnCapNhatTG;
    private javax.swing.JButton btnCapNhatTL;
    private javax.swing.JButton btnMoiNXB;
    private javax.swing.JButton btnMoiTG;
    private javax.swing.JButton btnMoiTL;
    private javax.swing.JButton btnThemNXB;
    private javax.swing.JButton btnThemTG;
    private javax.swing.JButton btnThemTL;
    private javax.swing.JButton btnXoaNXB;
    private javax.swing.JButton btnXoaTG;
    private javax.swing.JButton btnXoaTL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTable tblNXB;
    private javax.swing.JTable tblTL;
    private javax.swing.JTable tblTacGia;
    private javax.swing.JTextField txtIdNxb;
    private javax.swing.JTextField txtIdTacGia;
    private javax.swing.JTextField txtIdTheLoai;
    private javax.swing.JTextField txtTenNxb;
    private javax.swing.JTextField txtTenTL;
    private javax.swing.JTextField txtTenTacGia;
    private javax.swing.JTextField txtTimKiemNXB;
    private javax.swing.JTextField txtTimKiemTacGia;
    private javax.swing.JTextField txtTimKiemTheLoai;
    // End of variables declaration//GEN-END:variables
}
