package igu;

import igu.util.alerts.ErrorAlert;
import igu.main.MainFramee;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import igu.main.Login;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Asullom
 */
public class App {
    private static File archivo = new File("db2.db");

    public static void main(String[] args) {
        
         /*if (!archivo.exists()) {
            ErrorAlert er = new ErrorAlert(null, true);
            er.Errortitle.setText("SORRY");
            er.Errormsg.setText("NO SE ENCONTRÓ LA BASE DE DATOS!");
            er.errormsg1.setText("Revise que la DB este junto al programa, o comuníquese con su proveedor");
            er.setVisible(true);
            System.exit(0);
        }*/
         
        //String s = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        //try {
            Login m = new Login();
            //javax.swing.UIManager.setLookAndFeel(s);
            m.setLocationRelativeTo(null);
            m.setVisible(true);

        //} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        //    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
}
