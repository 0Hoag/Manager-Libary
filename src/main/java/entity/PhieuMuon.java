/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 *
 * @author Admin
 */
@Data //Getter, Setter and toString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // tự động set private và final nếu không cần có thể xóa
@Entity
public class PhieuMuon {
    @Id // xác định priamry key
    int maPhieu;
    DocGia maDocGia;
    int soNgayMuon;
    int SoLuongMuon;
    Date ngayMuon;
    Date ngayTraDuTinh;
    String trangThaiTra;
}
