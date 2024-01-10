package br.com.dyad.infrastructure.aspect;

import java.util.Vector;

import br.com.dyad.client.AppException;
import br.com.dyad.infrastructure.widget.BaseServerWidget;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;

public aspect ExecuteWidgetListenerAspect {
	
	pointcut callWidgetListener() : call(* br.com.dyad.infrastructure.widget.BaseServerWidget+.on*(..));	
	
	before() : callWidgetListener() {
		String methodName = thisJoinPoint.getSignature().getName();
		System.out.println( "Aspect ExecuteWidgetListener -> " +  methodName );
		BaseServerWidget widget = (BaseServerWidget)thisJoinPoint.getTarget();
		
		if (widget != null && widget.getWidgetListeners() != null){				
			DyadEvents event = null; 
			
			try {
				event = DyadEvents.valueOf(methodName);
			} catch (Exception e1) {
				// quando não existe no enum da erro
			}
			
			if (event != null){				
				Vector<WidgetListener> listeners = widget.getWidgetListeners().get(event);
				
				if (listeners != null){
					for (WidgetListener l : listeners) {					
						try {				
							l.handleEvent(widget);
						} catch (Exception e) {
							if ( e instanceof AppException ){
								throw (AppException)e;
							} else {
								throw new RuntimeException(e);
							}	
						}
					}
				}
			}
		}
	}	

}
