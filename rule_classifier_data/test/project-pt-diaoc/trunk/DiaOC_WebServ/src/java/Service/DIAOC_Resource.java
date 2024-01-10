/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.DiaOcLogic;
import Entities.DIAOC;
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
@Path("DIAOC")
public class DIAOC_Resource {

    @Context
    private UriInfo context;
    DiaOcLogic db;

    /**
     * Creates a new instance of DIAOC_Resource
     */
    public DIAOC_Resource() throws MyServiceException {
        try{
            db = new DiaOcLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<DIAOC> GetAllRows(){
        try {
            System.out.print("\n DIAOC/GetAllRows - ");
            ArrayList<DIAOC> list = db.GetAllRows();
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc,
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho,
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan,
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong,
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong,
        @DefaultValue("20/10") @QueryParam("DiaChiCT") String DiaChiCT,
        @DefaultValue("30") @QueryParam("dienTichDat") float dienTichDat,
        @DefaultValue("20") @QueryParam("dienTichXayDung") float dienTichXayDung,
        @DefaultValue("Nam") @QueryParam("huong") String huong,
        @DefaultValue("Ngoai o") @QueryParam("viTri") String viTri,
        @DefaultValue("Nha Dep") @QueryParam("moTa") String moTa,
        @DefaultValue("Chua Ban") @QueryParam("trangThai") String trangThai,
        @DefaultValue("1000") @QueryParam("giaBan") float giaBan){
        try {
            System.out.print("\n DIAOC/Insert - ");
            String s = db.Insert(maLoaiDiaOc, maThanhPho, maQuan, maPhuong, maDuong, DiaChiCT, 
                    dienTichDat, dienTichXayDung, huong, viTri, moTa, trangThai, giaBan);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc,    
        @DefaultValue("DO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc,
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho,
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan,
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong,
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong,
        @DefaultValue("20/10") @QueryParam("DiaChiCT") String DiaChiCT,
        @DefaultValue("30") @QueryParam("dienTichDat") float dienTichDat,
        @DefaultValue("20") @QueryParam("dienTichXayDung") float dienTichXayDung,
        @DefaultValue("Nam") @QueryParam("huong") String huong,
        @DefaultValue("Ngoai o") @QueryParam("viTri") String viTri,
        @DefaultValue("Nha Dep") @QueryParam("moTa") String moTa,
        @DefaultValue("Chua Ban") @QueryParam("trangThai") String trangThai,
        @DefaultValue("0") @QueryParam("giaBan") float giaBan){
        try {
            System.out.print("\n DIAOC/Update - ");
            String s = db.Update(maDiaOc, maLoaiDiaOc, maThanhPho, maQuan, maPhuong, maDuong, 
                    DiaChiCT, dienTichDat, dienTichXayDung, huong, viTri, moTa, trangThai, giaBan);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/Delete - ");
            String s = db.Delete(maDiaOc).toString();
            System.out.println("Success");
            return s;
            
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public DIAOC GetRowByID(
        @DefaultValue("DO004") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/GetRowByID - ");
            DIAOC obj = db.GetRowByID(maDiaOc);
            System.out.println("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/ValidationID - ");
            String s = db.ValidationID(maDiaOc).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/IsLoaiDiaOcExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsLoaiDiaOcExist(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n DIAOC/IsLoaiDiaOcExist - ");
            String s = db.IsLoaiDiaOcExist(maLoaiDiaOc).toString();
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetRowsByMaLoaiDiaOc")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<DIAOC> GetRowsByMaLoaiDiaOc(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n DIAOC/GetRowsByMaLoaiDiaOc - ");
            ArrayList<DIAOC> list = db.GetRowsByMaLoaiDiaOc(maLoaiDiaOc);
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetRowsByTrangThai")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<DIAOC> GetRowsByTrangThai(
        @DefaultValue("Chưa Bán") @QueryParam("trangThai") String trangThai){
        try {
            System.out.print("\n DIAOC/GetRowsByTrangThai - ");
            ArrayList<DIAOC> list = db.GetRowsByTrangThai(trangThai);
            System.out.println("Success");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetMaND_ToBuom_From_MaDiaOc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetMaND_ToBuom_From_MaDiaOc(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/GetMaND_ToBuom_From_MaDiaOc - ");
            String s = db.GetMaND_ToBuom_From_MaDiaOc(maDiaOc);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetMaND_QCBao_From_MaDiaOc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetMaND_QCBao_From_MaDiaOc(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/GetMaND_QCBao_From_MaDiaOc - ");
            String s = db.GetMaND_QCBao_From_MaDiaOc(maDiaOc);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
    
    @Path("/GetMaND_QCBang_From_MaDiaOc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetMaND_QCBang_From_MaDiaOc(
        @DefaultValue("DO001") @QueryParam("maDiaOc") String maDiaOc){
        try {
            System.out.print("\n DIAOC/GetMaND_QCBang_From_MaDiaOc - ");
            String s = db.GetMaND_QCBang_From_MaDiaOc(maDiaOc);
            System.out.println("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.toString(), "DIAOC");
        }
    }
}
