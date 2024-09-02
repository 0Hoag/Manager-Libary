/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import dao.*;
import entity.*;
import java.io.File;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import utils.*;

/**
 *
 * @author Tien
 */
public class QLSach extends javax.swing.JPanel {

    /**
     * Creates new form Sach
     */
    DefaultTableModel sachTableModel;
    DefaultComboBoxModel tacGiaModel;
    DefaultComboBoxModel theLoaiModel;
    DefaultComboBoxModel NxbModel;

    SachDAO sachDAO;
    TacGiaDAO tgDAO;
    TheLoaiDAO tlDAO;
    NXBDAO nxbDAO;

    List<Sach> listSach = new ArrayList<>();
    List<TacGia> listTacGia = new ArrayList<>();
    List<TheLoai> listTheLoai = new ArrayList<>();
    List<Nxb> listNxb = new ArrayList<>();
//List<Sach> listTacGia = new ArrayList<>();
//List<Sach> listTheLoai = new ArrayList<>();
//List<Sach> list = new ArrayList<>();

    public QLSach() {
        initComponents();
        sachTableModel = (DefaultTableModel) tblSach.getModel();
        tacGiaModel = (DefaultComboBoxModel) cboTacGia.getModel();
        theLoaiModel = (DefaultComboBoxModel) cboTheLoai.getModel();
        NxbModel = (DefaultComboBoxModel) cboNxb.getModel();
        fillCboTacGia();
        fillCboNxb();
        fillCboTheLoai();
    }

    public void fillCboTacGia() {
        tgDAO = new TacGiaDAO();
        tacGiaModel.removeAllElements();
        listTacGia = tgDAO.selectAll();
        for (TacGia tg : listTacGia) {
            tacGiaModel.addElement(tg.getTen());
        }

    }

    public void fillCboTheLoai() {
        tlDAO = new TheLoaiDAO();
        theLoaiModel.removeAllElements();
        listTheLoai = tlDAO.selectAll();
        for (TheLoai tl : listTheLoai) {
            theLoaiModel.addElement(tl.getTen());
        }
    }

    public void fillCboNxb() {
        nxbDAO = new NXBDAO();
        NxbModel.removeAllElements();
        listNxb = nxbDAO.selectAll();
        for (Nxb nxb : listNxb) {
            NxbModel.addElement(nxb.getTen());
        }
    }

    public void loadTableSach() {
        sachDAO = new SachDAO();
        listSach = sachDAO.load();
        sachTableModel.setRowCount(0);
        int index = 0;
        for (Sach sach : listSach) {
            Object[] rowData = {
                ++index,
                sach.getMaSach(),
                sach.getTenSach(),
                sach.getTacGia().getTen(),
                sach.getNxb().getTen(),
                sach.getTheLoai().getTen(),
                sach.getGiaSach(),
                sach.getTongBanSao(),
                sach.getSoBanSaoHienCo(),
                sach.getHinh()

            };
            sachTableModel.addRow(rowData);
        }
    }

    public Sach getForm() {
        tgDAO = new TacGiaDAO();
        tlDAO = new TheLoaiDAO();
        nxbDAO = new NXBDAO();
        int maSach;
        if(txtMaSach.getText().equals("")){
            maSach = 0;
        }else{
             maSach = Integer.parseInt(txtMaSach.getText());
        }
        String tenSach = txtTenSach.getText();
        int giaSach = Integer.parseInt(txtGiaSach.getText());
        int tongSoSach = Integer.parseInt(txtTongSoSach.getText());
        int sachHienCo = Integer.parseInt(txtHienCo.getText());
        String hinh =   lblAnh.getToolTipText();

        String tenTacGia = (String) cboTacGia.getSelectedItem();
        TacGia tg = tgDAO.selectByTen(tenTacGia);

        String tenTheLoai = (String) cboTheLoai.getSelectedItem();
        TheLoai tl = tlDAO.selectByTen(tenTheLoai);

        String tenNxb = (String) cboNxb.getSelectedItem();
        Nxb nx = nxbDAO.selectByTen(tenNxb);

        return new Sach(maSach, tenSach, tg, nx, tl, giaSach, tongSoSach, sachHienCo, hinh);
    }
    public void setForm(int index) {
        Sach sach = listSach.get(index); //lấy Sach ở thứ index được truyền vào
        txtMaSach.setText(sach.getMaSach() + "");
        txtTenSach.setText(sach.getTenSach());
        cboTacGia.setSelectedItem(sach.getTacGia().getTen());
        cboTheLoai.setSelectedItem(sach.getTheLoai().getTen());
        cboNxb.setSelectedItem(sach.getNxb().getTen());
        txtGiaSach.setText(sach.getGiaSach() + "");
        txtTongSoSach.setText(sach.getTongBanSao() + "");
        txtHienCo.setText(sach.getSoBanSaoHienCo() + "");
        lblAnh.setIcon(XImage.readIcon("NoImage.png"));
        if (sach.getHinh() != null) {
            lblAnh.setToolTipText(sach.getHinh());
            lblAnh.setIcon(XImage.readIcon(sach.getHinh()));
        }
    }
    public void insert() {
        Sach s = getForm();
        System.out.println(s);
        try {
            sachDAO.insert(s);
            XDialog.alert(this, "Thêm thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm thất bại");
        }

    }

