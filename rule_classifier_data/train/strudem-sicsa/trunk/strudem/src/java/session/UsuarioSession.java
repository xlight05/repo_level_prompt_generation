/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import bean.UsuarioBean;

/**
 *
 * @author Yoniffer
 */
public class UsuarioSession {

    public static UsuarioBean getUser() {
        UsuarioBean u;
        Map session = ActionContext.getContext().getSession();
        u = (UsuarioBean) session.get("usuario");
        return u;
    }
}
