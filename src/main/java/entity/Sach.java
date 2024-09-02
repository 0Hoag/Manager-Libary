/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Admin
 */
@Data 
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // tự động set private và final nếu không cần có thể xóa
@Entity
public class Sach {
    @Id // xác định priamry key
    int maSach;
    String tenSach;
    TacGia tacGia;
    Nxb nxb;
    TheLoai theLoai;
    int giaSach;
    int tongBanSao;
    int soBanSaoHienCo;
    String hinh;
}
