/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ThamSoLogic;
import Entities.THAMSO;
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
@Path("THAMSO")
public class THAMSO_Resource {

    @Context
    private UriInfo context;
    ThamSoLogic db;

    /**
     * Creates a new instance of THAMSO_Resource
     */
    public THAMSO_Resource() throws MyServiceException {
        try{
            db = new ThamSoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THAMSO> GetAllRows(){
        try {
            System.out.print("\n THAMSO/GetAllRows - ");
            ArrayList<THAMSO> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("GiaTriNhoNhat") @QueryParam("tenThamSo") String tenThamSo,
        @DefaultValue("0") @QueryParam("giaTri") float giaTri,
        @DefaultValue("GiaTriNhoNhat") @QueryParam("giaiThich") String giaiThich){
        try {
            System.out.print("\n THAMSO/Insert - ");
            String s = db.Insert(tenThamSo, giaTri, giaiThich).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("GiaTriNhoNhat") @QueryParam("tenThamSo") String tenThamSo,
        @DefaultValue("0") @QueryParam("giaTri") float giaTri,
        @DefaultValue("GiaTriNhoNhat") @QueryParam("giaiThich") String giaiThich){
        try {
            System.out.print("\n THAMSO/Update - ");
            String s = db.Update(tenThamSo, giaTri, giaiThich).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("GiaTriNhoNhat") @QueryParam("tenThamSo") String tenThamSo){
        try {
            System.out.print("\n THAMSO/Delete - ");
            String s = db.Delete(tenThamSo).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public THAMSO GetRowByID(
        @DefaultValue("GiaTriNhoNhat") @QueryParam("tenThamSo") String tenThamSo){
        try {
            System.out.print("\n THAMSO/GetRowByID - ");
            THAMSO obj = db.GetRowByID(tenThamSo);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }
    
    @Path("/GetThamSoByTen")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public THAMSO GetThamSoByTen(
        @DefaultValue("GiaTriNhoNhat") @QueryParam("tenThamSo") String tenThamSo){
        try {
            System.out.print("\n THAMSO/GetThamSoByTen - ");
            THAMSO obj = db.GetThamSoByTen(tenThamSo);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THAMSO");
        }
    }
}
