/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RSCall;

import Entities.THONGKEDIAOC;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author VooKa
 */
public class ThongKeDiaOcRSc {
    
    String mHost = Utilities.Utilities.host;
    String mPort = Utilities.Utilities.port;
    final Client client = ClientBuilder.newClient();
    final String URI = "http://"+mHost+":"+mPort+"/DiaOC_WebServ/Resources/THONGKEDIAOC/";
    WebTarget THONGKEDIAOC_Resource = client.target(URI);

    //liệt kê tất cả
    public ArrayList<THONGKEDIAOC> GetAllRows()throws Exception{
        try {
            ArrayList<THONGKEDIAOC> l = THONGKEDIAOC_Resource.path("GetAllRows").
                request(MediaType.APPLICATION_XML).get(new  GenericType<ArrayList<THONGKEDIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    //liệt kê theo mã khách hàng
    public ArrayList<THONGKEDIAOC> GetRowsByMaKH(String maKH)throws Exception{
        try {
            ArrayList<THONGKEDIAOC> l = THONGKEDIAOC_Resource.path("GetRowsByMaKH").
                    queryParam("maKH",maKH).request(MediaType.APPLICATION_XML).
                    get(new  GenericType<ArrayList<THONGKEDIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    //liệt kê theo họ tên
    public ArrayList<THONGKEDIAOC> GetRowsByHoTen(String hoTen)throws Exception{
        try {
            ArrayList<THONGKEDIAOC> l = THONGKEDIAOC_Resource.path("GetRowsByHoTen").
                    queryParam("hoTen",hoTen).request(MediaType.APPLICATION_XML).
                    get(new  GenericType<ArrayList<THONGKEDIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }

    //liệt kê theo trạng thái
    public ArrayList<THONGKEDIAOC> GetRowsByTrangThai(String trangThai)throws Exception{
        try {
            ArrayList<THONGKEDIAOC> l = THONGKEDIAOC_Resource.path("GetRowsByTrangThai").
                    queryParam("trangThai",trangThai).request(MediaType.APPLICATION_XML).
                    get(new  GenericType<ArrayList<THONGKEDIAOC>>(){});
            return l;
        } catch (WebApplicationException we){
            throw new Exception(we.getMessage());
        }
    }
}
