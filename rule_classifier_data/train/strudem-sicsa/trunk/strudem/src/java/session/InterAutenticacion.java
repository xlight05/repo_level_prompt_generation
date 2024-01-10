/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

/**
 *
 * @author Yoniffer
 */
import bean.UsuarioBean;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;

public class InterAutenticacion implements Interceptor {

 
    @Override
  public void init() { }
    @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception {//cuerpo del interceptor
      Map session = actionInvocation.getInvocationContext().getSession();//Obtenemos la session
      UsuarioBean usuario = (UsuarioBean) session.get("usuario");//Creamos un objeto usuario y le asignamos el usuario que se encuentre en session
      if (usuario == null) {
          return Action.LOGIN;//si no existe ningun usuario en sesion lo regresamos al login
      } else {
          Action action = (Action) actionInvocation.getAction();//indica que obtenemos la accion que se intenta ejecutar y si tiene permiso el usuario
          if (action instanceof UsuarioHabilitado) {//pasamos la referencia del objeto usuario a la accion para que la pueda usar, para eso hacemos uso de la interface UsuarioHabilitado
              ((UsuarioHabilitado) action).setUsuario(usuario);//le pasamos el objeto usuario
          }
          return actionInvocation.invoke();
      }
  }

    @Override
    public void destroy() {
        
    }
}
