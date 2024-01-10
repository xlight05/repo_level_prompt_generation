/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import ExceptionMessage.MyServiceException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Wjnd_Field
 */
@Path("HOST")
public class HOST_Resource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HOST_Resource
     */
    public HOST_Resource() {
    }

    @Path("/CheckHost")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String CheckHost() {
        return "success";
    }
}
