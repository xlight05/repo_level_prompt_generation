/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ChiTietBaoLogic;
import Entities.CHITIETBAO;
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
@Path("CHITIETBAO")
public class CHITIETBAO_Resource {

    @Context
    private UriInfo context;
    ChiTietBaoLogic db;

    /**
     * Creates a new instance of CHITIETBAO_Resource
     */
    public CHITIETBAO_Resource() throws MyServiceException {
        try{
            db = new ChiTietBaoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    /**
     * Retrieves representation of an instance of Service.CHITIETBAO_Resource
     * @return an instance of java.lang.String
     */
    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<CHITIETBAO> GetAllRows(){
        try {
            ArrayList<CHITIETBAO> list = db.GetAllRows();
            System.out.println("CHITIETBAO/GetAllRows Success");
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao,
        @DefaultValue("MB001") @QueryParam("maBao") String maBao){
        try {
            String s = db.Insert(maQCBao, maBao);
            System.out.println("CHITIETBAO/Insert Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("CTB004") @QueryParam("maCT") String maCT,
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao,
        @DefaultValue("MB001") @QueryParam("maBao") String maBao){
        try {
            String s = db.Update(maCT, maQCBao, maBao);
            System.out.println("CHITIETBAO/Update Success");
            return s;
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("CTB006") @QueryParam("maCT") String maCT){
        try {
            String s = db.Delete(maCT).toString();
            System.out.println("CHITIETBAO/Delete Success");
            return s;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public CHITIETBAO GetRowByID(
        @DefaultValue("CTB001") @QueryParam("maCT") String maCT){
        try {
            CHITIETBAO ct = db.GetRowByID(maCT);
            System.out.println("CHITIETBAO/GetRowByID Success");
            return ct;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("CTB003") @QueryParam("maCT") String maCT){
        try {
            String s = db.ValidationID(maCT).toString();
            System.out.println("CHITIETBAO/ValidationID Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/IsMaBaoExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaBaoExist(
        @DefaultValue("MB001") @QueryParam("maBao") String maBao){
        try {
            String s = db.IsMaBaoExist(maBao).toString();
            System.out.println("CHITIETBAO/IsMaBaoExist Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/IsMaQCBaoExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaQCBaoExist(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao){
        try {
            String s = db.IsMaQCBaoExist(maQCBao).toString();
            System.out.println("CHITIETBAO/IsMaQCBaoExist Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
    
    @Path("/GetMaBaoByMaQCBao")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<String> GetMaBaoByMaQCBao(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao){
        try {
            ArrayList<String> list = db.GetMaBaoByMaQCBao(maQCBao);
            System.out.println("CHITIETBAO/GetMaBaoByMaQCBao Success");
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "CHITIETBAO");
        }
    }
}
