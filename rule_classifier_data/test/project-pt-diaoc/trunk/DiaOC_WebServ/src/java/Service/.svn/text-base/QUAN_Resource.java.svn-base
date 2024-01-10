/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.QuanLogic;
import Entities.QUAN;
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
@Path("QUAN")
public class QUAN_Resource {

    @Context
    private UriInfo context;
    QuanLogic db;

    /**
     * Creates a new instance of QUAN_Resource
     */
    public QUAN_Resource() throws MyServiceException {
        try{
            db = new QuanLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<QUAN> GetAllRows(){
        try {
            System.out.print("\n QUAN/GetAllRows - ");
            ArrayList<QUAN> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Quan 7") @QueryParam("tenQuan") String tenQuan){
        try {
            System.out.print("\n QUAN/Insert - ");
            String s = db.Insert(tenQuan);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan,
        @DefaultValue("Quan 4") @QueryParam("tenQuan") String tenQuan){
        try {
            System.out.print("\n QUAN/Update - ");
            String s = db.Update(maQuan, tenQuan);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan){
        try {
            System.out.print("\n QUAN/Delete - ");
            String s = db.Delete(maQuan).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public QUAN GetRowByID(
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan){
        try {
            System.out.print("\n QUAN/GetRowByID - ");
            QUAN obj = db.GetRowByID(maQuan);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("QN001") @QueryParam("maQuan") String maQuan){
        try {
            System.out.print("\n QUAN/ValidationID - ");
            String s = db.ValidationID(maQuan).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QUAN");
        }
    }
}
