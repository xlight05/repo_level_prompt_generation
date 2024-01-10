package br.com.dyad.infrastructure.aspect;

import java.lang.reflect.Field;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.navigation.admin.security.SecurityUtil;
import br.com.dyad.infrastructure.widget.BaseAction;
import br.com.dyad.infrastructure.widget.BaseServerWidget;
import br.com.dyad.infrastructure.widget.Window;

public aspect DisableActionAspect {
	//Usado para setar a propriedade enabled = false de uma action quando o usuário não tiver permissão 
	pointcut alterFieldPointcut() : set(* br.com.dyad.infrastructure.widget.BaseServerWidget+.*);
	
	after() : alterFieldPointcut() {
		Object action = thisJoinPoint.getArgs()[0];
		BaseServerWidget target = (BaseServerWidget)thisJoinPoint.getTarget();
		
		if (action != null && target != null && ReflectUtil.inheritsFrom(action.getClass(), BaseAction.class)){			
			Field fld = ReflectUtil.getDeclaredField(target.getClass(), thisJoinPoint.getSignature().getName());
			Long windowId = Window.getWindowId(target.getClass());			
			
			if (windowId != null){
				Authorization auth = (Authorization)fld.getAnnotation(Authorization.class);
				if (auth != null){					
					String permission = auth.permissionFieldName();
					
					if (!permission.equals("")){
						try {						
							Boolean hasPermission = SecurityUtil.userHasPermission(UserInfoAspect.getDatabase(), UserInfoAspect.getUserKey(), windowId, permission);
							
							if (hasPermission != null && !hasPermission){
								((BaseAction)action).setEnabled(false);
							}
						} catch (Exception e) {
							throw new RuntimeException(e);
						}										
					}
				}
			}
		}
				
	}
}
