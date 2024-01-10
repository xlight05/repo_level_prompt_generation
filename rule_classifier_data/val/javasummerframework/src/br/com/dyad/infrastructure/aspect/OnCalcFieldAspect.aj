package br.com.dyad.infrastructure.aspect;

import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.grid.Grid;


public aspect OnCalcFieldAspect {
	pointcut callCalcMethod() : call(* br.com.dyad.infrastructure.widget.field.Field+.setValue*(..));
	pointcut callSetReadOnly() : call(* br.com.dyad.infrastructure.widget.field.Field+.setIsCalculated(Boolean));

	after() : callCalcMethod(){
		Field fld = (Field)thisJoinPoint.getTarget();		
		
		if (fld != null && (fld.getIsCalculated() == null || !fld.getIsCalculated())){
			Grid grid = fld.getGrid();
			
			if (grid != null && grid.getFields() != null && grid.getFields().size() > 0){				
				for (Field temp : grid.getFields()){
					if (temp.getIsCalculated() != null && temp.getIsCalculated()){
						temp.onCalcField();
					}
				}
			}
		}
	}
	
	before() : callSetReadOnly(){
		Field fld = (Field)thisJoinPoint.getTarget();
		Object[] objs = thisJoinPoint.getArgs();
		if (objs.length > 0 && (objs[0] == null || !((Boolean)objs[0]))){			
			if ( fld.getReadOnly() != null && fld.getReadOnly() && fld.getIsCalculated() != null && fld.getIsCalculated()){
				fld.setReadOnly(false);
				fld.setReadOnlyFromClass(false);
			}
		}
	}
	
	after() : callSetReadOnly(){
		Field fld = (Field)thisJoinPoint.getTarget();		
		if (fld.getIsCalculated() != null && fld.getIsCalculated()){
			fld.setReadOnly(true);
			fld.setReadOnlyFromClass(true);
		}
	}
}
