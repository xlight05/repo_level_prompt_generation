/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.KhachHangLogic;
import Entities.KHACHHANG;
import ExceptionMessage.MyServiceException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Wjnd_Field
 */
@Path("KHACHHANG")
public class KHACHHANG_Resource {

    @Context
    private UriInfo context;
    KhachHangLogic db;

    /**
     * Creates a new instance of KHACHHANG_Resource
     */
    public KHACHHANG_Resource() throws MyServiceException {
        try{
            db = new KhachHangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<KHACHHANG> GetAllRows(){
        try {
            System.out.print("\n KHACHHANG/GetAllRows - ");
            ArrayList<KHACHHANG> list = db.GetAllRows();
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Duy") @QueryParam("hoTen") String hoTen,
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho,
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan,
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong,
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong,
        @DefaultValue("20/10") @QueryParam("DiaChiCT") String DiaChiCT,
        @DefaultValue("01223008424") @QueryParam("soDT") String soDT,
        @DefaultValue("phanhoangduy@gmail.com") @QueryParam("email") String email){
        try {
            System.out.print("\n KHACHHANG/Insert - ");
            String s = db.Insert(hoTen, maThanhPho, maQuan, maPhuong, maDuong,
                    DiaChiCT, soDT, email);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH,    
        @DefaultValue("Duy") @QueryParam("hoTen") String hoTen,
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho,
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan,
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong,
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong,
        @DefaultValue("20/10") @QueryParam("DiaChiCT") String DiaChiCT,
        @DefaultValue("01223008424") @QueryParam("soDT") String soDT,
        @DefaultValue("phanhoangduy@gmail.com") @QueryParam("email") String email){
        try {
            System.out.print("\n KHACHHANG/Update - ");
            String s = db.Update(maKH, hoTen, maThanhPho, maQuan, maPhuong,
                    maDuong, DiaChiCT, soDT, email);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n KHACHHANG/Delete - ");
            String s = db.Delete(maKH).toString();
            System.out.println("Success");
            return s;
            
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public KHACHHANG GetRowByID(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n KHACHHANG/GetRowByID - ");
            KHACHHANG obj = db.GetRowByID(maKH);
            System.out.println("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n KHACHHANG/ValidationID - ");
            String s = db.ValidationID(maKH).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/GetRowsByName")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<KHACHHANG> GetRowsByName(
        @DefaultValue("Duy") @QueryParam("hoTen") String hoTen){
        try {
            System.out.print("\n KHACHHANG/GetAllRows - ");
            ArrayList<KHACHHANG> list = db.GetRowsByName(hoTen);
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
    
    @Path("/GetRowsByEmail")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<KHACHHANG> GetRowsByEmail(
        @DefaultValue("phanhoangduy@gmail.com") @QueryParam("email") String email){
        try {
            System.out.print("\n KHACHHANG/GetAllRows - ");
            ArrayList<KHACHHANG> list = db.GetRowsByEmail(email);
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "KHACHHANG");
        }
    }
}
