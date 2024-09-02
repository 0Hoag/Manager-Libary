/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Tien
 */
public class XImage {
    public static Image getAppIcon() {
        String file = "/com/edusys/icons/fpt.png";
        return new ImageIcon(XValidate.class.getResource(file)).getImage();
    }
    
    public static ImageIcon readIcon(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(280, 220, Image.SCALE_SMOOTH));
    }
    
    public static void saveIcon(File src) {
        File file = new File("logos", src.getName());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(file.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
