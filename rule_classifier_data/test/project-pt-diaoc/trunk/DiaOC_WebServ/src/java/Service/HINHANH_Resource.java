/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.HinhAnhLogic;
import Entities.HINHANH;
import ExceptionMessage.MyServiceException;
import java.sql.Date;
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
@Path("HINHANH")
public class HINHANH_Resource {

    @Context
    private UriInfo context;
    HinhAnhLogic db;

    /**
     * Creates a new instance of HINHANH_Resource
     */
    public HINHANH_Resource() throws MyServiceException {
        try{
            db = new HinhAnhLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<HINHANH> GetAllRows(){
        try {
            System.out.print("\n HINHANH/GetAllRows - ");
            ArrayList<HINHANH> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("/img") @QueryParam("anh") String anh,
        @DefaultValue("hinh") @QueryParam("moTa") String moTa,
        @DefaultValue("1382202000000") @QueryParam("thoiGianChupAnh") long thoiGianChupAnh,
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n HINHANH/Insert - ");
            Date d = new Date(thoiGianChupAnh);
            String s = String.valueOf(db.Insert(anh, moTa, d, maND));
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("10") @QueryParam("maHinhAnh") int maHinhAnh,    
        @DefaultValue("/img") @QueryParam("anh") String anh,
        @DefaultValue("hinh") @QueryParam("moTa") String moTa,
        @DefaultValue("1382202000000") @QueryParam("thoiGianChupAnh") long thoiGianChupAnh,
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n HINHANH/Update - ");
            Date d = new Date(thoiGianChupAnh);
            int i = db.Update(maHinhAnh, anh, moTa, d, maND);
            System.out.print("Success");
            return String.valueOf(i);
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("11") @QueryParam("maHinhAnh") int maHinhAnh){
        try {
            System.out.print("\n HINHANH/Delete - ");
            String s = db.Delete(maHinhAnh).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public HINHANH GetRowByID(
        @DefaultValue("1") @QueryParam("maHinhAnh") int maHinhAnh){
        try {
            System.out.print("\n HINHANH/GetRowByID - ");
            HINHANH obj = db.GetRowByID(maHinhAnh);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("1") @QueryParam("maHinhAnh") int maHinhAnh){
        try {
            System.out.print("\n HINHANH/ValidationID - ");
            String s = db.ValidationID(maHinhAnh).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/IsMaND_HinhAnhExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaND_HinhAnhExist(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n HINHANH/IsMaND_HinhAnhExist - ");
            String s = db.IsMaND_HinhAnhExist(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
    
    @Path("/SelectHinhAnhByMaND")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<HINHANH> SelectHinhAnhByMaND(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n HINHANH/SelectHinhAnhByMaND - ");
            ArrayList<HINHANH> list = db.SelectHinhAnhByMaND(maND);
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "HINHANH");
        }
    }
}
