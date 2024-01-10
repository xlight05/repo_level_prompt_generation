/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.LoaiBangLogic;
import Entities.LOAIBANG;
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
@Path("LOAIBANG")
public class LOAIBANG_Resource {

    @Context
    private UriInfo context;
    LoaiBangLogic db;


    /**
     * Creates a new instance of LOAIBANG_Resource
     * @throws java.lang.Exception
     */
    public LOAIBANG_Resource() throws Exception {
        try{
            db = new LoaiBangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "PHUONG");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<LOAIBANG> GetAllRows(){
        try {
            System.out.print("\n LOAIBANG/GetAllRows - ");
            ArrayList<LOAIBANG> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("20x40") @QueryParam("loaiBang") String loaiBang){
        try {
            System.out.print("\n LOAIBANG/Insert - ");
            String s = db.Insert(loaiBang);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang,
        @DefaultValue("20x10") @QueryParam("loaiBang") String loaiBang){
        try {
            System.out.print("\n LOAIBANG/Update - ");
            String s = db.Update(maLoaiBang, loaiBang);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("LB006") @QueryParam("maLoaiBang") String maLoaiBang){
        try {
            System.out.print("\n LOAIBANG/Delete - ");
            String s = db.Delete(maLoaiBang).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public LOAIBANG GetRowByID(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang){
        try {
            System.out.print("\n LOAIBANG/GetRowByID - ");
            LOAIBANG obj = db.GetRowByID(maLoaiBang);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("LB001") @QueryParam("maLoaiBang") String maLoaiBang){
        try {
            System.out.print("\n LOAIBANG/ValidationID - ");
            String s = db.ValidationID(maLoaiBang).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIBANG");
        }
    }
}
