/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.GiaTienQCBangLogic;
import Entities.GIATIEN_QC_BANG;
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
@Path("GIATIENBANG")
public class GIATIENBANG_Resource {

    @Context
    private UriInfo context;
    GiaTienQCBangLogic db;

    /**
     * Creates a new instance of GIATIENBANG_Resource
     */
    public GIATIENBANG_Resource() throws MyServiceException {
        try{
            db = new GiaTienQCBangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<GIATIEN_QC_BANG> GetAllRows(){
        try {
            System.out.print("\n GIATIEN_QC_BANG/GetAllRows - ");
            ArrayList<GIATIEN_QC_BANG> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang,
        @DefaultValue("Bang A") @QueryParam("moTa") String moTa,
        @DefaultValue("40") @QueryParam("kichCo") Float kichCo,
        @DefaultValue("??") @QueryParam("dvt") String dvt,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_BANG/Insert - ");
            String s = db.Insert(maLoaiBang, moTa, kichCo, dvt, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien,    
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang,
        @DefaultValue("Bang A") @QueryParam("moTa") String moTa,
        @DefaultValue("40") @QueryParam("kichCo") Float kichCo,
        @DefaultValue("??") @QueryParam("dvt") String dvt,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_BANG/Update - ");
            String s = db.Update(maGiaTien, maLoaiBang, moTa, kichCo, dvt, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BANG/Delete - ");
            String s = db.Delete(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GIATIEN_QC_BANG GetRowByID(
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BANG/GetRowByID - ");
            GIATIEN_QC_BANG obj = db.GetRowByID(maGiaTien);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BANG/ValidationID - ");
            String s = db.ValidationID(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
    
    @Path("/IsMaLoaiBangExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaLoaiBangExist(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang){
        try {
            System.out.print("\n GIATIEN_QC_BANG/IsMaLoaiBangExist - ");
            String s = db.IsMaLoaiBangExist(maLoaiBang).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }  
    
    @Path("/GetLoaiBang")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetLoaiBang(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang){
        try {
            System.out.print("\n GIATIEN_QC_BANG/GetLoaiBang - ");
            String s = db.GetLoaiBang(maLoaiBang);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BANG");
        }
    }
}
