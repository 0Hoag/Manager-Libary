/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Tien
 */
public class XDate {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    /*
     * Chuyển đổi từ String sang Date
     */
    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                sdf.applyPattern(pattern[0]);
            }
            if (date == null) {
                return XDate.now();
            }
            return sdf.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Chuyển đổi từ Date sang String
     */
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            sdf.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = XDate.now();
        }
        return sdf.format(date);
    }

    /*
     * Lấy thời gian hiện tại
     */
    public static Date now() {
        return new Date(); //new Date lấy giờ hiện tại
    }

    
    public static String addDay(int days){
        Date today = now();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date result = calendar.getTime();
        return toString(result);
    }
    
}
