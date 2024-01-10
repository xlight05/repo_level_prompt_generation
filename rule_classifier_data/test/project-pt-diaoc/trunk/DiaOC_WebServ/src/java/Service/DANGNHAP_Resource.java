/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.DangNhapLogic;
import Entities.DANGNHAP;
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
@Path("DANGNHAP")
public class DANGNHAP_Resource {

    @Context
    private UriInfo context;
    DangNhapLogic db;

    /**
     * Creates a new instance of DANGNHAP_Resource
     */
    public DANGNHAP_Resource() throws MyServiceException {
        try{
            db = new DangNhapLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<DANGNHAP> GetAllRows(){
        try {
            ArrayList<DANGNHAP> list = db.GetAllRows();
            System.out.println("DANGNHAP/GetAllRows Success");
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("duy") @QueryParam("user") String user,
        @DefaultValue("duy") @QueryParam("pass") String pass){
        try {
            String s = db.Insert(user, pass).toString();
            System.out.println("DANGNHAP/Insert Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("3") @QueryParam("id") int id,
        @DefaultValue("abc") @QueryParam("user") String user,
        @DefaultValue("abc") @QueryParam("pass") String pass){
        try {
            String s = db.Update(id, user, pass).toString();
            System.out.println("DANGNHAP/Update Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("1") @QueryParam("id") int id){
        try {
            String s = db.Delete(id).toString();
            System.out.println("DANGNHAP/Delete Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }
    
    @Path("/CheckDangNhap")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String CheckDangNhap(
        @DefaultValue("admin") @QueryParam("user") String user,
        @DefaultValue("admin") @QueryParam("pass") String pass){
        try {
            String s = db.CheckDangNhap(user, pass).toString();
            System.out.println("DANGNHAP/CheckDangNhap Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }
    
    @Path("/IsExistUserName_DangNhap")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsExistUserName_DangNhap(
        @DefaultValue("admin") @QueryParam("user") String user){
        try {
            String s = db.IsExistUserName_DangNhap(user).toString();
            System.out.println("DANGNHAP/IsExistUserName_DangNhap Success");
            return s;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "DANGNHAP");
        }
    }
}
