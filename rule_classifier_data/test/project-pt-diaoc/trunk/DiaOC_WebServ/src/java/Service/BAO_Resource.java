/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Entities.BAO;
import Business.BaoLogic;
import ExceptionMessage.MyServiceException;
import java.util.ArrayList;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

/**
 * REST Web Service
 *
 * @author Wjnd_Field
 */
@Path("BAO")
public class BAO_Resource {

    @Context
    private UriInfo context;
    BaoLogic db;
    
    /*
     * Creates a new instance of BAO_Resource
     */
    public BAO_Resource() throws MyServiceException {
        try{
            db = new BaoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }
 
    /**
     * Retrieves representation of an instance of Service.BAO_Resource
     * @return an instance of java.lang.String
     */
    
    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<BAO> GetAllRows(){
        try {
            ArrayList<BAO> list = db.GetAllRows();
            return list;
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("ABC") @QueryParam("TenBao") String Tenbao,
        @DefaultValue("1") @QueryParam("Thu") int Thu){
        try {
            String s = db.Insert(Tenbao, Thu);
            return s;
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("MB001") @QueryParam("MaBao") String MaBao,
        @DefaultValue("ABC") @QueryParam("TenBao") String Tenbao,
        @DefaultValue("1") @QueryParam("Thu") int Thu){
        try {
            String s = db.Update(MaBao, Tenbao, Thu);
            return s;
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("MB004") @QueryParam("MaBao") String MaBao){
        try {
            return db.Delete(MaBao).toString();
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public BAO GetRowByID(
        @DefaultValue("MB001") @QueryParam("MaBao") String MaBao){
        try {
            BAO b = db.GetRowByID(MaBao);
            return b;
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("MB005") @QueryParam("MaBao") String MaBao){
        try {
            return db.ValidationID(MaBao).toString();
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
    
    @Path("/GetNgayPHByTenBao")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetNgayPHByTenBao(
        @DefaultValue("Tuoi Tre") @QueryParam("TenBao") String tenBao){
        try {
            return String.valueOf(db.GetNgayPHByTenBao(tenBao));
        } catch (Exception ex) {
            throw new MyServiceException(ex.getMessage(), "Bao");
        }
    }
}
