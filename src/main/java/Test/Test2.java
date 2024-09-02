/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;
import dao.*;
import entity.*;
/**
 *
 * @author Tien
 */
public class Test2 {
    public static void main(String[] args) {
        NXBDAO dao = new NXBDAO();
        Nxb tg = dao.selectByTen("Nhà xuất bản Kim Đồng");
        
        System.out.println(tg.getId() + tg.getTen());
    }
}
