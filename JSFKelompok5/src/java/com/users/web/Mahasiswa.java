/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.users.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author RYH
 */
@ManagedBean
@RequestScoped
public class Mahasiswa {
    
    private String NIM;
    public void setNIM(String NIM) {
        this.NIM = NIM;
    }
    public String getNIM() {
        return NIM;
    }

    private String NAMA;
    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }
    public String getNAMA() {
        return NAMA;
    }
    
    private String PENJURUSAN;
    public void setPENJURUSAN(String PENJURUSAN) {
        this.PENJURUSAN = PENJURUSAN;
    }
    public String getPENJURUSAN() {
        return PENJURUSAN;
    }
   
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

  public String Edit_Mahasiswa(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String > params = fc.getExternalContext().getRequestParameterMap();
     String Field_NIM = params.get("action");
     try {
          Koneksi obj_koneksi = new Koneksi();
          Connection connection = obj_koneksi.get_connection();
          Statement st = connection.createStatement();
          ResultSet rs = st.executeQuery("select * from mahasiswa where NIM="+Field_NIM);
          Mahasiswa obj_Mahasiswa = new Mahasiswa();
          rs.next();
          obj_Mahasiswa.setNIM(rs.getString("NIM"));
          obj_Mahasiswa.setNAMA(rs.getString("Nama"));
          obj_Mahasiswa.setPENJURUSAN(rs.getString("ID_Penjurusan"));
          sessionMap.put("EditMahasiswa", obj_Mahasiswa);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/Edit.xhtml?faces-redirect=true";   
   }

  public String Delete_Mahasiswa(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String Field_NIM = params.get("action");
      try {
         Koneksi obj_koneksi = new Koneksi();
         Connection connection = obj_koneksi.get_connection();
         PreparedStatement ps = connection.prepareStatement("DELETE FROM mahasiswa WHERE NIM=?");
         ps.setString(1, Field_NIM);
         System.out.println(ps);
         ps.executeUpdate();
            } catch (Exception e) {
         System.out.println(e);
        }
       return "/index.xhtml?faces-redirect=true";   
  }

  public String Update_Mahasiswa(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String Update_NIM= params.get("Update_NIM");
      
      try {
          if(PENJURUSAN.equals("1") || PENJURUSAN.equals("2") || PENJURUSAN.equals("3") || PENJURUSAN.equals("4")){
          Koneksi obj_koneksi = new Koneksi();
          Connection connection = obj_koneksi.get_connection();
          PreparedStatement ps = connection.prepareStatement("update mahasiswa set NIM=?, Nama=?, ID_Penjurusan=? where NIM=?");
          ps.setString(1, NIM);
          ps.setString(2, NAMA);
          ps.setString(3, PENJURUSAN);
          ps.setString(4, Update_NIM);
          System.out.println(ps);
          ps.executeUpdate();
          }
          else {
              return "/EditError.xhtml?faces-redirect=true";
          }
      } catch (Exception e) {
          System.out.println(e);
      }
      return "/index.xhtml?faces-redirect=true";   
  }
    
    public ArrayList getGet_all_mahasiswa() throws Exception{
           ArrayList list_of_mahasiswa=new ArrayList();
           Connection connection=null;
        try {
            Koneksi obj_koneksi = new Koneksi();
            connection = obj_koneksi.get_connection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT mahasiswa.NIM, mahasiswa.Nama, penjurusan.nama FROM mahasiswa INNER JOIN penjurusan ON mahasiswa.ID_Penjurusan = penjurusan.ID ORDER BY mahasiswa.NIM ASC");
            while(rs.next()){
                Mahasiswa obj_Mahasiswa = new Mahasiswa();
                obj_Mahasiswa.setNIM(rs.getString("mahasiswa.NIM"));
                obj_Mahasiswa.setNAMA(rs.getString("mahasiswa.Nama"));
                obj_Mahasiswa.setPENJURUSAN(rs.getString("penjurusan.nama"));
                list_of_mahasiswa.add(obj_Mahasiswa);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_mahasiswa;
    }
    
  public String Tambah_Mahasiswa(){
      try {
          Connection connection = null;
          Koneksi obj_koneksi = new Koneksi();
          connection = obj_koneksi.get_connection();
          PreparedStatement ps = connection.prepareStatement("INSERT INTO mahasiswa(NIM, Nama, ID_Penjurusan) value('"+NIM+"','"+NAMA+"','"+PENJURUSAN+"')");
          if (PENJURUSAN.equals("Software") || PENJURUSAN.equals("software")){
               PreparedStatement pc = connection.prepareStatement("INSERT INTO mahasiswa(NIM, Nama, ID_Penjurusan) value('"+NIM+"','"+NAMA+"','1')");
               pc.executeUpdate();
               return "/index.xhtml?faces-redirect=true";
            } else if (PENJURUSAN.equals("Embedded") || PENJURUSAN.equals("embedded")){
               PreparedStatement pc = connection.prepareStatement("INSERT INTO mahasiswa(NIM, Nama, ID_Penjurusan) value('"+NIM+"','"+NAMA+"','4')");
               pc.executeUpdate();
               return "/index.xhtml?faces-redirect=true";
            } else if (PENJURUSAN.equals("Network") || PENJURUSAN.equals("network")){
               PreparedStatement pc = connection.prepareStatement("INSERT INTO mahasiswa(NIM, Nama, ID_Penjurusan) value('"+NIM+"','"+NAMA+"','2')");
               pc.executeUpdate();
               return "/index.xhtml?faces-redirect=true";
            } else if (PENJURUSAN.equals("Multimedia") || PENJURUSAN.equals("multimedia")){
               PreparedStatement pc = connection.prepareStatement("INSERT INTO mahasiswa(NIM, Nama, ID_Penjurusan) value('"+NIM+"','"+NAMA+"','3')");
               pc.executeUpdate();
               return "/index.xhtml?faces-redirect=true";
            } else {
                return "/TambahError.xhtml?faces-redirect=true";
            }
      } catch (Exception e) {
          System.out.println(e);
      }
       return "/index.xhtml?faces-redirect=true";
  }
  public Mahasiswa() {}   
}