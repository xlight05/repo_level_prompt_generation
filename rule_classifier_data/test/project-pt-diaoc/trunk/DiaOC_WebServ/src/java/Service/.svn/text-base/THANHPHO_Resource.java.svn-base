/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ThanhPhoLogic;
import Entities.THANHPHO;
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
@Path("THANHPHO")
public class THANHPHO_Resource {

    @Context
    private UriInfo context;
    ThanhPhoLogic db;

    /**
     * Creates a new instance of THANHPHO_Resource
     */
    public THANHPHO_Resource() throws MyServiceException {
        try{
            db = new ThanhPhoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERRORs");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THANHPHO> GetAllRows(){
        try {
            System.out.print("\n THANHPHO/GetAllRows - ");
            ArrayList<THANHPHO> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Ho Chi Minh") @QueryParam("tenThanhPho") String tenThanhPho){
        try {
            System.out.print("\n THANHPHO/Insert - ");
            String s = db.Insert(tenThanhPho);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho,
        @DefaultValue("Ho Chi Minh") @QueryParam("tenThanhPho") String tenThanhPho){
        try {
            System.out.print("\n THANHPHO/Update - ");
            String s = db.Update(maThanhPho, tenThanhPho);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("TP002") @QueryParam("maThanhPho") String maThanhPho){
        try {
            System.out.print("\n THANHPHO/Delete - ");
            String s = db.Delete(maThanhPho).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public THANHPHO GetRowByID(
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho){
        try {
            System.out.print("\n THANHPHO/GetRowByID - ");
            THANHPHO obj = db.GetRowByID(maThanhPho);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("TP001") @QueryParam("maThanhPho") String maThanhPho){
        try {
            System.out.print("\n THANHPHO/ValidationID - ");
            String s = db.ValidationID(maThanhPho).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THANHPHO");
        }
    }
}
