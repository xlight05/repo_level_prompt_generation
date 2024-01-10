/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.LoaiDiaOCLogic;
import Entities.LOAIDIAOC;
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
@Path("LOAIDIAOC")
public class LOAIDIAOC_Resource {

    @Context
    private UriInfo context;
    LoaiDiaOCLogic db;

    /**
     * Creates a new instance of LOAIDIAOC_Resource
     */
    public LOAIDIAOC_Resource() throws MyServiceException {
        try{
            db = new LoaiDiaOCLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<LOAIDIAOC> GetAllRows(){
        try {
            System.out.print("\n LOAIDIAOC/GetAllRows - ");
            ArrayList<LOAIDIAOC> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Chung Cu") @QueryParam("tenLoaiDiaOc") String tenLoaiDiaOc,
        @DefaultValue("CC") @QueryParam("kyHieu") String kyHieu){
        try {
            System.out.print("\n LOAIDIAOC/Insert - ");
            String s = db.Insert(tenLoaiDiaOc, kyHieu);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc,
        @DefaultValue("Vila") @QueryParam("tenLoaiDiaOc") String tenLoaiDiaOc,
        @DefaultValue("VL") @QueryParam("kyHieu") String kyHieu){
        try {
            System.out.print("\n LOAIDIAOC/Update - ");
            String s = db.Update(maLoaiDiaOc, tenLoaiDiaOc, kyHieu);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("LDO004") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n LOAIDIAOC/Delete - ");
            String s = db.Delete(maLoaiDiaOc).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public LOAIDIAOC GetRowByID(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n LOAIDIAOC/GetRowByID - ");
            LOAIDIAOC obj = db.GetRowByID(maLoaiDiaOc);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("LDO004") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n LOAIDIAOC/ValidationID - ");
            String s = db.ValidationID(maLoaiDiaOc).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/GetNameByID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetNameByID(
        @DefaultValue("LDO001") @QueryParam("maLoaiDiaOc") String maLoaiDiaOc){
        try {
            System.out.print("\n LOAIDIAOC/Insert - ");
            String s = db.GetNameByID(maLoaiDiaOc);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
    
    @Path("/IsKyHieuExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsKyHieuExist(
        @DefaultValue("VL") @QueryParam("kyHieu") String kyHieu){
        try {
            System.out.print("\n LOAIDIAOC/ValidationID - ");
            String s = db.IsKyHieuExist(kyHieu).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "LOAIDIAOC");
        }
    }
}
