/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.PhuongLogic;
import Entities.PHUONG;
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
@Path("PHUONG")
public class PHUONG_Resource {

    @Context
    private UriInfo context;
    PhuongLogic db;

    /**
     * Creates a new instance of PHUONG_Resource
     */
    
    public PHUONG_Resource() throws MyServiceException {
        try{
            db = new PhuongLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<PHUONG> GetAllRows(){
        try {
            System.out.print("\n PHUONG/GetAllRows - ");
            ArrayList<PHUONG> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Phuong 7") @QueryParam("tenPhuong") String tenPhuong){
        try {
            System.out.print("\n PHUONG/Insert - ");
            String s = db.Insert(tenPhuong);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong,
        @DefaultValue("Phuong 5") @QueryParam("tenPhuong") String tenPhuong){
        try {
            System.out.print("\n PHUONG/Update - ");
            String s = db.Update(maPhuong, maPhuong);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("PG005") @QueryParam("maPhuong") String maPhuong){
        try {
            System.out.print("\n PHUONG/Delete - ");
            String s = db.Delete(maPhuong).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public PHUONG GetRowByID(
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong){
        try {
            System.out.print("\n PHUONG/GetRowByID - ");
            PHUONG obj = db.GetRowByID(maPhuong);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("PG001") @QueryParam("maPhuong") String maPhuong){
        try {
            System.out.print("\n PHUONG/ValidationID - ");
            String s = db.ValidationID(maPhuong).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }
}
