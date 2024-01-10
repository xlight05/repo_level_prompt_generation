/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.DuongLogic;
import Entities.DUONG;
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
@Path("DUONG")
public class DUONG_Resource {

    @Context
    private UriInfo context;
    DuongLogic db;

    /**
     * Creates a new instance of DUONG_Resource
     */
    public DUONG_Resource() throws MyServiceException {
        try{
            db = new DuongLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<DUONG> GetAllRows(){
        try {
            System.out.print("\n DUONG/GetAllRows - ");
            ArrayList<DUONG> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Phan Chu Trinh") @QueryParam("tenDuong") String tenDuong){
        try {
            System.out.print("\n DUONG/Insert - ");
            String s = db.Insert(tenDuong);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong,
        @DefaultValue("Phan Chu Trinh") @QueryParam("tenDuong") String tenDuong){
        try {
            System.out.print("\n DUONG/Update - ");
            String s = db.Update(maDuong, tenDuong);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("DG005") @QueryParam("maDuong") String maDuong){
        try {
            System.out.print("\n DUONG/Delete - ");
            String s = db.Delete(maDuong).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public DUONG GetRowByID(
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong){
        try {
            System.out.print("\n DUONG/GetRowByID - ");
            DUONG obj = db.GetRowByID(maDuong);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("DG001") @QueryParam("maDuong") String maDuong){
        try {
            System.out.print("\n DUONG/ValidationID - ");
            String s = db.ValidationID(maDuong).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DUONG");
        }
    }
}
