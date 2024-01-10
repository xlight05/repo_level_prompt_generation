jjsTest.dom.elementTest = function(tst) {
	var div = J.div({id:'myDiv'});
	div.render();
	var test = 1;
	var fn1 = function(){test*=2;};
	var fn2 = function(){test*=3;};
	var fn3 = function(){test*=5;};
	div.on('click',fn1);
	div.on('click',fn2);
	div.on('click',fn3);
	
	div.un('click',fn2);
	
	div.fire('click');
	
	tst.assertEquals(10, test);
	
	div.fire('click');
	
	tst.assertEquals(100, test);
	
	div.remove();
};

jjsTest.dom.queryTest = function(tst) {
	tst.trace('start setting up');

	J.ln(28,arguments);
	var div = J.div({id:'top'});
	div.render();
	
	J.ln(32,arguments);
	var p = J.el('p',{id:'p1Id',cls:'test-class test4 test5 test6'});
	p.render(div);

	J.ln(36,arguments);
	var img = J.el('img',{alt:'somesrc]'});
	img.render(p);
	
	J.ln(40,arguments);
	var p2 = J.el('p',{id:'p2Id',cls:'test6'});
	p2.render(div);
	
	J.ln(44,arguments);
	var div2 = J.div({cls:'test2 test3'});
	div2.render(p2);
	
	J.ln(48,arguments);
	var div3 = J.div({cls:'test3'});
	div3.render(p2);
	
	J.ln(52,arguments);
	var div4 = J.div({cls:'test2 test4 test5'});
	div4.render(p2);
	
	J.ln(56,arguments);
	var div5 = J.div({id:'div5Id',cls:'test3 test4'});
	div5.render(p);
	
	J.ln(60,arguments);
	tst.trace('end setting up, start performing tests');

	tst.trace('id selectors');
	tst.assertEquals(div, J('#top'));
	tst.assertEquals(p2, J('#p2Id'));
	tst.assertNot(J('#top .unknown-class'), "Found non-existent element");
	
	tst.trace('attribute selectors');
	
	tst.assertEquals(div, J('*[id=top]'));
	tst.assertEquals(div, J('*[id="top"]'));
	tst.assertEquals(div, J[0]);
	tst.assertEquals(div, J("*[id='top']"));
	tst.assertEquals(img, J("*[alt='somesrc]']"));
	tst.assertEquals(img, J("img[alt='somesrc]']"));
	tst.assertEquals(img, J[0]);
	
	tst.trace('elements selectors');
	var rez1 = J('div');
	tst.assertTrue(rez1 instanceof Array, "Result should be an array");
	tst.assertTrue(rez1.indexOf(div)>-1,"Result should contain top div");
	tst.assertEquals(div, J[0]);
	tst.assertTrue(rez1.indexOf(div5)>-1,"Result should contain div5");
	tst.assertEquals(div5, J[1]);
	tst.assertTrue(rez1.indexOf(div2)>-1,"Result should contain div2");
	tst.assertEquals(div2, J[2]);
	tst.assertTrue(rez1.indexOf(div3)>-1,"Result should contain div3");
	tst.assertEquals(div3, J[3]);
	tst.assertTrue(rez1.indexOf(div4)>-1,"Result should contain div4");
	tst.assertEquals(div4, J[4]);
	tst.assertEquals(5,rez1.length);
	tst.assertEquals(5, J.count);
	
	tst.assertEquals(img, J('img'));
	tst.assertEquals(img, J[0]);
	tst.assertNot(J[1],'Old results should be removed');
	tst.assertEquals(1, J.count);

	var rez2 = J('p > div');
	tst.assertTrue(rez2 instanceof Array, "Result should be an array");
	tst.assertTrue(rez2.indexOf(div2)>-1,"Result should contain div2");
	tst.assertTrue(rez2.indexOf(div3)>-1,"Result should contain div3");
	tst.assertTrue(rez2.indexOf(div4)>-1,"Result should contain div4");
	tst.assertTrue(rez2.indexOf(div5)>-1,"Result should contain div5");
	tst.assertEquals(4,rez2.length);

	var rez3 = J('p:first-child');
	tst.assertTrue(rez3 instanceof Array, "Result should be an array");
	tst.assertTrue(rez3.indexOf(div2)>-1,"Result should contain div2");
	tst.assertTrue(rez3.indexOf(img)>-1,"Result should contain img");
	tst.assertEquals(2,rez3.length);
	
	// @TODOtst.assertEquals(div5, J("img + div"));
	
	tst.assertEquals(p,J('.test-class'));
	tst.assertEquals(p,J('*.test-class'));
	tst.assertEquals(p,J('p.test-class'));
	
	var rez4 = J('.test2');
	tst.assertTrue(rez4 instanceof Array, "Result should be an array");
	tst.assertTrue(rez4.indexOf(div2)>-1,"Result should contain div2");
	tst.assertTrue(rez4.indexOf(div4)>-1,"Result should contain div4");
	tst.assertEquals(2,rez4.length);
	
	var rez5 = J('.test3');
	tst.assertTrue(rez5 instanceof Array, "Result should be an array");
	tst.assertTrue(rez5.indexOf(div2)>-1,"Result should contain div2");
	tst.assertTrue(rez5.indexOf(div3)>-1,"Result should contain div3");
	tst.assertTrue(rez5.indexOf(div5)>-1,"Result should contain div5");
	tst.assertEquals(3,rez5.length);
	
	var rez6 = J('.test4.test5');
	tst.assertTrue(rez6 instanceof Array, "Result should be an array");
	tst.assertTrue(rez6.indexOf(p)>-1,"Result should contain p");
	tst.assertTrue(rez6.indexOf(div4)>-1,"Result should contain div4");
	tst.assertEquals(2,rez6.length);

	tst.assertEquals(div4,J('div.test4.test5'));
	tst.assertEquals(p,J('p.test4.test5'));
	tst.assertEquals(p,J('#p1Id.test4.test5'));
	
	var rez7 = J('.test4,.test5');
	tst.assertTrue(rez7 instanceof Array, "Result should be an array");
	tst.assertTrue(rez7.indexOf(p)>-1,"Result should contain p");
	tst.assertTrue(rez7.indexOf(div4)>-1,"Result should contain div4");
	tst.assertTrue(rez7.indexOf(div5)>-1,"Result should contain div5");
	tst.assertEquals(3,rez7.length);
	
	tst.assertTrue(J('#p1Id.test4.test5', false) instanceof Array, "Result should be an array when 2nd param is false");
	tst.assertTrue(J('#unknownId', false) instanceof Array, "Result should be an array when 2nd param is false");
	tst.assertEquals(0, J('#unknownId', false).length);
	tst.assertEquals(p,J('.test4.test5', true));
	tst.assertEquals(div2,J('.test2', true));
	tst.assertEquals(div2, J[0]);
	tst.assertNot(J[1],'Single object should be stored in results as flag is true');
	tst.assertEquals(1, J.count);
	
	J('p > div.test4.test5,#div5Id,*[class=test6]');
	tst.assertEquals(3,J.count);
	tst.assertEquals(div4,J[0]);
	tst.assertEquals(div5,J[1]);
	tst.assertEquals(p2,J[2]);
	
	div.remove();
};
