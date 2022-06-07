/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tokokomputer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class Koneksi {
    private static Connection koneksi;
public static Connection getKoneksi(){
    if(koneksi==null){
        try{
            String url;
            url="jdbc:mysql://localhost:3306/tokokomputer";
            String username ="root";
            String password = "";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, username, password);   
        } catch(SQLException t) {
            JOptionPane.showMessageDialog(null, "Database Tidak Terhubung" + t.getMessage());
        }
    }return koneksi;
}static Object getConnection(){
        throw new UnsupportedOperationException("Tidak Dapat Terhubung");
}
 
}