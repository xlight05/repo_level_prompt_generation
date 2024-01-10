/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ToBuomLogic;
import Entities.TOBUOM;
import ExceptionMessage.MyServiceException;
import java.util.ArrayList;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
@Path("TOBUOM")
public class TOBUOM_Resource {

    @Context
    private UriInfo context;
    ToBuomLogic db;

    /**
     * Creates a new instance of TOBUOM_Resource
     */
    public TOBUOM_Resource() throws MyServiceException {
        try{
            db = new ToBuomLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<TOBUOM> GetAllRows(){
        try {
            System.out.print("\n TOBUOM/GetAllRows - ");
            ArrayList<TOBUOM> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTT001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n TOBUOM/Insert - ");
            String s = db.Insert(maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("QCT001") @QueryParam("maToBuom") String maToBuom,
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTT001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n TOBUOM/Update - ");
            String s = db.Update(maToBuom, maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("QCT001") @QueryParam("maToBuom") String maToBuom){
        try {
            System.out.print("\n TOBUOM/Delete - ");
            String s = db.Delete(maToBuom).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TOBUOM GetRowByID(
        @DefaultValue("QCT001") @QueryParam("maToBuom") String maToBuom){
        try {
            System.out.print("\n TOBUOM/GetRowByID - ");
            TOBUOM obj = db.GetRowByID(maToBuom);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("QCT001") @QueryParam("maToBuom") String maToBuom){
        try {
            System.out.print("\n TOBUOM/ValidationID - ");
            String s = db.ValidationID(maToBuom).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/IsMaND_ToBuomExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaND_ToBuomExist(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n TOBUOM/IsMaND_ToBuomExist - ");
            String s = db.IsMaND_ToBuomExist(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/IsMaPhieuDK_ToBuomExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaPhieuDK_ToBuomExist(
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n TOBUOM/IsMaPhieuDK_ToBuomExist - ");
            String s = db.IsMaPhieuDK_ToBuomExist(maPhieuDK).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/IsMaGiaTien_ToBuomExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaGiaTien_ToBuomExist(
        @DefaultValue("GTT001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n TOBUOM/IsMaGiaTien_ToBuomExist - ");
            String s = db.IsMaGiaTien_ToBuomExist(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
    
    @Path("/GetToBuomByMaPhieuDK")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TOBUOM GetToBuomByMaPhieuDK(
        @DefaultValue("PDK003") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n TOBUOM/GetToBuomByMaPhieuDK - ");
            TOBUOM obj = db.GetToBuomByMaPhieuDK(maPhieuDK);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "TOBUOM");
        }
    }
}
