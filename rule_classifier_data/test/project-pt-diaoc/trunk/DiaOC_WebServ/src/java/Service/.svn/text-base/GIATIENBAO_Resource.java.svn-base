/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.GiaTienQCBaoLogic;
import Entities.GIATIEN_QC_BAO;
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
@Path("GIATIENBAO")
public class GIATIENBAO_Resource {
    
    @Context
    private UriInfo context;
    GiaTienQCBaoLogic db;

    /**
     * Creates a new instance of GIATIENBANG_Resource
     */
    public GIATIENBAO_Resource() throws MyServiceException {
        try{
            db = new GiaTienQCBaoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<GIATIEN_QC_BAO> GetAllRows(){
        try {
            System.out.print("\n GIATIEN_QC_BAO/GetAllRows - ");
            ArrayList<GIATIEN_QC_BAO> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("Giua") @QueryParam("viTri") String viTri,
        @DefaultValue("A2") @QueryParam("khoIn") String khoIn,
        @DefaultValue("xanh") @QueryParam("mauSac") String mauSac,
        @DefaultValue("!") @QueryParam("ghiChu") String ghiChu,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_BAO/Insert - ");
            String s = db.Insert(viTri, khoIn, mauSac, ghiChu, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("GTB003") @QueryParam("maGiaTien") String maGiaTien,    
        @DefaultValue("Giua") @QueryParam("viTri") String viTri,
        @DefaultValue("A2") @QueryParam("khoIn") String khoIn,
        @DefaultValue("Den") @QueryParam("mauSac") String mauSac,
        @DefaultValue("Nhat") @QueryParam("ghiChu") String ghiChu,
        @DefaultValue("1000") @QueryParam("giaTien") Float giaTien){
        try {
            System.out.print("\n GIATIEN_QC_BAO/Update - ");
            String s = db.Update(maGiaTien, viTri, khoIn, mauSac, ghiChu, giaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("GTB004") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BAO/Delete - ");
            String s = db.Delete(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public GIATIEN_QC_BAO GetRowByID(
        @DefaultValue("GTB001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BAO/GetRowByID - ");
            GIATIEN_QC_BAO obj = db.GetRowByID(maGiaTien);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("GTB001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n GIATIEN_QC_BAO/ValidationID - ");
            String s = db.ValidationID(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "GIATIEN_QC_BAO");
        }
    }
}
