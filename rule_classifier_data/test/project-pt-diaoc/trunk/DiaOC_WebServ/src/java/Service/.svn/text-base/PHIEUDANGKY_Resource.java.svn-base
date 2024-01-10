/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.PhieuDangKyLogic;
import Entities.PHIEUDANGKY;
import ExceptionMessage.MyServiceException;
import java.sql.Date;
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
@Path("PHIEUDANGKY")
public class PHIEUDANGKY_Resource {

    @Context
    private UriInfo context;
    PhieuDangKyLogic db;

    /**
     * Creates a new instance of PHIEUDANGKY_Resource
     */
    public PHIEUDANGKY_Resource() throws MyServiceException {
        try{
            db = new PhieuDangKyLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<PHIEUDANGKY> GetAllRows(){
        try {
            System.out.print("\n PHIEUDANGKY/GetAllRows - ");
            ArrayList<PHIEUDANGKY> list = db.GetAllRows();
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc,
        @DefaultValue("1382202000000") @QueryParam("tuNgay") long tuNgay,
        @DefaultValue("1382202000000") @QueryParam("denNgay") long denNgay,
        @DefaultValue("1000") @QueryParam("soTien") float soTien,
        @DefaultValue("1382202000000") @QueryParam("thoiGianHenChupAnh") long thoiGianHenChupAnh,
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n PHIEUDANGKY/Insert - ");
            Date tN = new Date(tuNgay);
            Date dN = new Date(denNgay);
            Date nH = new Date(thoiGianHenChupAnh);
            String s = db.Insert(maDiaOc, tN, dN, soTien, nH, maKH);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("PDK001") @QueryParam("maPhieuDangKy") String maPhieuDangKy,    
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc,
        @DefaultValue("1382202000000") @QueryParam("tuNgay") long tuNgay,
        @DefaultValue("1382202000000") @QueryParam("denNgay") long denNgay,
        @DefaultValue("1000") @QueryParam("soTien") float soTien,
        @DefaultValue("1382202000000") @QueryParam("thoiGianHenChupAnh") long thoiGianHenChupAnh,
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n PHIEUDANGKY/Update - ");
            Date tN = new Date(tuNgay);
            Date dN = new Date(denNgay);
            Date nH = new Date(thoiGianHenChupAnh);
            String s = db.Update(maPhieuDangKy, maDiaOc, tN, dN, soTien, nH, maKH);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("PDK005") @QueryParam("maPhieuDangKy") String maPhieuDangKy){
        try {
            System.out.print("\n PHIEUDANGKY/Delete - ");
            String s = db.Delete(maPhieuDangKy).toString();
            System.out.println("Success");
            return s;
            
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public PHIEUDANGKY GetRowByID(
        @DefaultValue("PDK001") @QueryParam("maPhieuDangKy") String maPhieuDangKy){
        try {
            System.out.print("\n PHIEUDANGKY/GetRowByID - ");
            PHIEUDANGKY obj = db.GetRowByID(maPhieuDangKy);
            System.out.println("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("PDK001") @QueryParam("maPhieuDangKy") String maPhieuDangKy){
        try {
            System.out.print("\n PHIEUDANGKY/ValidationID - ");
            String s = db.ValidationID(maPhieuDangKy).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/IsDiaOcExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsDiaOcExist(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n PHIEUDANGKY/IsDiaOcExist - ");
            String s = db.IsDiaOcExist(maDiaOc).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/IsKhachHangExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsKhachHangExist(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n PHIEUDANGKY/IsKhachHangExist - ");
            String s = db.IsKhachHangExist(maKH).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/GetPhieuDKByMaDiaOc")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public PHIEUDANGKY GetPhieuDKByMaDiaOc(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n PHIEUDANGKY/GetPhieuDKByMaDiaOc - ");
            PHIEUDANGKY obj = db.GetPhieuDKByMaDiaOc(maDiaOc);
            System.out.println("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
    
    @Path("/GetAllPhieuDKByMaDiaOc")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<PHIEUDANGKY> GetAllPhieuDKByMaDiaOc(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n PHIEUDANGKY/GetAllPhieuDKByMaDiaOc - ");
            ArrayList<PHIEUDANGKY> list = db.GetAllPhieuDKByMaDiaOc(maDiaOc);
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHIEUDANGKY");
        }
    }
}
