/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ThongKeSoLuongKhachHangLogic;
import ExceptionMessage.MyServiceException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Wjnd_Field
 */
@Path("THONGKESLKHACHHANG")
public class THONGKESLKHACHHANG_Resource {

    @Context
    private UriInfo context;
    ThongKeSoLuongKhachHangLogic db;

    /**
     * Creates a new instance of THONGKESLKHACHHANGResource
     */
    public THONGKESLKHACHHANG_Resource() throws MyServiceException {
        try{
            db = new ThongKeSoLuongKhachHangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetSoLuongKH_Bang")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetSoLuongKH_Bang(){
        try {
            System.out.print("\n THONGKESLKHACHHANG/GetSoLuongKH_Bang - ");
            int i = db.GetSoLuongKH_Bang();
            System.out.print("Success ");
            return String.valueOf(i);
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKESLKHACHHANG");
        }
    }
    
    @Path("/GetSoLuongKH_Bao")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetSoLuongKH_Bao(){
        try {
            System.out.print("\n THONGKESLKHACHHANG/GetSoLuongKH_Bao - ");
            int i = db.GetSoLuongKH_Bao();
            System.out.print("Success ");
            return String.valueOf(i);
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKESLKHACHHANG");
        }
    }
    
    @Path("/GetSoLuongKH_ToBuom")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String GetSoLuongKH_ToBuom(){
        try {
            System.out.print("\n THONGKESLKHACHHANG/GetSoLuongKH_ToBuom - ");
            int i = db.GetSoLuongKH_ToBuom();
            System.out.print("Success ");
            return String.valueOf(i);
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKESLKHACHHANG");
        }
    }
}
