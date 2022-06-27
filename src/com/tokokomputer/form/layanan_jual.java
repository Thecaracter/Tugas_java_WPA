/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tokokomputer.form;

import static com.itextpdf.xmp.XMPMetaFactory.reset;
import com.tokokomputer.controller.Koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class layanan_jual extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String id;
 public final void setJam(){
ActionListener taskPerformer = new ActionListener() {

            @Override
public void actionPerformed(ActionEvent evt) {
String nol_jam = "", nol_menit = "",nol_detik = "";

java.util.Date dateTime = new java.util.Date();
int nilai_jam = dateTime.getHours();
int nilai_menit = dateTime.getMinutes();
int nilai_detik = dateTime.getSeconds();

if(nilai_jam <= 9) nol_jam= "0";
if(nilai_menit <= 9) nol_menit= "0";
if(nilai_detik <= 9) nol_detik= "0";

String jam = nol_jam + Integer.toString(nilai_jam);
String menit = nol_menit + Integer.toString(nilai_menit);
String detik = nol_detik + Integer.toString(nilai_detik);

Jam.setText(jam+":"+menit+":"+detik+"");
}
};
new Timer(1000, taskPerformer).start();
}
 public void tanggal(){
    String tanggal;
    java.util.Date dateTime = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     tanggal =sdf.format(dateTime) ;
   
  tanggal_beli.setText(tanggal);
     
     
}
    public void kliktable_layananpenjualan(){
        int baris =tampil_barang.getSelectedRow();
        txt_id_barang.setText((String)tampil_barang.getValueAt(baris, 0));
        txt_namabarang.setText((String)tampil_barang.getValueAt(baris, 1));
        txt_jenisbarang.setText((String)tampil_barang.getValueAt(baris, 2));
        txt_HargaBarang.setText((String) tampil_barang.getValueAt (baris,3));
        
       
    }
    public void auto_idpenjualan() throws SQLException {
        try{
            String sql = "SELECT * FROM Penjualan ORDER BY id_penjualan DESC";
            java.sql.Connection conn =(Connection) Koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs =  stm.executeQuery(sql);
            if (rs.next()) {
                String id_transaksi = rs.getString("id_penjualan").substring(2);
                String ID = "" + (Integer.parseInt(id_transaksi) + 1);
                String Nol = "";

                if (ID.length() == 1) {
                    Nol = "000";
                } else if (ID.length() == 2) {
                    Nol = "00";
                } else if (ID.length() == 3) {
                    Nol = "0";
                } else if (ID.length() == 4) {
                    Nol = "";
                }
                txt_id_penjualan.setText("RI" + Nol + ID);
            } else {
                txt_id_penjualan.setText("RI0001");
            
                    
        } 
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
                }
    }
    
    public void tampil() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Barang");
        tbl.addColumn("Nama Barang");
        tbl.addColumn("Jenis Produk");
        tbl.addColumn("Harga Produk");
        try {
            String sql = "SELECT id_barang,nama_barang,jenis_produk,harga_produk FROM barang";
            java.sql.Connection conn = (Connection) Koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),});
            }
            tampil_barang.setModel(tbl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void tampilTable(){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("ID Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Harga Satuan");
        tabel.addColumn("Kuantitas");
        tabel.addColumn("Jenis Barang");
        tabel.addColumn("Total");
        
        tblKeranjang.setModel(tabel);
                
    }
    
    public void hitungTotalHarga(){
        DefaultTableModel model = (DefaultTableModel) tblKeranjang.getModel();
        int totalHarga = 0;
            
        for(int j = 0; j < tblKeranjang.getRowCount(); j++){
            totalHarga = totalHarga + Integer.parseInt(model.getValueAt(j, 5).toString());     
        }

        txtJmlHarga.setText(String.valueOf(totalHarga));
    }
    void aturTombol (){

    txt_id_barang.setEnabled(false);
    txt_id_penjualan.setEnabled(false);

}
     public void balikdata_layananjual(){
        try{
           String sql = "SELECT id_karyawan FROM karyawan where id_karyawan =" + id;
           java.sql.Connection conn=(Connection)Koneksi.getKoneksi();
           java.sql.PreparedStatement pst = conn.prepareCall(sql);
           java.sql.ResultSet rs=pst.executeQuery();
          
           if(rs.next()){
           new Dashboard(rs.getString(1)).setVisible(true);
           this.setVisible(false);
               }
               
     
       }catch (Exception e){
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
     }
    
    
    public layanan_jual() throws SQLException {
        initComponents();
        tampilTable();
        tampil();
        setJam();
        aturTombol();
        id_karyawan();
        tanggal();
        auto_idpenjualan();
        
    }
    
    public layanan_jual(String id) throws SQLException {
        initComponents();
        this.conn = Koneksi.getKoneksi();
        this.id = id;
        id_karyawan();
        tanggal();
        auto_idpenjualan();
        tampil();
        tampilTable();
         aturTombol();
         setJam();
        
    }

    public void id_karyawan(){
            try {
                     String sql = "select id_karyawan from karyawan where id_karyawan = " + id;
            pst=conn.prepareCall(sql);
            rs=pst.executeQuery();
            
            if(rs.next()){
                id_karyawan.setText(rs.getString(1));
                System.out.println(sql);
                
            }
            } catch (Exception e){
               
                
            }
       
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblKeranjang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_id_barang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Jam = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        id_karyawan = new javax.swing.JLabel();
        btnEditItem = new javax.swing.JButton();
        btnHapusItem = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        txtJmlKembalian = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnHitungJmlKembalian = new javax.swing.JButton();
        txtJmlBayar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtJmlHarga = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        txt_jumlahbeli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_HargaBarang = new javax.swing.JTextField();
        txt_jenisbarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_namabarang = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        txt_id_penjualan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tanggal_beli = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampil_barang = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Kasir Sederhana");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblKeranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKeranjangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKeranjang);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 550, 500));

        jPanel1.setBackground(new java.awt.Color(15, 247, 147));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel1.setText("Id penjualan");

        txt_id_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_barangActionPerformed(evt);
            }
        });

        jLabel9.setText("Jam :");

        Jam.setText("00.00.00");

        jLabel11.setText("ID Karyawan :");

        id_karyawan.setText("0");

        btnEditItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditItem.setText("Edit Item");
        btnEditItem.setEnabled(false);
        btnEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditItemActionPerformed(evt);
            }
        });

        btnHapusItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHapusItem.setText("Hapus Item");
        btnHapusItem.setEnabled(false);
        btnHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusItemActionPerformed(evt);
            }
        });

        btnBatal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBatal.setText("Batalkan Transaksi");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnCetak.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCetak.setText("Cetak Struk");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        txtJmlKembalian.setEditable(false);
        txtJmlKembalian.setBackground(new java.awt.Color(204, 204, 204));
        txtJmlKembalian.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJmlKembalian.setText("0");
        txtJmlKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlKembalianActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Kembalian");

        btnHitungJmlKembalian.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHitungJmlKembalian.setText("Jumlah Kembalian");
        btnHitungJmlKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungJmlKembalianActionPerformed(evt);
            }
        });

        txtJmlBayar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJmlBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlBayarActionPerformed(evt);
            }
        });
        txtJmlBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJmlBayarKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tunai");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Total");

        txtJmlHarga.setEditable(false);
        txtJmlHarga.setBackground(new java.awt.Color(204, 204, 204));
        txtJmlHarga.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtJmlHarga.setText("0");
        txtJmlHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlHargaActionPerformed(evt);
            }
        });

        btnTambah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTambah.setText("Tambah Ke Keranjang");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        txt_jumlahbeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_jumlahbeli.setText("1");
        txt_jumlahbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahbeliActionPerformed(evt);
            }
        });
        txt_jumlahbeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahbeliKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Kuantitas");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Harga Barang");

        txt_HargaBarang.setEditable(false);
        txt_HargaBarang.setBackground(new java.awt.Color(204, 204, 204));
        txt_HargaBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_HargaBarang.setText("0");

        txt_jenisbarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_jenisbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jenisbarangActionPerformed(evt);
            }
        });
        txt_jenisbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jenisbarangKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Barang");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nama Barang");

        txt_namabarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarangActionPerformed(evt);
            }
        });
        txt_namabarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_namabarangKeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        txt_id_penjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_penjualanActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel10.setText("Id Barang");

        tanggal_beli.setText("00.00.00");

        tampil_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tampil_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tampil_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tampil_barang);

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggal_beli)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(Jam, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(400, 400, 400)
                .addComponent(jLabel11)
                .addGap(19, 19, 19)
                .addComponent(id_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addGap(82, 82, 82)
                .addComponent(txt_id_penjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel10)
                .addGap(102, 102, 102)
                .addComponent(txt_id_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel2)
                .addGap(78, 78, 78)
                .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addGap(17, 17, 17)
                .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel3)
                .addGap(84, 84, 84)
                .addComponent(txt_jenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel8)
                .addGap(77, 77, 77)
                .addComponent(txt_HargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel5)
                .addGap(104, 104, 104)
                .addComponent(txt_jumlahbeli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel4)
                .addGap(130, 130, 130)
                .addComponent(txtJmlHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel7)
                .addGap(127, 127, 127)
                .addComponent(txtJmlBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel6)
                .addGap(99, 99, 99)
                .addComponent(txtJmlKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnHitungJmlKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnEditItem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnHapusItem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCetak))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(tanggal_beli)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(Jam))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11)
                    .addComponent(id_karyawan))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txt_id_penjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txt_id_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(btnKeluar))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txt_jenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txt_HargaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txt_jumlahbeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtJmlHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtJmlBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtJmlKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHitungJmlKembalian))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditItem)
                    .addComponent(btnHapusItem)
                    .addComponent(btnBatal)
                    .addComponent(btnCetak)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_jenisbarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jenisbarangKeyReleased
        // TODO add your handling code here:
