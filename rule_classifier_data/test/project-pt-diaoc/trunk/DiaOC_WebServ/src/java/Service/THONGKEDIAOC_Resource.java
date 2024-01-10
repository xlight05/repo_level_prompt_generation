/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ThongKeDiaOcLogic;
import Entities.THONGKEDIAOC;
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
@Path("THONGKEDIAOC")
public class THONGKEDIAOC_Resource {

    @Context
    private UriInfo context;
    ThongKeDiaOcLogic db;

    /**
     * Creates a new instance of THONGKEDIAOC_Resource
     */
    public THONGKEDIAOC_Resource() throws MyServiceException {
        try{
            db = new ThongKeDiaOcLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKEDIAOC> GetAllRows(){
        try {
            System.out.print("\n THONGKEDIAOC/GetAllRows - ");
            ArrayList<THONGKEDIAOC> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKEDIAOC");
        }
    }
    
    @Path("/GetRowsByMaKH")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKEDIAOC> GetRowsByMaKH(
        @DefaultValue("KH001") @QueryParam("maKH") String maKH){
        try {
            System.out.print("\n THONGKEDIAOC/GetRowsByMaKH - ");
            ArrayList<THONGKEDIAOC> list = db.GetRowsByMaKH(maKH);
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKEDIAOC");
        }
    }
    
    @Path("/GetRowsByHoTen")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKEDIAOC> GetRowsByHoTen(
        @DefaultValue("Duy") @QueryParam("hoTen") String hoTen){
        try {
            System.out.print("\n THONGKEDIAOC/GetRowsByHoTen - ");
            ArrayList<THONGKEDIAOC> list = db.GetRowsByHoTen(hoTen);
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKEDIAOC");
        }
    }
    
    @Path("/GetRowsByTrangThai")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKEDIAOC> GetRowsByTrangThai(
        @DefaultValue("Chua Ban") @QueryParam("trangThai") String trangThai){
        try {
            System.out.print("\n THONGKEDIAOC/GetRowsByTrangThai - ");
            ArrayList<THONGKEDIAOC> list = db.GetRowsByTrangThai(trangThai);
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKEDIAOC");
        }
    }
}
