/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Business.QCBaoLogic;
import Entities.QC_BAO;
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
@Path("QCBAO")
public class QCBAO_Resource {

    @Context
    private UriInfo context;
    QCBaoLogic db;

    /**
     * Creates a new instance of QCBAO_Resource
     */
    public QCBAO_Resource() throws MyServiceException {
        try{
            db = new QCBaoLogic();
        }catch(Exception ex){
            throw new MyServiceException(ex.getMessage(), "SQL ERROR");
        }
    }

    @Path("/GetAllRows")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<QC_BAO> GetAllRows(){
        try {
            System.out.print("\n QCBAO/GetAllRows - ");
            ArrayList<QC_BAO> list = db.GetAllRows();
            System.out.print("Success ");
            return list;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }

    @Path("/Insert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Insert(
        @DefaultValue("1382202000000") @QueryParam("ngayBatDauPhatHanh") long ngayBatDauPhatHanh,
        @DefaultValue("false") @QueryParam("hinhAnh") boolean hinhAnh,
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTB001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBAO/Insert - ");
            Date d = new Date(ngayBatDauPhatHanh);
            String s = db.Insert(d, hinhAnh, maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/Update")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Update(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao,
        @DefaultValue("1382202000000") @QueryParam("ngayBatDauPhatHanh") long ngayBatDauPhatHanh,
        @DefaultValue("false") @QueryParam("hinhAnh") boolean hinhAnh,
        @DefaultValue("ND001") @QueryParam("maND") String maND,
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK,
        @DefaultValue("GTB001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBAO/Update - ");
            Date d = new Date(ngayBatDauPhatHanh);
            String s = db.Update(maQCBao, d, hinhAnh, maND, maPhieuDK, maGiaTien);
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/Delete")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Delete(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao){
        try {
            System.out.print("\n QCBAO/Delete - ");
            String s = db.Delete(maQCBao).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/GetRowByID")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public QC_BAO GetRowByID(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao){
        try {
            System.out.print("\n QCBAO/GetRowByID - ");
            QC_BAO obj = db.GetRowByID(maQCBao);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/ValidationID")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ValidationID(
        @DefaultValue("QCB001") @QueryParam("maQCBao") String maQCBao){
        try {
            System.out.print("\n QCBAO/ValidationID - ");
            String s = db.ValidationID(maQCBao).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/IsMaND_BaoExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaND_BaoExist(
        @DefaultValue("ND001") @QueryParam("maND") String maND){
        try {
            System.out.print("\n QCBAO/IsMaND_BaoExist - ");
            String s = db.IsMaND_BaoExist(maND).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/IsMaPhieuDK_BaoExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaPhieuDK_BangExist(
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n QCBAO/IsMaPhieuDK_BaoExist - ");
            String s = db.IsMaPhieuDK_BaoExist(maPhieuDK).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/IsMaGiaTien_BaoExist")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String IsMaGiaTien_BangExist(
        @DefaultValue("GTB001") @QueryParam("maGiaTien") String maGiaTien){
        try {
            System.out.print("\n QCBAO/IsMaGiaTien_BaoExist - ");
            String s = db.IsMaGiaTien_BaoExist(maGiaTien).toString();
            System.out.print("Success");
            return s;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
    
    @Path("/GetQCBaoByMaPhieuDK")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public QC_BAO GetQCBaoByMaPhieuDK(
        @DefaultValue("PDK001") @QueryParam("maPhieuDK") String maPhieuDK){
        try {
            System.out.print("\n QCBAO/GetQCBaoByMaPhieuDK - ");
            QC_BAO obj = db.GetQCBaoByMaPhieuDK(maPhieuDK);
            System.out.print("Success");
            return obj;
        } catch (Exception ex) {
            System.out.print("Failed : " + ex.getMessage());
            throw new MyServiceException(ex.getMessage(), "QCBAO");
        }
    }
}
