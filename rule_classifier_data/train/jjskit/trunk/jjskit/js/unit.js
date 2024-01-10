J.unit = function(suite, run, name, rez) {
	if (typeof run == 'undefined') {
		run = true;
	}
	if (typeof suite == 'boolean' && arguments.length == 1) {
		run = suite;
		suite = null;
	}
	suite = suite||window;
	rez = rez||[];
	name = name||'';
	var suiteRegExp = /(^\w+test)|(^\w+testcase)/i;
	for (var k in suite) {
		var isSuite = suite[k]&&suiteRegExp.test(k);
		if (isSuite && suite[k] instanceof Function) {
			rez.push({
				name:name+k, 
				fn:suite[k]
			});
			continue;
		} else if ((name || isSuite) && typeof(suite[k])==='object') {
			J.unit(suite[k], false, name+k+'.', rez);
		}
	}
	if (!run) {
		return rez;
	}
	
	// @Include unit/assert.js
	
	// @Include unit/run.js
	// @Include unit/print.js
	J.unit.result = runTests(rez);
	printTestResults(J.unit.result);
};