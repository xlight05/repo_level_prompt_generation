/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.QCBangLogic;
import Entities.QC_BANG;
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
@Path("QCBANG")
public class QCBANG_Resource {

    @Context
    private UriInfo context;
    QCBangLogic db;

    /**
     * Creates a new instance of QCBANG_Resource
     */
    public QCBANG_Resource() throws MyServiceException {
        try{
            db = new QCBangLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<QC_BANG> GetAllRows(){
        try {
            System.out.print("\n QCBANG/GetAllRows - ");
            ArrayList<QC_BANG> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBANG/Insert - ");
            String s = db.Insert(maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("QCG001") @QueryParam("maQCBang") String maQCBang,
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBANG/Update - ");
            String s = db.Update(maQCBang, maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("QCG001") @QueryParam("maQCBang") String maQCBang){
        try {
            System.out.print("\n QCBANG/Delete - ");
            String s = db.Delete(maQCBang).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public QC_BANG GetRowByID(
        @DefaultValue("QCG001") @QueryParam("maQCBang") String maQCBang){
        try {
            System.out.print("\n QCBANG/GetRowByID - ");
            QC_BANG obj = db.GetRowByID(maQCBang);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("QCG001") @QueryParam("maQCBang") String maQCBang){
        try {
            System.out.print("\n QCBANG/ValidationID - ");
            String s = db.ValidationID(maQCBang).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/IsMaND_BangExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaND_BangExist(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n QCBANG/IsMaND_BangExist - ");
            String s = db.IsMaND_BangExist(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/IsMaPhieuDK_BangExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaPhieuDK_BangExist(
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n QCBANG/IsMaPhieuDK_BangExist - ");
            String s = db.IsMaPhieuDK_BangExist(maPhieuDK).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/IsMaGiaTien_BangExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaGiaTien_BangExist(
        @DefaultValue("GTG001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBANG/IsMaGiaTien_BangExist - ");
            String s = db.IsMaGiaTien_BangExist(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
    
    @Path("/GetQCBangByMaPhieuDK")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public QC_BANG GetQCBangByMaPhieuDK(
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n QCBANG/GetQCBangByMaPhieuDK - ");
            QC_BANG obj = db.GetQCBangByMaPhieuDK(maPhieuDK);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBANG");
        }
    }
}
