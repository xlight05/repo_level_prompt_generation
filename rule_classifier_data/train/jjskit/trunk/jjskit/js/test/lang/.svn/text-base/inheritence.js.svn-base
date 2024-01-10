jjsTest.lang.inheritenceTest = function(tst) {
	J.ln(2,arguments);
	(function() {
		J.ln(4,arguments);
		var Animal = window.Animal = function(name) {
			this.name = name;
		    this.canWalk = true;
		    var inner = 'aaa';

			this['@'] = function(varName) {
		        return eval(varName);
		    };
		};
		Animal['class'] = 'Animal';
		Animal['@'] = function(varName) {
			try{
	        return eval(varName);
			}catch(e){}
	    };
		var zz = 'sss';
		Animal.prototype.myMethod = function(){
		};
		J.ln(23,arguments);
	})();
	var iFace = function(){};
	var Rabbit = J.ext(function (name) {
		Rabbit['super'].constructor.call(this,name);
		this.name = name;
	}, Animal, iFace);
	J.ln(30,arguments);
	big = new Rabbit('Chuk');
	small = new Rabbit('Gek');
		 
	tst.assertEquals('Chuk',big.name);
	tst.assertEquals('Gek',small.name);

	J.ln(36,arguments);
	tst.assertTrue(big instanceof Animal);
	tst.assertTrue(big instanceof Rabbit);
	tst.assertFalse(big instanceof iFace);
	tst.assertTrue(J.is(big,Animal));
	tst.assertTrue(J.is(big,Rabbit));
	tst.assertTrue(J.is(big,iFace));
	
	J.ln(45,arguments);
	tst.assertEquals("aaa", big['@']('inner'));
	tst.assertEquals("sss", big['@']('zz'));
	tst.assertUndefined(Animal['@']('inner'));
	tst.assertEquals("sss", Animal['@']('zz'));
};