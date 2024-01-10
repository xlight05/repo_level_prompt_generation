var __bootLoader = {};

function loadClass(cls,silent) {
	if (__bootLoader[cls]) {
		return __bootLoader[cls];
	} else {
		var rez = null;
		__bootLoader[cls] = rez;
		if (!rez && !silent) {
			var err = loadClass('java.lang.ClassNotFoundException',true);
			if (!err){
				throw new J.error("No class definition found: "+cls);
			}
			err = new err(cls);
			var init = err['(Ljava/lang/String;)'];
			if(init)init(cls);
			throw err;
		} 
		return rez;
	}
};