//        if(txtJmlBeli.getText().equals("") || txtJmlBeli.getText().equals("0")){
//           JOptionPane.showMessageDialog(rootPane, "Jumlah Beli Tidak Boleh Kosong!!!"); 
//           txtJmlBeli.setText("1");
//        }else {
//            int jumlahBeli = Integer.parseInt(txtJmlBeli.getText());
//            int harga = Integer.parseInt(txtHarga.getText());
//            int jumlahHarga = harga * jumlahBeli;
//            txtJmlHarga.setText(String.valueOf(jumlahHarga));
//        }
        
    }//GEN-LAST:event_txt_jenisbarangKeyReleased

    private void txtJmlBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlBayarKeyReleased
        
        if(txtJmlBayar.getText().equals("") || txtJmlBayar.getText().equals("0")){
           JOptionPane.showMessageDialog(rootPane, "Jumlah Bayar Tidak Boleh Kosong!!!"); 
        } else {
            int jharga,bayar,hasil;
        
            jharga=Integer.parseInt(txtJmlHarga.getText());
            bayar=Integer.parseInt(txtJmlBayar.getText());
            hasil= bayar-jharga;
            txtJmlKembalian.setText(String.valueOf(hasil));
        }
    }//GEN-LAST:event_txtJmlBayarKeyReleased

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        txt_jenisbarang.setText("");
        txt_HargaBarang.setText("0");
        txt_jenisbarang.setText("0");
        txtJmlHarga.setText("0");
        txtJmlBayar.setText("");
        txtJmlKembalian.setText("0");
        
        DefaultTableModel model = (DefaultTableModel) tblKeranjang.getModel();
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
        
        btnEditItem.setEnabled(false);
        btnHapusItem.setEnabled(false);
        btnTambah.setEnabled(true);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusItemActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblKeranjang.getModel();
        
        try{
            int SelectedRowIndex = tblKeranjang.getSelectedRow();
            
            int jmlHarga = Integer.parseInt(tblKeranjang.getValueAt(SelectedRowIndex, 3).toString());
            
            model.removeRow(SelectedRowIndex);
            
            hitungTotalHarga();
                    
            txt_HargaBarang.setText("");
            txt_jenisbarang.setText("");
            txt_jenisbarang.setText("");
            
            btnEditItem.setEnabled(false);
            btnHapusItem.setEnabled(false);
            btnTambah.setEnabled(true);

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnHapusItemActionPerformed

    private void btnHitungJmlKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungJmlKembalianActionPerformed
       
      
       int tampung = Integer.parseInt(txtJmlKembalian.getText());
        
       if(tampung < 0){
           JOptionPane.showMessageDialog(this,"duitnya kurang");
       }else if(txtJmlBayar.getText().equals("")){
             JOptionPane.showMessageDialog(this, "Duitnya dulu ngab");
        }else{
            
             try {
      
        
       String sql = 
        "INSERT into penjualan (id_penjualan,tanggal_transaksi,total,tunai,kembali,id_karyawan) values"
               +"('"+txt_id_penjualan.getText()
               +"','"+tanggal_beli.getText()
               +"','"+txtJmlHarga.getText()
               +"','"+txtJmlBayar.getText()
               +"','"+txtJmlKembalian.getText()
               +"','"+id_karyawan.getText()+"')";
               
       java.sql.Connection conn = (Connection) Koneksi.getKoneksi();
       java.sql.PreparedStatement pst = conn.prepareStatement(sql);
       pst.execute();
       reset();
       } catch (Exception ex){
           JOptionPane.showMessageDialog(this, ex.getMessage());
           
       }
    try {
            Connection conn = (Connection) Koneksi.getKoneksi();
            int baris = tblKeranjang.getRowCount();

            for (int i = 0; i < baris; i++) {
                String sql = "INSERT INTO detailpenjualan(id_penjualan, id_barang, jumlah_penjualan) VALUES('"
                        + txt_id_penjualan.getText() + "','" + tblKeranjang.getValueAt(i, 0) + "','"
                        + tblKeranjang.getValueAt(i, 3) + "')";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                
                pst.close();

                
            }
            JOptionPane.showMessageDialog(this, "Duitnya udah masuk ngab ,Terimakasih");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Double jharga,bayar,hasil;
        
        jharga=Double.parseDouble(txtJmlHarga.getText());
        bayar=Double.parseDouble(txtJmlBayar.getText());
        hasil=bayar-jharga;
        txtJmlKembalian.setText(String.valueOf(hasil));
   
            
     } 
           
        
        
        
   
    
        
      
    }//GEN-LAST:event_btnHitungJmlKembalianActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
     balikdata_layananjual();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed


        String id_penjualan = txt_id_penjualan.getText().toString();
        JasperReport report;
        String path = ".\\src\\Report\\report2.jasper";
        try {
            HashMap ha = new HashMap();
            ha.put("id_penjualan", id_penjualan);
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            java.sql.Connection conn =(Connection) Koneksi.getKoneksi();
            JasperPrint jprint = JasperFillManager.fillReport(path, ha, conn);
            JasperViewer jviewer = new JasperViewer(jprint, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
            
        } catch (Exception e) {
            System.out.println("error print " + e);
        }
        balikdata_layananjual();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
  
        if(txt_namabarang.getText().equals("-- Pilih Barang --")){
            JOptionPane.showMessageDialog(rootPane, "Pilih Barang terlebih dahulu!!!");
        } else {
            DefaultTableModel model = (DefaultTableModel) tblKeranjang.getModel();
            int jmlHargaBarang = Integer.parseInt(txt_HargaBarang.getText()) * Integer.parseInt(txt_jumlahbeli.getText());
            model.addRow(new Object[] {txt_id_barang.getText(),txt_namabarang.getText(), txt_HargaBarang.getText(),txt_jumlahbeli.getText() ,txt_jenisbarang.getText(), jmlHargaBarang});
         
            hitungTotalHarga();
           
          txt_namabarang.setText("");
          txt_HargaBarang.setText("");
          txt_jenisbarang.setText("");
          txt_id_barang.setText("");
          
            
          
        }
        
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tblKeranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeranjangMouseClicked
        int table = tblKeranjang.getSelectedRow();
        
        String a = tblKeranjang.getValueAt(table, 0).toString();
        String b = tblKeranjang.getValueAt(table, 1).toString();
        String c = tblKeranjang.getValueAt(table, 2).toString();
        
        txt_jenisbarang.setText(a);
        txt_HargaBarang.setText(b);
        txt_jenisbarang.setText(c);
        
        btnEditItem.setEnabled(true);
        btnHapusItem.setEnabled(true);
        btnTambah.setEnabled(false);
    }//GEN-LAST:event_tblKeranjangMouseClicked

    private void btnEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditItemActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblKeranjang.getModel();
        int i = tblKeranjang.getSelectedRow();
                
        if(i >= 0) 
        {
            model.setValueAt(txt_jenisbarang.getText(), i, 0);
            model.setValueAt(txt_HargaBarang.getText(), i, 1);
            model.setValueAt(txt_jenisbarang.getText(), i, 2);
           
            int jmlHargaBarang = Integer.parseInt(txt_HargaBarang.getText()) * Integer.parseInt(txt_jenisbarang.getText());
    
            model.setValueAt(jmlHargaBarang, i, 3);
            hitungTotalHarga();
            
            txt_HargaBarang.setText("");
            txt_jenisbarang.setText("");
            txt_jenisbarang.setText("");
            
            btnEditItem.setEnabled(false);
            btnHapusItem.setEnabled(false);
            btnTambah.setEnabled(true);
            
        }
        else{
            System.out.println("Update Error");
        }
    }//GEN-LAST:event_btnEditItemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txt_jenisbarang.setText("");
        txt_HargaBarang.setText("");
        txt_jenisbarang.setText("");
        
        btnEditItem.setEnabled(false);
        btnHapusItem.setEnabled(false);
        btnTambah.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtJmlHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlHargaActionPerformed
        
    }//GEN-LAST:event_txtJmlHargaActionPerformed

    private void txtJmlKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJmlKembalianActionPerformed

    private void txt_jumlahbeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahbeliKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahbeliKeyReleased

    private void txt_jenisbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jenisbarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jenisbarangActionPerformed

    private void txt_namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangActionPerformed

    private void txt_namabarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namabarangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangKeyReleased

    private void txt_jumlahbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahbeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahbeliActionPerformed

    private void tampil_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tampil_barangMouseClicked
        kliktable_layananpenjualan();
    }//GEN-LAST:event_tampil_barangMouseClicked

    private void txt_id_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_barangActionPerformed
     
    }//GEN-LAST:event_txt_id_barangActionPerformed

    private void txt_id_penjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_penjualanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_penjualanActionPerformed

    private void txtJmlBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJmlBayarActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String key=jTextField1.getText();
        if(key!=""){
            DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Barang");
        tbl.addColumn("Nama Barang");
        tbl.addColumn("Jenis Produk");
        tbl.addColumn("Harga Produk");
        try {
            String sql = "Select * from barang where id_barang like '%" + jTextField1.getText() + "%'" +
                          "or nama_barang like '%" + jTextField1.getText() + "%'";
            java.sql.Connection conn =(Connection) Koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res =  stm.executeQuery(sql);
            while (res.next()){
                tbl.addRow(new Object[] {res.getString(1),res.getString(2),res.getString(3),res.getString(6)});
            }tampil_barang.setModel(tbl);
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }else{
            tampil();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(layanan_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(layanan_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(layanan_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(layanan_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new layanan_jual().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(layanan_jual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Jam;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnEditItem;
    private javax.swing.JButton btnHapusItem;
    private javax.swing.JButton btnHitungJmlKembalian;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel id_karyawan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tampil_barang;
    private javax.swing.JLabel tanggal_beli;
    private javax.swing.JTable tblKeranjang;
    private javax.swing.JTextField txtJmlBayar;
    private javax.swing.JTextField txtJmlHarga;
    private javax.swing.JTextField txtJmlKembalian;
    private javax.swing.JTextField txt_HargaBarang;
    private javax.swing.JTextField txt_id_barang;
    private javax.swing.JTextField txt_id_penjualan;
    private javax.swing.JTextField txt_jenisbarang;
    private javax.swing.JTextField txt_jumlahbeli;
    private javax.swing.JTextField txt_namabarang;
    // End of variables declaration//GEN-END:variables
}
