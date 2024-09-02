/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Admin
 */
@Data //Getter, Setter and toString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // tự động set private và final nếu không cần có thể xóa
@Entity
public class PhieuPhat {
    @Id // xác định priamry key
    int phieuPhatId;
    DocGia maDocGia;
    String noiDungPhat;
    int phiphat; //trong database t thấy ghi như này chả hiểu cho lắm là gì? 
    Date ngayTao;
}
