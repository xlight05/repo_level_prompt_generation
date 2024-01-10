/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.GiaTienQCToBuomLogic;
import Entities.GIATIEN_TOBUOM;
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
@Path("GIATIENTOBUOM")
public class GIATIENTOBUOM_Resource {

    @Context
    private UriInfo context;
    GiaTienQCToBuomLogic db;

    /**
     * Creates a new instance of GIATIENBANG_Resource
     */
    public GIATIENTOBUOM_Resource() throws MyServiceException {
        try{
            db = new GiaTienQCToBuomLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<GIATIEN_TOBUOM> GetAllRows(){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/GetAllRows - ");
            ArrayList<GIATIEN_TOBUOM> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("20") @QueryParam("soLuong") int soLuong,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/Insert - ");
            String s = db.Insert(soLuong, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("GTT004") @QueryParam("maGiaTien") String maGiaTien,
        @DefaultValue("20") @QueryParam("soLuong") int soLuong,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/Update - ");
            String s = db.Update(maGiaTien, soLuong, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("GTT006") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/Delete - ");
            String s = db.Delete(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GIATIEN_TOBUOM GetRowByID(
        @DefaultValue("GTT001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/GetRowByID - ");
            GIATIEN_TOBUOM obj = db.GetRowByID(maGiaTien);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("GTT001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/ValidationID - ");
            String s = db.ValidationID(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
    
    @Path("/GetGiaBySoLuong")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetGiaBySoLuong(
        @DefaultValue("30") @QueryParam("soLuong") int soLuong){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/GetGiaBySoLuong - ");
            String s = db.GetGiaBySoLuong(soLuong).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }  
    
    @Path("/GetIDBySoLuong")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetIDBySoLuong(
        @DefaultValue("30") @QueryParam("soLuong") int soLuong){
        try {
            System.out.print("\n GIATIEN_QC_TOBUOM/GetIDBySoLuong - ");
            String s = db.GetIDBySoLuong(soLuong);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_TOBUOM");
        }
    }
}
