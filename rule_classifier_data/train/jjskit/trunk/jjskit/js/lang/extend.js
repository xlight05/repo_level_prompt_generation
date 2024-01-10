function extend(createFn, superClass) {
	if (superClass) {
		var F = function(){};
		F.prototype = superClass.prototype || superClass;
		createFn.prototype = new F();
		createFn.prototype.constructor = createFn;
		createFn['super'] = superClass.prototype || superClass;
	}
	if (arguments.length>2) {
		var iFaces = createFn.prototype['implements'];
		if (!iFaces) {
			iFaces = [];
			createFn.prototype['implements'] = iFaces;
		}
		iFaces.push.apply(iFaces,Array.prototype.slice.call(arguments, 2));
	}
	return createFn;
};

function apply(obj, src) {
	if (!src) return obj;
	for (var k in src) {
		obj[k] = src[k];
	}
	return obj;
};