/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tokokomputer.form;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import javax.swing.text.Document;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




/**
 *
 * @author User
 */
public class Cetak {
    Cetak(String[] judul, String[][] oo, int bar, int kol, String total, String tunai, String kembalian) throws FileNotFoundException, DocumentException{
        
        Document d = new Document();
        PdfWriter.getInstance(d, new FileOutputStream("Nota.pdf"));
        
        PdfPTable pt = new PdfPTable(judul.length);
        d.open();
        
        Paragraph p = new Paragraph("Nota Pembayaran");
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingAfter(10);
        d.add(p);
        
        for(int i = 0; i < judul.length; i++){
           pt.addCell(judul[i]);
        }
        
        for(int i =0; i < bar; i++){
            for(int j=0; j < kol; j++){
                pt.addCell(oo[i][j]);
            }
        }
        
        Paragraph pTotal = new Paragraph("Total\t : Rp." + total);
        pTotal.setAlignment(Paragraph.ALIGN_RIGHT);
        
        Paragraph pBayar = new Paragraph("Tunai\t : Rp." + tunai);
        pBayar.setAlignment(Paragraph.ALIGN_RIGHT);
        
        Paragraph pKembalian = new Paragraph("Kembalian\t : Rp." + kembalian);
        pKembalian.setAlignment(Paragraph.ALIGN_RIGHT);
        
        d.add(pt);
        d.add(pTotal);
        d.add(pBayar);
        d.add(pKembalian);
        d.close();
    }
}
