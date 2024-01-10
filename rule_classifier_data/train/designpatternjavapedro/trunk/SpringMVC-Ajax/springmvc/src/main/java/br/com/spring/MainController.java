package br.com.spring;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.spring.entity.Usuario;

@Controller()
public class MainController {
	
	/**
	 * Este metodo recebe um nome como parametro e retorna um objeto Usuario.
	 * A view de retorno do Spring MVC (org.springframework.web.servlet.view.xml.MarshallingView) automaticamente converte este objeto para XML
	 * como resposta do request. Eh utilizado o JAXB para fazer esta conversao para XML
	 * @param name
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/{name}",method=RequestMethod.GET)
	private @ResponseBody Usuario getWelcome( @PathVariable String name,HttpServletResponse response){
		Usuario user = new Usuario();
		user.setName(name+ " Aoki ");
		return user;
		
	}
	
	
	

}
