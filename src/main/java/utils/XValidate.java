/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Tien
 */
public class XValidate {

    public static boolean checkNullText(JTextField txt) {
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    public static boolean checkNullText(JTextComponent... components) {
        for (JTextComponent component : components) {
            if (component.getText().trim().isEmpty()) {
                XDialog.alert(component.getRootPane(),"Không được để trống thông tin");
                return false;
            }
        }
        return true;
    }

    public static boolean checkMail(JTextField txt) {
        String regex = "^[a-zA-Z][\\w]+@gmail.com";
        if (txt.getText().matches(regex)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Email không hợp lệ");
        }
        return false;
    }

    public static boolean checkPhone(JTextField txt) {
        String regex = "^(\\+84|0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5|8|9]|9[0-9])[0-9]{7}$";;
        if (txt.getText().matches(regex)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Số điện thoại không hợp lệ.");
        }
        return false;
    }

    public static boolean checkUsername(JTextField txt) {
        String regex = "^[a-zA-Z][\\w]+";
        if (txt.getText().matches(regex)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Tên tài khoản không bắt đầu bằng số.");
        }
        return false;
    }

    public static boolean checkPass(JPasswordField txt) {
        String password = new String(txt.getPassword());
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        if (password.length() < 8) {
            XDialog.alert(txt.getRootPane(), "Mật khẩu quá ngắn");
            return false;

        }
        if (!password.matches(regex)) {
            XDialog.alert(txt.getRootPane(), "Mật khẩu phải bao gồm ít nhất một chữ viết hoa, một chữ thường, một số và một ký tự đặc biệt.");
            return false;
        }
        return true;
    }
    
}
