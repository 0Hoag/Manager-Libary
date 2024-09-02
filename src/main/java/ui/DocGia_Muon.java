/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import dao.AccountDAO;
import dao.DocGiaDAO;
import dao.PhieuMuonDAO;
import dao.SachDAO;
import dao.SachMuonDao;
import entity.PhieuMuon;
import entity.Sach;
import entity.SachMuon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import utils.TaskManager;
import utils.XDate;
import utils.XDialog;

/**
 *
 * @author Tien
 */
public class DocGia_Muon extends javax.swing.JPanel {

    /**
     * Creates new form DocGia_Muon
     */
    DefaultTableModel sachTableModel;
    DefaultTableModel sachMuonPhieuMuon;

    PhieuMuonDAO phieuMuonDao;
    SachDAO sachDAO;

    int soSachMuon = 0;
    List<Object[]> bookHardData = new ArrayList<>();

    ;
    
    public DocGia_Muon() {
        initComponents();
        txtNgayTra.setText(XDate.addDay(Integer.parseInt(txtSoNgayMuon.getText())));

        sachTableModel = (DefaultTableModel) tblSachPhieuMuon.getModel();
        sachMuonPhieuMuon = (DefaultTableModel) tblSachMuonPhieuMuon.getModel();
        txtMaDocGia.setText(AccountDAO.getCurrentId() + "");
    }

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
        txtMaDocGia.setText("");
        txtSoNgayMuon.setText("");
        bookHardData.clear();
        sachMuonPhieuMuon.setRowCount(0);
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
        try {
            phieuMuonDao = new PhieuMuonDAO();
            phieuMuonDao.borrowBooks(Integer.parseInt(txtMaDocGia.getText()),
                    Integer.parseInt(txtSoNgayMuon.getText()),
                    "Đang mượn", getTableSachMuon());
            clearForm();
            XDialog.alert(this, "Lập phiếu thành công");
        } catch (Exception e) {
            XDialog.alert(this, "Lập phiếu không thành công");
        }

    }

    private int checkContain(List<Object[]> listData, Object check) {
        int index = -1;
        for (int i = 0; i < listData.size(); i++) {
            Object[] array = listData.get(i);
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
        sachMuonPhieuMuon.setRowCount(0);
        int index = 0;
        for (Object[] b : bookHardData) {
            b[0] = ++index;
            sachMuonPhieuMuon.addRow(b);
        }
    }

    private void addBooks() {
        soSachMuon = 0;
        for (Object[] sach : bookHardData) {
            soSachMuon += (int) sach[2];
        }
        System.out.println("so sach muon:" + soSachMuon);
        int row = tblSachPhieuMuon.getSelectedRow();
        String tenSach = (String) sachTableModel.getValueAt(row, 1);
        int index = checkContain(bookHardData, tenSach);
        int soLuongHienCo = (int) tblSachPhieuMuon.getValueAt(row, 4);

        if (soSachMuon < 5) {
            if (soLuongHienCo == 0) {
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

                }
            }

        } else {
            XDialog.alert(this, "Chỉ được mượn tối đa 5 cuốn sách");
        }

        tblSachPhieuMuon.clearSelection();

    }

    private void removeBook() {
        int row = tblSachMuonPhieuMuon.getSelectedRow();
        soSachMuon -= (int) bookHardData.get(row)[2];
        bookHardData.remove(row);
        loadHardDataBorrowedBooks();

    }

    private void searchSach() {
        String key = txtTimKiem.getText();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSachMuonPhieuMuon = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSachPhieuMuon = new javax.swing.JTable();
        btnThemSach = new javax.swing.JButton();
        btnXoaSach = new javax.swing.JButton();
        btnThemPhieuMuon = new javax.swing.JButton();
        btnLamMoiPhieuMuon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaDocGia = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        txtSoNgayMuon = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNgayTra = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(234, 239, 239));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 720));

        jPanel3.setBackground(new java.awt.Color(234, 239, 239));
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 700));

        tblSachMuonPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSachMuonPhieuMuon.setShowGrid(true);
        tblSachMuonPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblSachMuonPhieuMuonMouseReleased(evt);
            }
        });
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
        jScrollPane5.setViewportView(tblSachPhieuMuon);

        btnThemSach.setText("Thêm sách");
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

        btnLamMoiPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiPhieuMuon.setText("Mới");
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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Số ngày mượn:");

        txtMaDocGia.setEditable(false);

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        txtSoNgayMuon.setEditable(false);
        txtSoNgayMuon.setText("30");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Ngày trả dự tính:");

        txtNgayTra.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaDocGia))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSoNgayMuon)
                                    .addComponent(txtNgayTra, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSach)
                            .addComponent(btnXoaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemPhieuMuon)
                        .addGap(28, 28, 28)
                        .addComponent(btnLamMoiPhieuMuon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem)))
                .addGap(47, 47, 47))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)))
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnThemSach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaSach))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtSoNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnThemPhieuMuon)
                                    .addComponent(btnLamMoiPhieuMuon))))))
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("PHIẾU MƯỢN");
        jLabel56.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(463, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(403, 403, 403))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addContainerGap(682, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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

    private void btnThemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuMuonActionPerformed
        createPhieuMuon();
        loadTableSach();
    }//GEN-LAST:event_btnThemPhieuMuonActionPerformed

    private void btnLamMoiPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiPhieuMuonActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiPhieuMuonActionPerformed

    private void tblSachMuonPhieuMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMuonPhieuMuonMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSachMuonPhieuMuonMouseReleased

    private void btnThemSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSachActionPerformed
        // TODO add your handling code here:
        addBooks();
        loadHardDataBorrowedBooks();
        TaskManager.getInstance().submitTask(() -> {
            loadTableSach();
        });


    }//GEN-LAST:event_btnThemSachActionPerformed

    private void btnXoaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSachActionPerformed
        // TODO add your handling code here:
        removeBook();
    }//GEN-LAST:event_btnXoaSachActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        TaskManager.getInstance().submitTask(() -> {
            searchSach();
        });

    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoiPhieuMuon;
    private javax.swing.JButton btnThemPhieuMuon;
    private javax.swing.JButton btnThemSach;
    private javax.swing.JButton btnXoaSach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tblSachMuonPhieuMuon;
    private javax.swing.JTable tblSachPhieuMuon;
    private javax.swing.JTextField txtMaDocGia;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSoNgayMuon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
