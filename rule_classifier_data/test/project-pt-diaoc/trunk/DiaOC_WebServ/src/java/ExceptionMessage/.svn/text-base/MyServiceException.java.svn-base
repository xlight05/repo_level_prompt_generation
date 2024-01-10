/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExceptionMessage;

/**
 *
 * @author Wjnd_Field
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;
 
/** Throw this exception to return a 401 Unauthorized response.  The WWW-Authenticate header is
 * set appropriately and a short message is included in the response entity. */
public class MyServiceException extends WebApplicationException
{
    private static final long serialVersionUID = 1L;
 
    public MyServiceException()
    {
        this("Please authenticate.", "Name of your web service");
    }
 
    public MyServiceException(String message, String realm)
    {
        super(Response.status(499).header(HttpHeaders.WWW_AUTHENTICATE,
                "Basic realm =\"" + realm + "\"").entity(message).build());
    }
}
