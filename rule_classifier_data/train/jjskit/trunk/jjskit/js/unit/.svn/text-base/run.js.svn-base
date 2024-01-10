function runTests(tests){
	var failed = [];
	var skiped = [];
	var passed = [];
	for (var i=tests.length;i--;) {
		var name = tests[i].name;
		try {
			J.ln(-1);
			var asrt = new J.assert();
			tests[i].fn(asrt);
			var custom = asrt.getCustomTraces();
			passed.push({
				name:name,
				message:asrt.getMessage(),
				desc:custom?'<div>'+custom.join('</div><div>')+'</div>':''
			});
		} catch (e) {
			if (!(e instanceof J.assert)) {
				var e = new J.error(e);
				failed.push({
					name:name,
					message:String(e.message),
					desc:'<div>'+e.stack.join('</div><div>')+'</div>'
				});
			}
			else {
				var custom = asrt.getCustomTraces();
				custom = custom?'<div>Custom traces:</div><div>'+custom.join('</div><div>')+'</div>':'';
				if (e.isSkiped()) {
					skiped.push({
						name:name,
						message:e.getMessage(),
						desc:'<div>'+e.getTraces().join('</div><div>')+'</div>'+custom
					});
				} else {
					failed.push({
						name:name,
						message:e.getMessage(),
						desc:'<div>'+e.getTraces().join('</div><div>')+'</div>'+custom
					});
				}
			}
		}
		J.ln(-1);
	}
	return {
		failed:failed,
		skiped:skiped,
		passed:passed,
		total:skiped.length+passed.length+failed.length
	};
};