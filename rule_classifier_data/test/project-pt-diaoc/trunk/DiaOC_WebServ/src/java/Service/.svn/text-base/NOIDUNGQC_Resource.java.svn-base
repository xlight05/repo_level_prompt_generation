/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.NoiDungLogic;
import Entities.NOIDUNGQC;
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
@Path("NOIDUNGQC")
public class NOIDUNGQC_Resource {

    @Context
    private UriInfo context;
    NoiDungLogic db;

    /**
     * Creates a new instance of NOIDUNGQC_Resource
     */
    public NOIDUNGQC_Resource() throws MyServiceException {
        try{
            db = new NoiDungLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<NOIDUNGQC> GetAllRows(){
        try {
            System.out.print("\n NOIDUNGQC/GetAllRows - ");
            ArrayList<NOIDUNGQC> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("abc") @QueryParam("thongTin") String thongTin,
        @DefaultValue("xyz") @QueryParam("moTa") String moTa){
        try {
            System.out.print("\n NOIDUNGQC/Insert - ");
            String s = db.Insert(thongTin, moTa);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("ND005") @QueryParam("maND") String maND,
        @DefaultValue("abc") @QueryParam("thongTin") String thongTin,
        @DefaultValue("xyz") @QueryParam("moTa") String moTa){
        try {
            System.out.print("\n NOIDUNGQC/Update - ");
            String s = db.Update(maND, thongTin, moTa);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("ND005") @QueryParam("maND") String maND){
        try {
            System.out.print("\n NOIDUNGQC/Delete - ");
            String s = db.Delete(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public NOIDUNGQC GetRowByID(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n NOIDUNGQC/GetRowByID - ");
            NOIDUNGQC obj = db.GetRowByID(maND);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n NOIDUNGQC/ValidationID - ");
            String s = db.ValidationID(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "NOIDUNGQC");
        }
    }
}
