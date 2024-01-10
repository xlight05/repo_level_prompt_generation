function isInstanceOf(obj, cls) {
	if (!obj) {
		return false;
	}
	if (obj instanceof cls) {
		return true;
	}
	var ifs = obj['implements'];
	return ifs&&ifs.indexOf(cls)>=0;
};

function checkCast(obj, cls) {
	if (!isInstanceOf(obj, cls)) {
		var ex = loadClass("java/lang/ClassCastException", true) || new J.error("Unable to cast "+obj+" to "+cls);
		throw new ex();
	}
};