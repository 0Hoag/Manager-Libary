/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import ui.*;
import utils.Connect;
import utils.XDialog;
import utils.XValidate;

/**
 *
 * @author Tien
 */
public class Test {

    static void randomNumber() {
        int number1 = (int) Math.ceil(Math.random() * 9);
        int number2 = (int) Math.ceil(Math.random() * 9);
        int number3 = (int) Math.ceil(Math.random() * 9);
        int number4 = (int) Math.ceil(Math.random() * 9);
        int number5 = (int) Math.ceil(Math.random() * 9);
        int number6 = (int) Math.ceil(Math.random() * 9);
        String result = "" + number1 + number2 + number3 + number4 + number5 + number6;
        System.out.println(result);
    }

    public static void countdown(int seconds) {
        while (seconds > 0) {
            System.out.println("Seconds remaining: " + seconds);
            try {
                Thread.sleep(1000); // Chờ 1 giây
            } catch (InterruptedException e) {
                System.out.println("Countdown interrupted");
                return;
            }
            seconds--;
        }
        System.out.println("Countdown finished!");
    }

    public static void main(String[] args) {
//
        JFrame f = new JFrame();
        f.setSize(1024, 720);
        QLMuonTra qlmt = new QLMuonTra(); //muốn test panel nào thì đổi lại
        f.add(qlmt);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(3);
        //dự án bản chính

    }
}
