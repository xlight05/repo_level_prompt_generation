/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.ThongKeThangLogic;
import Entities.THONGKETHANG;
import ExceptionMessage.MyServiceException;
import java.util.ArrayList;
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
@Path("THONGKETHANG")
public class THONGKETHANG_Resource {

    @Context
    private UriInfo context;
    ThongKeThangLogic db;

    /**
     * Creates a new instance of THONGKETHANG_Resource
     */
    public THONGKETHANG_Resource() throws MyServiceException {
        try{
            db = new ThongKeThangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows_Bao")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKETHANG> GetAllRows_Bao(){
        try {
            System.out.print("\n THONGKETHANG/GetAllRows_Bao - ");
            ArrayList<THONGKETHANG> list = db.GetAllRows_Bao();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKETHANG");
        }
    }
    
    @Path("/GetAllRows_ToBuom")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKETHANG> GetAllRows_ToBuom(){
        try {
            System.out.print("\n THONGKETHANG/GetAllRows_ToBuom - ");
            ArrayList<THONGKETHANG> list = db.GetAllRows_ToBuom();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKETHANG");
        }
    }
    
    @Path("/GetAllRows_Bang")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<THONGKETHANG> GetAllRows_Bang(){
        try {
            System.out.print("\n THONGKETHANG/GetAllRows_Bang - ");
            ArrayList<THONGKETHANG> list = db.GetAllRows_Bang();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "THONGKETHANG");
        }
    }
}
