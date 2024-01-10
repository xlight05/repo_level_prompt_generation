package org.jsemantic.services.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


public class MVCExceptionResolver implements HandlerExceptionResolver {
    private static final String ERROR_VIEW = "default";
    
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg, Exception exception) {
        // return the Error Page view in case of any unhandled exception
        if (exception != null) {

            Map<String, Exception> model = new HashMap<String, Exception>();
            model.put("MVCExecutionException", exception);
            return new ModelAndView(ERROR_VIEW, model);
        } else {
            return null;
        }
    }
}