    public void update() {
        Sach s = getForm();
        try {
            sachDAO.update(s);
            XDialog.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật thất bại");
        }
    }

    public void clearForm() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtGiaSach.setText("");
        cboTacGia.setSelectedIndex(0);
        cboTheLoai.setSelectedIndex(0);
        cboNxb.setSelectedIndex(0);
        txtTongSoSach.setText("");
        txtHienCo.setText("");
        lblAnh.setIcon(null);

    }
    private void chonAnh() {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png", "webp");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            XImage.saveIcon(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIcon(file.getName()); // đọc hình từ logos
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName()); // giữ tên hình trong tooltip
        }
    }

    private void searchSach(String key) {
        listSach = sachDAO.searchSach(key);
        sachTableModel.setRowCount(0);
        int index = 0;
        for (Sach sach : listSach) {
            Object[] rows = new Object[]{
                ++index,
                sach.getMaSach(),
                sach.getTenSach(),
                sach.getTacGia().getTen(),
                sach.getNxb().getTen(),
                sach.getTheLoai().getTen(),
                sach.getGiaSach(),
                sach.getTongBanSao(),
                sach.getSoBanSaoHienCo(),
                sach.getHinh()
            };
            sachTableModel.addRow(rows);
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

        jPanel8 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        txtGiaSach = new javax.swing.JTextField();
        txtTongSoSach = new javax.swing.JTextField();
        txtHienCo = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        cboTacGia = new javax.swing.JComboBox<>();
        cboTheLoai = new javax.swing.JComboBox<>();
        cboNxb = new javax.swing.JComboBox<>();

        jPanel8.setBackground(new java.awt.Color(234, 239, 239));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("THÔNG TIN SÁCH");
        jLabel56.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setText("Tên sách:");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("Tác giả:");

        txtTenSach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setText("NXB:");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setText("Giá sách:");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Tổng số sách:");

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Hiện có");

        lblAnh.setText("Ảnh");
        lblAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        txtGiaSach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTongSoSach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtHienCo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Thể loại:");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setText("Mã sách:");

        txtMaSach.setEditable(false);
        txtMaSach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setText("Tìm kiếm:");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên sách", "Tác giả", "NXb", "Thể loại", "Giá sách", "Tổng số sách", "Hiện có", "Hình"
            }
        ));
        tblSach.setShowGrid(true);
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        cboTacGia.setSelectedIndex(-1);

        cboTheLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboNxb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(55, 55, 55)
                .addComponent(btnCapNhat)
                .addGap(46, 46, 46)
                .addComponent(btnMoi)
                .addGap(411, 411, 411))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(174, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtTimKiem))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(27, 27, 27))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMaSach)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(txtTongSoSach, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(17, 17, 17)
                                    .addComponent(txtHienCo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboNxb, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                        .addComponent(cboTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtTenSach, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtGiaSach, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(174, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(498, 498, 498))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jLabel65)
                            .addComponent(cboTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(cboNxb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(jLabel63)
                            .addComponent(txtTongSoSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHienCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnThem)
                    .addComponent(btnMoi))
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        int selectedRow = tblSach.getSelectedRow();
        setForm(selectedRow);
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
        loadTableSach();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        update();
        loadTableSach();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        chonAnh();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        searchSach(txtTimKiem.getText());
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboNxb;
    private javax.swing.JComboBox<String> cboTacGia;
    private javax.swing.JComboBox<String> cboTheLoai;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtGiaSach;
    private javax.swing.JTextField txtHienCo;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongSoSach;
    // End of variables declaration//GEN-END:variables
}
